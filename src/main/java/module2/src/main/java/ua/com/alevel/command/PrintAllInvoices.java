package ua.com.alevel.command;

import ua.com.alevel.dao.invoicedao.InvoiceDao;
import ua.com.alevel.dao.invoicedao.InvoiceDaoImpl;

public class PrintAllInvoices implements Command {
    private static final InvoiceDao invoiceDao = InvoiceDaoImpl.getInstance();

    @Override
    public void execute() {
        if (!invoiceDao.findAll().isEmpty()) {
            System.out.println("\nAll invoices:");
            invoiceDao.findAll()
                    .forEach(invoice -> {
                        System.out.println(invoice.getCustomer() + " " +
                                invoice.getInvoiceType() + " " +
                                invoice.getCreated());
                        invoice.getProductList().forEach(element -> System.out.println("   " + element));
                    });
        }
    }
}
