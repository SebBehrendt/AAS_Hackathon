package OrderModel;

import Helper.AASHelper;
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

import java.util.HashMap;
import java.util.Map;

public class GeneralOrderInformation {
    private static final String PREFIX_ASSET = "Asset_";
    private static final String MLP_ORDER_DESCRIPTION_SHORT_ID= "OrderDescription";
    private static KeyElements KEYELEMENTS_MLP = KeyElements.MULTILANGUAGEPROPERTY; //Or Conceptdescription if referenced IRDI?
    private static final String ORDER_DESCRIPTION = "Order_Description";
    private static final String SMC_ORDERFILES_ID_SHORT = "Order_Files";
    // Customer Info
    // Files
    String orderId; //-> from Order general?
    String orderPriority;
    Map<String, String> multiLanguageOrderDescription = new HashMap();
    Map<String,String> listOrderFiles = new HashMap(); //-> SMC OrderFiles //Map <Name, Link>

    CustomerInformation customerInformation = null;
    TimeScheduling timeScheduling = null;


    public void setListOrderFiles(Map<String, String> listOrderFiles) {this.listOrderFiles = listOrderFiles;}
    public void addOrderFile (String filename, String filepath) {this.listOrderFiles.put(filename, filepath);}
    public void AddMultiLanguageOrderDescription (String language, String description) {this.multiLanguageOrderDescription.put(language, description);}


    protected Submodel createSubmodelGeneralInfo (Order order)
    {
        Submodel generalInfoSM = new Submodel();

        generalInfoSM.addSubmodelElement(new Property(AASHelper.nameToIdShort("Order_Identification"),order.getOrderIdentification()));
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
        order.addSubmodelToListOfOrderSubmodels(generalInfoSM);
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

}
