package ua.com.alevel.hw2.container;

import ua.com.alevel.hw2.model.Plane;
import java.util.Random;

public class PlaneContainer<E extends Plane> {

    private E plane;
    private static final Random RANDOM = new Random();
    private static final int UPPER_LIMIT = 21;
    private static final int LOWER_LIMIT = 10;

    public PlaneContainer(E plane) {
        this.plane = plane;
    }

    public void setPlane(E plane) {
        this.plane = plane;
    }

    public E getPlane() {
        return plane;
    }

    public void setRandomDiscountToPlanePrice() {
        int discount = RANDOM.nextInt(UPPER_LIMIT) + LOWER_LIMIT;
        int newPrice = (plane.getPrice() * (100 - discount)) / 100;
        plane.setPrice(newPrice);
    }

    public <X extends Number> void addPlaneCount(X count) {
        plane.setCount(plane.getCount() + count.intValue());
    }
}
