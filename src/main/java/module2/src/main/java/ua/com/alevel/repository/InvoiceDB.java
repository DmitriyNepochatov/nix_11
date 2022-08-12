package ua.com.alevel.repository;

import ua.com.alevel.model.invoice.Invoice;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class InvoiceDB implements InvoiceDBI {
    private static InvoiceDB instance;
    private final List<Invoice> invoices;

    private InvoiceDB() {
        invoices = new ArrayList<>();
    }

    public static InvoiceDB getInstance() {
        if (instance == null) {
            instance = new InvoiceDB();
        }

        return instance;
    }

    @Override
    public void save(Invoice invoice) {
        if (!isDuplicate(invoice)) {
            invoices.add(invoice);
            System.out.println("\nInvoice has been saved: " + invoice);
        }
        else {
            System.out.println("\nInvoice is duplicate: " + invoice);
        }
    }

    @Override
    public List<Invoice> findAll() {
        if (invoices.isEmpty()) {
            System.out.println("\nNo invoices created");
            return Collections.emptyList();
        }
        else {
            return invoices;
        }
    }

    private boolean isDuplicate(Invoice invoice) {
        return invoices.stream().anyMatch(element -> element.equals(invoice));
    }
}
