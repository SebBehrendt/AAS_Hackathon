package ResourceModel;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hierarchy implements ISubmodel {
    private static final String SUBMODEL_HIERARCHY_ID_SHORT = "Hierarchy";
    private static final String SUBMODEL_HIERARCHY_IDENTIFIER = "Ident";
    private static final String SMC_SUBSYSTEM_PREFIX = "SubSystem_";

    Map<ResourceType, AssetAdministrationShell> listOfSubsystems = new HashMap<>(); //

    public Hierarchy (Map<ResourceType,AssetAdministrationShell> listOfSubs)
    {
        this.listOfSubsystems = listOfSubs;
    }

    private SubmodelElementCollection createSMCSubsystems(Map.Entry<ResourceType, AssetAdministrationShell> subSystem)
    {
        SubmodelElementCollection smcSubsystem = new SubmodelElementCollection(AASHelper.nameToIdShort(SMC_SUBSYSTEM_PREFIX + subSystem.getValue().getIdShort()));
        ReferenceElement refElSubsystem = new ReferenceElement(ID_SHORT, new Reference((Identifier)subSystem.getValue().getIdentification(),
                KeyElements.ASSETADMINISTRATIONSHELL, false));
        smcSubsystem.addSubmodelElement(refElSubsystem);
        smcSubsystem.addSubmodelElement(new Property(RESOURCE_TYPE, subSystem.getKey().getResourceTypeName()));
        return smcSubsystem;

    }
    @Override
    public Submodel createSubmodel(IAAS abstractShellObject) {
        Submodel submodelHierarchy = new Submodel(AASHelper.nameToIdShort(SUBMODEL_HIERARCHY_ID_SHORT),
                new Identifier(IdentifierType.CUSTOM, SUBMODEL_HIERARCHY_IDENTIFIER ));

        for (Map.Entry<ResourceType,AssetAdministrationShell> subSystemEntry: this.listOfSubsystems.entrySet())
        {
            submodelHierarchy.addSubmodelElement(createSMCSubsystems(subSystemEntry));
        }
        abstractShellObject.addSubmodelToList(submodelHierarchy);
        return submodelHierarchy;
    }
    private static final String ID_SHORT = "id_short";
    private static final String RESOURCE_TYPE = "Resource_Type";
}
