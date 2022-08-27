package ua.com.alevel.hw2.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import ua.com.alevel.hw2.model.invoice.Invoice;
import ua.com.alevel.hw2.model.manufacturingmaterial.ManufacturingMaterial;
import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Plane {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    protected String id;

    @Column(nullable = false)
    protected PlaneBrand brand;

    @Column(nullable = false)
    protected String model;

    @Column(nullable = false)
    protected int price;

    @Column(nullable = false)
    protected int count;

    @Column(nullable = false)
    protected String currency;

    @Column(nullable = false)
    protected Date created;

    @Transient
    protected ManufacturingMaterial manufacturingMaterial;

    @Transient
    protected PlaneType type;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    protected Invoice invoice;

    public Plane() {
    }

    public Plane(PlaneBrand brand, String model, int price, int count, String currency,
                 Date created, ManufacturingMaterial manufacturingMaterial, PlaneType type) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.count = count;
        this.currency = currency;
        this.created = created;
        this.manufacturingMaterial = manufacturingMaterial;
        this.type = type;
    }
}
