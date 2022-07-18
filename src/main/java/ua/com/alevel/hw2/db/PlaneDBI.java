package ua.com.alevel.hw2.db;

import ua.com.alevel.hw2.model.Plane;
import java.util.List;
import java.util.Optional;

public interface PlaneDBI {
    void save(Plane plane);

    void update(Plane plane);

    void delete(String id);

    Optional<Plane> findById(String id);

    List<Plane> findAll();
}
