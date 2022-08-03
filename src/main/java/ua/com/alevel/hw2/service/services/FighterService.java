package ua.com.alevel.hw2.service.services;

import ua.com.alevel.hw2.db.PlaneDB;
import ua.com.alevel.hw2.factory.PlaneFactory;
import ua.com.alevel.hw2.model.*;
import ua.com.alevel.hw2.service.PlaneService;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

public class FighterService extends PlaneService<Fighter> {
    private static FighterService instance;

    public static FighterService getInstance() {
        if (instance == null) {
            instance = new FighterService(new PlaneDB<>());
        }

        return instance;
    }

    public static FighterService getInstance(PlaneDB<Fighter> planeDB) {
        if (instance == null) {
            instance = new FighterService(planeDB);
        }

        return instance;
    }

    private FighterService(PlaneDB<Fighter> planeDB) {
        super(planeDB);
    }

    @Override
    public Fighter createPlane() {
        Plane[] arr = PlaneFactory.createPlane(PlaneType.FIGHTER, 1);
        return Arrays.copyOf(arr, arr.length, Fighter[].class)[0];
    }

    @Override
    public void updatePlane(Fighter updatepablePlane, Fighter plane) {
        updatepablePlane.setType(plane.getType());
        updatepablePlane.setBombLoad(plane.getBombLoad());
        updatepablePlane.setModel(plane.getModel());
        updatepablePlane.setPrice(plane.getPrice());
        updatepablePlane.setId(plane.getId());
        updatepablePlane.setBrand(plane.getBrand());
        updatepablePlane.setCount(plane.getCount());
    }

    public boolean isConcreteDetailExists(String findableDetail) {
        return findAll()
                .stream()
                .flatMap(plane -> plane.getDetails().stream())
                .anyMatch(detail -> detail.equals(findableDetail));
    }

    @Override
    public Fighter createPlaneFromMapFoo(Map<String, Object> map) {
        return new Fighter(null,
                PlaneBrand.valueOf(map.get("brand").toString()),
                map.get("model").toString(),
                Integer.parseInt(map.get("price").toString()),
                Integer.parseInt(map.get("count").toString()),
                map.get("currency").toString(),
                (Date) map.get("created"),
                (ManufacturingMaterial) map.get("manufacturingMaterial"),
                TypeOfFighter.valueOf(map.get("type").toString()),
                Integer.parseInt(map.get("bombLoad").toString()));
    }
}
