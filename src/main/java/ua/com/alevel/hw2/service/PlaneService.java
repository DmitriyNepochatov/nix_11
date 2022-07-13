package ua.com.alevel.hw2.service;

import ua.com.alevel.hw2.db.PlaneDB;
import ua.com.alevel.hw2.db.PlaneDBI;
import ua.com.alevel.hw2.model.*;
import java.util.List;
import java.util.Optional;

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

    public Optional<Plane> findById(String id) {
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

    public void updateIfPresent(Plane plane) {
        planeDB.findById(plane.getId()).ifPresent(updatepablePlane -> updatePlane(updatepablePlane, plane));
    }

    private void updatePlane(Plane updatepablePlane, Plane plane) {
        if (updatepablePlane instanceof Fighter) {
            ((Fighter) updatepablePlane).setType(((Fighter) plane).getType());
            ((Fighter) updatepablePlane).setBombLoad(((Fighter) plane).getBombLoad());
        }
        else if (updatepablePlane instanceof CargoPlane) {
            ((CargoPlane) updatepablePlane).setLoadCapacity(((CargoPlane) plane).getLoadCapacity());
            ((CargoPlane) updatepablePlane).setCountOfCrew(((CargoPlane) plane).getCountOfCrew());
        }
        else if (updatepablePlane instanceof PassengerPlane) {
            ((PassengerPlane) updatepablePlane).setNumberOfPassenger(((PassengerPlane) plane).getNumberOfPassenger());
            ((PassengerPlane) updatepablePlane).setRangeOfFlight(((PassengerPlane) plane).getRangeOfFlight());
        }

        updatepablePlane.setModel(plane.getModel());
        updatepablePlane.setPrice(plane.getPrice());
        updatepablePlane.setId(plane.getId());
        updatepablePlane.setBrand(plane.getBrand());
    }

    public Plane findByIdOrReturnFindablePlane(Plane plane) {
        return planeDB.findById(plane.getId()).orElse(plane);
    }

    public void findByIdOrSaveIfNotDuplicate(Plane plane) {
        Optional<Plane> result = Optional.of(planeDB.findById(plane.getId()).orElseGet(() -> {
            planeDB.save(plane);
            return plane;
        }));
    }

    public String planeToStringMap(String id) {
        return planeDB.findById(id).map(plane -> plane.toString()).orElse("Id: " + id + " was not found");
    }

    public void updateIfPresentOrSave(Plane plane) {
        planeDB.findById(plane.getId()).ifPresentOrElse(
                updatepablePlane -> updatePlane(updatepablePlane, plane),
                () -> planeDB.save(plane)
        );
    }

    public void deletePlaneIfWasBuiltByBoeing(String id) {
        planeDB.findById(id).filter(plane -> plane.getBrand().equals(PlaneBrand.BOEING)).
                ifPresent(deletablePlane -> planeDB.delete(id));
    }

    public Plane findByIdWithException(String id) {
        return planeDB.findById(id).orElseThrow(() -> new IllegalArgumentException("Id: " + id + " was not found"));
    }

    public Optional<Plane> findByIdOrBackPlaneInOptional(Plane plane) {
        return planeDB.findById(plane.getId()).or(() -> Optional.of(plane));
    }
}
