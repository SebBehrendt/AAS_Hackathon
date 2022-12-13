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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hierarchy implements ISubmodel {
    private static final String SUBMODEL_HIERARCHY_ID_SHORT = "Hierarchy";
    private static final String SUBMODEL_HIERARCHY_IDENTIFIER = "Ident";
    private static final String SMC_SUBSYSTEM_PREFIX = "SubSystem_";

    Map<String, AssetAdministrationShell> listOfSubsystems = new HashMap<>();

    public Hierarchy (List<AssetAdministrationShell> listOfSubs)
    {
        for (AssetAdministrationShell shell : listOfSubs) {
            this.listOfSubsystems.put(shell.getIdShort(), shell);
        }
    }

    private SubmodelElementCollection createSMCSubsystems(AssetAdministrationShell shell)
    {
        SubmodelElementCollection smcSubsystem = new SubmodelElementCollection(AASHelper.nameToIdShort(SMC_SUBSYSTEM_PREFIX + shell.getIdShort()));
        ReferenceElement refElSubsystem = new ReferenceElement("id_short", new Reference((Identifier)shell.getIdentification(),
                KeyElements.ASSETADMINISTRATIONSHELL, false));
        smcSubsystem.addSubmodelElement(refElSubsystem);
        return smcSubsystem;

    }

    @Override
    public Submodel createSubmodel(IAAS abstractShellObject) {
        Submodel submodelHierarchy = new Submodel(AASHelper.nameToIdShort(SUBMODEL_HIERARCHY_ID_SHORT),
                new Identifier(IdentifierType.CUSTOM, SUBMODEL_HIERARCHY_IDENTIFIER ));

        for (AssetAdministrationShell subsysAAS : this.listOfSubsystems.values())
        {
            submodelHierarchy.addSubmodelElement(createSMCSubsystems(subsysAAS));
        }
        abstractShellObject.addSubmodelToList(submodelHierarchy);
        return submodelHierarchy;
    }
}
