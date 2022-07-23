package ua.com.alevel.hw2.model;

import java.util.Comparator;

public class PlaneComparator<E extends Plane> implements Comparator<E> {
    @Override
    public int compare(E o1, E o2) {
        if (o1.getPrice() == o2.getPrice()) {
            if (o1.getModel().equals(o2.getModel())) {
                return Integer.compare(o1.getCount(), o2.getCount());
            }
            return o1.getModel().compareTo(o2.getModel());
        }
        return Integer.compare(o1.getPrice(), o2.getPrice());
    }
}
