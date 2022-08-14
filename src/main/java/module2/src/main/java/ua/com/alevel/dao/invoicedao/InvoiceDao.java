package ua.com.alevel.dao.invoicedao;

import ua.com.alevel.model.invoice.Invoice;
import java.util.List;

public interface InvoiceDao {
    void save(Invoice invoice);

    List<Invoice> findAll();
}
