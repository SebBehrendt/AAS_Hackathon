package ProductModel;

import Helper.AASHelper;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;

public class StandardComponent extends Product_abstract{

    public StandardComponent(DigitalNameplate digitalNameplate)
    {
        super(digitalNameplate);
    }


    @Override
    Asset createProductAsset() {
       return new Asset(AASHelper.nameToIdShort(ASSET_PREFIX + this.productIdentification),
               new Identifier(IDENTIFIER_TYPE_ASSET, ASSET_PREFIX + this.productIdentification), DEFAULT_ASSET_KIND);
    }

    @Override
    Asset createProductAsset(AssetKind assetKind) {
        return new Asset(AASHelper.nameToIdShort(ASSET_PREFIX + this.productIdentification),
                new Identifier(IDENTIFIER_TYPE_ASSET, ASSET_PREFIX + this.productIdentification), assetKind);
    }
    //in abstract: createAndUploadAAStoServer()

    private static final IdentifierType IDENTIFIER_TYPE_AAS = IdentifierType.CUSTOM;
    private static final IdentifierType IDENTIFIER_TYPE_ASSET = IdentifierType.CUSTOM;
    private static final String AAS_PREFIX = "AAS_";
    private static final String ASSET_PREFIX = "Asset_";
    private static final String AAS_IDENTIFIER_PREFIX = "AAS_";
    private static final String AAS_IDENTIFIER_SUFFIX = "_Identifier";
    private static AssetKind DEFAULT_ASSET_KIND = AssetKind.INSTANCE;
}
