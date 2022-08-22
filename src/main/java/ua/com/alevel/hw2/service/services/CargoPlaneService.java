package ua.com.alevel.hw2.service.services;

import ua.com.alevel.hw2.annotations.Autowired;
import ua.com.alevel.hw2.annotations.Singleton;
import ua.com.alevel.hw2.dao.products.cargoplanedatabase.CargoPlaneDatabase;
import ua.com.alevel.hw2.factory.PlaneFactory;
import ua.com.alevel.hw2.model.*;
import ua.com.alevel.hw2.model.cargoplane.CargoPlane;
import ua.com.alevel.hw2.model.manufacturingmaterial.ManufacturingMaterial;
import ua.com.alevel.hw2.service.PlaneService;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Singleton
public final class CargoPlaneService extends PlaneService<CargoPlane> {
    private static CargoPlaneService instance;

    public static CargoPlaneService getInstance() {
        if (instance == null) {
            instance = new CargoPlaneService(CargoPlaneDatabase.getInstance());
        }

        return instance;
    }

    public static CargoPlaneService getInstance(CargoPlaneDatabase planeDB) {
        if (instance == null) {
            instance = new CargoPlaneService(planeDB);
        }

        return instance;
    }

    @Autowired
    private CargoPlaneService(CargoPlaneDatabase planeDB) {
        super(planeDB);
    }

    @Override
    public CargoPlane[] createPlane(int count) {
        Plane[] arr = PlaneFactory.createPlane(PlaneType.CARGO_PLANE, count);
        return Arrays.copyOf(arr, arr.length, CargoPlane[].class);
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

    @Override
    public CargoPlane createPlaneFromMapFoo(Map<String, Object> map) {
        return new CargoPlane(UUID.randomUUID().toString(),
                PlaneBrand.valueOf(map.get("brand").toString()),
                map.get("model").toString(),
                Integer.parseInt(map.get("price").toString()),
                Integer.parseInt(map.get("count").toString()),
                map.get("currency").toString(),
                (Date) map.get("created"),
                (ManufacturingMaterial) map.get("manufacturingMaterial"),
                Integer.parseInt(map.get("loadCapacity").toString()),
                Integer.parseInt(map.get("countOfCrew").toString()));
    }
}
