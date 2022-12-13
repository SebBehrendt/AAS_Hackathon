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

import java.util.ArrayList;
import java.util.List;

public abstract class Product_abstract  implements IAAS {

    DigitalNameplate digitalNameplate = null;
    String productIdentification;

    private List<Submodel> listOfSubmodels = new ArrayList<>();

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

     public void addSubmodelToList(Submodel submodel)
    {
        this.listOfSubmodels.add(submodel);
    }

    /**
     * AAS Environment
     */

    public AssetAdministrationShell createAAS()
    {
        AssetAdministrationShell productAAS =
                new AssetAdministrationShell(AASHelper.nameToIdShort(AAS_PREFIX + this.productIdentification),
                        new Identifier(IDENTIFIER_TYPE_AAS, AASHelper.nameToIdentifier(AAS_IDENTIFIER_PREFIX  + this.productIdentification +AAS_IDENTIFIER_SUFFIX)),
                        createProductAsset(DEFAULT_ASSET_KIND));

        createSubmodelsOfProduct(productAAS);
        return productAAS;
    }

   @Override
   public AssetAdministrationShell createAAS(AssetKind assetKind)
    {
        AssetAdministrationShell productAAS =
                new AssetAdministrationShell(AASHelper.nameToIdShort(AAS_PREFIX + this.productIdentification),
                        new Identifier(IDENTIFIER_TYPE_AAS, AASHelper.nameToIdentifier(AAS_IDENTIFIER_PREFIX  + this.productIdentification +AAS_IDENTIFIER_SUFFIX)),
                        createProductAsset(assetKind));

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

    abstract Asset createProductAsset();
    abstract Asset createProductAsset(AssetKind assetKind);
    public void createAndUploadAAStoServer() //TODO does it work?
    {
        Helper.ServerAASX.uploadAAStoServer(this.createAAS(), this.listOfSubmodels);
    }

    private static final IdentifierType IDENTIFIER_TYPE_AAS = IdentifierType.CUSTOM;
    private static final IdentifierType IDENTIFIER_TYPE_ASSET = IdentifierType.CUSTOM;
    private static final String AAS_PREFIX = "AAS_";
    private static final String ASSET_PREFIX = "Asset_";
    private static final String AAS_IDENTIFIER_PREFIX = "AAS_";
    private static final String AAS_IDENTIFIER_SUFFIX = "_Identifier";
    private static AssetKind DEFAULT_ASSET_KIND = AssetKind.INSTANCE;
}


