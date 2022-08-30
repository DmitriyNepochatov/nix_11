package ua.com.alevel.hw2.dao.mongodb.products.cargoplanedatabase;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import ua.com.alevel.hw2.annotations.Autowired;
import ua.com.alevel.hw2.annotations.Singleton;
import ua.com.alevel.hw2.config.MongoDBConfig;
import ua.com.alevel.hw2.model.cargoplane.CargoPlane;
import ua.com.alevel.hw2.model.invoice.Invoice;
import java.util.*;

@Singleton
public final class CargoPlaneDatabaseMongoDB implements CargoPlaneDaoMongoDB {
    private static final MongoCollection<Document> COLLECTION = MongoDBConfig.getMongoDatabase()
            .getCollection(CargoPlane.class.getSimpleName());
    private static final MongoCollection<Document> COLLECTION_INVOICES = MongoDBConfig.getMongoDatabase()
            .getCollection(Invoice.class.getSimpleName());
    private static Gson gson;
    private static CargoPlaneDatabaseMongoDB instance;

    @Autowired
    private CargoPlaneDatabaseMongoDB() {
        gson = new Gson();
    }

    public static CargoPlaneDatabaseMongoDB getInstance() {
        if (instance == null) {
            instance = new CargoPlaneDatabaseMongoDB();
        }

        return instance;
    }

    @Override
    public void save(CargoPlane plane) {
        COLLECTION.insertOne(Document.parse(gson.toJson(plane)));
    }

    @Override
    public void saveAll(CargoPlane[] planes) {
        List<Document> cargoPlanes = Arrays.stream(planes)
                .map(plane -> Document.parse(gson.toJson(plane)))
                .toList();

        COLLECTION.insertMany(cargoPlanes);
    }

    @Override
    public void update(CargoPlane plane) {
        COLLECTION.updateOne(Filters.eq("id", plane.getId()), new Document("$set", gson.toJson(plane)));
    }

    @Override
    public void delete(String id) {
        COLLECTION.deleteOne(Filters.eq("id", id));
    }

    @Override
    public Optional<CargoPlane> findById(String id) {
        CargoPlane plane = COLLECTION.find(Filters.eq("id", id))
                .map(object -> gson.fromJson(object.toJson(), CargoPlane.class))
                .first();

        return Optional.ofNullable(plane);
    }

    @Override
    public List<CargoPlane> findAll() {
        List<CargoPlane> planeList = new ArrayList<>();
        return COLLECTION.find()
                .map(object -> gson.fromJson(object.toJson(), CargoPlane.class))
                .into(planeList);
    }

    @Override
    public List<CargoPlane> findAllInStock() {
        List<CargoPlane> cargoPlaneList = findAll();
        return (cargoPlaneList.isEmpty()) ? Collections.emptyList() : cargoPlaneList.stream()
                .filter(plane -> COLLECTION_INVOICES.find(Filters.eq("planes", plane.getId())).first() == null)
                .toList();
    }
}
