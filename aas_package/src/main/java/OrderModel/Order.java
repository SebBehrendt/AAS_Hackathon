package OrderModel;

import Helper.AASHelper;
import AAS_Framework.IAAS;
import AAS_Framework.ISubmodel;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
//TODO: implement framework (AAS_Object and ISubmodel)

public class Order implements IAAS {

    String orderIdentification;
    private ProductInstances productInstances = null; //Position --> Ref AAS, Quantity (rename)
    private GeneralOrderInformation generalOrderInformation = null;

    public Order(String orderId) {this.orderIdentification = orderId; }
    public Order(String orderId, ProductInstances instances) {
        this.orderIdentification = orderId;
        this.productInstances = instances;
        listOfSubmodelClasses.add(this.productInstances);
    }
    public Order(String orderId, ProductInstances instances, GeneralOrderInformation generalOrderInformation) {
        this.orderIdentification = orderId;
        this.productInstances = instances;
        listOfSubmodelClasses.add(this.productInstances);
        this.generalOrderInformation = generalOrderInformation;
        listOfSubmodelClasses.add(this.generalOrderInformation);
    }
    public void setGeneralOrderInformation (GeneralOrderInformation generalInfo)
    {
        this.generalOrderInformation = generalInfo;
        listOfSubmodelClasses.add(this.generalOrderInformation);
    }
    public void setProductInstances (ProductInstances instances)
    {
        this.productInstances = instances;
        listOfSubmodelClasses.add(this.productInstances);
    }
    //Getters and Setters
    @Override
    public String getIdentification() {
        return orderIdentification;
    }

     @Override
     public void addSubmodelToList(Submodel submodel)
    {
        this.listOfSubmodels.add(submodel);
    }

    //AAS-Environment
    @Override
    public AssetAdministrationShell createAAS()
    {
        AssetAdministrationShell orderAAS = new AssetAdministrationShell(AASHelper.nameToIdShort(this.getIdentification()),
                new Identifier(IDENTIFIER_TYPE, createAASIdentifier()), createAsset());
        /**
         * Create SMs
         */
       createSubmodels(orderAAS);
        return orderAAS;
    }

    @Override
    public AssetAdministrationShell createAAS(AssetKind kind) {
        AssetAdministrationShell orderAAS = new AssetAdministrationShell(AASHelper.nameToIdShort(this.getIdentification()),
                new Identifier(IDENTIFIER_TYPE, createAASIdentifier()), createAsset(kind));

        createSubmodels(orderAAS );
        return orderAAS;
    }

    @Override
    public List<Submodel> getSubmodels() {
        return listOfSubmodels;
    }

    @Override
    public void createSubmodels(AssetAdministrationShell shell) {
        for(ISubmodel submodelObj : listOfSubmodelClasses)
        {
            Submodel createdSubmodel = submodelObj.createSubmodel(this);
            shell.addSubmodel(createdSubmodel);
            listOfSubmodels.add(createdSubmodel);
        }
    }

    @NotNull
    @Contract(pure = true)
    private String createAASIdentifier()
    {
        return AAS_IDENTIFIER_PREFIX + this.orderIdentification;
    }
   @Override
   public Asset createAsset()
    {
        return new Asset(PREFIX_ASSET+this.orderIdentification,
                new Identifier(IdentifierType.CUSTOM, PREFIX_ORDER_ASSET_IDENTIFIER), ASSET_KIND);
    }
    @Override
    public Asset createAsset(AssetKind kind)
    {
        return new Asset(PREFIX_ASSET+this.orderIdentification,
                new Identifier(IdentifierType.CUSTOM, PREFIX_ORDER_ASSET_IDENTIFIER), kind);
    }
    @Override
    public void createAndUploadAAStoServer()
    {
        Helper.ServerAASX.uploadAAStoServer(this.createAAS(), this.listOfSubmodels);
    }


    private static final IdentifierType IDENTIFIER_TYPE = IdentifierType.CUSTOM;
    private static AssetKind ASSET_KIND  = AssetKind.INSTANCE; // Default
    private static final String AAS_IDENTIFIER_PREFIX = "Order_AAS_";
    private static final String PREFIX_ASSET = "Asset_";
    private static final String PREFIX_ORDER_ASSET_IDENTIFIER= "Order_";

}
