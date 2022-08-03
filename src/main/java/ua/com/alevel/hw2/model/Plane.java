package ua.com.alevel.hw2.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public abstract class Plane {
    protected String id;
    protected PlaneBrand brand;
    protected String model;
    protected int price;
    protected int count;
    protected String currency;
    protected Date created;
    protected ManufacturingMaterial manufacturingMaterial;

    public Plane(String id, PlaneBrand brand, String model, int price, int count,
                 String currency, Date created, ManufacturingMaterial manufacturingMaterial) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.count = count;
        this.currency = currency;
        this.created = created;
        this.manufacturingMaterial = manufacturingMaterial;
    }
}
