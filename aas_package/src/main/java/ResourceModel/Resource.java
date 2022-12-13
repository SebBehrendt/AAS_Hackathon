package ResourceModel;

import Helper.AASHelper;
import Helper.ISubmodel;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;

import java.util.List;


public abstract class Resource implements IResource {

    Hierarchy hierarchicalStructureOfResource = null;
    Identification resourceIdentification;
    String id;

    /**
     * AAS Environment
     */
    public Resource (Identification resourceIdent)
    {
        this.resourceIdentification = resourceIdent;
        listOfSubmodelClasses.add(this.resourceIdentification);
        this.id = this.resourceIdentification.id;
    }
    public Resource (Identification resourceIdent, Hierarchy resourceHierarchy)
    {
        this.resourceIdentification = resourceIdent;
        listOfSubmodelClasses.add(this.resourceIdentification);
        this.id = this.resourceIdentification.id;

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
    protected String getId()
    {
        return this.id;
    }

   @Override
   public AssetAdministrationShell createAAS()
    {
        AssetAdministrationShell resourceAAS = new AssetAdministrationShell(AASHelper.nameToIdShort(this.getIdentification()),
                new Identifier(IdentifierType.CUSTOM, AAS_IDENTIFIER_PREFIX + this.getIdentification()),createAsset() );

        createSubmodels(resourceAAS);

        return resourceAAS;
    }
    @Override
    public AssetAdministrationShell createAAS(AssetKind kind)
    {
        AssetAdministrationShell resourceAAS = new AssetAdministrationShell(AASHelper.nameToIdShort(this.getIdentification()),
                new Identifier(IdentifierType.CUSTOM, AAS_IDENTIFIER_PREFIX + this.getIdentification()),createAsset(kind));

        for (ISubmodel submodelObject : listOfSubmodelClasses)
        {
            resourceAAS.addSubmodel(submodelObject.createSubmodel(this));
        }
        return resourceAAS;
    }

    @Override
    public void createSubmodels(AssetAdministrationShell shell ) {
        for(ISubmodel submodelObj : listOfSubmodelClasses)
        {
            Submodel createdSubmodel = submodelObj.createSubmodel(this);
            shell.addSubmodel(createdSubmodel);
            this.addSubmodelToList(createdSubmodel);
        }
    }
    @Override
    public List<Submodel> getSubmodels()
    {
        return listOfSubmodels;
    }

    @Override
    public Asset createAsset() {
        return new Asset(AASHelper.nameToIdShort(this.id), new Identifier(IdentifierType.CUSTOM, ASSET_IDENTIFIER_PREFIX + this.getIdentification()), AssetKind.INSTANCE);
    }
    @Override
    public Asset createAsset(AssetKind kind) {
        return new Asset(AASHelper.nameToIdShort(this.id), new Identifier(IdentifierType.CUSTOM, ASSET_IDENTIFIER_PREFIX + this.getIdentification()), kind);
    }
    @Override
    public void createAndUploadAAStoServer()
    {
        Helper.ServerAASX.uploadAAStoServer(this.createAAS(), this.listOfSubmodels);
    }

    private static final String ASSET_IDENTIFIER_PREFIX = "asset_";
    private static final String AAS_IDENTIFIER_PREFIX = "AAS_";
}
