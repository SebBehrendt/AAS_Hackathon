package ResourceModel;

import ResourceModel_backup.Hierarchy;
import ResourceModel_backup.ResourceInterfaces;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;

import java.util.ArrayList;
import java.util.List;

public abstract class Resource {

    Hierarchy hierarchicalStructureOfResource = null;
    ResourceInterfaces resourceInterfaces = null;
    Identification resourceIdentification = null;

    /**
     * AAS Environment
     */
    List<Submodel> listMachineSubmodels = new ArrayList<>();


    public Resource (Identification resourceIdent)
    {
        this.resourceIdentification = resourceIdent;
    }
}
