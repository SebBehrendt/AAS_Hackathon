package Helper;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;

import java.util.List;

public class ServerAASX {
    //TODO Registry!
    public static final String REGISTRYPATH = "http://193.196.37.23:4000/registry";
    public static final String AAS_SERVERPATH = "http://193.196.37.23:4001/aasServer";
    static final int REGISTRY_PORT = 4000;
    static final int AAS_SERVER_PORT = 4001;
    static final String REGISTRY_CONTEXT_PATH = "/registry";
    static final String SERVER_CONTEXT_PATH = "/aasServer";
   static ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(new AASRegistryProxy(REGISTRYPATH));

    public static void uploadAAStoServer(AssetAdministrationShell aas, List<Submodel> listOfSubmodels)
    {
            manager.createAAS(aas, AAS_SERVERPATH);
            for (Submodel submodel : listOfSubmodels)
            {
                manager.createSubmodel(aas.getIdentification(), submodel);
            }
    }
}
