package ua.com.alevel.hw2.model.fighter;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.hw2.model.*;
import ua.com.alevel.hw2.model.manufacturingmaterial.ManufacturingMaterial;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Fighter extends Plane {
    @Column(name = "type_of_fighter", nullable = false)
    private TypeOfFighter typeOfFighter;

    @Column(name = "bomb_load", nullable = false)
    private int bombLoad;

    @Transient
    private List<String> details;

    public Fighter() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fighter fighter = (Fighter) o;
        return bombLoad == fighter.bombLoad && typeOfFighter.equals(fighter.typeOfFighter)
                && super.brand.equals(fighter.brand)
                && super.model.equals(fighter.model) && super.price == fighter.price && super.count == fighter.count &&
                super.currency.equals(fighter.currency) && super.created.equals(fighter.created)
                && super.manufacturingMaterial.equals(fighter.manufacturingMaterial);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeOfFighter, bombLoad, super.brand, super.model, super.price, super.count,
                super.currency, super.created, super.manufacturingMaterial);
    }

    @Override
    public String toString() {
        return "Fighter{" +
                "typeOfFighter=" + typeOfFighter +
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

    public static class Builder {
        private Fighter fighter;

        public Builder(PlaneType type, int price) {
            fighter = new Fighter();
            if (type == null) {
                fighter.setType(PlaneType.FIGHTER);
            }
            else {
                fighter.setType(type);
            }
            fighter.setPrice(price);
        }

        public Builder setId(String id) {
            fighter.setId(id);
            return this;
        }

        public Builder setModel(String model) {
            if (model.length() > 20) {
                throw new IllegalArgumentException("Model name must not be more than 20 characters");
            }

            fighter.setModel(model);
            return this;
        }

        public Builder setCount(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("Count can't be negative or equal 0");
            }

            fighter.setCount(count);
            return this;
        }

        public Builder setBrand(PlaneBrand brand) {
            fighter.setBrand(brand);
            return this;
        }

        public Builder setCurrency(String currency) {
            fighter.setCurrency(currency);
            return this;
        }

        public Builder setCreated(Date created) {
            fighter.setCreated(created);
            return this;
        }

        public Builder setManufacturingMaterial(ManufacturingMaterial manufacturingMaterial) {
            fighter.setManufacturingMaterial(manufacturingMaterial);
            return this;
        }

        public Builder setTypeOfFighter(TypeOfFighter typeOfFighter) {
            fighter.setTypeOfFighter(typeOfFighter);
            return this;
        }

        public Builder setBombLoad(int bombLoad) {
            fighter.setBombLoad(bombLoad);
            return this;
        }

        public Builder setDetails(List<String> details) {
            fighter.setDetails(details);
            return this;
        }

        public Fighter build() {
            return fighter;
        }
    }
}
