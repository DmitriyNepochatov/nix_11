package ua.com.alevel.hw2.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Fighter extends Plane {
    private TypeOfFighter type;
    private int bombLoad;

    public Fighter(String id, PlaneBrand brand, String model, long price, TypeOfFighter type, int bombLoad) {
        super(id, brand, model, price);
        this.type = type;
        this.bombLoad = bombLoad;
    }

    @Override
    public String toString() {
        return "Fighter{" +
                "type='" + type + '\'' +
                ", bombLoad=" + bombLoad +
                ", id='" + id + '\'' +
                ", brand=" + brand +
                ", model='" + model + '\'' +
                ", price=" + price +
                '}';
    }
}
