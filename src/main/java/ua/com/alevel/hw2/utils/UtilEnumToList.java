package ua.com.alevel.hw2.utils;

import ua.com.alevel.hw2.command.Commands;
import ua.com.alevel.hw2.model.PlaneBrand;
import ua.com.alevel.hw2.model.PlaneType;
import java.util.ArrayList;
import java.util.List;

public final class UtilEnumToList {
    private UtilEnumToList() {
    }

    public static List<String> getNamesOfType(PlaneType[] planeTypes) {
        List<String> names = new ArrayList<>(planeTypes.length);
        for (PlaneType type : planeTypes) {
            names.add(type.name());
        }
        return names;
    }

    public static List<String> getNamesOfType(Commands[] commands) {
        List<String> names = new ArrayList<>(commands.length);
        for (Commands type : commands) {
            names.add(type.name());
        }
        return names;
    }

    public static List<String> getNamesOfType(PlaneBrand[] brands) {
        List<String> names = new ArrayList<>(brands.length);
        for (PlaneBrand type : brands) {
            names.add(type.name());
        }
        return names;
    }
}
