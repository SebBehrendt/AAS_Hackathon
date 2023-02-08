package AAS_Framework;

import Helper.AASHelper;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;

import java.util.List;

public abstract class AAS_abstract implements IAAS{


    @Override
    public AssetAdministrationShell createAAS(AssetKind kind) {
        AssetAdministrationShell myAAS = new AssetAdministrationShell(AASHelper.nameToIdShort(this.getIdentification()),
                new Identifier(IDENTIFIER_TYPE, createAASIdentifier()), createAsset(kind));

        createSubmodels(myAAS);
        return myAAS;
    }
    @Override
    public void createSubmodels(AssetAdministrationShell shell) {
        for(ISubmodel submodelObj : listOfSubmodelClasses)
        {
            Submodel createdSubmodel = submodelObj.createSubmodel(this);
            shell.addSubmodel(createdSubmodel);
            this.addSubmodelToList(createdSubmodel);
        }
    }

    @Override
    public Asset createAsset(AssetKind kind)
    {
        return new Asset(PREFIX_ASSET+this.getIdentification(),
                new Identifier(IdentifierType.CUSTOM, PREFIX_ORDER_ASSET_IDENTIFIER), kind);
    }
    @Override
    public void createAndUploadAAStoServer()
    {
        Helper.ServerAASX.uploadAAStoServer(this.createAAS(), this.listOfSubmodels);
    }
    @Override
    public List<Submodel> getSubmodels() {
        return listOfSubmodels;
    }
    @Override
    public Asset createAsset()
    {
        return createAsset(DEFAULT_ASSETKIND);
    }

    @Override
    public AssetAdministrationShell createAAS()
    {
        return createAAS(DEFAULT_ASSETKIND);
    }

    @Override
    public void addSubmodelToList(Submodel submodel)
    {
        this.listOfSubmodels.add(submodel);
    }

    private static final AssetKind DEFAULT_ASSETKIND = AssetKind.INSTANCE;

    private static final IdentifierType IDENTIFIER_TYPE = IdentifierType.CUSTOM;

    private static final String PREFIX_ASSET = "Asset_";
    private static final String PREFIX_ORDER_ASSET_IDENTIFIER= "Order_";


}
