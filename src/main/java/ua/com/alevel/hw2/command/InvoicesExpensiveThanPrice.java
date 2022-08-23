package ua.com.alevel.hw2.command;

import ua.com.alevel.hw2.model.invoice.Invoice;
import ua.com.alevel.hw2.service.invoiceservice.InvoiceService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class InvoicesExpensiveThanPrice implements Command {
    private static final InvoiceService INVOICE_SERVICE = InvoiceService.getInstance();
    private static final BufferedReader BUFFERED_READER = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void execute() {
        boolean check = false;

        while (!check) {
            try {
                System.out.print("\nEnter price >> ");
                int price = Integer.parseInt(BUFFERED_READER.readLine());
                if (price <= 0) {
                    throw new RuntimeException();
                }

                List<Invoice> invoices = INVOICE_SERVICE.getInvoicesExpensiveThanPrice(price);
                if (invoices.isEmpty()) {
                    System.out.println("Invoices not found");
                }
                else {
                    invoices.forEach(invoice ->
                    {
                        System.out.println("id: " + invoice.getId() + " sum: " + invoice.getSum() + " created: " + invoice.getCreated());
                        invoice.getProducts()
                                .forEach(plane -> System.out.println("   " + plane));
                    });
                }

                check = true;
            }
            catch (Exception e) {
                System.out.println("Incorrect input");
            }
        }
    }
}
