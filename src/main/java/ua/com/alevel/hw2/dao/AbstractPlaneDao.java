package ua.com.alevel.hw2.dao;

import ua.com.alevel.hw2.model.Plane;
import java.util.List;
import java.util.Optional;

public interface AbstractPlaneDao<E extends Plane> {
    void save(E plane);

    void saveAll(E[] planes);

    void update(E plane);

    void delete(String id);

    Optional<E> findById(String id);

    List<E> findAll();

    List<E> findAllInStock();
}
