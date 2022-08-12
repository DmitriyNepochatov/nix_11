package ua.com.alevel.model.product;

public enum ProductType {
    TELEPHONE("Telephone"),
    TELEVISION("Television");

    private final String name;

    ProductType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
