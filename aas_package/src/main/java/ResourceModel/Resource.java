package ResourceModel;

import ResourceModel.Hierarchy;
import ResourceModel.ResourceInterfaces;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;

import java.util.ArrayList;
import java.util.List;

public abstract class Resource implements IResource {

    Hierarchy hierarchicalStructureOfResource = null;
    Identification resourceIdentification = null;


    /**
     * AAS Environment
     */
    protected List<Submodel> listOfSubmodels = new ArrayList<>();


    public Resource (Identification resourceIdent)
    {
        this.resourceIdentification = resourceIdent;
    }
    public Resource (Identification resourceIdent, Hierarchy resourceHierarchy)
    {
        this.resourceIdentification = resourceIdent;
        listOfSubmodelClasses.add(resourceIdent);
        this.hierarchicalStructureOfResource = resourceHierarchy;
    }

   @Override
   public AssetAdministrationShell createAAS()
    {
        return null;
    }
}
