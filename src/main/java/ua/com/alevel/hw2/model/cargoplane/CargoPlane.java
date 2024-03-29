package ua.com.alevel.hw2.model.cargoplane;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.hw2.model.manufacturingmaterial.ManufacturingMaterial;
import ua.com.alevel.hw2.model.Plane;
import ua.com.alevel.hw2.model.PlaneBrand;
import ua.com.alevel.hw2.model.PlaneType;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
public class CargoPlane extends Plane {
    @Column(name = "load_capacity", nullable = false)
    private int loadCapacity;

    @Column(name = "count_of_crew", nullable = false)
    private int countOfCrew;

    public CargoPlane(PlaneBrand brand, String model, int price, int count,
                      String currency, Date created, ManufacturingMaterial manufacturingMaterial,
                      int loadCapacity, int countOfCrew) {
        super(brand, model, price, count, currency, created, manufacturingMaterial, PlaneType.CARGO_PLANE);
        this.loadCapacity = loadCapacity;
        this.countOfCrew = countOfCrew;
    }

    public CargoPlane() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CargoPlane that = (CargoPlane) o;
        return loadCapacity == that.loadCapacity && countOfCrew == that.countOfCrew
                && super.brand.equals(that.brand)
                && super.model.equals(that.model) && super.price == that.price && super.count == that.count
                && super.currency.equals(that.currency) && super.created.equals(that.created)
                && super.manufacturingMaterial.equals(that.manufacturingMaterial);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loadCapacity, countOfCrew, super.brand, super.model, super.price,
                super.count, super.currency, super.created, super.manufacturingMaterial);
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
                ", count=" + count +
                ", currency='" + currency + '\'' +
                ", created=" + created +
                ", manufacturingMaterial=" + manufacturingMaterial +
                '}';
    }
}
