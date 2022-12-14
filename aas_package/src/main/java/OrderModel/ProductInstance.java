package OrderModel;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;

public class ProductInstance {

    AssetAdministrationShell instance;
    String quantity; //String, so that quantity can have different units

    public ProductInstance (AssetAdministrationShell instanceAAS, String quantity)
    {
        this.instance = instanceAAS;
        this.quantity = quantity;
    }
    public ProductInstance(AssetAdministrationShell instanceAAS)
    {
        this.instance = instanceAAS;
        quantity = DEFAULT_QUANTITY;
    }
    AssetAdministrationShell getInstance()
    {
        return this.instance;
    }
    String getQuantity()
    {
        return this.quantity;
    }
    protected void setQuantity (String quantity)
    {
        this.quantity = quantity;
    }
    private static  String DEFAULT_QUANTITY = "1";
}
