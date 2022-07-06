package ua.com.alevel.hw2.model;

import lombok.Getter;
import lombok.Setter;

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
