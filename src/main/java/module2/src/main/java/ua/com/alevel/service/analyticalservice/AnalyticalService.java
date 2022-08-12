package ua.com.alevel.service.analyticalservice;

import ua.com.alevel.dao.invoicedao.InvoiceDao;
import ua.com.alevel.dao.invoicedao.InvoiceDaoImpl;
import ua.com.alevel.model.invoice.Invoice;
import ua.com.alevel.model.invoice.InvoiceType;
import ua.com.alevel.model.product.Product;
import ua.com.alevel.model.product.ProductType;
import ua.com.alevel.utils.SimpleOperationsOnInvoice;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;

public final class AnalyticalService {
    private static AnalyticalService instance;
    private static final InvoiceDao invoiceDao = InvoiceDaoImpl.getInstance();

    private AnalyticalService() {
    }

    public static AnalyticalService getInstance() {
        if (instance == null) {
            instance = new AnalyticalService();
        }

        return instance;
    }

    private List<Invoice> getList() {
        //return invoiceDao.findAll();     //Аналогичная проблема что и в ShopService
        return InvoiceDaoImpl.getInstance().findAll();
    }

    public void numberOfItemsSoldByCategory() {
        List<Invoice> invoices = getList();
        if (!invoices.isEmpty()) {
            Arrays.stream(ProductType.values())
                    .forEach(productType -> numberOfItemsSoldByCategoryPrint(productType, invoices));
        }
    }

    private void numberOfItemsSoldByCategoryPrint(ProductType productType, List<Invoice> invoices) {
        System.out.println("\n" + productType.getName() + "s sold: " + invoices.stream()
                .flatMap(invoice -> invoice.getProductList().stream())
                .filter(product -> product.getClass().getSimpleName().equals(productType.getName()))
                .count());
    }

    public void sumTheLowestInvoice() {
        List<Invoice> invoices = getList();
        if (!invoices.isEmpty()) {
            int sizeTheLowestInvoice = invoices.stream()
                    .sorted(Comparator.comparing(element -> element.getProductList().size()))
                    .toList()
                    .get(0)
                    .getProductList()
                    .size();

            Invoice invoice = invoices.stream()
                    .filter(element -> element.getProductList().size() == sizeTheLowestInvoice)
                    .sorted(Comparator.comparing(element -> SimpleOperationsOnInvoice.invoicePrice(element.getProductList())))
                    .toList()
                    .get(0);

            System.out.println("\n" + invoice.getCustomer() + " has the lowest invoice, and price is " +
                    SimpleOperationsOnInvoice.invoicePrice(invoice.getProductList()));
        }
    }

    public void sumOfAllInvoices() {
        List<Invoice> invoices = getList();
        if (!invoices.isEmpty()) {
            System.out.println("\nSum of all invoices: " + invoices.stream()
                    .flatMap(invoice -> invoice.getProductList().stream())
                    .mapToDouble(Product::getPrice)
                    .sum());
        }
    }

    public void retailInvoicesCount() {
        List<Invoice> invoices = getList();
        if (!invoices.isEmpty()) {
            System.out.println("\nCount of retail invoices: " + invoices.stream()
                    .filter(invoice -> invoice.getInvoiceType().equals(InvoiceType.RETAIL))
                    .count());
        }
    }

    public void invoicesContainsOnlyOneProductType() {
        List<Invoice> invoices = getList();
        if (!invoices.isEmpty()) {
            BiPredicate<Invoice, ProductType> predicate = (invoice, productType) -> invoice.getProductList().stream()
                    .allMatch(product -> product.getClass().getSimpleName().equals(productType.getName()));

            Arrays.stream(ProductType.values())
                    .forEach(productType -> invoicesContainsOnlyOneProductTypePrint(productType, invoices, predicate));
        }
    }

    private void invoicesContainsOnlyOneProductTypePrint(ProductType productType, List<Invoice> invoices,
                                                         BiPredicate<Invoice, ProductType> predicate) {
        System.out.println("\nInvoices which contains only " + productType.getName() + "s :");
        List<Invoice> result = invoicesContainsOnlyOneProductTypeFind(invoices, predicate, productType);
        System.out.println(result.isEmpty() ? "There are no such invoices" : result);
    }

    private List<Invoice> invoicesContainsOnlyOneProductTypeFind(List<Invoice> invoices, BiPredicate<Invoice,
            ProductType> predicate, ProductType productType) {

        return invoices.stream()
                .filter(invoice -> predicate.test(invoice, productType))
                .toList();
    }

    public void printFirstThreeInvoices() {
        List<Invoice> invoices = getList();
        if (!invoices.isEmpty()) {
            System.out.println("\nFirst three invoices: ");
            invoices.stream()
                    .sorted(Comparator.comparing(Invoice::getCreated))
                    .limit(3)
                    .forEach(System.out::println);
        }
    }

    public void printInvoicesWhichWereMadeMinorsCustomers() {
        List<Invoice> invoices = getList();
        if (!invoices.isEmpty()) {
            List<Invoice> result = invoices.stream()
                    .filter(invoice -> invoice.getCustomer().getAge() < 18)
                    .toList();

            if (!result.isEmpty()) {
                System.out.println("\nInvoices which were made minors customers:");
                result.forEach(invoice -> {
                    invoice.setInvoiceType(InvoiceType.LOW_AGE);
                    System.out.println(invoice);
                });
            }
            else {
                System.out.println("\nAll customers are adults");
            }
        }
    }

    public void sortAllInvoices() {
        List<Invoice> invoices = getList();
        if (!invoices.isEmpty()) {
            System.out.println("\nSorted invoices:");

            Comparator<Invoice> ageComparator = Comparator
                    .comparing(invoice -> invoice.getCustomer().getAge(), Comparator.reverseOrder());
            Comparator<Invoice> productListSizeComparator = Comparator
                    .comparing(invoice -> invoice.getProductList().size());
            Comparator<Invoice> invoiceSumComparator = Comparator
                    .comparing(invoice -> SimpleOperationsOnInvoice.invoicePrice(invoice.getProductList()));

            invoices.stream()
                    .sorted(ageComparator
                            .thenComparing(productListSizeComparator)
                            .thenComparing(invoiceSumComparator))
                    .forEach(System.out::println);
        }
    }
}
