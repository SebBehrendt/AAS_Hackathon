package ResourceModel_backup;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;

import java.util.ArrayList;
import java.util.List;

public abstract class Resource implements IResource {

Hierarchy hierarchicalStructureOfMachine;
ResourceInterfaces resourceInterfaces;

    /**
     * AAS Environment
     */
    List<Submodel> listMachineSubmodels = new ArrayList<>();

void test()
{
    List<AssetAdministrationShell> listAAS = new ArrayList<>();
    listAAS.add( new AssetAdministrationShell("id1", new Identifier(IdentifierType.CUSTOM, "id1"),new Asset()));
  listAAS.add(new AssetAdministrationShell("id2", new Identifier(IdentifierType.CUSTOM, "id2"),new Asset()));
    Hierarchy hierarchyMachine = new Hierarchy(listAAS);
    AssetAdministrationShell MachineShell = new AssetAdministrationShell();
    MachineShell.addSubmodel(hierarchyMachine.createSubmodelHierarchy() );
}
}
