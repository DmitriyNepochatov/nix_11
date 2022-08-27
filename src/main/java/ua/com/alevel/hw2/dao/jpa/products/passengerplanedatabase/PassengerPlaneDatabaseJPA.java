package ua.com.alevel.hw2.dao.jpa.products.passengerplanedatabase;

import ua.com.alevel.hw2.annotations.Singleton;
import ua.com.alevel.hw2.config.JPAConfig;
import ua.com.alevel.hw2.model.passengerplane.PassengerPlane;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Singleton
public final class PassengerPlaneDatabaseJPA implements PassengerPlaneDaoJPA {
    private static final EntityManager ENTITY_MANAGER = JPAConfig.getEntityManager();
    private static PassengerPlaneDatabaseJPA instance;

    private PassengerPlaneDatabaseJPA() {
    }

    public static PassengerPlaneDatabaseJPA getInstance() {
        if (instance == null) {
            instance = new PassengerPlaneDatabaseJPA();
        }

        return instance;
    }

    @Override
    public void save(PassengerPlane plane) {
        ENTITY_MANAGER.getTransaction().begin();
        ENTITY_MANAGER.persist(plane);
        ENTITY_MANAGER.flush();
        ENTITY_MANAGER.getTransaction().commit();
    }

    @Override
    public void saveAll(PassengerPlane[] planes) {
        ENTITY_MANAGER.getTransaction().begin();
        for (PassengerPlane plane : planes) {
            ENTITY_MANAGER.persist(plane);
        }
        ENTITY_MANAGER.flush();
        ENTITY_MANAGER.getTransaction().commit();
    }

    @Override
    public void update(PassengerPlane plane) {
        ENTITY_MANAGER.getTransaction().begin();
        ENTITY_MANAGER.merge(plane);
        ENTITY_MANAGER.getTransaction().commit();
    }

    @Override
    public void delete(String id) {
        ENTITY_MANAGER.getTransaction().begin();
        ENTITY_MANAGER.remove(ENTITY_MANAGER.find(PassengerPlane.class, id));
        ENTITY_MANAGER.getTransaction().commit();
    }

    @Override
    public Optional<PassengerPlane> findById(String id) {
        List<PassengerPlane> list = ENTITY_MANAGER.createQuery("from PassengerPlane as c where c.id = :id")
                .setParameter("id", id)
                .getResultList();

        return (list.isEmpty()) ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public List<PassengerPlane> findAll() {
        List<PassengerPlane> list = ENTITY_MANAGER.createQuery("from PassengerPlane")
                .getResultList();

        return (list.isEmpty()) ? Collections.emptyList() : list;
    }

    @Override
    public List<PassengerPlane> findAllInStock() {
        List<PassengerPlane> list = ENTITY_MANAGER.createQuery("from PassengerPlane as c where c.invoice = null")
                .getResultList();

        return (list.isEmpty()) ? Collections.emptyList() : list;
    }
}
