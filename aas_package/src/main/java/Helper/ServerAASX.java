package Helper;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;

import AAS_Framework.IAAS;

import java.util.List;

public class ServerAASX {
    // TODO: make this configurable
    public static final String REGISTRYPATH = "http://193.196.37.23:4000/registry/api/v1/registry";
    public static final String AAS_SERVERPATH = "http://193.196.37.23:4001/aasServer";

    static ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(
            new AASRegistryProxy(REGISTRYPATH));

    public static void uploadAAStoServer(AssetAdministrationShell aas, List<Submodel> listOfSubmodels) {
        manager.createAAS(aas, AAS_SERVERPATH);
        for (Submodel submodel : listOfSubmodels) {
            manager.createSubmodel(aas.getIdentification(), submodel);
        }
    }

    /**
     * Pushes the AAS to a server and registers it
     * 
     * @param aas               the AssetAdministrationShell to be pushed to the
     *                          server
     * @param aasServerURL      the URL of the aas server (e.g.
     *                          http://localhost:8080/aasComponent)
     * @param registryServerURL the URL of the registry server (e.g.
     *                          http://localhost:8080/registry)
     */
    public static void pushAAS(AssetAdministrationShell aas, List<Submodel> submodels, String aasServerURL,
            String registryServerURL) {
        // Create a proxy pointing to the registry server
        AASRegistryProxy registryProxy = new AASRegistryProxy(registryServerURL);

        // Create a ConnectedAASManager using the registryProxy as its registry
        ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(registryProxy);

        // The ConnectedAASManager automatically pushes the given AAS
        // to the server to which the address was given
        // It also registers the AAS in the registry it got in its ctor
        manager.createAAS(aas, aasServerURL);

        for (Submodel submodel : submodels) {
            manager.createSubmodel(aas.getIdentification(), submodel);
        }
    }

    // Needed later
    static final int REGISTRY_PORT = 4000;
    static final int AAS_SERVER_PORT = 4001;
    static final String REGISTRY_CONTEXT_PATH = "/registry";
    static final String SERVER_CONTEXT_PATH = "/aasServer";
}
