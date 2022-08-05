package com.example.model;

import lombok.Setter;

@Setter
public class ProductBundle extends Product {
    protected int amount;

    public int getAmountInBundle() {
        return amount;
    }

    @Override
    public String toString() {
        return "ProductBundle{" +
                "id=" + id +
                ", available=" + available +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}