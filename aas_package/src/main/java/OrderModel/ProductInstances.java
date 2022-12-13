package OrderModel;

import Helper.AASHelper;
import Helper.IAAS;
import Helper.ISubmodel;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

import java.util.*;

public class ProductInstances implements ISubmodel {
    private static final String QUANTITY = "Quantity";
    Map<String, ProductInstance> listProductInstances = new HashMap<>(); // id Short, {AAS, quantity}


    public ProductInstances (List<ProductInstance> listOfProductAAS)
    {
        for (ProductInstance productAAS : listOfProductAAS)
        {
            this.listProductInstances.put(productAAS.getInstance().getIdShort(), productAAS);
        }
    }
    public ProductInstances (ProductInstance productAAS)
    {
            this.listProductInstances.put(productAAS.getInstance().getIdShort(), productAAS);
    }
    public void addProductAAS (AssetAdministrationShell productAAS)
    {
        this.listProductInstances.put(productAAS.getIdShort(), new ProductInstance(productAAS));
    }

    /**
     * AAS Environment
     *      Submodel ProductInstances includes SMCs for every Instance and references AAS of the Instance
     */
    @Override
    public Submodel createSubmodel(IAAS order) {
        Submodel productInstancesSubmodel = new Submodel(INSTANCES_ID_SHORT,
                new Identifier(IdentifierType.CUSTOM, INSTANCES_IDENTIFIER));

        for (Map.Entry<String, ProductInstance> entry : this.listProductInstances.entrySet())
        {
            SubmodelElementCollection productSMC = new SubmodelElementCollection(AASHelper.nameToIdShort(entry.getKey()));
            ReferenceElement productRef = new ReferenceElement(AASHelper.nameToIdShort(entry.getKey()),
                    new Reference(entry.getValue().getInstance(),
                            KeyElements.ASSETADMINISTRATIONSHELL,false));
            productSMC.addSubmodelElement(productRef);
            productInstancesSubmodel.addSubmodelElement(new Property(AASHelper.nameToIdShort(QUANTITY), entry.getValue().getQuantity()));
            productInstancesSubmodel.addSubmodelElement(productSMC);
        }
        order.addSubmodelToList(productInstancesSubmodel);
        return productInstancesSubmodel;
    }
    private static final String INSTANCES_ID_SHORT = "Instances";
    private static final String INSTANCES_IDENTIFIER= "Instances_Identifier";

}

