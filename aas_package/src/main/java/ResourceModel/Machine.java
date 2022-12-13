package ResourceModel;

import ResourceModel.ResourceInterfaces;

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
}
