package ua.com.alevel.hw2.service;

import ua.com.alevel.hw2.db.PlaneDB;
import ua.com.alevel.hw2.db.PlaneDBI;
import ua.com.alevel.hw2.model.*;
import java.util.Optional;

public class OptionalExamples<E extends Plane, Service extends PlaneService> {
    private final PlaneDBI<E> planeDB;

    public OptionalExamples(PlaneDB<E> planeDB) {
        this.planeDB = planeDB;
    }

    public void updateIfPresent(E plane, Service service) {
        planeDB.findById(plane.getId()).ifPresent(updatepablePlane -> service.updatePlane(updatepablePlane, plane));
    }

    public E findByIdOrReturnFindablePlane(E plane) {
        return planeDB.findById(plane.getId()).orElse(plane);
    }

    public void findByIdOrSaveIfNotDuplicate(E plane) {
        Optional<E> result = Optional.of(planeDB.findById(plane.getId()).orElseGet(() -> {
            planeDB.save(plane);
            return plane;
        }));
    }

    public String planeToStringMap(String id) {
        return planeDB.findById(id).map(plane -> plane.toString()).orElse("Id: " + id + " was not found");
    }

    public void updateIfPresentOrSave(E plane, Service service) {
        planeDB.findById(plane.getId()).ifPresentOrElse(
                updatepablePlane -> service.updatePlane(updatepablePlane, plane),
                () -> planeDB.save(plane)
        );
    }

    public void deletePlaneIfWasBuiltByBoeing(String id) {
        planeDB.findById(id).filter(plane -> plane.getBrand().equals(PlaneBrand.BOEING)).
                ifPresent(deletablePlane -> planeDB.delete(id));
    }

    public E findByIdWithException(String id) {
        return planeDB.findById(id).orElseThrow(() -> new IllegalArgumentException("Id: " + id + " was not found"));
    }

    public Optional<E> findByIdOrBackPlaneInOptional(E plane) {
        return planeDB.findById(plane.getId()).or(() -> Optional.of(plane));
    }
}
