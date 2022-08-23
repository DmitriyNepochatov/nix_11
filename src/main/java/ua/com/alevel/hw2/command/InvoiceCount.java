package ua.com.alevel.hw2.command;

import ua.com.alevel.hw2.service.invoiceservice.InvoiceService;

public class InvoiceCount implements Command {
    private static final InvoiceService INVOICE_SERVICE = InvoiceService.getInstance();

    @Override
    public void execute() {
        System.out.print("\nInvoice count: " + INVOICE_SERVICE.getInvoiceCount()+"\n");
    }
}
