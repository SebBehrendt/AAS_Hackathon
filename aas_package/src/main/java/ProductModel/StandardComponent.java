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



    private static final IdentifierType IDENTIFIER_TYPE_AAS = IdentifierType.CUSTOM;
    private static final IdentifierType IDENTIFIER_TYPE_ASSET = IdentifierType.CUSTOM;
    private static final String AAS_PREFIX = "AAS_";
    private static final String ASSET_PREFIX = "Asset_";
    private static final String AAS_IDENTIFIER_PREFIX = "AAS_";
    private static final String AAS_IDENTIFIER_SUFFIX = "_Identifier";
    private static AssetKind DEFAULT_ASSET_KIND = AssetKind.INSTANCE;


}
