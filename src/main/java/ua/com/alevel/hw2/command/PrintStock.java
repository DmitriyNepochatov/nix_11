package ua.com.alevel.hw2.command;

import ua.com.alevel.hw2.model.Plane;
import ua.com.alevel.hw2.model.PlaneType;
import ua.com.alevel.hw2.model.cargoplane.CargoPlane;
import ua.com.alevel.hw2.model.fighter.Fighter;
import ua.com.alevel.hw2.model.passengerplane.PassengerPlane;
import ua.com.alevel.hw2.service.PlaneService;
import ua.com.alevel.hw2.service.services.CargoPlaneService;
import ua.com.alevel.hw2.service.services.FighterService;
import ua.com.alevel.hw2.service.services.PassengerPlaneService;
import ua.com.alevel.hw2.utils.UserInputUtil;
import ua.com.alevel.hw2.utils.UtilEnumToList;
import java.util.List;

public class PrintStock implements Command {
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
        if (service.findAllInStock().isEmpty()) {
            System.out.println("\nNo planes in stock");
        }
        else {
            System.out.println("\nPlanes in stock: ");
            service.findAllInStock().forEach(System.out::println);
        }
    }
}