package ua.com.alevel.hw2.command;

import ua.com.alevel.hw2.model.*;
import ua.com.alevel.hw2.service.CargoPlaneService;
import ua.com.alevel.hw2.service.FighterService;
import ua.com.alevel.hw2.service.PassengerPlaneService;
import ua.com.alevel.hw2.service.PlaneService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

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
                    if (!service.findById(id).isEmpty()) {
                        service.update(updateFields(service.findById(id).get()));
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

                plane.setBrand(brands[brand]);
                plane.setModel(model);
                plane.setPrice(price);
                plane.setCount(count);
                check = true;
            }
            catch (Exception e) {
                System.out.println("Incorrect input");
            }
        }

        return plane;
    }
}
