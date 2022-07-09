package ua.com.alevel.hw2.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Objects;

@Getter
@Setter
public class PassengerPlane extends Plane {
    private int numberOfPassenger;
    private int rangeOfFlight;

    public PassengerPlane(String id, PlaneBrand brand, String model, long price, int numberOfPassenger, int rangeOfFlight) {
        super(id, brand, model, price);
        this.numberOfPassenger = numberOfPassenger;
        this.rangeOfFlight = rangeOfFlight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PassengerPlane that = (PassengerPlane) o;
        return numberOfPassenger == that.numberOfPassenger && rangeOfFlight == that.rangeOfFlight
                && super.id == that.id && super.brand == that.brand
                && super.model == that.model && super.price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfPassenger, rangeOfFlight, super.id, super.brand, super.model, super.price);
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
                '}';
    }
}
