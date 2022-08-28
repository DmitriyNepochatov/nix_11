package ua.com.alevel.hw2.dao.jpa.invoices;

import org.hibernate.Session;
import org.hibernate.type.IntegerType;
import ua.com.alevel.hw2.annotations.Autowired;
import ua.com.alevel.hw2.annotations.Singleton;
import ua.com.alevel.hw2.config.JPAConfig;
import ua.com.alevel.hw2.dao.InvoiceDao;
import ua.com.alevel.hw2.model.invoice.Invoice;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

@Singleton
public final class InvoiceDatabaseJPA implements InvoiceDao {
    private static final EntityManager ENTITY_MANAGER = JPAConfig.getEntityManager();
    private static InvoiceDatabaseJPA instance;

    @Autowired
    private InvoiceDatabaseJPA() {
    }

    public static InvoiceDatabaseJPA getInstance() {
        if (instance == null) {
            instance = new InvoiceDatabaseJPA();
        }

        return instance;
    }

    @Override
    public void save(Invoice invoice) {
        invoice.getProducts().forEach(product -> product.setInvoice(invoice));
        ENTITY_MANAGER.getTransaction().begin();
        ENTITY_MANAGER.persist(invoice);
        ENTITY_MANAGER.flush();
        ENTITY_MANAGER.getTransaction().commit();
    }

    @Override
    public List<Invoice> getInvoicesExpensiveThanPrice(int price) {
        List<Invoice> invoices = ENTITY_MANAGER.createQuery("from Invoice as i where i.sum > :price")
                .setParameter("price", price)
                .getResultList();

        return (invoices.isEmpty()) ? Collections.emptyList() : invoices;
    }

    @Override
    public int getInvoiceCount() {
        return (int) ENTITY_MANAGER.unwrap(Session.class)
                .createSQLQuery("select count(*) as count from Invoice")
                .addScalar("count", IntegerType.INSTANCE)
                .getSingleResult();
    }

    @Override
    public Optional<Invoice> findById(String id) {
        List<Invoice> list = ENTITY_MANAGER.createQuery("from Invoice as i where i.id = :id")
                .setParameter("id", id)
                .getResultList();

        return (list.isEmpty()) ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public void update(String id, Date date) {
        Invoice invoice = findById(id).get();
        invoice.setCreated(date);

        ENTITY_MANAGER.getTransaction().begin();
        ENTITY_MANAGER.merge(invoice);
        ENTITY_MANAGER.getTransaction().commit();
    }

    @Override
    public Map<Integer, Integer> groupBySum() {
        CriteriaBuilder criteriaBuilder = ENTITY_MANAGER.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = criteriaBuilder.createQuery(Object[].class);
        Root<Invoice> root = query.from(Invoice.class);

        query.multiselect(criteriaBuilder.count(root.get("id")), root.get("sum"));
        query.groupBy(root.get("sum"));
        List<Object[]> result = ENTITY_MANAGER.createQuery(query).getResultList();
        Map<Integer, Integer> resultMap = new HashMap<>();

        result.forEach(element -> resultMap.put((Integer) element[1], (Integer) element[0]));

        return (resultMap.isEmpty()) ? Collections.emptyMap() : resultMap;
    }

    @Override
    public List<Invoice> findAll() {
        List<Invoice> invoices = ENTITY_MANAGER.createQuery("from Invoice").getResultList();

        return (invoices.isEmpty()) ? Collections.emptyList() : invoices;
    }
}
