package ResourceModel;

import Helper.IAAS;
import Helper.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;

// Might change to digital Nameplate --> to DNP
public class Identification implements ISubmodel {
    String name;
    String id;
    String manufacturer;
    ResourceType typeOfResource;

    public Identification(String name, String id, String manufacturer, ResourceType resourceType )
    {
        this.name = name;
        this.id = id;
        this.manufacturer = manufacturer;
        this.typeOfResource = resourceType;
    }


    @Override
    public Submodel createSubmodel(IAAS abstactShellObject) {
        return null;
    }
}
