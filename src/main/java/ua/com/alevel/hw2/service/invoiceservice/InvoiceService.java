package ua.com.alevel.hw2.service.invoiceservice;

import ua.com.alevel.hw2.annotations.Autowired;
import ua.com.alevel.hw2.annotations.Singleton;
import ua.com.alevel.hw2.dao.invoices.InvoiceDatabase;
import ua.com.alevel.hw2.model.Plane;
import ua.com.alevel.hw2.model.invoice.Invoice;
import ua.com.alevel.hw2.utils.SimpleOperationsOnInvoice;
import java.util.*;

@Singleton
public final class InvoiceService {
    private static InvoiceService instance;
    private final InvoiceDatabase invoiceDatabase;

    @Autowired
    private InvoiceService(InvoiceDatabase invoiceDatabase) {
        this.invoiceDatabase = invoiceDatabase;
    }

    public static InvoiceService getInstance() {
        if (instance == null) {
            instance = new InvoiceService(InvoiceDatabase.getInstance());
        }

        return instance;
    }

    public Invoice createInvoice(List<Plane> planes) {
        return new Invoice(UUID.randomUUID().toString(), SimpleOperationsOnInvoice.invoicePrice(planes), planes, new Date());
    }

    public void save(Invoice invoice) {
        if (invoice != null) {
            invoiceDatabase.save(invoice);
        }
        else {
            throw new IllegalArgumentException("Invoice was null");
        }
    }

    public List<Invoice> getInvoicesExpensiveThanPrice(int price) {
        return invoiceDatabase.getInvoicesExpensiveThanPrice(price);
    }

    public int getInvoiceCount() {
        return invoiceDatabase.getInvoiceCount();
    }

    public Optional<Invoice> findById(String id) {
        if (id != null) {
            return invoiceDatabase.findById(id);
        }
        else {
            throw new IllegalArgumentException("id was null");
        }
    }

    public void update(String id, Date date) {
        if (id != null && date != null) {
            invoiceDatabase.update(id, date);
        }
        else {
            throw new IllegalArgumentException("id was null or date was null");
        }
    }

    public Map<Integer, Integer> groupBySum() {
        return invoiceDatabase.groupBySum();
    }

    public List<Invoice> findAll() {
        return invoiceDatabase.findAll();
    }
}
