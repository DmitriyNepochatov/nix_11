package ua.com.alevel.service.analyticalservice;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.com.alevel.dao.invoicedao.InvoiceDao;
import ua.com.alevel.dao.invoicedao.InvoiceDaoImpl;
import ua.com.alevel.dao.productdao.ProductDaoImpl;
import ua.com.alevel.model.customer.Customer;
import ua.com.alevel.model.invoice.Invoice;
import ua.com.alevel.model.invoice.InvoiceType;
import ua.com.alevel.model.product.Product;
import ua.com.alevel.service.parser.DataParser;
import ua.com.alevel.service.personservice.PersonService;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


class AnalyticalServiceTest {
    private static AnalyticalService target;
    private static InvoiceDao invoiceDao;

    @BeforeAll
    public static void setup() {
        target = AnalyticalService.getInstance();
        invoiceDao = Mockito.mock(InvoiceDaoImpl.class);
        setMock(invoiceDao);
    }

    @BeforeEach
    public void setUp() {
        Mockito.reset(invoiceDao);
    }

    @Test
    void numberOfItemsSoldByCategory_positive() {
        List<Product> products = DataParser.getInstance().parse(ProductDaoImpl.getInstance().findAll());

        List<Product> productsForInvoice1 = new ArrayList<>();
        productsForInvoice1.add(products.get(0));
        productsForInvoice1.add(products.get(1));
        productsForInvoice1.add(products.get(5));
        Invoice invoice1 = new Invoice(productsForInvoice1, null, null, null);

        List<Product> productsForInvoice2 = new ArrayList<>();
        productsForInvoice2.add(products.get(0));
        productsForInvoice2.add(products.get(1));
        Invoice invoice2 = new Invoice(productsForInvoice2, null, null, null);

        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);

        Mockito.when(invoiceDao.findAll()).thenReturn(invoices);
        System.out.println("numberOfItemsSoldByCategory_positive------------------");
        System.out.println("Expected:\nTelevisions: 1\nTelephones:4");
        System.out.println("Actual:");
        target.numberOfItemsSoldByCategory();
        System.out.println("numberOfItemsSoldByCategory_positive------------------");
    }

    @Test
    void numberOfItemsSoldByCategory_negative() {
        Mockito.when(invoiceDao.findAll()).thenReturn(Collections.emptyList());
        System.out.println("numberOfItemsSoldByCategory_negative------------------");
        System.out.println("Expected:\nnone");
        System.out.println("Actual:");
        target.numberOfItemsSoldByCategory();
        System.out.println("numberOfItemsSoldByCategory_negative------------------");
    }

    @Test
    void sumTheLowestInvoice_positive() {
        List<Product> products = DataParser.getInstance().parse(ProductDaoImpl.getInstance().findAll());

        List<Product> productsForInvoice1 = new ArrayList<>();
        productsForInvoice1.add(products.get(0));
        productsForInvoice1.add(products.get(1));
        productsForInvoice1.add(products.get(5));
        Invoice invoice1 = new Invoice(productsForInvoice1, PersonService.getInstance().createRandomCustomer(), null, null);

        List<Product> productsForInvoice2 = new ArrayList<>();
        productsForInvoice2.add(products.get(0));
        productsForInvoice2.add(products.get(1));
        Invoice invoice2 = new Invoice(productsForInvoice2, PersonService.getInstance().createRandomCustomer(), null, null);

        List<Product> productsForInvoice3 = new ArrayList<>();
        productsForInvoice3.add(products.get(0));
        Invoice invoice3 = new Invoice(productsForInvoice3, PersonService.getInstance().createRandomCustomer(), null, null);

        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);

        Mockito.when(invoiceDao.findAll()).thenReturn(invoices);
        System.out.println("sumTheLowestInvoice_positive------------------");
        System.out.println("Expected:\n" + invoice3);
        System.out.println("Actual:");
        target.sumTheLowestInvoice();
        System.out.println("sumTheLowestInvoice_positive------------------");
    }

    @Test
    void sumTheLowestInvoice_negative() {
        Mockito.when(invoiceDao.findAll()).thenReturn(Collections.emptyList());
        System.out.println("sumTheLowestInvoice_negative------------------");
        System.out.println("Expected:\nnone");
        System.out.println("Actual:");
        target.sumTheLowestInvoice();
        System.out.println("sumTheLowestInvoice_negative------------------");
    }

    @Test
    void sumOfAllInvoices_positive() {
        List<Product> products = DataParser.getInstance().parse(ProductDaoImpl.getInstance().findAll());

        List<Product> productsForInvoice1 = new ArrayList<>();
        productsForInvoice1.add(products.get(0));
        productsForInvoice1.add(products.get(1));
        productsForInvoice1.add(products.get(5));
        Invoice invoice1 = new Invoice(productsForInvoice1, PersonService.getInstance().createRandomCustomer(), null, null);

        List<Product> productsForInvoice2 = new ArrayList<>();
        productsForInvoice2.add(products.get(0));
        productsForInvoice2.add(products.get(1));
        Invoice invoice2 = new Invoice(productsForInvoice2, PersonService.getInstance().createRandomCustomer(), null, null);

        List<Product> productsForInvoice3 = new ArrayList<>();
        productsForInvoice3.add(products.get(0));
        Invoice invoice3 = new Invoice(productsForInvoice3, PersonService.getInstance().createRandomCustomer(), null, null);

        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);

        Mockito.when(invoiceDao.findAll()).thenReturn(invoices);
        System.out.println("sumOfAllInvoices_positive------------------");
        System.out.println("Expected:\n3500");
        System.out.println("Actual:");
        target.sumOfAllInvoices();
        System.out.println("sumOfAllInvoices_positive------------------");
    }

    @Test
    void sumOfAllInvoices_negative() {
        Mockito.when(invoiceDao.findAll()).thenReturn(Collections.emptyList());
        System.out.println("sumOfAllInvoices_negative------------------");
        System.out.println("Expected:\nnone");
        System.out.println("Actual:");
        target.sumOfAllInvoices();
        System.out.println("sumOfAllInvoices_negative------------------");
    }

    @Test
    void retailInvoicesCount_positive() {
        Invoice invoice1 = new Invoice(Collections.emptyList(), null, InvoiceType.RETAIL, null);
        Invoice invoice2 = new Invoice(Collections.emptyList(), null, InvoiceType.RETAIL, null);
        Invoice invoice3 = new Invoice(Collections.emptyList(), null, InvoiceType.WHOLESALE, null);

        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);

        Mockito.when(invoiceDao.findAll()).thenReturn(invoices);
        System.out.println("retailInvoicesCount_positive------------------");
        System.out.println("Expected:\n2");
        System.out.println("Actual:");
        target.retailInvoicesCount();
        System.out.println("retailInvoicesCount_positive------------------");
    }

    @Test
    void retailInvoicesCount_negative() {
        Mockito.when(invoiceDao.findAll()).thenReturn(Collections.emptyList());
        System.out.println("retailInvoicesCount_negative------------------");
        System.out.println("Expected:\nnone");
        System.out.println("Actual:");
        target.retailInvoicesCount();
        System.out.println("retailInvoicesCount_negative------------------");
    }

    @Test
    void invoicesContainsOnlyOneProductType_positive() {
        List<Product> products = DataParser.getInstance().parse(ProductDaoImpl.getInstance().findAll());

        List<Product> productsForInvoice1 = new ArrayList<>();
        productsForInvoice1.add(products.get(0));
        productsForInvoice1.add(products.get(1));
        productsForInvoice1.add(products.get(5));
        Invoice invoice1 = new Invoice(productsForInvoice1, PersonService.getInstance().createRandomCustomer(), null, null);

        List<Product> productsForInvoice2 = new ArrayList<>();
        productsForInvoice2.add(products.get(0));
        productsForInvoice2.add(products.get(1));
        Invoice invoice2 = new Invoice(productsForInvoice2, PersonService.getInstance().createRandomCustomer(), null, null);

        List<Product> productsForInvoice3 = new ArrayList<>();
        productsForInvoice3.add(products.get(0));
        Invoice invoice3 = new Invoice(productsForInvoice3, PersonService.getInstance().createRandomCustomer(), null, null);

        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);

        Mockito.when(invoiceDao.findAll()).thenReturn(invoices);
        System.out.println("invoicesContainsOnlyOneProductType_positive------------------");
        System.out.println("Expected:\nTelephones\n" + invoice2 + "\n" + invoice3 + "\nTelevisions:\nNo invoices");
        System.out.println("Actual:");
        target.invoicesContainsOnlyOneProductType();
        System.out.println("invoicesContainsOnlyOneProductType_positive------------------");
    }

    @Test
    void invoicesContainsOnlyOneProductType_negative() {
        Mockito.when(invoiceDao.findAll()).thenReturn(Collections.emptyList());
        System.out.println("invoicesContainsOnlyOneProductType_negative------------------");
        System.out.println("Expected:\nnone");
        System.out.println("Actual:");
        target.invoicesContainsOnlyOneProductType();
        System.out.println("invoicesContainsOnlyOneProductType_negative------------------");
    }

    @Test
    void printFirstThreeInvoices_positive() throws InterruptedException {
        List<Product> products = DataParser.getInstance().parse(ProductDaoImpl.getInstance().findAll());

        List<Product> productsForInvoice1 = new ArrayList<>();
        productsForInvoice1.add(products.get(0));
        productsForInvoice1.add(products.get(1));
        productsForInvoice1.add(products.get(5));
        Invoice invoice1 = new Invoice(productsForInvoice1, PersonService.getInstance().createRandomCustomer(), null, new Date());
        Thread.sleep(1000);
        List<Product> productsForInvoice2 = new ArrayList<>();
        productsForInvoice2.add(products.get(0));
        productsForInvoice2.add(products.get(1));
        Invoice invoice2 = new Invoice(productsForInvoice2, PersonService.getInstance().createRandomCustomer(), null, new Date());
        Thread.sleep(1000);
        List<Product> productsForInvoice3 = new ArrayList<>();
        productsForInvoice3.add(products.get(0));
        Invoice invoice3 = new Invoice(productsForInvoice3, PersonService.getInstance().createRandomCustomer(), null, new Date());
        Thread.sleep(1000);
        List<Product> productsForInvoice4 = new ArrayList<>();
        productsForInvoice4.add(products.get(8));
        productsForInvoice4.add(products.get(9));
        Invoice invoice4 = new Invoice(productsForInvoice4, PersonService.getInstance().createRandomCustomer(), null, new Date());

        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);
        invoices.add(invoice4);

        Mockito.when(invoiceDao.findAll()).thenReturn(invoices);
        System.out.println("printFirstThreeInvoices_positive------------------");
        System.out.println("Expected:\n" + invoice1 + "\n" + invoice2 + "\n" + invoice3);
        System.out.println("Actual:");
        target.printFirstThreeInvoices();
        System.out.println("printFirstThreeInvoices_positive------------------");
    }

    @Test
    void printFirstThreeInvoices_negative() {
        Mockito.when(invoiceDao.findAll()).thenReturn(Collections.emptyList());
        System.out.println("printFirstThreeInvoices_negative------------------");
        System.out.println("Expected:\nnone");
        System.out.println("Actual:");
        target.printFirstThreeInvoices();
        System.out.println("printFirstThreeInvoices_negative------------------");
    }

    @Test
    void printInvoicesWhichWereMadeMinorsCustomers_positive() {
        List<Product> products = DataParser.getInstance().parse(ProductDaoImpl.getInstance().findAll());

        List<Product> productsForInvoice1 = new ArrayList<>();
        productsForInvoice1.add(products.get(0));
        productsForInvoice1.add(products.get(1));
        productsForInvoice1.add(products.get(5));
        Invoice invoice1 = new Invoice(productsForInvoice1, new Customer("0", "1@mailcom", 65), null, null);

        List<Product> productsForInvoice2 = new ArrayList<>();
        productsForInvoice2.add(products.get(0));
        productsForInvoice2.add(products.get(1));
        Invoice invoice2 = new Invoice(productsForInvoice2, new Customer("1", "2@mail.com", 14), null, null);

        List<Product> productsForInvoice3 = new ArrayList<>();
        productsForInvoice3.add(products.get(0));
        Invoice invoice3 = new Invoice(productsForInvoice3, new Customer("2", "3@mail.com", 54), null, null);

        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);

        Mockito.when(invoiceDao.findAll()).thenReturn(invoices);
        System.out.println("printInvoicesWhichWereMadeMinorsCustomers_positive------------------");
        System.out.println("Expected:\n" + invoice2);
        System.out.println("Actual:");
        target.printInvoicesWhichWereMadeMinorsCustomers();
        System.out.println("printInvoicesWhichWereMadeMinorsCustomers_positive------------------");
    }

    @Test
    void printInvoicesWhichWereMadeMinorsCustomers_negative() {
        Mockito.when(invoiceDao.findAll()).thenReturn(Collections.emptyList());
        System.out.println("printInvoicesWhichWereMadeMinorsCustomers_negative------------------");
        System.out.println("Expected:\nnone");
        System.out.println("Actual:");
        target.printInvoicesWhichWereMadeMinorsCustomers();
        System.out.println("printInvoicesWhichWereMadeMinorsCustomers_negative------------------");
    }

    @Test
    void sortAllInvoices_positive() {
        List<Product> products = DataParser.getInstance().parse(ProductDaoImpl.getInstance().findAll());

        List<Product> productsForInvoice1 = new ArrayList<>();
        productsForInvoice1.add(products.get(0));
        productsForInvoice1.add(products.get(1));
        productsForInvoice1.add(products.get(5));
        Invoice invoice1 = new Invoice(productsForInvoice1, new Customer("0", "1@mailcom", 65), null, null);

        List<Product> productsForInvoice2 = new ArrayList<>();
        productsForInvoice2.add(products.get(0));
        productsForInvoice2.add(products.get(1));
        Invoice invoice2 = new Invoice(productsForInvoice2, new Customer("1", "2@mail.com", 14), null, null);

        List<Product> productsForInvoice3 = new ArrayList<>();
        productsForInvoice3.add(products.get(0));
        Invoice invoice3 = new Invoice(productsForInvoice3, new Customer("2", "3@mail.com", 54), null, null);

        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);

        Mockito.when(invoiceDao.findAll()).thenReturn(invoices);
        System.out.println("sortAllInvoices_positive------------------");
        System.out.println("Expected:\n" + invoice1 + "\n" + invoice3 + "\n" + invoice2);
        System.out.println("Actual:");
        target.sortAllInvoices();
        System.out.println("sortAllInvoices_positive------------------");
    }

    @Test
    void sortAllInvoices_negative() {
        Mockito.when(invoiceDao.findAll()).thenReturn(Collections.emptyList());
        System.out.println("sortAllInvoices_negative------------------");
        System.out.println("Expected:\nnone");
        System.out.println("Actual:");
        target.sortAllInvoices();
        System.out.println("sortAllInvoices_negative------------------");
    }

    private static void setMock(InvoiceDao mock) {
        try {
            Field instance = InvoiceDaoImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, mock);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}