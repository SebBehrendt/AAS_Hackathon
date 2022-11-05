package sdm_aas;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class PushAAStoServer {

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
    public static void pushAAS(Map<AssetAdministrationShell, List<Submodel>> aas, String aasServerURL,
            String registryServerURL) {
        // Create a proxy pointing to the registry server
        AASRegistryProxy registryProxy = new AASRegistryProxy(registryServerURL);

        // Create a ConnectedAASManager using the registryProxy as its registry
        ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(registryProxy);

        // The ConnectedAASManager automatically pushes the given AAS
        // to the server to which the address was given
        // It also registers the AAS in the registry it got in its ctor

        for (Map.Entry<AssetAdministrationShell, List<Submodel>> entry : aas.entrySet()) {
            manager.createAAS(entry.getKey(), aasServerURL);

            for (Submodel sm :  entry.getValue()) {
                manager.createSubmodel(entry.getKey().getIdentification(), sm);
            }
        }

    }
}
