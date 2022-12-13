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

    private SubmodelElementCollection createInterfaceSMC(ResourceInterface interfaceResource)
    {
        SubmodelElementCollection smcInterface = new SubmodelElementCollection(AASHelper.nameToIdShort(SMC_INTERFACE_PREFIX + interfaceResource.getInterfaceType()));
        for (Map.Entry entryset: interfaceResource.getListOfAttributes().entrySet())
        {
            smcInterface.addSubmodelElement(new Property(AASHelper.nameToIdShort(entryset.getKey().toString()), entryset.getValue()));
        }
        return smcInterface;
    }


    @Override
    public Submodel createSubmodel(IAAS abstractShellObject) {
        Submodel submodelResourceInterfaces = new Submodel(INTERFACES_ID_SHORT, new Identifier(IdentifierType.CUSTOM, INTERFACES_IDENTIFIER));
        for (ResourceInterface resourceInterface: listOfResourceInterfaces)
        {
            submodelResourceInterfaces.addSubmodelElement(createInterfaceSMC(resourceInterface));
        }
        abstractShellObject.addSubmodelToList(submodelResourceInterfaces);
        return submodelResourceInterfaces;
    }

    private static final String INTERFACES_ID_SHORT =  "Interfaces_id_Short";
    private static final String INTERFACES_IDENTIFIER =  "Submodel_Interfaces_Identifier";
    private static final String SMC_INTERFACE_PREFIX =  "SMC_Interface_";
}
