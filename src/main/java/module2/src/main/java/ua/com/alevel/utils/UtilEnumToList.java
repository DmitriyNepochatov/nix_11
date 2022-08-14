package ua.com.alevel.utils;

import ua.com.alevel.command.Commands;
import java.util.ArrayList;
import java.util.List;

public final class UtilEnumToList {
    private UtilEnumToList() {
    }

    public static List<String> getNamesOfType(Commands[] commands) {
        List<String> names = new ArrayList<>(commands.length);
        for (Commands type : commands) {
            names.add(type.getName());
        }
        return names;
    }
}