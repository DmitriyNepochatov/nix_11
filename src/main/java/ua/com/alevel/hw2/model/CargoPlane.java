package ua.com.alevel.hw2.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Objects;

@Getter
@Setter
public class CargoPlane extends Plane {
    private int loadCapacity;
    private int countOfCrew;

    public CargoPlane(String id, PlaneBrand brand, String model, long price, int loadCapacity, int countOfCrew) {
        super(id, brand, model, price);
        this.loadCapacity = loadCapacity;
        this.countOfCrew = countOfCrew;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CargoPlane that = (CargoPlane) o;
        return loadCapacity == that.loadCapacity && countOfCrew == that.countOfCrew
                && super.id == that.id && super.brand == that.brand
                && super.model == that.model && super.price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(loadCapacity, countOfCrew, super.id, super.brand, super.model, super.price);
    }

    @Override
    public String toString() {
        return "CargoPlane{" +
                "loadCapacity=" + loadCapacity +
                ", countOfCrew=" + countOfCrew +
                ", id='" + id + '\'' +
                ", brand=" + brand +
                ", model='" + model + '\'' +
                ", price=" + price +
                '}';
    }
}
