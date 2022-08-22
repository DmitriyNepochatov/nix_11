package ua.com.alevel.hw2.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.hw2.model.cargoplane.CargoPlane;
import java.util.*;

public class CargoPlaneDB implements PlaneDBI<CargoPlane> {
    private final List<CargoPlane> planes;
    private static final Logger LOGGER = LoggerFactory.getLogger(CargoPlaneDB.class);
    private static CargoPlaneDB instance;

    private CargoPlaneDB() {
        planes = new ArrayList<>();
    }

    public static CargoPlaneDB getInstance() {
        if (instance == null) {
            instance = new CargoPlaneDB();
        }

        return instance;
    }

    @Override
    public void save(CargoPlane plane) {
        if (!isDuplicate(plane)) {
            plane.setId(UUID.randomUUID().toString());
            planes.add(plane);
            LOGGER.info("Plane {} {} has been created", plane.getId(), plane.getClass().getSimpleName());
        }
        else {
            LOGGER.warn("Plane {} {} duplicate", plane.getId(), plane.getClass().getSimpleName());
        }
    }

    @Override
    public void update(CargoPlane plane) {
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
    public Optional<CargoPlane> findById(String id) {
        for (int i = 0; i < planes.size(); i++) {
            if (planes.get(i).getId().equals(id)) {
                return Optional.of(planes.get(i));
            }
        }

        LOGGER.info("Id {} doesn't exist", id);
        return Optional.empty();
    }

    @Override
    public List<CargoPlane> findAll() {
        if (planes.isEmpty()) {
            LOGGER.info("Database is empty");
            return Collections.emptyList();
        }
        else {
            return planes;
        }
    }

    private boolean isDuplicate(CargoPlane plane) {
        for (int i = 0; i < planes.size(); i++) {
            if (planes.get(i).equals(plane)) {
                return true;
            }
        }

        return false;
    }
}
