package ua.com.alevel.model.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Product {
    protected String series;
    protected String screenType;
    protected double price;

    public Product(String series, String screenType, double price) {
        this.series = series;
        this.screenType = screenType;
        this.price = price;
    }
}
