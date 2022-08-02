package ua.com.alevel.hw2.service;

import ua.com.alevel.hw2.db.PlaneDB;
import ua.com.alevel.hw2.db.PlaneDBI;
import ua.com.alevel.hw2.model.Plane;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class PlaneService<E extends Plane> {
    private final PlaneDBI<E> planeDB;

    private final Predicate<Collection<E>> predicate = planes -> planes.stream().allMatch(plane -> plane.getPrice() != 0);
    private final Function<Map<String, Object>, E> mapToPlane = this::createPlaneFromMapFoo;

    protected PlaneService(PlaneDB<E> planeDB) {
        this.planeDB = planeDB;
    }

    public void save(E plane) {
        if (plane != null) {
            planeDB.save(plane);
        }
        else {
            throw new IllegalArgumentException("Plane was null");
        }
    }

    public void update(Plane plane) {
        if (plane != null) {
            planeDB.update((E) plane);
        }
        else {
            throw new IllegalArgumentException("Plane was null");
        }
    }

    public void delete(String id) {
        if (id != null) {
            planeDB.delete(id);
        }
        else {
            throw new IllegalArgumentException("id was null");
        }
    }

    public Optional<E> findById(String id) {
        if (id != null) {
            return planeDB.findById(id);
        }
        else {
            throw new IllegalArgumentException("id was null");
        }
    }

    public List<E> findAll() {
        return planeDB.findAll();
    }

    public abstract E createPlane();

    public abstract void updatePlane(E updatepablePlane, E plane);

    public void findPlanesMoreExpensiveThanPrice(int price) {
        planeDB.findAll()
                .stream()
                .filter(plane -> plane.getPrice() > price)
                .forEach(System.out::println);
    }

    public int calculatePrice() {
        return planeDB.findAll()
                .stream()
                .map(plane -> plane.getPrice() * plane.getCount())
                .reduce(0, Integer::sum);
    }

    public Map<String, String> sortedOfModelDistinctsPlanesToMap() {
        return planeDB.findAll()
                .stream()
                .sorted(Comparator.comparing(Plane::getModel))
                .distinct()
                .collect(Collectors.toMap(Plane::getId, Plane::toString, (p1, p2) -> p2));
    }

    public IntSummaryStatistics getSummaryPriceStatistics() {
        return planeDB.findAll()
                .stream()
                .mapToInt(Plane::getPrice)
                .summaryStatistics();
    }

    public boolean isAllPlanesHavePrice() {
        return predicate.test(planeDB.findAll());
    }

    public E createPlaneFromMap(Map<String, Object> creatableMap) {
        return mapToPlane.apply(creatableMap);
    }

    protected abstract E createPlaneFromMapFoo(Map<String, Object> map);
}
