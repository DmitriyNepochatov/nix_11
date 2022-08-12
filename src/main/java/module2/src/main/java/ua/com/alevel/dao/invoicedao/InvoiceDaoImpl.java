package ua.com.alevel.dao.invoicedao;

import ua.com.alevel.model.invoice.Invoice;
import ua.com.alevel.repository.InvoiceDB;
import ua.com.alevel.repository.InvoiceDBI;
import java.util.List;

public final class InvoiceDaoImpl implements InvoiceDao {
    private static InvoiceDaoImpl instance;
    private final InvoiceDBI invoiceDB = InvoiceDB.getInstance();

    private InvoiceDaoImpl() {
    }

    public static InvoiceDaoImpl getInstance() {
        if (instance == null) {
            instance = new InvoiceDaoImpl();
        }

        return instance;
    }

    @Override
    public void save(Invoice invoice) {
        invoiceDB.save(invoice);
    }

    @Override
    public List<Invoice> findAll() {
        return invoiceDB.findAll();
    }
}
