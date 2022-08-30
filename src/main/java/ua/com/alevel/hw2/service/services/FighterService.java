package ua.com.alevel.hw2.service.services;

import ua.com.alevel.hw2.annotations.Autowired;
import ua.com.alevel.hw2.annotations.Singleton;
import ua.com.alevel.hw2.dao.mongodb.products.fighterdatabase.FighterDatabaseMongoDB;
import ua.com.alevel.hw2.factory.PlaneFactory;
import ua.com.alevel.hw2.model.*;
import ua.com.alevel.hw2.model.fighter.Fighter;
import ua.com.alevel.hw2.model.fighter.TypeOfFighter;
import ua.com.alevel.hw2.model.manufacturingmaterial.ManufacturingMaterial;
import ua.com.alevel.hw2.service.PlaneService;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@Singleton
public final class FighterService extends PlaneService<Fighter> {
    private static FighterService instance;

    public static FighterService getInstance() {
        if (instance == null) {
            instance = new FighterService(FighterDatabaseMongoDB.getInstance());
        }

        return instance;
    }

    public static FighterService getInstance(FighterDatabaseMongoDB planeDB) {
        if (instance == null) {
            instance = new FighterService(planeDB);
        }

        return instance;
    }

    @Autowired
    private FighterService(FighterDatabaseMongoDB planeDB) {
        super(planeDB);
    }

    @Override
    public Fighter[] createPlane(int count) {
        Plane[] arr = PlaneFactory.createPlane(PlaneType.FIGHTER, count);
        return Arrays.copyOf(arr, arr.length, Fighter[].class);
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
        return new Fighter.Builder(PlaneType.FIGHTER, Integer.parseInt(map.get("price").toString()))
                .setBrand(PlaneBrand.valueOf(map.get("brand").toString()))
                .setModel(map.get("model").toString())
                .setCount(Integer.parseInt(map.get("count").toString()))
                .setCurrency(map.get("currency").toString())
                .setCreated((Date) map.get("created"))
                .setManufacturingMaterial((ManufacturingMaterial) map.get("manufacturingMaterial"))
                .setTypeOfFighter(TypeOfFighter.valueOf(map.get("typeOfFighter").toString()))
                .setBombLoad(Integer.parseInt(map.get("bombLoad").toString()))
                .build();
    }
}
