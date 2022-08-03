package ua.com.alevel.hw2.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Fighter extends Plane {
    private TypeOfFighter type;
    private int bombLoad;
    private List<String> details;

    public Fighter(String id, PlaneBrand brand, String model, int price, int count,
                   String currency, Date created, ManufacturingMaterial manufacturingMaterial,
                   TypeOfFighter type, int bombLoad) {
        super(id, brand, model, price, count, currency, created, manufacturingMaterial);
        this.type = type;
        this.bombLoad = bombLoad;
    }

    public Fighter(String id, PlaneBrand brand, String model, int price, int count,
                   String currency, Date created, ManufacturingMaterial manufacturingMaterial,
                   TypeOfFighter type, int bombLoad, List<String> details) {
        super(id, brand, model, price, count, currency, created, manufacturingMaterial);
        this.type = type;
        this.bombLoad = bombLoad;
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fighter fighter = (Fighter) o;
        return bombLoad == fighter.bombLoad && type.equals(fighter.type)
                && super.brand.equals(fighter.brand)
                && super.model.equals(fighter.model) && super.price == fighter.price && super.count == fighter.count &&
                super.currency.equals(fighter.currency) && super.created.equals(fighter.created)
                && super.manufacturingMaterial.equals(fighter.manufacturingMaterial);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, bombLoad, super.brand, super.model, super.price, super.count,
                super.currency, super.created, super.manufacturingMaterial);
    }

    @Override
    public String toString() {
        return "Fighter{" +
                "type=" + type +
                ", bombLoad=" + bombLoad +
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
