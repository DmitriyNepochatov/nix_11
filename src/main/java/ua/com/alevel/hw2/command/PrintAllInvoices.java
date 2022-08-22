package ua.com.alevel.hw2.command;

import ua.com.alevel.hw2.model.invoice.Invoice;
import ua.com.alevel.hw2.service.invoiceservice.InvoiceService;
import java.util.List;

public class PrintAllInvoices implements Command {
    private static final InvoiceService INVOICE_SERVICE = InvoiceService.getInstance();

    @Override
    public void execute() {
        List<Invoice> invoices = INVOICE_SERVICE.findAll();

        if (invoices.isEmpty()) {
            System.out.println("\nNo invoices");
        }
        else {
            System.out.println("\nInvoices:");
            invoices.forEach(invoice ->
            {
                System.out.println("id: " + invoice.getId() + " sum: " + invoice.getSum() + " created: " + invoice.getCreated());
                invoice.getProducts()
                        .forEach(plane -> System.out.println("   " + plane));
            });
        }
    }
}
