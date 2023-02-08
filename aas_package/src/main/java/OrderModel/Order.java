package OrderModel;

import AAS_Framework.AAS_abstract;
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

public class Order extends AAS_abstract {

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


   public String createAASIdentifier()
    {
        return AAS_IDENTIFIER_PREFIX + this.orderIdentification;
    }


    private static final IdentifierType IDENTIFIER_TYPE = IdentifierType.CUSTOM;
    private static final String AAS_IDENTIFIER_PREFIX = "Order_AAS_";


}
