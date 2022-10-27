package ProductModel;

public enum ProductType {
    PRODUCT ("Product"),
    COMPONENT("Component");

    String value;

    ProductType (String value)
    {
        this.value = value;
    }
}
