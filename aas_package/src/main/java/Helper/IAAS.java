package Helper;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;

import java.util.ArrayList;
import java.util.List;

public interface IAAS {
    List<ISubmodel> listOfSubmodelClasses = new ArrayList<>();
    List<Submodel> listOfSubmodels = new ArrayList<>();

 /**
  * Adds Submodel (IDTA / Basyx) to list -> for AAS upload to server.
   * @param basyxSubmodel
  */
 void addSubmodelToList(Submodel basyxSubmodel);

 /**
  * Get´s Identification for creating Identifiers
  * @return returns Identification of Asset / AAS
  */
     String getIdentification();
     AssetAdministrationShell createAAS();

 /**
  * Creates AAS from Object with possibility to create Type AAS
  * @param kind AssetKind type or Instance
  * @return created AAS -> does not yet upload it to server
  */
 AssetAdministrationShell createAAS(AssetKind kind);
 Asset createAsset();
 Asset createAsset(AssetKind kind);
 List<Submodel> getSubmodels();

 public void createSubmodels(AssetAdministrationShell aas) ;
 public void createAndUploadAAStoServer();




}
