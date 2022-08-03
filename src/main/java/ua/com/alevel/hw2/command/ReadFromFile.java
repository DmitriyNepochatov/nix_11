package ua.com.alevel.hw2.command;

import ua.com.alevel.hw2.model.*;
import ua.com.alevel.hw2.service.PlaneService;
import ua.com.alevel.hw2.service.reader.JSONReader;
import ua.com.alevel.hw2.service.reader.MyFileReader;
import ua.com.alevel.hw2.service.reader.XMLReader;
import ua.com.alevel.hw2.service.services.CargoPlaneService;
import ua.com.alevel.hw2.service.services.FighterService;
import ua.com.alevel.hw2.service.services.PassengerPlaneService;
import ua.com.alevel.hw2.utils.UserInputUtil;
import ua.com.alevel.hw2.utils.UtilEnumToList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ReadFromFile implements Command {
    private static final PlaneService<Fighter> FIGHTER_SERVICE = FighterService.getInstance();
    private static final PlaneService<CargoPlane> CARGO_PLANE_SERVICE = CargoPlaneService.getInstance();
    private static final PlaneService<PassengerPlane> PASSENGER_PLANE_SERVICE = PassengerPlaneService.getInstance();
    private static final MyFileReader XML_READER = XMLReader.getInstance();
    private static final MyFileReader JSON_READER = JSONReader.getInstance();
    private static final BufferedReader BUFFERED_READER = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void execute() {
        System.out.print("\nWhich plane do you want read from file?");
        PlaneType[] planeTypes = PlaneType.values();
        List<String> names = UtilEnumToList.getNamesOfType(planeTypes);
        int userInput = UserInputUtil.getUserInput(planeTypes.length, names);
        switch (planeTypes[userInput]) {
            case FIGHTER -> read(FIGHTER_SERVICE);
            case CARGO_PLANE -> read(CARGO_PLANE_SERVICE);
            case PASSENGER_PLANE -> read(PASSENGER_PLANE_SERVICE);
        }
    }

    private void read(PlaneService<? extends Plane> service) {
        System.out.print("\nEnter file name with format .xml or .json >> ");
        try {
            String fileName = BUFFERED_READER.readLine();

            if (fileName.lastIndexOf(".json") != -1) {
                JSON_READER.readPlaneFromFile(fileName).ifPresent(optional ->
                        service.save(service.createPlaneFromMapFoo(optional)));
            }
            else if (fileName.lastIndexOf(".xml") != -1) {
                XML_READER.readPlaneFromFile(fileName).ifPresent(optional ->
                        service.save(service.createPlaneFromMapFoo(optional)));
            }
            else {
                System.out.println("\nIncorrect file name");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
