package ua.com.alevel.hw2.dao.mongodb.products.passengerplanedatabase;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import ua.com.alevel.hw2.annotations.Autowired;
import ua.com.alevel.hw2.annotations.Singleton;
import ua.com.alevel.hw2.config.MongoDBConfig;
import ua.com.alevel.hw2.model.invoice.Invoice;
import ua.com.alevel.hw2.model.passengerplane.PassengerPlane;
import java.util.*;

//@Singleton
public final class PassengerPlaneDatabaseMongoDB implements PassengerPlaneDaoMongoDB {
    private static final MongoCollection<Document> COLLECTION = MongoDBConfig.getMongoDatabase()
            .getCollection(PassengerPlane.class.getSimpleName());
    private static final MongoCollection<Document> COLLECTION_INVOICES = MongoDBConfig.getMongoDatabase()
            .getCollection(Invoice.class.getSimpleName());
    private static Gson gson;
    private static PassengerPlaneDatabaseMongoDB instance;

    //@Autowired
    private PassengerPlaneDatabaseMongoDB() {
        gson = new Gson();
    }

    public static PassengerPlaneDatabaseMongoDB getInstance() {
        if (instance == null) {
            instance = new PassengerPlaneDatabaseMongoDB();
        }

        return instance;
    }

    @Override
    public void save(PassengerPlane plane) {
        COLLECTION.insertOne(Document.parse(gson.toJson(plane)));
    }

    @Override
    public void saveAll(PassengerPlane[] planes) {
        List<Document> passengerPlanes = Arrays.stream(planes)
                .map(plane -> Document.parse(gson.toJson(plane)))
                .toList();

        COLLECTION.insertMany(passengerPlanes);
    }

    @Override
    public void update(PassengerPlane plane) {
        COLLECTION.updateOne(Filters.eq("id", plane.getId()), new Document("$set", gson.toJson(plane)));
    }

    @Override
    public void delete(String id) {
        COLLECTION.deleteOne(Filters.eq("id", id));
    }

    @Override
    public Optional<PassengerPlane> findById(String id) {
        PassengerPlane plane = COLLECTION.find(Filters.eq("id", id))
                .map(object -> gson.fromJson(object.toJson(), PassengerPlane.class))
                .first();

        return Optional.ofNullable(plane);
    }

    @Override
    public List<PassengerPlane> findAll() {
        List<PassengerPlane> planeList = new ArrayList<>();
        return COLLECTION.find()
                .map(object -> gson.fromJson(object.toJson(), PassengerPlane.class))
                .into(planeList);
    }

    @Override
    public List<PassengerPlane> findAllInStock() {
        List<PassengerPlane> passengerPlaneList = findAll();
        return (passengerPlaneList.isEmpty()) ? Collections.emptyList() : passengerPlaneList.stream()
                .filter(plane -> COLLECTION_INVOICES.find(Filters.eq("planes", plane.getId())).first() == null)
                .toList();
    }
}
