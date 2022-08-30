package ua.com.alevel.hw2.dao.mongodb.products.fighterdatabase;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import ua.com.alevel.hw2.annotations.Autowired;
import ua.com.alevel.hw2.annotations.Singleton;
import ua.com.alevel.hw2.config.MongoDBConfig;
import ua.com.alevel.hw2.model.fighter.Fighter;
import ua.com.alevel.hw2.model.invoice.Invoice;
import java.util.*;

@Singleton
public final class FighterDatabaseMongoDB implements FighterDaoMongoDB {
    private static final MongoCollection<Document> COLLECTION = MongoDBConfig.getMongoDatabase()
            .getCollection(Fighter.class.getSimpleName());
    private static final MongoCollection<Document> COLLECTION_INVOICES = MongoDBConfig.getMongoDatabase()
            .getCollection(Invoice.class.getSimpleName());
    private static Gson gson;
    private static FighterDatabaseMongoDB instance;

    @Autowired
    private FighterDatabaseMongoDB() {
        gson = new Gson();
    }

    public static FighterDatabaseMongoDB getInstance() {
        if (instance == null) {
            instance = new FighterDatabaseMongoDB();
        }

        return instance;
    }

    @Override
    public void save(Fighter plane) {
        COLLECTION.insertOne(Document.parse(gson.toJson(plane)));
    }

    @Override
    public void saveAll(Fighter[] planes) {
        List<Document> fighterList = Arrays.stream(planes)
                .map(plane -> Document.parse(gson.toJson(plane)))
                .toList();

        COLLECTION.insertMany(fighterList);
    }

    @Override
    public void update(Fighter plane) {
        COLLECTION.updateOne(Filters.eq("id", plane.getId()), new Document("$set", gson.toJson(plane)));
    }

    @Override
    public void delete(String id) {
        COLLECTION.deleteOne(Filters.eq("id", id));
    }

    @Override
    public Optional<Fighter> findById(String id) {
        Fighter plane = COLLECTION.find(Filters.eq("id", id))
                .map(object -> gson.fromJson(object.toJson(), Fighter.class))
                .first();

        return Optional.ofNullable(plane);
    }

    @Override
    public List<Fighter> findAll() {
        List<Fighter> planeList = new ArrayList<>();
        return COLLECTION.find()
                .map(object -> gson.fromJson(object.toJson(), Fighter.class))
                .into(planeList);
    }

    @Override
    public List<Fighter> findAllInStock() {
        List<Fighter> fighterList = findAll();
        return (fighterList.isEmpty()) ? Collections.emptyList() : fighterList.stream()
                .filter(plane -> COLLECTION_INVOICES.find(Filters.eq("planes", plane.getId())).first() == null)
                .toList();
    }
}
