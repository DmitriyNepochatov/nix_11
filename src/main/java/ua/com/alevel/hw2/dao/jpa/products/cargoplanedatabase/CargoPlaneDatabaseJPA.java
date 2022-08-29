package ua.com.alevel.hw2.dao.jpa.products.cargoplanedatabase;

import ua.com.alevel.hw2.annotations.Autowired;
import ua.com.alevel.hw2.annotations.Singleton;
import ua.com.alevel.hw2.config.JPAConfig;
import ua.com.alevel.hw2.model.cargoplane.CargoPlane;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Singleton
public final class CargoPlaneDatabaseJPA implements CargoPlaneDaoJPA {
    private static final EntityManager ENTITY_MANAGER = JPAConfig.getEntityManager();
    private static CargoPlaneDatabaseJPA instance;

    @Autowired
    private CargoPlaneDatabaseJPA() {
    }

    public static CargoPlaneDatabaseJPA getInstance() {
        if (instance == null) {
            instance = new CargoPlaneDatabaseJPA();
        }

        return instance;
    }

    @Override
    public void save(CargoPlane plane) {
        ENTITY_MANAGER.getTransaction().begin();
        ENTITY_MANAGER.persist(plane);
        ENTITY_MANAGER.flush();
        ENTITY_MANAGER.getTransaction().commit();
    }

    @Override
    public void saveAll(CargoPlane[] planes) {
        ENTITY_MANAGER.getTransaction().begin();
        for (CargoPlane plane : planes) {
            ENTITY_MANAGER.persist(plane);
        }
        ENTITY_MANAGER.flush();
        ENTITY_MANAGER.getTransaction().commit();
    }

    @Override
    public void update(CargoPlane plane) {
        ENTITY_MANAGER.getTransaction().begin();
        ENTITY_MANAGER.merge(plane);
        ENTITY_MANAGER.getTransaction().commit();
    }

    @Override
    public void delete(String id) {
        ENTITY_MANAGER.getTransaction().begin();
        ENTITY_MANAGER.remove(ENTITY_MANAGER.find(CargoPlane.class, id));
        ENTITY_MANAGER.getTransaction().commit();
    }

    @Override
    public Optional<CargoPlane> findById(String id) {
        List<CargoPlane> list = ENTITY_MANAGER.createQuery("from CargoPlane as c where c.id = :id")
                .setParameter("id", id)
                .getResultList();

        return (list.isEmpty()) ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public List<CargoPlane> findAll() {
        List<CargoPlane> list = ENTITY_MANAGER.createQuery("from CargoPlane")
                .getResultList();

        return (list.isEmpty()) ? Collections.emptyList() : list;
    }

    @Override
    public List<CargoPlane> findAllInStock() {
        List<CargoPlane> list = ENTITY_MANAGER.createQuery("from CargoPlane as c where c.invoice = null")
                .getResultList();

        return (list.isEmpty()) ? Collections.emptyList() : list;
    }
}
