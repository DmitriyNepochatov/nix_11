package ua.com.alevel.service.televisionservice;

import ua.com.alevel.model.product.television.Television;
import ua.com.alevel.service.CreatableFromMap;
import java.util.Map;

public final class TelevisionService implements CreatableFromMap {
    private static TelevisionService instance;

    private TelevisionService() {
    }

    public static TelevisionService getInstance() {
        if (instance == null) {
            instance = new TelevisionService();
        }

        return instance;
    }

    @Override
    public Television createProduct(Map<String, Object> params) {
        return new Television(params.get("series").toString(),
                params.get("screen type").toString(),
                Double.parseDouble(params.get("price").toString()),
                Integer.parseInt(params.get("diagonal").toString()),
                params.get("country").toString());
    }
}
