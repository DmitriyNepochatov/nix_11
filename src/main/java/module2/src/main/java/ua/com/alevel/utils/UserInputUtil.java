package ua.com.alevel.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public final class UserInputUtil {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    private UserInputUtil() {
    }

    public static int getUserInput(List<String> names) {
        int userType = -1;
        do {
            userType = getUserInputDo(names);
        }
        while (userType == -1);

        return userType;
    }

    private static int getUserInputDo(List<String> names) {
        try {
            System.out.println("\nPlease enter number between 0 and " + (names.size() - 1));
            for (int i = 0; i < names.size(); i++) {
                System.out.printf("%d) %s%n", i, names.get(i));
            }

            System.out.println();
            System.out.print(">> ");
            int input = Integer.parseInt(READER.readLine());

            if (input >= 0 && input < names.size()) {
                return input;
            }
        }
        catch (IOException | NumberFormatException e) {
            System.out.println("Input is not valid");
        }
        return -1;
    }

    public static double getUserInputDouble(String command) {
        boolean check = false;
        double result = -1;
        while (!check) {
            System.out.print("\n" + command + " >> ");
            try {
                String value = READER.readLine();
                result = Double.parseDouble(value);

                if (result <= 0.0) {
                    throw new IllegalArgumentException();
                }

                check = true;
            }
            catch (Exception e) {
                System.out.println("Incorrect input");
            }
        }

        return result;
    }

    public static int getUserInputInt(String command) {
        boolean check = false;
        int result = -1;
        while (!check) {
            System.out.print("\n" + command + " >> ");
            try {
                String value = READER.readLine();
                result = Integer.parseInt(value);

                if (result <= 0) {
                    throw new IllegalArgumentException();
                }

                check = true;
            }
            catch (Exception e) {
                System.out.println("Incorrect input");
            }
        }

        return result;
    }
}
