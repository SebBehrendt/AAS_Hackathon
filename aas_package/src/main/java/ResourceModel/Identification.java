package ResourceModel;

import Helper.AASHelper;
import AAS_Framework.IAAS;
import AAS_Framework.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

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
    public Submodel createSubmodel(IAAS abstractShellObject) {
        Submodel digitalNameplateMinimal = new Submodel(AASHelper.nameToIdShort(DIGITAL_NAMEPLATE_SHORT_ID),
                new Identifier(IdentifierType.CUSTOM,DIGITAL_NAMEPLATE_IDENTIFIER ));

        digitalNameplateMinimal.addSubmodelElement(new Property(AASHelper.nameToIdShort(RESOURCE_NAME), this.name));
        digitalNameplateMinimal.addSubmodelElement(new Property(AASHelper.nameToIdShort(RESOURCE_IDENTIFICATION), this.id));
        digitalNameplateMinimal.addSubmodelElement(new Property(AASHelper.nameToIdShort(MANUFACTURER), this.manufacturer));
        digitalNameplateMinimal.addSubmodelElement(new Property(AASHelper.nameToIdShort(RESOURCE_TYPE), this.typeOfResource.getResourceTypeName()));

        abstractShellObject.addSubmodelToList(digitalNameplateMinimal);
        return digitalNameplateMinimal;
    }

    private static final String RESOURCE_NAME = "Resource_Name";
    private static final String RESOURCE_IDENTIFICATION= "Resource_Identification";
    private static final String MANUFACTURER = "Manufacturer";
    private static final String  RESOURCE_TYPE = "Resource_Type";
    private static final String  DIGITAL_NAMEPLATE_SHORT_ID = "Digital_Nameplate";
    private static final String  DIGITAL_NAMEPLATE_IDENTIFIER = "Digital_Nameplate_Identifier_";
}
