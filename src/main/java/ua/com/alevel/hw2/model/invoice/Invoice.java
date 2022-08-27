package ua.com.alevel.hw2.model.invoice;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import ua.com.alevel.hw2.model.Plane;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Invoice {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(nullable = false)
    private int sum;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Plane> products;

    @Column(nullable = false)
    private Date created;

    public Invoice(int sum, List<Plane> products, Date created) {
        this.sum = sum;
        this.products = products;
        this.created = created;
    }

    public Invoice() {
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
