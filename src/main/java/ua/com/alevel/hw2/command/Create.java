package ua.com.alevel.hw2.command;

import ua.com.alevel.hw2.model.CargoPlane;
import ua.com.alevel.hw2.model.Fighter;
import ua.com.alevel.hw2.model.PassengerPlane;
import ua.com.alevel.hw2.model.PlaneType;
import ua.com.alevel.hw2.service.CargoPlaneService;
import ua.com.alevel.hw2.service.FighterService;
import ua.com.alevel.hw2.service.PassengerPlaneService;
import ua.com.alevel.hw2.service.PlaneService;
import java.util.List;

public class Create implements Command {
    private static final PlaneService<Fighter> FIGHTER_SERVICE = FighterService.getInstance();
    private static final PlaneService<CargoPlane> CARGO_PLANE_SERVICE = CargoPlaneService.getInstance();
    private static final PlaneService<PassengerPlane> PASSENGER_PLANE_SERVICE = PassengerPlaneService.getInstance();

    @Override
    public void execute() {
        System.out.print("\nWhat type of plane do you want to create and save in the database?");
        PlaneType[] planeTypes = PlaneType.values();
        List<String> names = UtilEnumToList.getNamesOfType(planeTypes);
        int userInput = UserInputUtil.getUserInput(planeTypes.length, names);
        switch (planeTypes[userInput]) {
            case FIGHTER -> FIGHTER_SERVICE.save(FIGHTER_SERVICE.createPlane());
            case CARGO_PLANE -> CARGO_PLANE_SERVICE.save(CARGO_PLANE_SERVICE.createPlane());
            case PASSENGER_PLANE -> PASSENGER_PLANE_SERVICE.save(PASSENGER_PLANE_SERVICE.createPlane());
        }
    }
}
