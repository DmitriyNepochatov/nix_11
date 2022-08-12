package ua.com.alevel.service.shopservice;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.com.alevel.dao.invoicedao.InvoiceDao;
import ua.com.alevel.dao.invoicedao.InvoiceDaoImpl;
import ua.com.alevel.dao.productdao.ProductDao;
import ua.com.alevel.dao.productdao.ProductDaoImpl;
import ua.com.alevel.model.customer.Customer;
import ua.com.alevel.model.invoice.Invoice;
import ua.com.alevel.model.product.Product;
import ua.com.alevel.model.product.telephone.Telephone;
import ua.com.alevel.model.product.television.Television;
import ua.com.alevel.service.parser.DataParser;
import ua.com.alevel.service.personservice.PersonService;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;

class ShopServiceTest {
    private static ShopService target;
    private static ProductDao productDao;
    private static InvoiceDao invoiceDao;
    private static DataParser dataParser;
    private static PersonService personService;

    @BeforeAll
    public static void setup() {
        target = ShopService.getInstance(1500);
        productDao = Mockito.mock(ProductDaoImpl.class);
        invoiceDao = Mockito.mock(InvoiceDaoImpl.class);
        dataParser = Mockito.mock(DataParser.class);
        personService = Mockito.mock(PersonService.class);
        setMock(productDao);
        setMock(invoiceDao);
        setMock(dataParser);
        setMock(personService);
    }

    @BeforeEach
    public void setUp() {
        Mockito.reset(productDao);
        Mockito.reset(invoiceDao);
        Mockito.reset(dataParser);
        Mockito.reset(personService);
    }

    @Test
    void createRandomInvoices_positive() {
        List<String> data = new ArrayList<>();
        data.add("type,series,model,diagonal,screen type,country,price");
        data.add("Telephone,9R,OnePlus,none,OLED,none,500");
        data.add("Telephone,S20,Samsung,none,OLED,none,700");
        data.add("Television,P1 43,none,43,LED,Taiwan,2000");
        data.add("Television,UF-45A45,none,55,VA,USA,3000");

        Telephone telephone1 = new Telephone("9R", "OLED", 500, "OnePlus");
        Telephone telephone2 = new Telephone("S20", "OLED", 700, "Samsung");
        Television television1 = new Television("P1 43", "LED", 2000, 43, "Taiwan");
        Television television2 = new Television("UF-45A45", "VA", 3000, 55, "USA");
        List<Product> products = new ArrayList<>();
        products.add(telephone1);
        products.add(telephone2);
        products.add(television1);
        products.add(television2);

        Mockito.when(productDao.findAll()).thenReturn(data);
        Mockito.when(dataParser.parse(data)).thenReturn(products);
        Mockito.when(personService.createRandomCustomer()).thenReturn(new Customer(null, null, 0));
        int count = 4;
        target.createRandomInvoices(count);
        Mockito.verify(invoiceDao, Mockito.times(count)).save(any(Invoice.class));
    }

    @Test
    void createRandomInvoices_negative() {
        List<String> data = new ArrayList<>();
        Mockito.when(productDao.findAll()).thenReturn(data);
        int count = 4;
        target.createRandomInvoices(count);
        Mockito.verify(invoiceDao, Mockito.times(0)).save(any(Invoice.class));
    }

    private static void setMock(ProductDao mock) {
        try {
            Field instance = ProductDaoImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, mock);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    private static void setMock(DataParser mock) {
        try {
            Field instance = DataParser.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, mock);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void setMock(PersonService mock) {
        try {
            Field instance = PersonService.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, mock);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
