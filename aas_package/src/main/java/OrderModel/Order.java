package OrderModel;

import Helper.AASHelper;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
//TODO: implement framework (AAS_Object and ISubmodel)

public class Order {
    private static final IdentifierType IDENTIFIER_TYPE = IdentifierType.CUSTOM;
    private static AssetKind ASSET_KIND  = AssetKind.INSTANCE; // Default
    private static final String AAS_IDENTIFIER_PREFIX = "Order_AAS_";
    private static final String PREFIX_ASSET = "Asset_";
    private static final String PREFIX_ORDER_ASSET_IDENTIFIER= "Order_";


    String orderIdentification;
    ProductInstances productInstances = null; //Position --> Ref AAS, Quantity (rename)
    GeneralOrderInformation generalOrderInformation = null;

    private List<Submodel> listOfOrderSubmodels = new ArrayList<>();

    public Order(String orderId) {this.orderIdentification = orderId; }
    public Order(String orderId, ProductInstances instances) {
        this.orderIdentification = orderId;
        this.productInstances = instances;
    }
    public Order(String orderId, ProductInstances instances, GeneralOrderInformation generalOrderInformation) {
        this.orderIdentification = orderId;
        this.productInstances = instances;
        this.generalOrderInformation = generalOrderInformation;
    }
    public void setGeneralOrderInformation (GeneralOrderInformation generalInfo)
    {
        this.generalOrderInformation = generalInfo;
    }
    public void setProductInstances (ProductInstances instances)
    {
        this.productInstances = productInstances;
    }
    //Getters and Setters
    public String getOrderIdentification() {
        return orderIdentification;
    }

    protected void addSubmodelToListOfOrderSubmodels(Submodel submodel)
    {
        this.listOfOrderSubmodels.add(submodel);
    }

    public List<Submodel> getListOfOrderSubmodels() {return listOfOrderSubmodels;}

    //AAS-Environment
    public AssetAdministrationShell createProductAAS()
    {
        AssetAdministrationShell orderAAS = new AssetAdministrationShell(AASHelper.nameToIdShort(this.getOrderIdentification()),
                new Identifier(IDENTIFIER_TYPE, createAASIdentifier()), createOrderAsset());
        /**
         * Create SMs
         */
        if (this.generalOrderInformation != null) {
            orderAAS.addSubmodel(generalOrderInformation.createSubmodelGeneralInfo(this));
        }
        if (this.productInstances != null) {
            orderAAS.addSubmodel(this.productInstances.createSubmodelProductInstancesOfOrder(this));
        }
        return orderAAS;
    }

    @NotNull
    @Contract(pure = true)
    private String createAASIdentifier()
    {
        return AAS_IDENTIFIER_PREFIX + this.orderIdentification;
    }
    private Asset createOrderAsset()
    {
        return new Asset(PREFIX_ASSET+this.orderIdentification, new Identifier(IdentifierType.CUSTOM, PREFIX_ORDER_ASSET_IDENTIFIER), ASSET_KIND);

    }


}
