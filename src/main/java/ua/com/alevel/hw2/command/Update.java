package ua.com.alevel.hw2.command;

import ua.com.alevel.hw2.model.*;
import ua.com.alevel.hw2.model.cargoplane.CargoPlane;
import ua.com.alevel.hw2.model.fighter.Fighter;
import ua.com.alevel.hw2.model.fighter.TypeOfFighter;
import ua.com.alevel.hw2.model.manufacturingmaterial.ManufacturingMaterial;
import ua.com.alevel.hw2.model.passengerplane.PassengerPlane;
import ua.com.alevel.hw2.service.services.CargoPlaneService;
import ua.com.alevel.hw2.service.services.FighterService;
import ua.com.alevel.hw2.service.services.PassengerPlaneService;
import ua.com.alevel.hw2.service.PlaneService;
import ua.com.alevel.hw2.utils.UserInputUtil;
import ua.com.alevel.hw2.utils.UtilEnumToList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Update implements Command {
    private static final PlaneService<Fighter> FIGHTER_SERVICE = FighterService.getInstance();
    private static final PlaneService<CargoPlane> CARGO_PLANE_SERVICE = CargoPlaneService.getInstance();
    private static final PlaneService<PassengerPlane> PASSENGER_PLANE_SERVICE = PassengerPlaneService.getInstance();
    private static final BufferedReader BUFFERED_READER = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void execute() {
        System.out.print("\nWhich plane do you want update?");
        PlaneType[] planeTypes = PlaneType.values();
        List<String> names = UtilEnumToList.getNamesOfType(planeTypes);
        int userInput = UserInputUtil.getUserInput(planeTypes.length, names);
        switch (planeTypes[userInput]) {
            case FIGHTER -> update(FIGHTER_SERVICE);
            case CARGO_PLANE -> update(CARGO_PLANE_SERVICE);
            case PASSENGER_PLANE -> update(PASSENGER_PLANE_SERVICE);
        }
    }

    private void update(PlaneService<? extends Plane> service) {
        if (service.findAll().isEmpty()) {
            System.out.println("\nDatabase is empty");
        }
        else {
            boolean check = false;
            while (!check) {
                System.out.print("\nEnter the id of the plane to be updated >> ");
                try {
                    String id = BUFFERED_READER.readLine();
                    Optional<? extends Plane> plane = service.findById(id);
                    if (plane.isPresent()) {
                        service.update(updateFields(plane.get()));
                        check = true;
                    }
                }
                catch (Exception e) {
                    System.out.println("Incorrect input");
                }
            }
        }
    }

    private Plane updateFields(Plane plane) {
        boolean check = false;
        PlaneBrand[] brands = PlaneBrand.values();
        List<String> brandList = UtilEnumToList.getNamesOfType(brands);
        TypeOfFighter[] typeOfFighters = TypeOfFighter.values();
        List<String> typeOfFightersList = UtilEnumToList.getNamesOfType(typeOfFighters);

        while (!check) {
            try {
                System.out.println("\nChoose plane brand:");
                int brand = UserInputUtil.getUserInput(brands.length, brandList);

                System.out.print("\nEnter model >> ");
                String model = BUFFERED_READER.readLine();
                System.out.println();
                if (model.isEmpty()) {
                    throw new RuntimeException();
                }

                System.out.print("Enter price >> ");
                int price = Integer.parseInt(BUFFERED_READER.readLine());
                System.out.println();
                if (price <= 0) {
                    throw new RuntimeException();
                }

                System.out.print("Enter count >> ");
                int count = Integer.parseInt(BUFFERED_READER.readLine());
                System.out.println();
                if (count <= 0) {
                    throw new RuntimeException();
                }

                System.out.print("Enter currency >> ");
                String currency = BUFFERED_READER.readLine();
                System.out.println();
                if (currency.isEmpty()) {
                    throw new RuntimeException();
                }

                System.out.print("Enter date (format: yyyy-MM-dd) >> ");
                String date = BUFFERED_READER.readLine();
                System.out.println();
                if (date.isEmpty()) {
                    throw new RuntimeException();
                }
                System.out.print("Enter time (format: HH:mm:ss) >> ");
                String time = BUFFERED_READER.readLine();
                System.out.println();
                if (time.isEmpty()) {
                    throw new RuntimeException();
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                Date dateClass = formatter.parse(date + "T" + time + "Z");

                System.out.print("Enter color >> ");
                String color = BUFFERED_READER.readLine();
                System.out.println();
                if (color.isEmpty()) {
                    throw new RuntimeException();
                }
                System.out.print("Enter material >> ");
                String material = BUFFERED_READER.readLine();
                System.out.println();
                if (material.isEmpty()) {
                    throw new RuntimeException();
                }
                ManufacturingMaterial manufacturingMaterial = new ManufacturingMaterial(material, color);

                plane.setBrand(brands[brand]);
                plane.setModel(model);
                plane.setPrice(price);
                plane.setCount(count);
                plane.setCurrency(currency);
                plane.setCreated(dateClass);
                plane.setManufacturingMaterial(manufacturingMaterial);

                if (plane.getClass().equals(CargoPlane.class)) {
                    System.out.print("Enter load capacity >> ");
                    int loadCapacity = Integer.parseInt(BUFFERED_READER.readLine());
                    System.out.println();
                    if (loadCapacity <= 0) {
                        throw new RuntimeException();
                    }
                    System.out.print("Enter count of crew >> ");
                    int countOfCrew = Integer.parseInt(BUFFERED_READER.readLine());
                    System.out.println();
                    if (countOfCrew <= 0) {
                        throw new RuntimeException();
                    }

                    ((CargoPlane) plane).setLoadCapacity(loadCapacity);
                    ((CargoPlane) plane).setCountOfCrew(countOfCrew);
                }
                else if (plane.getClass().equals(Fighter.class)) {
                    System.out.println("\nChoose fighter type:");
                    int typeOfFighter = UserInputUtil.getUserInput(typeOfFighters.length, typeOfFightersList);

                    System.out.print("Enter bomb load >> ");
                    int bombLoad = Integer.parseInt(BUFFERED_READER.readLine());
                    System.out.println();
                    if (bombLoad <= 0) {
                        throw new RuntimeException();
                    }

                    ((Fighter) plane).setTypeOfFighter(typeOfFighters[typeOfFighter]);
                    ((Fighter) plane).setBombLoad(bombLoad);
                }
                else if (plane.getClass().equals(PassengerPlane.class)) {
                    System.out.print("Enter number of passenger >> ");
                    int numberOfPassenger = Integer.parseInt(BUFFERED_READER.readLine());
                    System.out.println();
                    if (numberOfPassenger <= 0) {
                        throw new RuntimeException();
                    }
                    System.out.print("Enter range of flight >> ");
                    int rangeOfFlight = Integer.parseInt(BUFFERED_READER.readLine());
                    System.out.println();
                    if (rangeOfFlight <= 0) {
                        throw new RuntimeException();
                    }

                    ((PassengerPlane) plane).setNumberOfPassenger(numberOfPassenger);
                    ((PassengerPlane) plane).setRangeOfFlight(rangeOfFlight);
                }

                check = true;
            }
            catch (Exception e) {
                System.out.println("Incorrect input");
            }
        }

        return plane;
    }
}
