package ua.com.alevel.hw2.service;

import ua.com.alevel.hw2.db.PlaneDB;
import ua.com.alevel.hw2.db.PlaneDBI;
import ua.com.alevel.hw2.model.Plane;
import java.util.List;
import java.util.Optional;

public abstract class PlaneService<E extends Plane> {
    private final PlaneDBI<E> planeDB;

    protected PlaneService(PlaneDB<E> planeDB) {
        this.planeDB = planeDB;
    }

    public void save(E plane) {
        if (plane != null) {
            planeDB.save(plane);
        }
        else {
            throw new IllegalArgumentException("Plane was null");
        }
    }

    public void update(E plane) {
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

    public Optional<E> findById(String id) {
        if (id != null) {
            return planeDB.findById(id);
        }
        else {
            throw new IllegalArgumentException("id was null");
        }
    }

    public List<E> findAll() {
        return planeDB.findAll();
    }

    public abstract E createPlane();
}
