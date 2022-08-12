package ua.com.alevel.model.product.television;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.model.product.Product;
import java.util.Objects;

@Getter
@Setter
public class Television extends Product {
    private int diagonal;
    private String country;

    public Television(String series, String screenType, double price, int diagonal, String country) {
        super(series, screenType, price);
        this.diagonal = diagonal;
        this.country = country;
    }

    @Override
    public String toString() {
        return "Television{" +
                "series='" + series + '\'' +
                ", screenType='" + screenType + '\'' +
                ", price=" + price +
                ", diagonal=" + diagonal +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Television that = (Television) o;
        return diagonal == that.diagonal && country.equals(that.country) &&
                series.equals(that.series) &&
                screenType.equals(that.screenType) &&
                price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(diagonal, country, series, screenType, price);
    }
}
