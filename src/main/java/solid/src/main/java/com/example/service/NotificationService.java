package com.example.service;

import com.example.model.NotifiableProduct;
import com.example.model.Product;
import com.example.repository.IProductRepository;
import com.example.repository.ProductRepository;
import java.util.List;

public class NotificationService {
    private IProductRepository<Product> productRepository = ProductRepository.getInstance();
    private static NotificationService instance;

    private NotificationService(){
    }

    public static NotificationService getInstance(){
        if(instance == null){
            instance = new NotificationService();
        }

        return instance;
    }

    public int filterNotifiableProductsAndSendNotifications() {
        int notifications = 0;

        List<NotifiableProduct> products = productRepository.getAll()
                .stream()
                .filter(NotifiableProduct.class::isInstance)
                .map(NotifiableProduct.class::cast)
                .toList();

        return products.size();
    }
}