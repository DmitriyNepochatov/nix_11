package ua.com.alevel.service.telephoneservice;

import ua.com.alevel.model.product.telephone.Telephone;
import ua.com.alevel.service.CreatableFromMap;
import java.util.Map;

public final class TelephoneService implements CreatableFromMap {
    private static TelephoneService instance;

    private TelephoneService() {
    }

    public static TelephoneService getInstance() {
        if (instance == null) {
            instance = new TelephoneService();
        }

        return instance;
    }

    @Override
    public Telephone createProduct(Map<String, Object> params) {
        return new Telephone(params.get("series").toString(),
                params.get("screen type").toString(),
                Double.parseDouble(params.get("price").toString()),
                params.get("model").toString());
    }
}