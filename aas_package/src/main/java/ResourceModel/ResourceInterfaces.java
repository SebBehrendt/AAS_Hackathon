package ResourceModel;

import Helper.AASHelper;
import Helper.IAAS;
import Helper.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResourceInterfaces implements ISubmodel {
    List<ResourceInterface> listOfResourceInterfaces = new ArrayList<>();

    public ResourceInterfaces (List<ResourceInterface> listOfResourceInterfaces)
    {
        this.listOfResourceInterfaces = listOfResourceInterfaces;
    }
    public ResourceInterfaces (ResourceInterface resourceInterface)
    {
        this.listOfResourceInterfaces.add(resourceInterface);
    }
    public void addResourceInterface (ResourceInterface resourceInterface)
    {
        this.listOfResourceInterfaces.add(resourceInterface);
    }
    //TODO
    protected Submodel createSubmodelResourceInterfaces ()
    {
        Submodel submodelResourceInterfaces = new Submodel("dummy", new Identifier(IdentifierType.CUSTOM, "test"));
        for (ResourceInterface resourceInterface: listOfResourceInterfaces)
        {
            submodelResourceInterfaces.addSubmodelElement(createInterfaceSMC(resourceInterface));
        }
        return submodelResourceInterfaces;
    }
    private SubmodelElementCollection createInterfaceSMC(ResourceInterface interfaceResource)
    {
        SubmodelElementCollection smcInterface = new SubmodelElementCollection("smcInterface");
        for (Map.Entry entryset: interfaceResource.getListOfAttributes().entrySet())
        {
            smcInterface.addSubmodelElement(new Property(AASHelper.nameToIdShort(entryset.getKey().toString()), entryset.getValue()));
        }
        return smcInterface;
    }


    @Override
    public Submodel createSubmodel(IAAS abstactShellObject) {
        return null;
    }
}
