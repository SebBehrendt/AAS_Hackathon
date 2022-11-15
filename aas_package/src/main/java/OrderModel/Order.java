package OrderModel;

import Helper.AASHelper;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangString;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.MultiLanguageProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {

    private static IdentifierType IDENTIFIER_TYPE = IdentifierType.CUSTOM;
    private static AssetKind ASSET_KIND  = AssetKind.INSTANCE; // Default
    private static final String AAS_IDENTIFIER_PREFIX = "Order_AAS_";
    private static final String PREFIX_ASSET = "Asset_";
    private static final String MLP_ORDER_DESCRIPTION_SHORT_ID= "OrderDescription";
    private static KeyElements KEYELEMENTS_MLP = KeyElements.MULTILANGUAGEPROPERTY; //Or Conceptdescription if referenced IRDI?

    String orderIdentification;
    Map<String, String> multiLanguageOrderDescription = new HashMap();
    Map<String,String> listOrderFiles = new HashMap();
    ProductInstances productInstances;
    List<Submodel> listOfOrderSubmodels = new ArrayList<>();

    //Constructors
    public Order (String orderId) {this.orderIdentification = orderId; }
    public Order (String orderId, ProductInstances instances) {
        this.orderIdentification = orderId;
        this.productInstances = instances;
    }

    //Getters and Setters
    public String getOrderIdentification() {
        return orderIdentification;
    }
    public void AddMultiLanguageOrderDescription (String language, String description)
    {
        this.multiLanguageOrderDescription.put(language, description);
    }

    public void setListOrderFiles(Map<String, String> listOrderFiles) {this.listOrderFiles = listOrderFiles;}
    public void addOrderFile (String filename, String filepath) {this.listOrderFiles.put(filename, filepath);}

    public List<Submodel> getListOfOrderSubmodels() {return listOfOrderSubmodels;}
    public AssetAdministrationShell updateOrderAAS ()
    {
        // tbd TODO
        return null;
    }
    //AAS-Environment
    public AssetAdministrationShell createOrderAAS()
    {
        AssetAdministrationShell orderAAS = new AssetAdministrationShell(AASHelper.nameToIdShort(this.getOrderIdentification()),
                new Identifier(IDENTIFIER_TYPE, createAASIdentifier()), createOrderAsset());
        /**
         * Create SMs
         */


        return orderAAS;
    }
    protected Submodel createSubmodelGeneralInfo () //TODO DUMMY VALUES
    {
        Submodel generalInfoSM = new Submodel();

        // Add MLP Description
        LangStrings mlp = new LangStrings();
        for (Map.Entry entry : this.multiLanguageOrderDescription.entrySet())
        {
            mlp.add(new LangString(AASHelper.nameToIdShort(entry.getKey().toString()), entry.getValue().toString()));
        }
        MultiLanguageProperty mlpOrderDescription = new MultiLanguageProperty(new Reference(new Identifier(IdentifierType.CUSTOM, "Dummy"),
                KEYELEMENTS_MLP, true), mlp);
        mlpOrderDescription.setIdShort(MLP_ORDER_DESCRIPTION_SHORT_ID);

        this.listOfOrderSubmodels.add(generalInfoSM);
        return generalInfoSM;
    }

    @NotNull
    @Contract(pure = true)
    private String createAASIdentifier()
    {
        return AAS_IDENTIFIER_PREFIX + this.orderIdentification;
    }
    private Asset createOrderAsset()
    {
        return new Asset(PREFIX_ASSET+this.orderIdentification, new Identifier(IdentifierType.CUSTOM, "dummy_Asset_Id"), ASSET_KIND);

    }
    private void addProductInstance (AssetAdministrationShell instanceAAS)
    {
        // Add specific  AAS to SM and upload new version to server
    }
}
