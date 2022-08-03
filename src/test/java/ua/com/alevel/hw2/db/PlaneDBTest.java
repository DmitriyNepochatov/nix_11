package ua.com.alevel.hw2.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.alevel.hw2.model.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

class PlaneDBTest {
    private PlaneDB<Fighter> target;
    private Fighter plane;
    private Date date;
    private ManufacturingMaterial manufacturingMaterial;

    @BeforeEach
    void setUp() {
        final Random random = new Random();
        target = new PlaneDB<>();

        date = new Date();
        manufacturingMaterial = new ManufacturingMaterial("Material-" + random.nextInt(150), "Color-" + random.nextInt(300));

        plane = new Fighter.Builder(PlaneType.FIGHTER, random.nextInt(5000))
                .setId("" + random.nextInt(100))
                .setBrand(PlaneBrand.LOCKHEED)
                .setModel("Model-" + random.nextInt(100))
                .setCount(random.nextInt(150))
                .setCurrency("Currency-" + random.nextInt(300))
                .setCreated(date)
                .setManufacturingMaterial(manufacturingMaterial)
                .setTypeOfFighter(TypeOfFighter.FIGHTER)
                .setBombLoad(10).build();
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

        Fighter fighter = new Fighter.Builder(PlaneType.FIGHTER, 3500)
                .setId(plane.getId())
                .setBrand(PlaneBrand.BOEING)
                .setModel("F-35A")
                .setCount(10)
                .setCurrency("Currency-13")
                .setCreated(date)
                .setManufacturingMaterial(manufacturingMaterial)
                .setTypeOfFighter(TypeOfFighter.FIGHTER)
                .setBombLoad(10).build();

        target.update(fighter);
        List<Fighter> planes = target.findAll();
        Assertions.assertEquals(1, planes.size());
        Assertions.assertEquals(planes.get(0), fighter);
    }

    @Test
    void update_idWasNotFound() {
        target.save(plane);

        Fighter fighter = new Fighter.Builder(PlaneType.FIGHTER, 3500)
                .setId("145")
                .setBrand(PlaneBrand.BOEING)
                .setModel("F-35A")
                .setCount(10)
                .setCurrency("Currency-13")
                .setCreated(date)
                .setManufacturingMaterial(manufacturingMaterial)
                .setTypeOfFighter(TypeOfFighter.FIGHTER)
                .setBombLoad(10).build();

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