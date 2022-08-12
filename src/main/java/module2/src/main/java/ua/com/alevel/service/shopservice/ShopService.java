package ua.com.alevel.service.shopservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.invoicedao.InvoiceDao;
import ua.com.alevel.dao.invoicedao.InvoiceDaoImpl;
import ua.com.alevel.dao.productdao.ProductDao;
import ua.com.alevel.dao.productdao.ProductDaoImpl;
import ua.com.alevel.model.customer.Customer;
import ua.com.alevel.model.invoice.Invoice;
import ua.com.alevel.model.invoice.InvoiceType;
import ua.com.alevel.model.product.Product;
import ua.com.alevel.service.parser.DataParser;
import ua.com.alevel.service.personservice.PersonService;
import ua.com.alevel.utils.SimpleOperationsOnInvoice;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public final class ShopService {
    private static final Random RANDOM = new Random();
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopService.class);
    private static ShopService instance;
    private static InvoiceDao invoiceDao = InvoiceDaoImpl.getInstance();
    private static ProductDao productDao = ProductDaoImpl.getInstance();
    private static DataParser dataParser = DataParser.getInstance();
    private static PersonService personService = PersonService.getInstance();
    private final double limit;

    private ShopService(double limit) {
        this.limit = limit;
    }

    public static ShopService getInstance(double limit) {
        if (instance == null) {
            instance = new ShopService(limit);
        }

        return instance;
    }

    public void createRandomInvoices(int count) {
        //List<String> data = productDao.findAll();                  //Через интерфейсы почему-то тесты не выполняются, только напрямую
        List<String> data = ProductDaoImpl.getInstance().findAll();

        if (!data.isEmpty()) {
            //List<Product> productList = dataParser.parse(data);
            List<Product> productList = DataParser.getInstance().parse(data);

            try {
                for (int i = 0; i < count; i++) {
                    Thread.sleep(1000 * (RANDOM.nextInt(2) + 1));

                    List<Product> products = createRandomProductList(productList);
                    //Customer customer = personService.createRandomCustomer();
                    Customer customer = PersonService.getInstance().createRandomCustomer();
                    InvoiceType invoiceType = getInvoiceType(SimpleOperationsOnInvoice.invoicePrice(products));
                    Date date = new Date();

                    //invoiceDao.save(new Invoice(products, customer, invoiceType, date));
                    InvoiceDaoImpl.getInstance().save(new Invoice(products, customer, invoiceType, date));
                    LOGGER.info("[{}] [{}] [{}]", date, customer, products);
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Product> createRandomProductList(List<Product> productList) {
        List<Product> randomList = new ArrayList<>();
        for (int i = 0; i < (RANDOM.nextInt(5) + 1); i++) {
            randomList.add(productList.get(RANDOM.nextInt(productList.size())));
        }

        return randomList;
    }

    private InvoiceType getInvoiceType(double invoicePrice) {
        return (invoicePrice > limit) ? InvoiceType.WHOLESALE : InvoiceType.RETAIL;
    }
}
