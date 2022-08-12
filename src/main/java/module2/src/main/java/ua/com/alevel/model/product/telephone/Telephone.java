package ua.com.alevel.model.product.telephone;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.model.product.Product;
import java.util.Objects;

@Getter
@Setter
public class Telephone extends Product {
    private String model;

    public Telephone(String series, String screenType, double price, String model) {
        super(series, screenType, price);
        this.model = model;
    }

    @Override
    public String toString() {
        return "Telephone{" +
                "series='" + series + '\'' +
                ", screenType='" + screenType + '\'' +
                ", price=" + price +
                ", model='" + model + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Telephone telephone = (Telephone) o;
        return model.equals(telephone.model) &&
                series.equals(telephone.series) &&
                screenType.equals(telephone.screenType) &&
                price == telephone.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, series, screenType, price);
    }
}
