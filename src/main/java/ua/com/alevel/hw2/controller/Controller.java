package ua.com.alevel.hw2.controller;

import ua.com.alevel.hw2.command.Command;
import ua.com.alevel.hw2.command.Commands;
import ua.com.alevel.hw2.command.UserInputUtil;
import ua.com.alevel.hw2.command.UtilEnumToList;
import java.util.List;

public final class Controller {
    private Controller() {
    }

    public static void run() {
        int change = chooseAction();
        Commands[] commands = Commands.values();
        Command command = commands[change].getCommand();
        command.execute();
        run();
    }

    private static int chooseAction() {
        System.out.print("\nChoose action:");
        Commands[] commands = Commands.values();
        List<String> commandsList = UtilEnumToList.getNamesOfType(commands);
        return UserInputUtil.getUserInput(commands.length, commandsList);
    }
}
