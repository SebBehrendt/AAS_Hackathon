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
    private static final IdentifierType IDENTIFIER_TYPE = IdentifierType.CUSTOM;
    private static AssetKind ASSET_KIND  = AssetKind.INSTANCE; // Default
    private static final String AAS_IDENTIFIER_PREFIX = "Order_AAS_";
    private static final String PREFIX_ASSET = "Asset_";
    private static final String MLP_ORDER_DESCRIPTION_SHORT_ID= "OrderDescription";
    private static KeyElements KEYELEMENTS_MLP = KeyElements.MULTILANGUAGEPROPERTY; //Or Conceptdescription if referenced IRDI?
    private static final String ORDER_DESCRIPTION = "Order_Description";
    private static final String SMC_ORDERFILES_ID_SHORT = "Order_Files";

    String orderId;
    ProductInstances productInstances = null;
    GeneralOrderInformation generalOrderInformation = null;
    /**
     * Create Submodel GeneralInformation
     */
    String orderIdentification;


    List<Submodel> listOfOrderSubmodels = new ArrayList<>();

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
    public AssetAdministrationShell createOrderAAS()
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
        return new Asset(PREFIX_ASSET+this.orderIdentification, new Identifier(IdentifierType.CUSTOM, "dummy_Asset_Id"), ASSET_KIND);

    }


}
