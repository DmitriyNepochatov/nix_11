package ua.com.alevel.hw2.command;

import ua.com.alevel.hw2.model.cargoplane.CargoPlane;
import ua.com.alevel.hw2.model.fighter.Fighter;
import ua.com.alevel.hw2.model.passengerplane.PassengerPlane;
import ua.com.alevel.hw2.model.PlaneType;
import ua.com.alevel.hw2.service.services.CargoPlaneService;
import ua.com.alevel.hw2.service.services.FighterService;
import ua.com.alevel.hw2.service.services.PassengerPlaneService;
import ua.com.alevel.hw2.service.PlaneService;
import ua.com.alevel.hw2.utils.UserInputUtil;
import ua.com.alevel.hw2.utils.UtilEnumToList;
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
            case FIGHTER -> FIGHTER_SERVICE.saveAll(FIGHTER_SERVICE.createPlane(UserInputUtil.getUserInputInt("Enter count")));
            case CARGO_PLANE -> CARGO_PLANE_SERVICE.saveAll(CARGO_PLANE_SERVICE.createPlane(UserInputUtil.getUserInputInt("Enter count")));
            case PASSENGER_PLANE -> PASSENGER_PLANE_SERVICE.saveAll(PASSENGER_PLANE_SERVICE.createPlane(UserInputUtil.getUserInputInt("Enter count")));
        }
    }
}
