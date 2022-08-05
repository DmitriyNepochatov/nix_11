package ua.com.alevel.hw2.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
public class PassengerPlane extends Plane {
    private int numberOfPassenger;
    private int rangeOfFlight;

    public PassengerPlane(String id, PlaneBrand brand, String model, int price, int count, String currency,
                          Date created, ManufacturingMaterial manufacturingMaterial, int numberOfPassenger,
                          int rangeOfFlight) {
        super(id, brand, model, price, count, currency, created, manufacturingMaterial, PlaneType.PASSENGER_PLANE);
        this.numberOfPassenger = numberOfPassenger;
        this.rangeOfFlight = rangeOfFlight;
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
