package ua.com.alevel.hw2.service;

import ua.com.alevel.hw2.db.PlaneDB;
import ua.com.alevel.hw2.factory.PlaneFactory;
import ua.com.alevel.hw2.model.*;
import java.util.Arrays;

public class FighterService extends PlaneService<Fighter> {

    public FighterService(PlaneDB<Fighter> planeDB) {
        super(planeDB);
    }

    @Override
    public Fighter createPlane() {
        Plane[] arr = PlaneFactory.createPlane(PlaneType.FIGHTER, 1);
        return Arrays.copyOf(arr, arr.length, Fighter[].class)[0];
    }
}
