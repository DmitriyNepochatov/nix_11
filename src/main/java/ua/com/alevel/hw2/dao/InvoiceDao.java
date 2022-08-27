package ua.com.alevel.hw2.dao;

import ua.com.alevel.hw2.model.invoice.Invoice;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface InvoiceDao {
    void save(Invoice invoice);

    List<Invoice> getInvoicesExpensiveThanPrice(int price);

    int getInvoiceCount();

    Optional<Invoice> findById(String id);

    void update(String id, Date date);

    Map<Integer, Integer> groupBySum();

    List<Invoice> findAll();
}
