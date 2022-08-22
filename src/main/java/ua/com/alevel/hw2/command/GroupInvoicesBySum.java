package ua.com.alevel.hw2.command;

import ua.com.alevel.hw2.service.invoiceservice.InvoiceService;
import java.util.Map;

public class GroupInvoicesBySum implements Command {
    private static final InvoiceService INVOICE_SERVICE = InvoiceService.getInstance();

    @Override
    public void execute() {
        Map<Integer, Integer> groups = INVOICE_SERVICE.groupBySum();

        if (groups.isEmpty()) {
            System.out.println("\nNo invoices");
        }
        else {
            System.out.println("\nGroup by sum:");
            groups.forEach((key, value) -> System.out.println("Sum: " + key + " Count: " + value));
        }
    }
}
