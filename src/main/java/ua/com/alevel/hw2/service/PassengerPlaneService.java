package ua.com.alevel.hw2.service;

import ua.com.alevel.hw2.db.PlaneDB;
import ua.com.alevel.hw2.factory.PlaneFactory;
import ua.com.alevel.hw2.model.PassengerPlane;
import ua.com.alevel.hw2.model.Plane;
import ua.com.alevel.hw2.model.PlaneType;
import java.util.Arrays;

public class PassengerPlaneService extends PlaneService<PassengerPlane> {

    public PassengerPlaneService(PlaneDB<PassengerPlane> planeDB) {
        super(planeDB);
    }

    @Override
    public PassengerPlane createPlane() {
        Plane[] arr = PlaneFactory.createPlane(PlaneType.PASSENGER_PLANE, 1);
        return Arrays.copyOf(arr, arr.length, PassengerPlane[].class)[0];
    }
}
