package ua.com.alevel.hw2.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.hw2.annotations.Autowired;
import ua.com.alevel.hw2.annotations.Singleton;
import ua.com.alevel.hw2.model.Fighter;
import java.util.ArrayList;
import ua.com.alevel.hw2.generatorID.GeneratorID;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Singleton
public class FighterDB implements PlaneDBI<Fighter> {
    private final List<Fighter> planes;
    private static final Logger LOGGER = LoggerFactory.getLogger(FighterDB.class);
    private static FighterDB instance;

    @Autowired
    private FighterDB() {
        planes = new ArrayList<>();
    }

    public static FighterDB getInstance() {
        if (instance == null) {
            instance = new FighterDB();
        }

        return instance;
    }

    @Override
    public void save(Fighter plane) {
        if (!isDuplicate(plane)) {
            plane.setId(GeneratorID.createID());
            planes.add(plane);
            LOGGER.info("Plane {} {} has been created", plane.getId(), plane.getClass().getSimpleName());
        }
        else {
            LOGGER.warn("Plane {} {} duplicate", plane.getId(), plane.getClass().getSimpleName());
        }
    }

    @Override
    public void update(Fighter plane) {
        if (findById(plane.getId()).isPresent()) {
            int i;
            for (i = 0; i < planes.size(); i++) {
                if (planes.get(i).getId().equals(plane.getId())) {
                    break;
                }
            }

            planes.set(i, plane);
            LOGGER.info("The plane {} has been successfully updated", plane.getId());
        }
    }

    @Override
    public void delete(String id) {
        if (findById(id).isPresent()) {
            for (int i = 0; i < planes.size(); i++) {
                if (planes.get(i).getId().equals(id)) {
                    planes.remove(planes.get(i));
                }
            }

            LOGGER.info("The plane {} has been successfully deleted", id);
        }
    }

    @Override
    public Optional<Fighter> findById(String id) {
        for (int i = 0; i < planes.size(); i++) {
            if (planes.get(i).getId().equals(id)) {
                return Optional.of(planes.get(i));
            }
        }

        LOGGER.info("Id {} doesn't exist", id);
        return Optional.empty();
    }

    @Override
    public List<Fighter> findAll() {
        if (planes.isEmpty()) {
            LOGGER.info("Database is empty");
            return Collections.emptyList();
        }
        else {
            return planes;
        }
    }

    private boolean isDuplicate(Fighter plane) {
        for (int i = 0; i < planes.size(); i++) {
            if (planes.get(i).equals(plane)) {
                return true;
            }
        }

        return false;
    }
}