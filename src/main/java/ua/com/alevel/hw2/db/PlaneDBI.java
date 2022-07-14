package ua.com.alevel.hw2.db;

import ua.com.alevel.hw2.model.Plane;
import java.util.List;
import java.util.Optional;

public interface PlaneDBI<E extends Plane> {
    void save(E plane);

    void update(E plane);

    void delete(String id);

    Optional<E> findById(String id);

    List<E> findAll();
}
