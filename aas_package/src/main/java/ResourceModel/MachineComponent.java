package ResourceModel;

import Helper.AASHelper;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;

public class MachineComponent extends Resource{
    //TODO ATtributes and Class Tool (Tool extends component)
    public MachineComponent(Identification resourceIdent) {
        super(resourceIdent);
    }

    @Override
    public void addSubmodelToList(Submodel basyxSubmodel) {
        listOfSubmodels.add(basyxSubmodel);
    }

    @Override
    public String getIdentification() {
       return this.getId();
    }

    @Override
   public Asset createAsset() {
        return new Asset(AASHelper.nameToIdShort(this.getId()),
                new Identifier(IdentifierType.CUSTOM, ASSET_IDENTIFIER_PREFIX + this.getIdentification()), AssetKind.INSTANCE);
    }


    private static final String ASSET_IDENTIFIER_PREFIX = "asset_";
}
