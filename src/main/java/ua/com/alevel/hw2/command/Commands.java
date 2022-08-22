package ua.com.alevel.hw2.command;

public enum Commands {
    CREATE("Create", new Create()),
    UPDATE("Update", new Update()),
    DELETE("Delete", new Delete()),
    PRINT_ALL("Print all", new PrintAll()),
    PRINT_PLANES_WHICH_IN_STOCK("Print planes which in stock", new PrintStock()),
    READ_FROM_FILE("Read from file", new ReadFromFile()),
    CREATE_INVOICE("Create invoice", new CreateInvoice()),
    INVOICES_EXPENSIVE_THAN_PRICE("Invoices expensive than price", new InvoicesExpensiveThanPrice()),
    INVOICE_COUNT("Invoice count", new InvoiceCount()),
    UPDATE_INVOICE("Update invoice", new UpdateInvoice()),
    GROUP_INVOICES_BY_SUM("Group invoices by sum", new GroupInvoicesBySum()),
    PRINT_ALL_INVOICES("Print all invoices", new PrintAllInvoices()),
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
