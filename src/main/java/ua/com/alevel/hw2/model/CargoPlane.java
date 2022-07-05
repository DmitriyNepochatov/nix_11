package ua.com.alevel.hw2.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CargoPlane extends Plane
{
    private int loadCapacity;
    private int countOfCrew;

    @Override
    public String toString()
    {
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
