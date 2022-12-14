package ResourceModel;

import ResourceModel.Hierarchy;
import ResourceModel.ResourceInterfaces;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;

import java.util.ArrayList;
import java.util.List;

public abstract class Resource {

    Hierarchy hierarchicalStructureOfResource = null;

    Identification resourceIdentification = null;

    /**
     * AAS Environment
     */
    private List<Submodel> listMachineSubmodels = new ArrayList<>();


    public Resource (Identification resourceIdent)
    {
        this.resourceIdentification = resourceIdent;
    }
    public Resource (Identification resourceIdent, Hierarchy resourceHierarchy)
    {
        this.resourceIdentification = resourceIdent;
        this.hierarchicalStructureOfResource = resourceHierarchy;
    }
    public AssetAdministrationShell createResourceAAS()
    {
        return null;
    }
}
