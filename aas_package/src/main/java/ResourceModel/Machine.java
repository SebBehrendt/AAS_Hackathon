package ResourceModel;

import ResourceModel.ResourceInterfaces;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;

public class Machine extends Resource{

    ResourceInterfaces resourceInterfaces = null;
    // Constructors for combinations
    public Machine(Identification machineIdent)
    {
        super(machineIdent);
    }
    public Machine (Identification machineIdent, Hierarchy hierarchy, ResourceInterfaces resourceInterfaces)
    {
        super(machineIdent, hierarchy);
        this.resourceInterfaces = resourceInterfaces;
    }

    @Override
    public void addSubmodelToList(Submodel basyxSubmodel) {

    }

    @Override
    public String getIdentification() {
        return this.getIdentification();
    }

    @Override
    public void createSubmodels() {

    }

    @Override
    public AssetAdministrationShell createAAS() {
        return null;
    }

    @Override
    public AssetAdministrationShell createAAS(AssetKind kind) {
        return null;
    }
}
