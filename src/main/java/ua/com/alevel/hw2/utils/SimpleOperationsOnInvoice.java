package ua.com.alevel.hw2.utils;

import ua.com.alevel.hw2.model.Plane;
import java.util.List;

public final class SimpleOperationsOnInvoice {
    private SimpleOperationsOnInvoice() {
    }

    public static int invoicePrice(List<Plane> productList) {
        return productList.stream().mapToInt(Plane::getPrice).sum();
    }
}
