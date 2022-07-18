package ua.com.alevel.hw2;

import ua.com.alevel.hw2.db.PlaneDB;
import ua.com.alevel.hw2.factory.PlaneFactory;
import ua.com.alevel.hw2.model.*;
import ua.com.alevel.hw2.service.PlaneService;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final PlaneService fighterService = new PlaneService(new PlaneDB());
    private static final PlaneService cargoPlaneService = new PlaneService(new PlaneDB());
    private static final PlaneService passengerPlaneService = new PlaneService(new PlaneDB());
    private static final int COUNT = 10;

    public static void main(String[] args) {
        Plane[] fighterArr = PlaneFactory.createPlane(PlaneType.FIGHTER, COUNT);
        Plane[] cargoPlaneArr = PlaneFactory.createPlane(PlaneType.CARGO_PLANE, COUNT);
        Plane[] passengerPlaneArr = PlaneFactory.createPlane(PlaneType.PASSENGER_PLANE, COUNT);


        //------------------------------------Save
        for (int i = 0; i < COUNT; i++) {
            fighterService.save(fighterArr[i]);
            cargoPlaneService.save(cargoPlaneArr[i]);
            passengerPlaneService.save(passengerPlaneArr[i]);
        }

        //---------------------------------------FindAll
        System.out.println();
        List<Plane> fighterList = fighterService.findAll();
        for (Plane plane : fighterList) {
            System.out.println(plane);
        }

        System.out.println();
        List<Plane> cargoPlaneList = cargoPlaneService.findAll();
        for (Plane plane : cargoPlaneList) {
            System.out.println(plane);
        }

        System.out.println();
        List<Plane> passengerPlaneList = passengerPlaneService.findAll();
        for (Plane plane : passengerPlaneList) {
            System.out.println(plane);
        }

        //-----------------------------------------Update
        Fighter fighter = new Fighter("", null, "", 0, null, 0);
        fighter.setId("0");
        fighter.setType(TypeOfFighter.CARRIER_BASED);
        fighter.setBombLoad(0);
        fighter.setBrand(PlaneBrand.BOEING);
        fighter.setModel("F-35A");
        fighter.setPrice(9999999999l);

        System.out.println();
        fighterService.update(fighter);
        for (Plane plane : fighterList) {
            System.out.println(plane);
        }

        //-------------------------------------Delete
        System.out.println();
        passengerPlaneService.delete("2");
        for (Plane plane : passengerPlaneList) {
            System.out.println(plane);
        }

        //----------------------------------------Showed how work logger
        System.out.println();
        passengerPlaneService.delete("145");

        //-----------------------------------------FindById
        System.out.println();
        Optional<Plane> plane = cargoPlaneService.findById("16");
        System.out.println(plane);

        //---------------------------------------Showed how work logger
        System.out.println();
        Optional<Plane> plane2 = passengerPlaneService.findById("485");
        System.out.println(plane2);


        //---------------------------------------------------------------Optional methods
        System.out.println();
        Fighter fighter2 = new Fighter("27", PlaneBrand.LOCKHEED, "F-22 Raptor", 30_000_000l, TypeOfFighter.SCOUT, 10);
        fighterService.updateIfPresent(fighter2);
        for (Plane plane1 : fighterList) {
            System.out.println(plane1);
        }

        System.out.println();
        Plane resPlane = fighterService.findByIdOrReturnFindablePlane(new Fighter("27", PlaneBrand.BELL, "XP-36", 50_000_000l, TypeOfFighter.CARRIER_BASED, 50));
        System.out.println(resPlane);

        System.out.println();
        fighterService.findByIdOrSaveIfNotDuplicate(new Fighter("145", PlaneBrand.BELL, "XP-36", 50_000_000l, TypeOfFighter.CARRIER_BASED, 50));
        for (Plane plane1 : fighterList) {
            System.out.println(plane1);
        }

        System.out.println();
        System.out.println(fighterService.planeToStringMap("30"));

        System.out.println();
        fighterService.updateIfPresentOrSave(new Fighter("27", PlaneBrand.LOCKHEED, "F-22 Raptor stage 2", 150_000_000l, TypeOfFighter.SCOUT, 30));
        for (Plane plane1 : fighterList) {
            System.out.println(plane1);
        }

        System.out.println();
        fighterService.deletePlaneIfWasBuiltByBoeing("0");
        for (Plane plane1 : fighterList) {
            System.out.println(plane1);
        }

        System.out.println();
        //Plane result = fighterService.findByIdWithException("-1");
        //System.out.println(result);

        System.out.println();
        Optional<Plane> result2 = fighterService.findByIdOrBackPlaneInOptional(new Fighter("555", PlaneBrand.LOCKHEED, "B-17", 1_000_000l, TypeOfFighter.BOMBER, 30));
        System.out.println(result2);
    }
}
