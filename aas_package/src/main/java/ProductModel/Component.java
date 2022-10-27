package ProductModel;

import Helper.AASHelper;
import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;

import java.util.*;

public class Component implements IProduct {
    private static final String ASSET_ID_PREFIX = "Id_Prefix_Organisation_";
    private static final String ASSET = "Asset_";
    private static final String AAS_ID_PREFIX = "AAS_";
    private static final String PRODUCT_NAME = "Name";
    private static final String PRODUCT_IDENTIFICATION = "Product_Identification";
    private static final String PRODUCT_LIFECYCLE_STATE = "Product_Lifecycle_state";

    String name;
    public String componentIdentification;
    Optional <String> internalComponentId;
    Map<String,String> multiLanguageComponentDescription = new HashMap<>();
    String Supplier;
    // ContactInfo
    ConstructionData constructionAndSpecificationData;
    ProductKind componentKind = ProductKind.INSTANCE;
    ComponentState stateOfComponent;

    Submodel smDigitalNameplateOfComponent; //may be created, or downloaded
    Submodel smTechnicalDataOfComponent;
    AssetAdministrationShell aasOfComponent; //from supplier if
    List<Submodel> listOfSubmodels = new ArrayList<>();


    public Component (String nameOfComponent)
    {
        this.name = nameOfComponent;
    }
    public Component (String nameOfComponent, String componentIdSupplier)
    {
        this.name = nameOfComponent;
        this.componentIdentification = componentIdSupplier;
        this.setComponentKind(ProductKind.TYPE);
    }

    public Component (AssetAdministrationShell downloadedAasOfComponent, List<Submodel> listOfSubmodelsOfDownloadedAAS)
    {
        this.aasOfComponent = downloadedAasOfComponent;
        this.listOfSubmodels = listOfSubmodelsOfDownloadedAAS;
    }
    public Component (AssetAdministrationShell downloadedAasOFComponent)
    {
        this.aasOfComponent = downloadedAasOFComponent;
        //aasOfComponent.getSubmodels();
    }
    public void setName(String newName) {this.name = newName;}
    protected ProductKind getComponentKind() {return componentKind;}
    private Optional<String> getInternalComponentId() {return internalComponentId;}

    protected ComponentState getStateOfComponent() {return stateOfComponent;}
    public void setComponentKind(ProductKind componentKind) {
        this.componentKind = componentKind;
    }
    public void setSupplier(String supplier) {Supplier = supplier;}
    public void setStateOfComponent(ComponentState stateOfComponent) {this.stateOfComponent = stateOfComponent;}
    @Override
    public void setConstructionData(ConstructionData constructionData) {this.constructionAndSpecificationData = constructionData;}

    @Override
    public List<Submodel> getListOfSubmodels() {
        return null;
    }

    @Override
    public String getName() {return this.name;}
    @Override
    public void setMultiLanguageDescription(Map<String,String> description) {this.multiLanguageComponentDescription = description;}
    @Override
    public AssetAdministrationShell getProductAAS( ) {
       return this.aasOfComponent;
    }

    @Override
    public ConnectedAssetAdministrationShell getProductAASFromSupplier(Identifier componentAasId, ConnectedAssetAdministrationShellManager manager) {return null;}
    @Override
    public void updateAndUploadSM(Submodel submodelToUpdate, ConnectedAssetAdministrationShellManager manager) {}

    @Override
    public ProductType getTypeOfSubcomponent() {return ProductType.COMPONENT;}
    @Override
    public ProductionState getProductionState() {
        switch (this.stateOfComponent){
            case PLANNED:
                return ProductionState.PRODUCTPLANNING;
            case SELECTED:
                return ProductionState.SELECTED;
            case ORDERED:
                return ProductionState.ORDERED;
            case DELIVERED:
                return ProductionState.DELIVERED;
            case AVAILABLE:
                return ProductionState.AVAILABLE;
            case FINAL_STATE:
                return ProductionState.FINAL_STATE;
        }
        return ProductionState.UNKNOWN;
    }

    public AssetAdministrationShell createComponentAAS()
    {
        Asset componentAsset = createAssetOfComponent();
        AssetAdministrationShell componentAAS = new AssetAdministrationShell(AASHelper.nameToIdShort(this.getName()),
                createIdentifierOfAAS(), componentAsset);
        //check if internal ID is set for supplier Component
        this.componentIdentification = (this.componentIdentification == null) ? this.getName()+"_ID" : componentIdentification;

        this.aasOfComponent = componentAAS;
        return componentAAS;
    }
    private Asset createAssetOfComponent ()
    {
        return new Asset(ASSET+ AASHelper.nameToIdShort(this.getName()), createIdentifierOfAsset(),getAssetKind());
    }
    private Identifier createIdentifierOfAAS()
    {
        //TODO longterm: implement String builder for custom IDs
        return new Identifier(IdentifierType.CUSTOM, AAS_ID_PREFIX+this.getName());
    }
    private AssetKind getAssetKind()
    {
        if (this.getComponentKind().equals(ProductKind.TYPE))
        {
            return AssetKind.TYPE;
        }
        else return AssetKind.INSTANCE;
    }

    private Identifier createIdentifierOfAsset()
    {
        if (this.getInternalComponentId() != null)
        {
            return new Identifier(IdentifierType.CUSTOM, ASSET_ID_PREFIX + this.getInternalComponentId()) ;
        }
        else return new Identifier(IdentifierType.CUSTOM, ASSET_ID_PREFIX + this.getName());
    }
}
