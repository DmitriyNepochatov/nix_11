package com.example.controller;

import com.example.factory.ProductFactory;
import com.example.model.Product;
import com.example.service.NotificationService;
import com.example.service.ProductService;
import java.util.ArrayList;
import java.util.List;

public final class Controller {
    private static final ProductService productService = ProductService.getInstance();
    private static final NotificationService notificationService = NotificationService.getInstance();
    private static final ProductFactory productFactory = ProductFactory.getInstance();

    public static void run() {
        List<Product> list = new ArrayList<>();

        list.add(productFactory.generateRandomProduct());
        list.add(productFactory.generateRandomProduct());
        list.add(productFactory.generateRandomProduct());
        list.add(productFactory.generateRandomProduct());
        list.add(productFactory.generateRandomProduct());
        list.add(productFactory.generateRandomProduct());
        list.add(productFactory.generateRandomProduct());
        list.add(productFactory.generateRandomProduct());
        list.add(productFactory.generateRandomProduct());
        list.add(productFactory.generateRandomProduct());

        list.forEach(productService::save);
        productService.getAll().forEach(System.out::println);
        System.out.println("notifications sent: " + notificationService.filterNotifiableProductsAndSendNotifications());
    }
}