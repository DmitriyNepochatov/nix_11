package ua.com.alevel.hw2.service;

import org.slf4j.LoggerFactory;
import ua.com.alevel.hw2.db.PlaneDB;
import ua.com.alevel.hw2.db.PlaneDBI;
import ua.com.alevel.hw2.model.Plane;
import java.util.List;
import org.slf4j.Logger;

public class PlaneService {
    private final PlaneDBI planeDB = new PlaneDB();
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaneService.class);

    public void save(Plane plane) {
        if (plane != null) {
            planeDB.save(plane);
            LOGGER.info("Plane {} {} has been created", plane.getId(), plane.getClass().getSimpleName());
        }
        else {
            LOGGER.warn("Plane was null");
        }
    }

    public void update(Plane plane) {
        if (checkExistId(plane.getId())) {
            planeDB.update(plane);
            LOGGER.info("The plane {} has been successfully updated", plane.getId());
        }
        else {
            LOGGER.warn("Id was not found");
        }
    }

    public void delete(String id) {
        if (checkExistId(id)) {
            planeDB.delete(id);
            LOGGER.info("The plane {} has been successfully deleted", id);
        }
        else {
            LOGGER.warn("Id was not found");
        }
    }

    public Plane findById(String id) {
        Plane plane = planeDB.findById(id);
        if (plane == null) {
            LOGGER.info("Id {} doesn't exist", id);
            return null;
        }
        else {
            return plane;
        }
    }

    public List<Plane> findAll() {
        List<Plane> list = planeDB.findAll();
        if (list.size() == 0) {
            LOGGER.info("Database is empty");
            return null;
        }
        else {
            return list;
        }
    }

    private boolean checkExistId(String id) {
        if (findById(id) == null) {
            LOGGER.info("Id {} doesn't exist", id);
            return false;
        }
        return true;
    }
}
