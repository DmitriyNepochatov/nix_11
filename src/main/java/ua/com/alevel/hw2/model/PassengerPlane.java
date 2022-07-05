package ua.com.alevel.hw2.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassengerPlane extends Plane
{
    private int numberOfPassenger;
    private int rangeOfFlight;

    @Override
    public String toString()
    {
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
