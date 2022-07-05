package ua.com.alevel.hw2.db;

import ua.com.alevel.hw2.model.Plane;
import java.util.ArrayList;
import ua.com.alevel.hw2.generatorID.GeneratorID;
import java.util.List;

public final class PlaneDB implements PlaneDBI
{
    private final List<Plane> planes = new ArrayList<>();

    @Override
    public void create(Plane plane)
    {
        plane.setId(GeneratorID.createID());
        planes.add(plane);
    }

    @Override
    public void update(Plane plane)
    {
        int i;
        for (i = 0; i < planes.size(); i++)
            if(planes.get(i).getId().equals(plane.getId()))
                break;

        planes.set(i, plane);
    }

    @Override
    public void delete(String id)
    {
        for (int i = 0; i < planes.size(); i++)
            if (planes.get(i).getId().equals(id))
                planes.remove(planes.get(i));
    }

    @Override
    public Plane findById(String id)
    {
        for (int i = 0; i < planes.size(); i++)
            if(planes.get(i).getId().equals(id))
                return planes.get(i);

        return null;
    }

    @Override
    public List<Plane> findAll()
    {
        return planes;
    }
}
