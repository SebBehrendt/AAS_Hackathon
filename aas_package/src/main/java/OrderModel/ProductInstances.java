package OrderModel;

import Helper.AASHelper;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

import java.util.*;

public class ProductInstances {
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
    public Submodel createSubmodelProductInstancesOfOrder (Order order)
    {
        Submodel productInstancesSubmodel = new Submodel(); //TODO define values
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
        order.addSubmodelToListOfSubmodels(productInstancesSubmodel);
        return productInstancesSubmodel;
    }
 /*
    //TEST -> Working
    public static void main (String[] args) {
        AssetAdministrationShell aas1 = new AssetAdministrationShell("aas1", new Identifier(IdentifierType.CUSTOM,"identifier_aas1"), new Asset());
        AssetAdministrationShell aas2 = new AssetAdministrationShell("aas2", new Identifier(IdentifierType.CUSTOM,"identifier_aas2"), new Asset());

        List<AssetAdministrationShell> listAAS = new ArrayList<>();
        listAAS.add(aas1);
        listAAS.add(aas2);

        ProductInstances instances = new ProductInstances(listAAS);
        Submodel submodel = instances.createSubmodelProductInstancesOfOrder();

        System.out.println("end of test");

    }

  */
}

