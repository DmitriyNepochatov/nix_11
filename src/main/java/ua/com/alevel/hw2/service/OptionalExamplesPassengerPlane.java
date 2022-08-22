package ua.com.alevel.hw2.service;

import ua.com.alevel.hw2.db.PassengerPlaneDB;
import ua.com.alevel.hw2.db.PlaneDBI;
import ua.com.alevel.hw2.model.*;
import ua.com.alevel.hw2.service.services.PassengerPlaneService;
import java.util.Optional;

public class OptionalExamplesPassengerPlane {
    private final PlaneDBI<PassengerPlane> planeDB = PassengerPlaneDB.getInstance();

    public void updateIfPresent(PassengerPlane plane, PassengerPlaneService service) {
        planeDB.findById(plane.getId()).ifPresent(updatepablePlane -> service.updatePlane(updatepablePlane, plane));
    }

    public PassengerPlane findByIdOrReturnFindablePlane(PassengerPlane plane) {
        return planeDB.findById(plane.getId()).orElse(plane);
    }

    public void findByIdOrSaveIfNotDuplicate(PassengerPlane plane) {
        Optional<PassengerPlane> result = Optional.of(planeDB.findById(plane.getId()).orElseGet(() -> {
            planeDB.save(plane);
            return plane;
        }));
    }

    public String planeToStringMap(String id) {
        return planeDB.findById(id).map(plane -> plane.toString()).orElse("Id: " + id + " was not found");
    }

    public void updateIfPresentOrSave(PassengerPlane plane, PassengerPlaneService service) {
        planeDB.findById(plane.getId()).ifPresentOrElse(
                updatepablePlane -> service.updatePlane(updatepablePlane, plane),
                () -> planeDB.save(plane)
        );
    }

    public void deletePlaneIfWasBuiltByBoeing(String id) {
        planeDB.findById(id).filter(plane -> plane.getBrand().equals(PlaneBrand.BOEING)).
                ifPresent(deletablePlane -> planeDB.delete(id));
    }

    public PassengerPlane findByIdWithException(String id) {
        return planeDB.findById(id).orElseThrow(() -> new IllegalArgumentException("Id: " + id + " was not found"));
    }

    public Optional<PassengerPlane> findByIdOrBackPlaneInOptional(PassengerPlane plane) {
        return planeDB.findById(plane.getId()).or(() -> Optional.of(plane));
    }
}