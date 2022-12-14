package ProductModel_Backup;

/**
 * Product: Products consisting of several subParts or single part products manufactured
 */


import Helper.AASHelper;
import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.MultiLanguageProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Product class for Product-AAS. Proposed new hierarchy
 * Will replace ProductModel
 *
 * To think about: Digital Nameplate for "bought products/ own products"
 * SMs:
 *  Digital Nameplate
 *  Construction
 *  Quality
 *  Hierarchy (subProducts)
 */
public class Product implements IProduct {

    private static final String ASSET_ID_PREFIX = "Id_Prefix_Organisation_";
    private static final String ASSET = "Asset_";
    private static final String AAS_ID_PREFIX = "AAS_";
    private static final String PRODUCT_NAME = "Name";
    private static final String PRODUCT_IDENTIFICATION = "Product_Identification";
    private static final String PRODUCT_LIFECYCLE_STATE = "Product_Lifecycle_state";
    private static final String SMC_ORDER_INFO = "OrderInformation";
    private static final String SMC_ORDER_FILES = "OrderFiles";
    private static final String SMC_BOM = "BOM";
    private static final String MANUFACTURING_STATUS = "Manufacturing_status";
    private static final String SUBMODEL_GENERAL_INFORMATION = "Submodel_GeneralInformation";
    private static final String SUBMODEL_PRODUCTION_INFORMATION = "Submodel_ProductionInformation";
    private static String INIT_EMPTY_STRING = "";
    private static final String PRODUCT_DESCRIPTION_MLP_ID_SHORT = "id_schort_mlp";
    //general and order Files:
    String nameOfProduct;
    String typeOfProduct = INIT_EMPTY_STRING;
    String orderID; //only for ordered Products - can be the same as internal identification
    ProductKind productKind = ProductKind.INSTANCE;
    String internalProductIdentification; //TODO implement StringBuilder -> global ID
    ProductLifecycleState productLifecycleState = ProductLifecycleState.PRODUCTPLANNING; //state changes with implemented Submodels
    Map <String, String> productDescription = new HashMap<String, String>(); //key: langKey, value: description
    Map <String, String> productDescriptionParameters = new HashMap<String, String>(); //key: langKey, value: description
    Map <String, String> orderFiles = new HashMap<>();
    Map <String, String> orderInformation = new HashMap<>();
    ConstructionData constructionData; //CAD Files
    QualityData qualityData;
    Map<String, IProduct> listSubComponents = new HashMap<String, IProduct>(); //String -> Name, IPRoduct, Product .. get their Id for AAS reference
    //Production and Execution
    ManufacturingInformation manufacturingInformation; // all manufacturers from execution Data --> for digital Nameplate

    //for AAS
    private List<Submodel> listOfSubmodels = new ArrayList<>();
    private AssetAdministrationShell productAAS;


    public Product (String nameOfProduct) {this.nameOfProduct = nameOfProduct;}
    public Product (String nameOfProduct, String orderId) {
        this.nameOfProduct = nameOfProduct;
        this.orderID = orderId;}
    public void addSubProduct (IProduct subComponent) {this.listSubComponents.put(subComponent.getName(), subComponent);}
    public void addOrderFile(String file, String url) {this.orderFiles.put(file, url);}
    public void setOrderFiles(Map<String, String> orderFileslist)
    {
        this.orderFiles.putAll(orderFileslist);
    }
    public void setTypeOfProduct(String typeOfProduct) {
        this.typeOfProduct = typeOfProduct;
    }
    public String getTypeOfProduct() {
        return typeOfProduct;
    }
    public void setProductDescriptionParameters(Map<String, String> productDescriptionParameters) {this.productDescriptionParameters = productDescriptionParameters;}
    public void addProductDescriptionParameters(String key, String value) {this.productDescriptionParameters.put(key, value);}
    public void setOrderInformation(Map<String, String> orderInformation) {
        this.orderInformation = orderInformation;
    }
    public void addOrderInformation(String key, String value)
    {
        this.orderInformation.put(key, value);
    }
    public List<Submodel> getListOfSubmodels() {
        return listOfSubmodels;
    }
    public void setInternalProductIdentification(String internalProductIdentification) {this.internalProductIdentification = internalProductIdentification;}
    public void addProductDescription (String language, String description) {this.productDescription.put(language, description);}
    public String getOrderID() {
        return orderID;
    }
    public String getInternalProductIdentification() {
        return internalProductIdentification;
    }
    public void setProductLifecycleState(ProductLifecycleState productLifecycleState) {this.productLifecycleState = productLifecycleState;}
    protected ProductLifecycleState getProductLifecycleState() {
        return productLifecycleState;
    }

    public void setManufacturingInformation(ManufacturingInformation manufacturingInformation) {this.manufacturingInformation = manufacturingInformation;}
    public Map getListSubComponents() {return listSubComponents;}
    private ProductKind getProductKind() {return productKind;}
    private void updateProductState(ProductLifecycleState state) {this.productLifecycleState = state;}
    private void setProductAAS(AssetAdministrationShell aas)
    {
        this.productAAS = aas;
    }


    /**
     * Used to change AssetKind of the ProductAsset. Default: Instance
     * @param kindOfProduct Type or Instance
     */
    public void setProductKind(ProductKind kindOfProduct) {this.productKind = kindOfProduct;}
    public void setQualityData(QualityData qualityData) {this.qualityData = qualityData;}

    @Override
    public void setConstructionData(ConstructionData constructionData) {this.constructionData = constructionData;}
    @Override
    public String getName() {
        return this.nameOfProduct;
    }
    @Override
    public void setMultiLanguageDescription(Map<String,String> description) {
        this.productDescription = description;
    }
    @Override
    public AssetAdministrationShell getProductAAS() {
        return this.productAAS;
    }

    @Override
    public ConnectedAssetAdministrationShell getProductAASFromSupplier(Identifier componentAasId, ConnectedAssetAdministrationShellManager manager)
    { //HTTP CLient!
        return null;}


    /**
     * AAS Environment
     */
    // AAS with ProductInformation, but without any other submodels
    public AssetAdministrationShell createAAS ()
    {
        //create Productmodel and AAS with order infos
        Asset productAsset = createAssetOfProduct();
        AssetAdministrationShell productAAS = new AssetAdministrationShell(AASHelper.nameToIdShort(this.getName()),
                createIdentifierOfAAS(), productAsset);

        productAAS.addSubmodel(createSubmodelProductInformation(productAAS));
        if (this.productLifecycleState.getkey() >=1 && this.constructionData != null)
        {
            productAAS.addSubmodel(createSubmodelConstruction());
        }
        //Production Planning
        if (this.productLifecycleState.getkey() >=2 && this.listSubComponents != null)
        {
            productAAS.addSubmodel(createSubmodelProduction());
        }

        //for hierarchy ->
        this.productAAS = productAAS;
        return productAAS;
    }

    private Submodel createSubmodelProductInformation(AssetAdministrationShell aas)
    {
        Submodel submodelProductInformation = new Submodel(SUBMODEL_GENERAL_INFORMATION,
                new Identifier(IdentifierType.CUSTOM, SUBMODEL_GENERAL_INFORMATION+this.getName() ));

        //void set Attributes
        submodelProductInformation.addSubmodelElement(new Property(PRODUCT_NAME, this.getName()));
        submodelProductInformation.addSubmodelElement(new Property(PRODUCT_IDENTIFICATION, this.getInternalProductIdentification()));
        submodelProductInformation.addSubmodelElement(new Property(PRODUCT_LIFECYCLE_STATE, this.getProductLifecycleState().getState()));

        if (this.productDescription != null)
        {//Set MLPs for Product description
            for (Map.Entry<String, String> set : this.productDescription.entrySet())
            {
                MultiLanguageProperty mlp = new MultiLanguageProperty(new Reference(new Identifier(IdentifierType.CUSTOM, "customID"), //TODO IDs!
                        KeyElements.MULTILANGUAGEPROPERTY, false ), new LangStrings(AASHelper.nameToIdShort(set.getKey()), set.getValue()));
                mlp.setIdShort(PRODUCT_DESCRIPTION_MLP_ID_SHORT);
                submodelProductInformation.addSubmodelElement(mlp);
            }
        }
        if (this.orderFiles != null || this.orderInformation !=null)
        {submodelProductInformation.addSubmodelElement(createSMCOrderInformation());}
        if (this.listSubComponents != null) {submodelProductInformation.addSubmodelElement(createSMCOrderBOM());}

        this.listOfSubmodels.add(submodelProductInformation);
        return submodelProductInformation;
    }
    @NotNull
    private SubmodelElementCollection createSMCOrderInformation()
    {
        SubmodelElementCollection orderInfoSMC = new SubmodelElementCollection(SMC_ORDER_INFO);
        if (this.orderInformation != null)
        {
            for (Map.Entry<String, String> set : this.orderFiles.entrySet())
            {
                orderInfoSMC.addSubmodelElement(new Property(AASHelper.nameToIdShort(set.getKey()), set.getValue()));
            }
        }
        if (this.orderFiles != null)
        {
            orderInfoSMC.addSubmodelElement(createSMCOrderFiles());
        }
        return orderInfoSMC;
    }
    @NotNull
    private SubmodelElementCollection createSMCOrderFiles()
    {
        SubmodelElementCollection orderFilesSMC = new SubmodelElementCollection(SMC_ORDER_FILES);

        for (Map.Entry<String, String> set : this.orderFiles.entrySet())
        {
            orderFilesSMC.addSubmodelElement(new Property(AASHelper.nameToIdShort(set.getKey()), set.getValue()));
        }
        return  orderFilesSMC;
    }
    private SubmodelElementCollection createSMCOrderBOM()
    {
        SubmodelElementCollection SMCBOM = new SubmodelElementCollection(SMC_BOM);
        ConnectedAssetAdministrationShellManager manager =  Helper.Infrastructure.getManager();
        for (Map.Entry<String, IProduct> set : this.listSubComponents.entrySet())
        {
            // AssetAdministrationShell componentAAS = set.getValue().getProductAAS();
            Helper.Infrastructure.uploadProductToServer(set.getValue(), set.getValue().getProductAAS(), manager);
            SMCBOM.addSubmodelElement(new ReferenceElement(AASHelper.nameToIdShort(set.getKey()),
                    new Reference (set.getValue().getProductAAS(),
                            KeyElements.ASSETADMINISTRATIONSHELL, true)));
        }
        return SMCBOM;
    }
    public Submodel createSubmodelConstruction()
    {
        Submodel construction =  this.constructionData.createSubmodelConstruction(this.getName());
        this.listOfSubmodels.add(construction);
        return construction;
    }
    public Submodel createSubmodelProduction()
    {
        Submodel production = new Submodel (SUBMODEL_PRODUCTION_INFORMATION,
                new Identifier(IdentifierType.CUSTOM, SUBMODEL_PRODUCTION_INFORMATION+this.getName() ));
        //SMC status of SubComponents
        production.addSubmodelElement(createSMCProductionStatusOfSubComponents());

        this.listOfSubmodels.add(production);
        return production;
    }
    private SubmodelElementCollection createSMCProductionStatusOfSubComponents()
    {
        SubmodelElementCollection statusSMC = new SubmodelElementCollection(MANUFACTURING_STATUS);
        for ( IProduct subproduct : this.listSubComponents.values())
        {
            statusSMC.addSubmodelElement(new Property(AASHelper.nameToIdShort(subproduct.getName()), subproduct.getProductionState().getValue()));
        }
        return statusSMC;
    }
    private Identifier createIdentifierOfAAS()
    {
        //TODO longterm: implement String builder for custom IDs
        return new Identifier(IdentifierType.CUSTOM, AAS_ID_PREFIX+this.getName());
    }
    private Asset createAssetOfProduct ()
    {
        return new Asset(ASSET+AASHelper.nameToIdShort(this.getName()), createIdentifierOfAsset(),getAssetKind());
    }
    private Identifier createIdentifierOfAsset()
    {
        if (this.getInternalProductIdentification() != null)
        {
            return new Identifier(IdentifierType.CUSTOM, ASSET_ID_PREFIX + this.getInternalProductIdentification()) ;
        }
        else return new Identifier(IdentifierType.CUSTOM, ASSET_ID_PREFIX + this.getName());
    }
    private  AssetKind getAssetKind()
    {
        if (this.getProductKind().equals(ProductKind.TYPE))
        {
            return AssetKind.TYPE;
        }
        else return AssetKind.INSTANCE;
    }
    @Override
    public void updateAndUploadSM(Submodel submodelToUpdate, ConnectedAssetAdministrationShellManager manager) {
        // TODO To be implemented
    }
    @Override
    public ProductType getTypeOfSubcomponent() {
        return ProductType.PRODUCT;
    }
    @Override
    /**
     * Necessary to convert types of States of different classes.
     */
    public ProductionState getProductionState() {

        switch (this.productLifecycleState){
            case PRODUCTPLANNING:
                return ProductionState.PRODUCTPLANNING;
            case DESIGN:
                return ProductionState.DESIGN;
            case PRODUCTION_PLANNING:
                return ProductionState.PRODUCTION_PLANNING;
            case PRODUCTION:
                return ProductionState.PRODUCTION;
            case DELIVERY:
                return ProductionState.DELIVERY;
            case DELIVERED:
                return ProductionState.DELIVERED;
            case USAGE:
                return ProductionState.USAGE;
            case REUSAGE:
                return ProductionState.REUSAGE;
        }

        return ProductionState.UNKNOWN;
    }


}

