package ProductModel;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;

import java.util.List;
import java.util.Map;

public interface IProduct {

    public void setConstructionData(ConstructionData constructionData);
    public List<Submodel> getListOfSubmodels();
    public String getName();
    public void setMultiLanguageDescription(Map<String,String> description);
    public AssetAdministrationShell getProductAAS();
   // public void updateAAS(AssetAdministrationShell aas);
    public ConnectedAssetAdministrationShell getProductAASFromSupplier(Identifier componentAasId, ConnectedAssetAdministrationShellManager manager);
    void updateAndUploadSM(Submodel submodelToUpdate, ConnectedAssetAdministrationShellManager manager);
    ProductType getTypeOfSubcomponent ();
    ProductionState getProductionState();

}
