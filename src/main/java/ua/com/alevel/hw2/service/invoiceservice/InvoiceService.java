package ua.com.alevel.hw2.service.invoiceservice;

import ua.com.alevel.hw2.annotations.Autowired;
import ua.com.alevel.hw2.annotations.Singleton;
import ua.com.alevel.hw2.dao.InvoiceDao;
import ua.com.alevel.hw2.dao.mongodb.invoices.InvoiceDatabaseMongoDB;
import ua.com.alevel.hw2.model.Plane;
import ua.com.alevel.hw2.model.invoice.Invoice;
import ua.com.alevel.hw2.utils.SimpleOperationsOnInvoice;
import java.util.*;

@Singleton
public final class InvoiceService {
    private static InvoiceService instance;
    private final InvoiceDao invoiceDao;

    @Autowired
    private InvoiceService(InvoiceDatabaseMongoDB invoiceDatabaseJPA) {
        this.invoiceDao = invoiceDatabaseJPA;
    }

    public static InvoiceService getInstance() {
        if (instance == null) {
            instance = new InvoiceService(InvoiceDatabaseMongoDB.getInstance());
        }

        return instance;
    }

    public Invoice createInvoice(List<Plane> planes) {
        return new Invoice(SimpleOperationsOnInvoice.invoicePrice(planes), planes, new Date());
    }

    public void save(Invoice invoice) {
        if (invoice != null) {
            invoiceDao.save(invoice);
        }
        else {
            throw new IllegalArgumentException("Invoice was null");
        }
    }

    public List<Invoice> getInvoicesExpensiveThanPrice(int price) {
        return invoiceDao.getInvoicesExpensiveThanPrice(price);
    }

    public int getInvoiceCount() {
        return invoiceDao.getInvoiceCount();
    }

    public Optional<Invoice> findById(String id) {
        if (id != null) {
            return invoiceDao.findById(id);
        }
        else {
            throw new IllegalArgumentException("id was null");
        }
    }

    public void update(String id, Date date) {
        if (id != null && date != null) {
            invoiceDao.update(id, date);
        }
        else {
            throw new IllegalArgumentException("id was null or date was null");
        }
    }

    public Map<Integer, Integer> groupBySum() {
        return invoiceDao.groupBySum();
    }

    public List<Invoice> findAll() {
        return invoiceDao.findAll();
    }
}
