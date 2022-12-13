package ProductModel;

import Helper.AASHelper;
import Helper.ISubmodel;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;

public class Product extends Product_abstract{
    DesignInformation designInformation;
    BOM subComponentsBOM;
    Production productionInformation = null; //if null: create empty Submodel?
    public Product(String productId)
    {
        super(productId);
    }

    public Product (String productId, DigitalNameplate digitalNameplate)
    {
        super(productId, digitalNameplate);
    }
    public Product (String productId, DigitalNameplate digitalNameplate, BOM bom, Production production)
    {
        super(productId, digitalNameplate);
        this.subComponentsBOM = bom;
        listOfSubmodelClasses.add(bom);
        this.productionInformation = production;
        listOfSubmodelClasses.add(production);
    }
    public Product (String productId, DigitalNameplate digitalNameplate, BOM bom)
    {
        super(productId, digitalNameplate);
        this.subComponentsBOM = bom;
    }

    public void addDesignInformation(DesignInformation designInfo)
    {
        this.designInformation = designInfo;
        listOfSubmodelClasses.add(designInfo);
    }
    public void addSubComponents(BOM subComponents)
    {
        this.subComponentsBOM = subComponents;
        listOfSubmodelClasses.add(subComponents);
    }
    public void addProductionInformation(Production productionInformation)
    {
        this.productionInformation = productionInformation;
    }
    public String getIdentification()
    {
        return this.productIdentification;
    }


    /**
     * AAS Environment
     */
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

    private static final IdentifierType IDENTIFIER_TYPE_AAS = IdentifierType.CUSTOM;
    private static final IdentifierType IDENTIFIER_TYPE_ASSET = IdentifierType.CUSTOM;
    private static final String AAS_PREFIX = "AAS_";
    private static final String ASSET_PREFIX = "Asset_";
    private static final String AAS_IDENTIFIER_PREFIX = "AAS_";
    private static final String AAS_IDENTIFIER_SUFFIX = "_Identifier";
    private static AssetKind DEFAULT_ASSET_KIND = AssetKind.INSTANCE;

}


