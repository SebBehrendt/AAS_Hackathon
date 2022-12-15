package OrderModel;

import Helper.AASHelper;
import Helper.IAAS;
import Helper.ISubmodel;
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

public class GeneralOrderInformation implements ISubmodel {

    String orderPriority;
    Map<String, String> multiLanguageOrderDescription = new HashMap<>();
    Map<String,String> listOrderFiles = new HashMap<>(); //-> SMC OrderFiles //Map <Name, Link>

    CustomerInformation customerInformation = null;
    TimeScheduling timeScheduling = null;

    public GeneralOrderInformation()
    {
        this.orderPriority = DEFAULT;
    }
    public GeneralOrderInformation(String priority, CustomerInformation customerInfo, TimeScheduling timeScheduling, Map<String,String> orderFiles, Map<String,String> mlpDescription)
    {
        this.orderPriority = priority;
        this.customerInformation = customerInfo;
        this.timeScheduling = timeScheduling;
        this.listOrderFiles = orderFiles;
        this.multiLanguageOrderDescription = mlpDescription;
    }
    public void setListOrderFiles(Map<String, String> listOrderFiles) {this.listOrderFiles = listOrderFiles;}
    public void addOrderFile (String filename, String filepath) {this.listOrderFiles.put(filename, filepath);}
    public void AddMultiLanguageOrderDescription (String language, String description) {this.multiLanguageOrderDescription.put(language, description);}
    public void changePriority(String priority)
    {
        this.orderPriority = priority;
    }

    @Override
    public Submodel createSubmodel(IAAS abstractShellObject)
    {
        Submodel generalInfoSM = new Submodel("general_Information", new Identifier(IdentifierType.CUSTOM, "general_Info_Identifier")); //TODO add Aspects and Ident

        generalInfoSM.addSubmodelElement(new Property(AASHelper.nameToIdShort(ORDER_IDENTIFICATION), abstractShellObject.getIdentification()));
        generalInfoSM.addSubmodelElement(new Property(AASHelper.nameToIdShort(ORDER_PRIORITY),this.orderPriority));
        // Add MLP Description
        LangStrings mlp = new LangStrings();
        for (Map.Entry entry : this.multiLanguageOrderDescription.entrySet())
        {
            mlp.add(new LangString(AASHelper.nameToIdShort(entry.getKey().toString()), entry.getValue().toString()));
        }
        MultiLanguageProperty mlpOrderDescription = new MultiLanguageProperty(new Reference(new Identifier(IdentifierType.CUSTOM, ORDER_DESCRIPTION),
                KEYELEMENTS_MLP, true), mlp);
        mlpOrderDescription.setIdShort(MLP_ORDER_DESCRIPTION_SHORT_ID);

        // Add SMCs of submodel
        if (!this.listOrderFiles.isEmpty()) {generalInfoSM.addSubmodelElement(createOrderFilesSMC());}
        if (this.customerInformation != null) {generalInfoSM.addSubmodelElement( this.customerInformation.createCustomerInfoSMC());}
        if (this.timeScheduling != null) {generalInfoSM.addSubmodelElement(this.timeScheduling.createSMCTimeScheduling());}
        abstractShellObject.addSubmodelToList(generalInfoSM);
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
    private static final String ORDER_PRIORITY = "Priority";
    private static final String ORDER_IDENTIFICATION = "Order_Identification";
    private static final String MLP_ORDER_DESCRIPTION_SHORT_ID= "OrderDescription";
    private static final KeyElements KEYELEMENTS_MLP = KeyElements.MULTILANGUAGEPROPERTY; //Or Conceptdescription if referenced IRDI?
    private static final String ORDER_DESCRIPTION = "Order_Description";
    private static final String SMC_ORDERFILES_ID_SHORT = "Order_Files";
    private static String DEFAULT = "0";


}
