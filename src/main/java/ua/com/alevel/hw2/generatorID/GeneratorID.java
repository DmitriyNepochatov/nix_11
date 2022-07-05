package ua.com.alevel.hw2.generatorID;

public final class GeneratorID
{
    private static int idCounter=0;

    private GeneratorID() {}

    public static String createID()
    {
        return String.valueOf(idCounter++);
    }
}
