package ua.com.alevel.hw2.service;

import ua.com.alevel.hw2.db.PlaneDB;
import ua.com.alevel.hw2.factory.PlaneFactory;
import ua.com.alevel.hw2.model.CargoPlane;
import ua.com.alevel.hw2.model.Plane;
import ua.com.alevel.hw2.model.PlaneType;
import java.util.Arrays;

public class CargoPlaneService extends PlaneService<CargoPlane> {
    private static CargoPlaneService instance;

    public static CargoPlaneService getInstance() {
        if (instance == null) {
            instance = new CargoPlaneService(new PlaneDB<>());
        }

        return instance;
    }

    public static CargoPlaneService getInstance(PlaneDB<CargoPlane> planeDB) {
        if (instance == null) {
            instance = new CargoPlaneService(planeDB);
        }

        return instance;
    }

    private CargoPlaneService(PlaneDB<CargoPlane> planeDB) {
        super(planeDB);
    }

    @Override
    public CargoPlane createPlane() {
        Plane[] arr = PlaneFactory.createPlane(PlaneType.CARGO_PLANE, 1);
        return Arrays.copyOf(arr, arr.length, CargoPlane[].class)[0];
    }

    @Override
    public void updatePlane(CargoPlane updatepablePlane, CargoPlane plane) {
        updatepablePlane.setLoadCapacity(plane.getLoadCapacity());
        updatepablePlane.setCountOfCrew(plane.getCountOfCrew());
        updatepablePlane.setModel(plane.getModel());
        updatepablePlane.setPrice(plane.getPrice());
        updatepablePlane.setId(plane.getId());
        updatepablePlane.setBrand(plane.getBrand());
        updatepablePlane.setCount(plane.getCount());
    }
}
