package ua.com.alevel.hw2;

import ua.com.alevel.hw2.container.PlaneContainer;
import ua.com.alevel.hw2.db.PlaneDB;
import ua.com.alevel.hw2.model.*;
import ua.com.alevel.hw2.service.CargoPlaneService;
import ua.com.alevel.hw2.service.FighterService;
import ua.com.alevel.hw2.service.OptionalExamples;
import ua.com.alevel.hw2.service.PassengerPlaneService;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final FighterService fighterService = new FighterService(new PlaneDB<>());
    private static final CargoPlaneService cargoPlaneService = new CargoPlaneService(new PlaneDB<>());
    private static final PassengerPlaneService passengerPlaneService = new PassengerPlaneService(new PlaneDB<>());
    private static final int COUNT = 10;

    public static void main(String[] args) {

        //------------------------------------Save
        for (int i = 0; i < COUNT; i++) {
            fighterService.save(fighterService.createPlane());
            cargoPlaneService.save(cargoPlaneService.createPlane());
            passengerPlaneService.save(passengerPlaneService.createPlane());
        }

        //---------------------------------------FindAll
        System.out.println();
        List<Fighter> fighterList = fighterService.findAll();
        for (Fighter plane : fighterList) {
            System.out.println(plane);
        }

        System.out.println();
        List<CargoPlane> cargoPlaneList = cargoPlaneService.findAll();
        for (CargoPlane plane : cargoPlaneList) {
            System.out.println(plane);
        }

        System.out.println();
        List<PassengerPlane> passengerPlaneList = passengerPlaneService.findAll();
        for (PassengerPlane plane : passengerPlaneList) {
            System.out.println(plane);
        }

        //-----------------------------------------Update
        Fighter fighter = new Fighter("", null, "", 0, 0, null, 0);
        fighter.setId("0");
        fighter.setType(TypeOfFighter.CARRIER_BASED);
        fighter.setBombLoad(0);
        fighter.setBrand(PlaneBrand.BOEING);
        fighter.setModel("F-35A");
        fighter.setPrice(9999999);
        fighter.setCount(123);

        System.out.println();
        fighterService.update(fighter);
        for (Fighter plane : fighterList) {
            System.out.println(plane);
        }

        //-------------------------------------Delete
        System.out.println();
        passengerPlaneService.delete("2");
        for (PassengerPlane plane : passengerPlaneList) {
            System.out.println(plane);
        }

        //----------------------------------------Showed how work logger
        System.out.println();
        passengerPlaneService.delete("145");

        //-----------------------------------------FindById
        System.out.println();
        Optional<CargoPlane> plane = cargoPlaneService.findById("16");
        System.out.println(plane);

        //---------------------------------------Showed how work logger
        System.out.println();
        Optional<PassengerPlane> plane2 = passengerPlaneService.findById("485");
        System.out.println(plane2);

        System.out.println();
        optionalExamples();


        System.out.println();
        PlaneContainer<Fighter> fighterPlaneContainer = new PlaneContainer<>(new Fighter(
                "39",
                PlaneBrand.LOCKHEED,
                "F-22 Raptor",
                30000,
                999,
                TypeOfFighter.SCOUT,
                10));

        fighterPlaneContainer.setRandomDiscountToPlanePrice();
        System.out.println(fighterPlaneContainer.getPlane());
        System.out.println();
        fighterPlaneContainer.addPlaneCount(50.584);
        System.out.println(fighterPlaneContainer.getPlane());
    }

    public static void optionalExamples() {
        PlaneDB<Fighter> fighterPlaneDB = new PlaneDB<>();
        for (int i = 0; i < COUNT; i++) {
            fighterPlaneDB.save(fighterService.createPlane());
        }

        OptionalExamples<Fighter, FighterService> optionalExamples = new OptionalExamples<>(fighterPlaneDB);

        //---------------------------------------------------------------Optional methods
        System.out.println();
        Fighter fighter2 = new Fighter("39", PlaneBrand.LOCKHEED, "F-22 Raptor", 30000, 999, TypeOfFighter.SCOUT, 10);
        optionalExamples.updateIfPresent(fighter2, fighterService);
        for (Fighter plane : fighterPlaneDB.findAll()) {
            System.out.println(plane);
        }

        System.out.println();
        Fighter resPlane = optionalExamples.findByIdOrReturnFindablePlane(new Fighter("39", PlaneBrand.BELL, "XP-36", 500, 100, TypeOfFighter.CARRIER_BASED, 50));
        System.out.println(resPlane);

        System.out.println();
        optionalExamples.findByIdOrSaveIfNotDuplicate(new Fighter("145", PlaneBrand.BOEING, "XP-36", 500, 100, TypeOfFighter.CARRIER_BASED, 50));
        for (Fighter plane1 : fighterPlaneDB.findAll()) {
            System.out.println(plane1);
        }

        System.out.println();
        System.out.println(optionalExamples.planeToStringMap("30"));

        System.out.println();
        optionalExamples.updateIfPresentOrSave(new Fighter("39", PlaneBrand.LOCKHEED, "F-22 Raptor stage 2", 150, 50, TypeOfFighter.SCOUT, 30), fighterService);
        for (Fighter plane1 : fighterPlaneDB.findAll()) {
            System.out.println(plane1);
        }

        System.out.println();
        optionalExamples.deletePlaneIfWasBuiltByBoeing("40");
        for (Fighter plane1 : fighterPlaneDB.findAll()) {
            System.out.println(plane1);
        }

        //System.out.println();
        //Fighter result = optionalExamples.findByIdWithException("-1");
        //System.out.println(result);

        System.out.println();
        Optional<Fighter> result2 = optionalExamples.findByIdOrBackPlaneInOptional(new Fighter("555", PlaneBrand.LOCKHEED, "B-17", 1000, 777, TypeOfFighter.BOMBER, 30));
        System.out.println(result2);
    }
}
