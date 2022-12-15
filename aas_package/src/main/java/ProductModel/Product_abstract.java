package ProductModel;

import Helper.AASHelper;
import Helper.IAAS;
import Helper.ISubmodel;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;

import java.util.List;

public abstract class Product_abstract implements IAAS {

    DigitalNameplate digitalNameplate;
    String productIdentification;

    public Product_abstract(DigitalNameplate digitalNameplate)
    {
        this.digitalNameplate = digitalNameplate;
        listOfSubmodelClasses.add(digitalNameplate);
    }
    public Product_abstract(String productId)
    {
        this.productIdentification = productId;
    }
    public Product_abstract(String productId, DigitalNameplate digitalNameplate)
    {
        this.productIdentification = productId;
        this.digitalNameplate = digitalNameplate;
        listOfSubmodelClasses.add(digitalNameplate);
    }
    public String getIdentification()
    {
        return this.productIdentification;
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
    public List<Submodel> getSubmodels() {return listOfSubmodels;}

     public void addSubmodelToList(Submodel submodel)
    {
        this.listOfSubmodels.add(submodel);
    }

    public AssetAdministrationShell createAAS()
    {
        AssetAdministrationShell productAAS =
                new AssetAdministrationShell(AASHelper.nameToIdShort(AAS_PREFIX + this.productIdentification),
                        new Identifier(IDENTIFIER_TYPE_AAS, AASHelper.nameToIdentifier(AAS_IDENTIFIER_PREFIX  + this.productIdentification +AAS_IDENTIFIER_SUFFIX)),
                        createAsset(DEFAULT_ASSET_KIND));

        createSubmodelsOfProduct(productAAS);
        return productAAS;
    }

   @Override
   public AssetAdministrationShell createAAS(AssetKind assetKind)
    {
        AssetAdministrationShell productAAS =
                new AssetAdministrationShell(AASHelper.nameToIdShort(AAS_PREFIX + this.productIdentification),
                        new Identifier(IDENTIFIER_TYPE_AAS, AASHelper.nameToIdentifier(AAS_IDENTIFIER_PREFIX  + this.productIdentification +AAS_IDENTIFIER_SUFFIX)),
                        createAsset(assetKind));

        createSubmodelsOfProduct(productAAS);
        return productAAS;
    }
    void createSubmodelsOfProduct(AssetAdministrationShell aas)
    {
        for (ISubmodel submodelObject : listOfSubmodelClasses)
        {
            aas.addSubmodel(submodelObject.createSubmodel(this));
        }
    }
    @Override
    public Asset createAsset() {
        return new Asset(AASHelper.nameToIdShort(this.getIdentification()), new Identifier(IdentifierType.CUSTOM, ASSET_IDENTIFIER_PREFIX + this.getIdentification()), AssetKind.INSTANCE);
    }
    @Override
    public Asset createAsset(AssetKind kind) {
        return new Asset(AASHelper.nameToIdShort(this.getIdentification()), new Identifier(IdentifierType.CUSTOM, ASSET_IDENTIFIER_PREFIX + this.getIdentification()), kind);
    }

    public void createAndUploadAAStoServer()
    {
        Helper.ServerAASX.uploadAAStoServer(this.createAAS(), this.listOfSubmodels);
    }

    private static final IdentifierType IDENTIFIER_TYPE_AAS = IdentifierType.CUSTOM;
    private static final String AAS_PREFIX = "AAS_";
    private static final String AAS_IDENTIFIER_PREFIX = "AAS_";
    private static final String AAS_IDENTIFIER_SUFFIX = "_Identifier";
    private static final AssetKind DEFAULT_ASSET_KIND = AssetKind.INSTANCE;
    private static final String ASSET_IDENTIFIER_PREFIX = "asset_";
}


