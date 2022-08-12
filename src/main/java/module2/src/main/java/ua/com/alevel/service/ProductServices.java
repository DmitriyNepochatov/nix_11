package ua.com.alevel.service;

import ua.com.alevel.model.product.ProductType;
import ua.com.alevel.service.telephoneservice.TelephoneService;
import ua.com.alevel.service.televisionservice.TelevisionService;

public enum ProductServices {
    TELEPHONE_SERVICE(ProductType.TELEPHONE.getName(), TelephoneService.getInstance()),
    TELEVISION_SERVICE(ProductType.TELEVISION.getName(), TelevisionService.getInstance());

    private final String name;
    private final CreatableFromMap creatableFromMap;

    ProductServices(String name, CreatableFromMap creatableFromMap) {
        this.name = name;
        this.creatableFromMap = creatableFromMap;
    }

    public String getName() {
        return name;
    }

    public CreatableFromMap getCreatableFromMap() {
        return creatableFromMap;
    }
}
