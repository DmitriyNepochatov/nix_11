package ua.com.alevel.dao.productdao;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ProductDaoImpl implements ProductDao {
    private static ProductDaoImpl instance;
    private static final String fileName = "products.csv";

    private ProductDaoImpl() {
    }

    public static ProductDaoImpl getInstance() {
        if (instance == null) {
            instance = new ProductDaoImpl();
        }

        return instance;
    }

    @Override
    public List<String> findAll() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream != null) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                List<String> lines = new ArrayList<>();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    lines.add(line);
                }

                return lines;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\nFile: " + fileName + " wasn't found");
        return Collections.emptyList();
    }
}
