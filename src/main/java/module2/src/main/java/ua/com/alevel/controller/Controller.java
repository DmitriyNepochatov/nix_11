package ua.com.alevel.controller;

import ua.com.alevel.command.Command;
import ua.com.alevel.command.Commands;
import ua.com.alevel.service.shopservice.ShopService;
import ua.com.alevel.utils.UserInputUtil;
import ua.com.alevel.utils.UtilEnumToList;
import java.util.List;

public final class Controller {
    private Controller() {
    }

    public static void run() {
        Commands[] commands = Commands.values();
        boolean exit;

        ShopService.getInstance(UserInputUtil.getUserInputDouble("Enter price limit"))
                .createRandomInvoices(UserInputUtil.getUserInputInt("Enter how many invoices will be simulated"));

        do {
            exit = chooseAction(commands);
        } while (!exit);
    }

    private static boolean chooseAction(Commands[] commands) {
        System.out.print("\nChoose action:");
        List<String> commandsList = UtilEnumToList.getNamesOfType(commands);
        int choice = UserInputUtil.getUserInput(commandsList);
        Command command = commands[choice].getCommand();
        if (command == null) {
            return true;
        }
        else {
            command.execute();
            return false;
        }
    }
}
