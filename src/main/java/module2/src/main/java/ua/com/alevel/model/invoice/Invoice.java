package ua.com.alevel.model.invoice;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.model.customer.Customer;
import ua.com.alevel.model.product.Product;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Invoice {
    private List<Product> productList;
    private Customer customer;
    private InvoiceType invoiceType;
    private Date created;

    public Invoice(List<Product> productList, Customer customer, InvoiceType invoiceType, Date created) {
        this.productList = productList;
        this.customer = customer;
        this.invoiceType = invoiceType;
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(productList, invoice.productList) &&
                Objects.equals(customer, invoice.customer) &&
                invoiceType.equals(invoice.invoiceType) &&
                Objects.equals(created, invoice.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productList, customer, invoiceType, created);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "productList=" + productList +
                ", customer=" + customer +
                ", invoiceType=" + invoiceType +
                ", created=" + created +
                '}';
    }
}
