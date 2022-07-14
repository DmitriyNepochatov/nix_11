package ua.com.alevel.hw2.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Objects;

@Getter
@Setter
public class Fighter extends Plane {
    private TypeOfFighter type;
    private int bombLoad;

    public Fighter(String id, PlaneBrand brand, String model, int price, int count, TypeOfFighter type, int bombLoad) {
        super(id, brand, model, price, count);
        this.type = type;
        this.bombLoad = bombLoad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fighter fighter = (Fighter) o;
        return bombLoad == fighter.bombLoad && type == fighter.type
                && super.id == fighter.id && super.brand == fighter.brand
                && super.model == fighter.model && super.price == fighter.price && super.count == fighter.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, bombLoad, super.id, super.brand, super.model, super.price, super.count);
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
                '}';
    }
}
