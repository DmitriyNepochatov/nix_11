package ua.com.alevel.hw2.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.alevel.hw2.model.Fighter;
import ua.com.alevel.hw2.model.PlaneBrand;
import ua.com.alevel.hw2.model.TypeOfFighter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

class PlaneDBTest {
    private PlaneDB<Fighter> target;
    private Fighter plane;

    @BeforeEach
    void setUp() {
        final Random random = new Random();
        target = new PlaneDB<>();
        plane = new Fighter(
                "" + random.nextInt(100),
                PlaneBrand.LOCKHEED,
                "Model-" + random.nextInt(100),
                random.nextInt(5000),
                random.nextInt(150),
                TypeOfFighter.FIGHTER,
                random.nextInt(10)
        );
    }

    @Test
    void save() {
        target.save(plane);
        List<Fighter> planes = target.findAll();
        Assertions.assertEquals(1, planes.size());
        Assertions.assertEquals(planes.get(0).getId(), plane.getId());
    }

    @Test
    void save_duplicate() {
        target.save(plane);
        target.save(plane);
        List<Fighter> planes = target.findAll();
        Assertions.assertEquals(1, planes.size());
        Assertions.assertEquals(planes.get(0).getId(), plane.getId());
    }

    @Test
    void update() {
        target.save(plane);

        Fighter fighter = new Fighter(plane.getId(),
                PlaneBrand.BOEING,
                "F-35A",
                3500,
                10,
                TypeOfFighter.FIGHTER,
                10);

        target.update(fighter);
        List<Fighter> planes = target.findAll();
        Assertions.assertEquals(1, planes.size());
        Assertions.assertEquals(planes.get(0), fighter);
    }

    @Test
    void update_idWasNotFound() {
        target.save(plane);

        Fighter fighter = new Fighter("500",
                PlaneBrand.BOEING,
                "F-35A",
                3500,
                10,
                TypeOfFighter.FIGHTER,
                10);

        target.update(fighter);
        List<Fighter> planes = target.findAll();
        Assertions.assertEquals(1, planes.size());
        Assertions.assertEquals(planes.get(0), plane);
    }

    @Test
    void delete() {
        target.save(plane);
        target.delete(plane.getId());
        List<Fighter> planes = target.findAll();
        Assertions.assertEquals(0, planes.size());
    }

    @Test
    void delete_idWasNotFound() {
        target.save(plane);
        target.delete("500");
        List<Fighter> planes = target.findAll();
        Assertions.assertEquals(1, planes.size());
        Assertions.assertEquals(planes.get(0), plane);
    }

    @Test
    void findById() {
        target.save(plane);
        Optional<Fighter> findedPlane = target.findById(plane.getId());
        Assertions.assertEquals(Optional.of(plane), findedPlane);
    }

    @Test
    void findById_idWasNotFound() {
        target.save(plane);
        Optional<Fighter> findedPlane = target.findById("500");
        Assertions.assertNotEquals(plane, findedPlane);
    }

    @Test
    void findAll() {
        target.save(plane);
        List<Fighter> planes = target.findAll();
        Assertions.assertEquals(1, planes.size());
    }

    @Test
    void findAll_zeroElements() {
        List<Fighter> planes = target.findAll();
        Assertions.assertEquals(0, planes.size());
    }
}