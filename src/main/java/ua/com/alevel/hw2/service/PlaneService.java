package ua.com.alevel.hw2.service;

import ua.com.alevel.hw2.db.PlaneDB;
import ua.com.alevel.hw2.db.PlaneDBI;
import ua.com.alevel.hw2.model.Plane;
import java.util.List;

public class PlaneService {
    private final PlaneDBI planeDB;

    public PlaneService(PlaneDB planeDB) {
        this.planeDB = planeDB;
    }

    public void save(Plane plane) {
        if (plane != null) {
            planeDB.save(plane);
        }
        else {
            throw new IllegalArgumentException("Plane was null");
        }
    }

    public void update(Plane plane) {
        if (plane != null) {
            planeDB.update(plane);
        }
        else {
            throw new IllegalArgumentException("Plane was null");
        }
    }

    public void delete(String id) {
        if (id != null) {
            planeDB.delete(id);
        }
        else {
            throw new IllegalArgumentException("id was null");
        }
    }

    public Plane findById(String id) {
        if (id != null) {
            return planeDB.findById(id);
        }
        else {
            throw new IllegalArgumentException("id was null");
        }
    }

    public List<Plane> findAll() {
        return planeDB.findAll();
    }
}
