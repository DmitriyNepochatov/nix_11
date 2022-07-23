package ua.com.alevel.hw2.service;

import ua.com.alevel.hw2.db.PlaneDB;
import ua.com.alevel.hw2.factory.PlaneFactory;
import ua.com.alevel.hw2.model.PassengerPlane;
import ua.com.alevel.hw2.model.Plane;
import ua.com.alevel.hw2.model.PlaneType;
import java.util.Arrays;

public class PassengerPlaneService extends PlaneService<PassengerPlane> {
    private static PassengerPlaneService instance;

    public static PassengerPlaneService getInstance() {
        if (instance == null) {
            instance = new PassengerPlaneService(new PlaneDB<>());
        }

        return instance;
    }

    public static PassengerPlaneService getInstance(PlaneDB<PassengerPlane> planeDB) {
        if (instance == null) {
            instance = new PassengerPlaneService(planeDB);
        }

        return instance;
    }

    private PassengerPlaneService(PlaneDB<PassengerPlane> planeDB) {
        super(planeDB);
    }

    @Override
    public PassengerPlane createPlane() {
        Plane[] arr = PlaneFactory.createPlane(PlaneType.PASSENGER_PLANE, 1);
        return Arrays.copyOf(arr, arr.length, PassengerPlane[].class)[0];
    }

    @Override
    public void updatePlane(PassengerPlane updatepablePlane, PassengerPlane plane) {
        updatepablePlane.setNumberOfPassenger(plane.getNumberOfPassenger());
        updatepablePlane.setRangeOfFlight(plane.getRangeOfFlight());
        updatepablePlane.setModel(plane.getModel());
        updatepablePlane.setPrice(plane.getPrice());
        updatepablePlane.setId(plane.getId());
        updatepablePlane.setBrand(plane.getBrand());
        updatepablePlane.setCount(plane.getCount());
    }
}
