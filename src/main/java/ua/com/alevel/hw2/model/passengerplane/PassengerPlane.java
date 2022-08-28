package ua.com.alevel.hw2.model.passengerplane;

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
public class PassengerPlane extends Plane {
    @Column(name = "number_of_passenger", nullable = false)
    private int numberOfPassenger;

    @Column(name = "range_of_flight", nullable = false)
    private int rangeOfFlight;

    public PassengerPlane(PlaneBrand brand, String model, int price, int count, String currency,
                          Date created, ManufacturingMaterial manufacturingMaterial, int numberOfPassenger,
                          int rangeOfFlight) {
        super(brand, model, price, count, currency, created, manufacturingMaterial, PlaneType.PASSENGER_PLANE);
        this.numberOfPassenger = numberOfPassenger;
        this.rangeOfFlight = rangeOfFlight;
    }

    public PassengerPlane() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PassengerPlane that = (PassengerPlane) o;
        return numberOfPassenger == that.numberOfPassenger && rangeOfFlight == that.rangeOfFlight
                && super.brand.equals(that.brand)
                && super.model.equals(that.model) && super.price == that.price && super.count == that.count &&
                super.currency.equals(that.currency) && super.created.equals(that.created)
                && super.manufacturingMaterial.equals(that.manufacturingMaterial);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfPassenger, rangeOfFlight, super.brand, super.model, super.price,
                super.count, super.currency, super.created, super.manufacturingMaterial);
    }

    @Override
    public String toString() {
        return "PassengerPlane{" +
                "numberOfPassenger=" + numberOfPassenger +
                ", rangeOfFlight=" + rangeOfFlight +
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
