package ua.com.alevel.hw2.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Plane {
    protected String id;
    protected PlaneBrand brand;
    protected String model;
    protected int price;
    protected int count;

    public Plane(String id, PlaneBrand brand, String model, int price, int count) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.count = count;
    }
}
