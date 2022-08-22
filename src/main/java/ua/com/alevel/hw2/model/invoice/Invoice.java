package ua.com.alevel.hw2.model.invoice;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.hw2.model.Plane;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Invoice {
    private String id;
    private int sum;
    private List<Plane> products;
    private Date created;

    public Invoice(String id, int sum, List<Plane> products, Date created) {
        this.id = id;
        this.sum = sum;
        this.products = products;
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return sum == invoice.sum && Objects.equals(id, invoice.id)
                && Objects.equals(products, invoice.products)
                && Objects.equals(created, invoice.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sum, products, created);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + id + '\'' +
                ", sum=" + sum +
                ", products=" + products +
                ", created=" + created +
                '}';
    }
}
