package Helper;

import ProductModel.IProduct;
import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.registry.RegistryComponent;
import org.eclipse.basyx.components.registry.configuration.BaSyxRegistryConfiguration;
import org.eclipse.basyx.components.registry.configuration.RegistryBackend;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;

public class Infrastructure {
    public static final String REGISTRYPATH = "http://193.196.37.23:4000/registry";
   // public static final String AAS_SERVERPATH_MACHINES = "http://aas-machines.prj-sdm06.isw.uni-stuttgart.de/aasServer";
   public static final String AAS_SERVERPATH = "http://193.196.37.23:4001/aasServer";
    static final int REGISTRY_PORT = 4000;
    static final int AAS_SERVER_PORT = 4001;
    static final String REGISTRY_CONTEXT_PATH = "/registry";
    static final String SERVER_CONTEXT_PATH = "/aasServer";

    public static ConnectedAssetAdministrationShellManager createManager()
    {
        return new ConnectedAssetAdministrationShellManager(new AASRegistryProxy(REGISTRYPATH));
    }
    public static void setAASServerInfrastructure()
    {
        startInMemoryRegistry();
    }
    public static void uploadProductToServer (IProduct product, AssetAdministrationShell productAAS, ConnectedAssetAdministrationShellManager manager )
    {
        manager.createAAS(productAAS, AAS_SERVERPATH);

        for (  Submodel machineAASSubmodel : product.getListOfSubmodels())
        {
            manager.createSubmodel(productAAS.getIdentification(), machineAASSubmodel);
        }
    }
    public static void startInMemoryRegistry()
    {
        BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration(REGISTRY_PORT, REGISTRY_CONTEXT_PATH);
        BaSyxRegistryConfiguration registryConfig = new BaSyxRegistryConfiguration(RegistryBackend.INMEMORY);
        RegistryComponent registry = new RegistryComponent(contextConfig, registryConfig);

        registry.startComponent();
    }
}
