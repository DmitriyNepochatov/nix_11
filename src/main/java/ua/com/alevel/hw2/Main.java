package ua.com.alevel.hw2;

import ua.com.alevel.hw2.factory.PlaneFactory;
import ua.com.alevel.hw2.model.Fighter;
import ua.com.alevel.hw2.model.Plane;
import ua.com.alevel.hw2.model.PlaneBrand;
import ua.com.alevel.hw2.model.TypeOfFighter;
import ua.com.alevel.hw2.service.PlaneService;
import java.util.List;

public class Main
{
    private static final PlaneService fighterService = new PlaneService();
    private static final PlaneService cargoPlaneService = new PlaneService();
    private static final PlaneService passengerPlaneService = new PlaneService();
    private static final int COUNT=10;

    public static void main(String[] args)
    {
        Plane[] fighterArr = PlaneFactory.createPlane("Fighter", COUNT);
        Plane[] cargoPlaneArr = PlaneFactory.createPlane("CargoPlane", COUNT);
        Plane[] passengerPlaneArr = PlaneFactory.createPlane("PassengerPlane", COUNT);


        //------------------------------------Create
        for (int i = 0; i < COUNT; i++)
        {
            fighterService.create(fighterArr[i]);
            cargoPlaneService.create( cargoPlaneArr[i]);
            passengerPlaneService.create( passengerPlaneArr[i]);
        }

        //---------------------------------------findAll
        System.out.println();
        List<Plane> fighterList = fighterService.findAll();
        for (Plane plane: fighterList)
            System.out.println(plane);

        System.out.println();
        List<Plane> cargoPlaneList = cargoPlaneService.findAll();
        for (Plane plane: cargoPlaneList)
            System.out.println(plane);

        System.out.println();
        List<Plane> passengerPlaneList = passengerPlaneService.findAll();
        for (Plane plane: passengerPlaneList)
            System.out.println(plane);

        //-----------------------------------------Update
        Fighter fighter = new Fighter();
        fighter.setId("0");
        fighter.setType(TypeOfFighter.CarrierBased);
        fighter.setBombLoad(0);
        fighter.setBrand(PlaneBrand.Lockheed);
        fighter.setModel("F-35A");
        fighter.setPrice(9999999999l);

        System.out.println();
        fighterService.update(fighter);
        for (Plane plane: fighterList)
            System.out.println(plane);

        //-------------------------------------Delete
        System.out.println();
        passengerPlaneService.delete("2");
        for (Plane plane: passengerPlaneList)
            System.out.println(plane);

        //----------------------------------------Showed how work logger
        System.out.println();
        passengerPlaneService.delete("145");

        //-----------------------------------------FindById
        System.out.println();
        Plane plane = cargoPlaneService.findById("16");
        System.out.println(plane);

        //---------------------------------------Showed how work logger
        System.out.println();
        Plane plane2 = passengerPlaneService.findById("485");
        System.out.println(plane2);
    }
}
