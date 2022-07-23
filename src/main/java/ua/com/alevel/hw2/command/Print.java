package ua.com.alevel.hw2.command;

import ua.com.alevel.hw2.model.*;
import ua.com.alevel.hw2.service.CargoPlaneService;
import ua.com.alevel.hw2.service.FighterService;
import ua.com.alevel.hw2.service.PassengerPlaneService;
import ua.com.alevel.hw2.service.PlaneService;
import java.util.List;

public class Print implements Command {
    private static final PlaneService<Fighter> FIGHTER_SERVICE = FighterService.getInstance();
    private static final PlaneService<CargoPlane> CARGO_PLANE_SERVICE = CargoPlaneService.getInstance();
    private static final PlaneService<PassengerPlane> PASSENGER_PLANE_SERVICE = PassengerPlaneService.getInstance();

    @Override
    public void execute() {
        System.out.print("\nWhich planes do you want to print?");
        PlaneType[] planeTypes = PlaneType.values();
        List<String> names = UtilEnumToList.getNamesOfType(planeTypes);
        int userInput = UserInputUtil.getUserInput(planeTypes.length, names);
        switch (planeTypes[userInput]) {
            case FIGHTER -> print(FIGHTER_SERVICE);
            case CARGO_PLANE -> print(CARGO_PLANE_SERVICE);
            case PASSENGER_PLANE -> print(PASSENGER_PLANE_SERVICE);
        }
    }

    private void print(PlaneService<? extends Plane> service) {
        if (service.findAll().isEmpty()) {
            System.out.println("\nDatabase is empty");
        }
        else {
            System.out.println("\nDatabase: ");
            service.findAll().forEach(System.out::println);
        }
    }
}
