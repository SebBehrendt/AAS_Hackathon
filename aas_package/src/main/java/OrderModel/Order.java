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
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.MultiLanguageProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    /**
     * SMs in Order AAS:
     *  General_Order_Information -> Orderfiles integrated?
     *  Product_Instances
     *
     */
    private static final  IdentifierType IDENTIFIER_TYPE = IdentifierType.CUSTOM;
    private static AssetKind ASSET_KIND  = AssetKind.INSTANCE; // Default
    private static final String AAS_IDENTIFIER_PREFIX = "Order_AAS_";
    private static final String PREFIX_ASSET = "Asset_";
    private static final String MLP_ORDER_DESCRIPTION_SHORT_ID= "OrderDescription";
    private static KeyElements KEYELEMENTS_MLP = KeyElements.MULTILANGUAGEPROPERTY; //Or Conceptdescription if referenced IRDI?
    private static final String ORDER_DESCRIPTION = "Order_Description";
    private static final String SMC_ORDERFILES_ID_SHORT = "Order_Files";
    /**
     * Create Submodel GeneralInformation
     */
    String orderIdentification;
    Map<String, String> multiLanguageOrderDescription = new HashMap();
    Map<String,String> listOrderFiles = new HashMap(); //-> SMC OrderFiles //Map <Name, Link>

    List<Submodel> listOfOrderSubmodels = new ArrayList<>();
    /**
     * Submodels
     *      ProductInstances
     */
    ProductInstances productInstances;

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
    protected void addSubmodelToListOfSubmodels(Submodel submodel)
    {
        this.listOfOrderSubmodels.add(submodel);
    }

    public void setListOrderFiles(Map<String, String> listOrderFiles) {this.listOrderFiles = listOrderFiles;}
    public void addOrderFile (String filename, String filepath) {this.listOrderFiles.put(filename, filepath);}

    public List<Submodel> getListOfOrderSubmodels() {return listOfOrderSubmodels;}
    private Asset createAssetOfOrder()
    {
        return new Asset ("asset_id_short_dummy", new Identifier(IdentifierType.CUSTOM, "id_dummy"),AssetKind.INSTANCE);
    }

    //AAS-Environment
    public AssetAdministrationShell createOrderAAS()
    {
        AssetAdministrationShell orderAAS = new AssetAdministrationShell(AASHelper.nameToIdShort(this.getOrderIdentification()),
                new Identifier(IDENTIFIER_TYPE, createAASIdentifier()), createOrderAsset());
        /**
         * Create SMs
         */
        //create Submodel general Info
        orderAAS.addSubmodel(createSubmodelGeneralInfo());

        // create Submodel ProductInstances
        orderAAS.addSubmodel(this.productInstances.createSubmodelProductInstancesOfOrder(this));


        return orderAAS;
    }
    protected Submodel createSubmodelGeneralInfo ()
    {
        Submodel generalInfoSM = new Submodel();

        // Add MLP Description
        LangStrings mlp = new LangStrings();
        for (Map.Entry entry : this.multiLanguageOrderDescription.entrySet())
        {
            mlp.add(new LangString(AASHelper.nameToIdShort(entry.getKey().toString()), entry.getValue().toString()));
        }
        MultiLanguageProperty mlpOrderDescription = new MultiLanguageProperty(new Reference(new Identifier(IdentifierType.CUSTOM, ORDER_DESCRIPTION),
                KEYELEMENTS_MLP, true), mlp);
        mlpOrderDescription.setIdShort(MLP_ORDER_DESCRIPTION_SHORT_ID);
        // create SMC OrderFiles
        if (!this.listOrderFiles.isEmpty())
        {
            generalInfoSM.addSubmodelElement(createOrderFilesSMC());
        }

        this.listOfOrderSubmodels.add(generalInfoSM);
        return generalInfoSM;
    }
    private SubmodelElementCollection createOrderFilesSMC()
    {
        SubmodelElementCollection orderFileSMC = new SubmodelElementCollection(SMC_ORDERFILES_ID_SHORT);
        for (Map.Entry entry : this.listOrderFiles.entrySet())
        {
            orderFileSMC.addSubmodelElement(new Property(AASHelper.nameToIdShort(entry.getKey().toString()), entry.getValue().toString()));
        }
        return orderFileSMC;
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
