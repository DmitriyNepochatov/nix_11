package ua.com.alevel.hw2.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.hw2.model.Plane;
import java.util.ArrayList;
import ua.com.alevel.hw2.generatorID.GeneratorID;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PlaneDB<E extends Plane> implements PlaneDBI<E> {
    private final List<E> planes;
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaneDB.class);

    public PlaneDB() {
        planes = new ArrayList<>();
    }

    @Override
    public void save(E plane) {
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
    public void update(E plane) {
        if (isExistId(plane.getId())) {
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
        if (isExistId(id)) {
            for (int i = 0; i < planes.size(); i++) {
                if (planes.get(i).getId().equals(id)) {
                    planes.remove(planes.get(i));
                }
            }

            LOGGER.info("The plane {} has been successfully deleted", id);
        }
    }

    @Override
    public Optional<E> findById(String id) {
        for (int i = 0; i < planes.size(); i++) {
            if (planes.get(i).getId().equals(id)) {
                return Optional.of(planes.get(i));
            }
        }

        LOGGER.info("Id {} doesn't exist", id);
        return Optional.empty();
    }

    @Override
    public List<E> findAll() {
        if (planes.isEmpty()) {
            LOGGER.info("Database is empty");
            return Collections.emptyList();
        }
        else {
            return planes;
        }
    }

    private boolean isDuplicate(E plane) {
        for (int i = 0; i < planes.size(); i++) {
            if (planes.get(i).equals(plane)) {
                return true;
            }
        }

        return false;
    }

    private boolean isExistId(String id) {
        return findById(id).isEmpty() ? false : true;
    }
}
