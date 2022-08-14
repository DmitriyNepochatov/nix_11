package ua.com.alevel.repository;

import ua.com.alevel.model.invoice.Invoice;
import java.util.List;

public interface InvoiceDBI {
    void save(Invoice invoice);

    List<Invoice> findAll();
}
