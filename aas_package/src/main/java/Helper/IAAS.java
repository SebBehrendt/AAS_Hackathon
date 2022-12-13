package Helper;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;

import java.util.ArrayList;
import java.util.List;

public interface IAAS {
    List<ISubmodel> listOfSubmodelClasses = new ArrayList<>();
     void addSubmodelToList(Submodel basyxSubmodel);
     String getIdentification();
     void createSubmodels ();
     AssetAdministrationShell createAAS();
     AssetAdministrationShell createAAS(AssetKind kind);

    // void addSubmodelClass(ISubmodel submodelClass);


}
