package ua.com.alevel.hw2.command;

public enum Commands {
    CREATE("Create", new Create()),
    UPDATE("Update", new Update()),
    DELETE("Delete", new Delete()),
    PRINT("Print", new Print()),
    READ_FROM_FILE("Read from file", new ReadFromFile()),
    EXIT("Exit", null);

    private final String name;
    private final Command command;

    Commands(String name, Command command) {
        this.name = name;
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public Command getCommand() {
        return command;
    }
}
