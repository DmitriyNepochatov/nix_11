package ua.com.alevel.hw2.command;

import ua.com.alevel.hw2.model.*;
import ua.com.alevel.hw2.model.cargoplane.CargoPlane;
import ua.com.alevel.hw2.model.fighter.Fighter;
import ua.com.alevel.hw2.model.passengerplane.PassengerPlane;
import ua.com.alevel.hw2.service.services.CargoPlaneService;
import ua.com.alevel.hw2.service.services.FighterService;
import ua.com.alevel.hw2.service.services.PassengerPlaneService;
import ua.com.alevel.hw2.service.PlaneService;
import ua.com.alevel.hw2.utils.UserInputUtil;
import ua.com.alevel.hw2.utils.UtilEnumToList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class Delete implements Command {
    private static final PlaneService<Fighter> FIGHTER_SERVICE = FighterService.getInstance();
    private static final PlaneService<CargoPlane> CARGO_PLANE_SERVICE = CargoPlaneService.getInstance();
    private static final PlaneService<PassengerPlane> PASSENGER_PLANE_SERVICE = PassengerPlaneService.getInstance();
    private static final BufferedReader BUFFERED_READER = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void execute() {
        System.out.print("\nWhich plane do you want to remove?");
        PlaneType[] planeTypes = PlaneType.values();
        List<String> names = UtilEnumToList.getNamesOfType(planeTypes);
        int userInput = UserInputUtil.getUserInput(planeTypes.length, names);
        switch (planeTypes[userInput]) {
            case FIGHTER -> delete(FIGHTER_SERVICE);
            case CARGO_PLANE -> delete(CARGO_PLANE_SERVICE);
            case PASSENGER_PLANE -> delete(PASSENGER_PLANE_SERVICE);
        }
    }

    private void delete(PlaneService<? extends Plane> service) {
        if (service.findAll().isEmpty()) {
            System.out.println("\nDatabase is empty");
        }
        else {
            boolean check = false;
            while (!check) {
                System.out.print("\nEnter the id of the plane to be deleted >> ");
                try {
                    String id = BUFFERED_READER.readLine();
                    if (service.findById(id).isPresent()) {
                        service.delete(id);
                        check = true;
                    }
                }
                catch (Exception e) {
                    System.out.println("Incorrect input");
                }
            }
        }
    }
}
