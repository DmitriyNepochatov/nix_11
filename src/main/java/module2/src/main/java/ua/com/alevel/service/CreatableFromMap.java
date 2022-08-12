package ua.com.alevel.service;

import ua.com.alevel.model.product.Product;
import java.util.Map;

public interface CreatableFromMap {
    Product createProduct(Map<String, Object> params);
}
