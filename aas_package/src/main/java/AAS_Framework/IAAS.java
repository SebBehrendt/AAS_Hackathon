package AAS_Framework;

import AAS_Framework.ISubmodel;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface for classes, which instantiate an AAS
 */
public interface IAAS {
    List<ISubmodel> listOfSubmodelClasses = new ArrayList<>();
    List<Submodel> listOfSubmodels = new ArrayList<>();

 /**
  * Adds Submodel (IDTA / Basyx) to list -> for AAS upload to server.
   * @param basyxSubmodel Submodel created through Basyx classes
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
void createSubmodels(AssetAdministrationShell aas) ;
void createAndUploadAAStoServer();




}
