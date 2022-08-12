package ua.com.alevel.utils;

import ua.com.alevel.model.product.Product;
import java.util.List;

public final class SimpleOperationsOnInvoice {
    private SimpleOperationsOnInvoice() {
    }

    public static double invoicePrice(List<Product> productList) {
        return productList.stream().mapToDouble(Product::getPrice).sum();
    }
}
