package ResourceModel;

import AAS_Framework.AAS_abstract;
import Helper.AASHelper;
import AAS_Framework.ISubmodel;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;


public abstract class Resource  extends AAS_abstract {

    Hierarchy hierarchicalStructureOfResource = null;
    Identification resourceIdentification;
    String id;

    /**
     * AAS Environment
     */
    public Resource (Identification resourceIdent)
    {
        this.resourceIdentification = resourceIdent;
        this.id = this.resourceIdentification.id;

        listOfSubmodelClasses.add(this.resourceIdentification);

    }
    public Resource (Identification resourceIdent, Hierarchy resourceHierarchy)
    {
        this.resourceIdentification = resourceIdent;
        this.id = this.resourceIdentification.id;
        listOfSubmodelClasses.add(this.resourceIdentification);

        this.hierarchicalStructureOfResource = resourceHierarchy;
        listOfSubmodelClasses.add(this.hierarchicalStructureOfResource);
    }
    public void addHierarchy (Hierarchy hierarchy)
    {
        this.hierarchicalStructureOfResource = hierarchy;
        listOfSubmodelClasses.add(this.hierarchicalStructureOfResource);
    }
    public ResourceType getResourceType()
    {
        return this.resourceIdentification.typeOfResource;
    }
   @Override
   public String getIdentification()
    {
        return this.id;
    }

    @Override
    public String createAASIdentifier() {
        return AAS_IDENTIFIER_PREFIX + this.resourceIdentification;
    }



    private static final String ASSET_IDENTIFIER_PREFIX = "asset_";
    private static final String AAS_IDENTIFIER_PREFIX = "AAS_";
}
