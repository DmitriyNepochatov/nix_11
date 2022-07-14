package ua.com.alevel.hw2.service;

import ua.com.alevel.hw2.db.PlaneDB;
import ua.com.alevel.hw2.factory.PlaneFactory;
import ua.com.alevel.hw2.model.CargoPlane;
import ua.com.alevel.hw2.model.Plane;
import ua.com.alevel.hw2.model.PlaneType;
import java.util.Arrays;

public class CargoPlaneService extends PlaneService<CargoPlane> {

    public CargoPlaneService(PlaneDB<CargoPlane> planeDB) {
        super(planeDB);
    }

    @Override
    public CargoPlane createPlane() {
        Plane[] arr = PlaneFactory.createPlane(PlaneType.CARGO_PLANE, 1);
        return Arrays.copyOf(arr, arr.length, CargoPlane[].class)[0];
    }
}
