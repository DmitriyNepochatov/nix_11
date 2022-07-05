package ua.com.alevel.hw2.db;

import ua.com.alevel.hw2.model.Plane;
import java.util.List;

public interface PlaneDBI
{
    void create(Plane plane);

    void update(Plane plane);

    void delete(String id);

    Plane findById(String id);

    List<Plane> findAll();
}
