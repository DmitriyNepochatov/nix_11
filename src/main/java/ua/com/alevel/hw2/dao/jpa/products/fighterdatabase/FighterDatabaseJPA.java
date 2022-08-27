package ua.com.alevel.hw2.dao.jpa.products.fighterdatabase;

import ua.com.alevel.hw2.annotations.Singleton;
import ua.com.alevel.hw2.config.JPAConfig;
import ua.com.alevel.hw2.model.fighter.Fighter;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Singleton
public final class FighterDatabaseJPA implements FighterDaoJPA {
    private static final EntityManager ENTITY_MANAGER = JPAConfig.getEntityManager();
    private static FighterDatabaseJPA instance;

    private FighterDatabaseJPA() {
    }

    public static FighterDatabaseJPA getInstance() {
        if (instance == null) {
            instance = new FighterDatabaseJPA();
        }

        return instance;
    }

    @Override
    public void save(Fighter plane) {
        ENTITY_MANAGER.getTransaction().begin();
        ENTITY_MANAGER.persist(plane);
        ENTITY_MANAGER.flush();
        ENTITY_MANAGER.getTransaction().commit();
    }

    @Override
    public void saveAll(Fighter[] planes) {
        ENTITY_MANAGER.getTransaction().begin();
        for (Fighter plane : planes) {
            ENTITY_MANAGER.persist(plane);
        }
        ENTITY_MANAGER.flush();
        ENTITY_MANAGER.getTransaction().commit();
    }

    @Override
    public void update(Fighter plane) {
        ENTITY_MANAGER.getTransaction().begin();
        ENTITY_MANAGER.merge(plane);
        ENTITY_MANAGER.getTransaction().commit();
    }

    @Override
    public void delete(String id) {
        ENTITY_MANAGER.getTransaction().begin();
        ENTITY_MANAGER.remove(ENTITY_MANAGER.find(Fighter.class, id));
        ENTITY_MANAGER.getTransaction().commit();
    }

    @Override
    public Optional<Fighter> findById(String id) {
        List<Fighter> list = ENTITY_MANAGER.createQuery("from Fighter as c where c.id = :id")
                .setParameter("id", id)
                .getResultList();

        return (list.isEmpty()) ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public List<Fighter> findAll() {
        List<Fighter> list = ENTITY_MANAGER.createQuery("from Fighter ")
                .getResultList();

        return (list.isEmpty()) ? Collections.emptyList() : list;
    }

    @Override
    public List<Fighter> findAllInStock() {
        List<Fighter> list = ENTITY_MANAGER.createQuery("from Fighter as c where c.invoice = null")
                .getResultList();

        return (list.isEmpty()) ? Collections.emptyList() : list;
    }
}
