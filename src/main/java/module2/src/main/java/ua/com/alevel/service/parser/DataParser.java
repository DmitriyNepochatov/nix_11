package ua.com.alevel.service.parser;

import ua.com.alevel.exception.IncorrectStringException;
import ua.com.alevel.model.product.Product;
import ua.com.alevel.service.ProductServices;
import java.util.*;
import java.util.stream.IntStream;

public final class DataParser {
    private static DataParser instance;

    private DataParser() {
    }

    public static DataParser getInstance() {
        if (instance == null) {
            instance = new DataParser();
        }

        return instance;
    }

    public List<Product> parse(List<String> data) {
        String[] headlines = data.get(0).split(",");
        int typeIndex = Arrays.asList(headlines).indexOf("type");
        data.remove(0);
        ProductServices[] productServices = ProductServices.values();
        List<Product> result = new ArrayList<>();

        data.forEach(string -> {
            try {
                String[] values = string.split(",");
                if (Arrays.asList(values).contains("")) {
                    throw new IncorrectStringException("Incorrect line: " + string);
                }

                Map<String, Object> map = new HashMap<>();
                IntStream.range(0, values.length)
                        .forEach(index -> {
                            if (index != typeIndex) {
                                map.put(headlines[index], values[index]);
                            }
                        });

                Optional<ProductServices> object = Arrays.stream(productServices)
                        .filter(element -> element.getName().equals(values[typeIndex]))
                        .findFirst();

                object.ifPresent(service -> result.add(service.getCreatableFromMap().createProduct(map)));
            }
            catch (IncorrectStringException e) {
                System.out.println("\n" + e.getMessage());
            }
        });

        return result;
    }
}
