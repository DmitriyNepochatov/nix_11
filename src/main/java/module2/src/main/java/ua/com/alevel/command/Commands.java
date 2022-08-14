package ua.com.alevel.command;

public enum Commands {
    PRINT_ALL_INVOICES("Print all invoices", new PrintAllInvoices()),
    MAKE_ALL_ANALYTICAL_DATA("Make all analytical data", new MakeAllAnalyticalData()),
    EXIT("Exit", null);

    private final Command command;
    private final String name;

    Commands(String name, Command command) {
        this.command = command;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Command getCommand() {
        return command;
    }
}
