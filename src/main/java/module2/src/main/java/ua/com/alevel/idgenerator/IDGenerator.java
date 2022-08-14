package ua.com.alevel.idgenerator;

public final class IDGenerator {
    private static int idCounter = 0;

    private IDGenerator() {
    }

    public static String createID() {
        return String.valueOf(idCounter++);
    }
}