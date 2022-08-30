package ua.com.alevel.hw2.dao.mongodb.invoices;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import ua.com.alevel.hw2.annotations.Autowired;
import ua.com.alevel.hw2.annotations.Singleton;
import ua.com.alevel.hw2.config.MongoDBConfig;
import ua.com.alevel.hw2.dao.InvoiceDao;
import ua.com.alevel.hw2.model.cargoplane.CargoPlane;
import ua.com.alevel.hw2.model.fighter.Fighter;
import ua.com.alevel.hw2.model.invoice.Invoice;
import ua.com.alevel.hw2.model.passengerplane.PassengerPlane;
import java.util.*;

@Singleton
public final class InvoiceDatabaseMongoDB implements InvoiceDao {
    private static InvoiceDatabaseMongoDB instance;
    private static final MongoCollection<Document> COLLECTION = MongoDBConfig.getMongoDatabase()
            .getCollection(Invoice.class.getSimpleName());
    private static final MongoCollection<Document> COLLECTION_CARGO_PLANES = MongoDBConfig.getMongoDatabase()
            .getCollection(CargoPlane.class.getSimpleName());
    private static final MongoCollection<Document> COLLECTION_FIGHTERS = MongoDBConfig.getMongoDatabase()
            .getCollection(Fighter.class.getSimpleName());
    private static final MongoCollection<Document> COLLECTION_PASSENGER_PLANE = MongoDBConfig.getMongoDatabase()
            .getCollection(PassengerPlane.class.getSimpleName());
    private static Gson gson;

    @Autowired
    private InvoiceDatabaseMongoDB() {
        gson = new Gson();
    }

    public static InvoiceDatabaseMongoDB getInstance() {
        if (instance == null) {
            instance = new InvoiceDatabaseMongoDB();
        }

        return instance;
    }

    @Override
    public void save(Invoice invoice) {
        COLLECTION.insertOne(Document.parse(gson.toJson(invoice)));
    }

    @Override
    public List<Invoice> getInvoicesExpensiveThanPrice(int price) {
        List<Invoice> invoices = new ArrayList<>();
        COLLECTION.find(Filters.gt("sum", price))
                .map(object -> gson.fromJson(object.toJson(), Invoice.class))
                .into(invoices);

        invoices.forEach(this::identifyPlanes);
        return invoices;
    }

    @Override
    public int getInvoiceCount() {
        return (int) COLLECTION.countDocuments();
    }

    @Override
    public Optional<Invoice> findById(String id) {
        Invoice invoice = COLLECTION.find(Filters.eq("id", id))
                .map(object -> gson.fromJson(object.toJson(), Invoice.class))
                .first();

        return (invoice == null) ? Optional.empty() : Optional.of(identifyPlanes(invoice));
    }

    private Invoice identifyPlanes(Invoice invoice) {
        invoice.setProducts(new ArrayList<>(invoice.getPlanes().size()));
        for (String planeId : invoice.getPlanes()) {
            CargoPlane cargoPlane = COLLECTION_CARGO_PLANES.find(Filters.eq("id", planeId))
                    .map(object -> gson.fromJson(object.toJson(), CargoPlane.class))
                    .first();

            if (cargoPlane != null) {
                invoice.getProducts().add(cargoPlane);
                continue;
            }

            Fighter fighter = COLLECTION_FIGHTERS.find(Filters.eq("id", planeId))
                    .map(object -> gson.fromJson(object.toJson(), Fighter.class))
                    .first();

            if (fighter != null) {
                invoice.getProducts().add(fighter);
                continue;
            }

            PassengerPlane passengerPlane = COLLECTION_PASSENGER_PLANE.find(Filters.eq("id", planeId))
                    .map(object -> gson.fromJson(object.toJson(), PassengerPlane.class))
                    .first();

            if (passengerPlane != null) {
                invoice.getProducts().add(passengerPlane);
            }
        }

        return invoice;
    }

    @Override
    public void update(String id, Date date) {
        Invoice invoice = findById(id).get();
        invoice.setCreated(date);
        COLLECTION.updateOne(Filters.eq("id", id), new Document("$set", Document.parse(gson.toJson(invoice))));
    }

    @Override
    public Map<Integer, Integer> groupBySum() {
        Map<Integer, Integer> query = new HashMap<>();

        COLLECTION.aggregate(List.of(Aggregates.group("$sum", Accumulators.sum("count", 1))))
                .map(object -> gson.fromJson(object.toJson(), JsonObject.class))
                .into(new ArrayList<>())
                .forEach(object -> query.put(object.get("_id").getAsInt(), object.get("count").getAsInt()));

        return query;
    }

    @Override
    public List<Invoice> findAll() {
        List<Invoice> invoices = new ArrayList<>();
        COLLECTION.find()
                .map(object -> gson.fromJson(object.toJson(), Invoice.class))
                .into(invoices);

        invoices.forEach(this::identifyPlanes);
        return invoices;
    }
}
