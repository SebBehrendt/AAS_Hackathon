package ResourceModel;

import org.eclipse.basyx.submodel.metamodel.map.Submodel;


public class Machine extends Resource{

    ResourceInterfaces resourceInterfaces = null;

    public Machine(Identification machineIdent)
    {
        super(machineIdent);
    }


    public Machine (Identification machineIdent, Hierarchy hierarchy, ResourceInterfaces resourceInterfaces)
    {
        super(machineIdent, hierarchy);
        this.resourceInterfaces = resourceInterfaces;
        listOfSubmodelClasses.add(this.resourceInterfaces);
    }

    @Override
    public void addSubmodelToList(Submodel basyxSubmodel) {
       listOfSubmodels.add(basyxSubmodel);
    }

    @Override
    public String getIdentification() {
        return this.getId();
    }

private static final String ASSET_IDENTIFIER_PREFIX = "Asset_";
}
