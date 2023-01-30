package Ge;

import java.util.List;

import javax.servlet.http.HttpServlet;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.CustomId;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.aas.registration.restapi.AASRegistryModelProvider;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.MultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.restapi.SubmodelProvider;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MachineAAS {

	private static final Logger logger = LoggerFactory.getLogger(ProductAAS.class);
	public List<String> offeredProcesses;
    public String URIreferenceCurrentProduct;

	public MachineAAS(List<String> offeredProcesses, String currentProductReference) {
        this.offeredProcesses = offeredProcesses;
        this.URIreferenceCurrentProduct = currentProductReference;
    }

	public Submodel createProcessesSubmodel() {
		Submodel processesSubModel = new Submodel();
		processesSubModel.setIdShort("ProcessesSubmodel");
		processesSubModel.setIdentification(new ModelUrn("ProcessesSubmodel"));

		SubmodelElementCollection processSubmodelCollection = new SubmodelElementCollection("Processes");
		int counter = 0;
		for (String process : this.offeredProcesses) {
			Property processProprty = new Property("Processs" + counter, process);
			processSubmodelCollection.addSubmodelElement(processProprty);
			counter++;
		}

		processesSubModel.addSubmodelElement(processSubmodelCollection);

		return processesSubModel;
	}

    public Submodel createProductReferenceSubmodel() {
		Submodel productReferenceSubmodel = new Submodel();
		productReferenceSubmodel.setIdShort("CurrentProductReferenceSubmodel");
		productReferenceSubmodel.setIdentification(new ModelUrn("CurrentProductReferenceSubmodel"));

        Identifier productIdentifier = new Identifier(IdentifierType.IRI, this.URIreferenceCurrentProduct);
        Reference productReference = new Reference(productIdentifier, KeyElements.ASSETADMINISTRATIONSHELL, false);
        ReferenceElement productReferenceElement = new ReferenceElement("CurrentProductReference", productReference);
        productReferenceSubmodel.addSubmodelElement(productReferenceElement);

        return productReferenceSubmodel;
	}

	public static Asset createAsset(String machineIdentifier) {
		Asset asset = new Asset(machineIdentifier, new CustomId(machineIdentifier), AssetKind.INSTANCE);
		return asset;
	}

	public static AssetAdministrationShell createAAS(Asset productAsset, String AASIdentifier, String description) {
		// create product asset and set aas
		AssetAdministrationShell productShell = new AssetAdministrationShell(AASIdentifier, new CustomId(AASIdentifier),
				productAsset);
		// create description for product shell
		LangStrings descriptionProduct = new LangStrings("english", description);
		productShell.setDescription(descriptionProduct);
		return productShell;
	}

	public static List<Submodel> generateAndRegisterSubmodels(AssetAdministrationShell machineShell,
			MachineAAS generator) {
		// create Geometry, Costing and Process Submodel
        Submodel processesSubmodel = generator.createProcessesSubmodel();
        Submodel productReferenceSubmodel = generator.createProductReferenceSubmodel();

		// add submodels to AAS
		machineShell.addSubmodel(processesSubmodel);
		machineShell.addSubmodel(productReferenceSubmodel);

		return List.of(processesSubmodel, productReferenceSubmodel);
	}

	public static MultiSubmodelProvider getFullProvier(AssetAdministrationShell machineShell,
			List<Submodel> submodels) {
		// create aas model provider for all submodels
		MultiSubmodelProvider fullProvider = new MultiSubmodelProvider();

		AASModelProvider aasProvider = new AASModelProvider(machineShell);
		fullProvider.setAssetAdministrationShell(aasProvider);

		for (Submodel submodel : submodels) {
			SubmodelProvider submodelProvider = new SubmodelProvider(submodel);
			fullProvider.addSubmodel(submodelProvider);
		}
		return fullProvider;

	}

	public static void startServerWithInMemoryRegistry(MultiSubmodelProvider fullProvider,
		AssetAdministrationShell machineShell) {
		// create aas server
		HttpServlet aasServlet = new VABHTTPInterface<IModelProvider>(fullProvider);
		logger.info("Created a servlet for the model");

		// create registry with provider and servlet
		IAASRegistry registry = new InMemoryRegistry();
		IModelProvider registryProvider = new AASRegistryModelProvider(registry);
		HttpServlet registryServlet = new VABHTTPInterface<IModelProvider>(registryProvider);
		logger.info("Created a registry servlet for the model");

		// Register the VAB model at the directory
		AASDescriptor aasDescriptor = new AASDescriptor(machineShell, "http://localhost:4009/aasserver/shells/TestMachineAAS/aas");
		registry.register(aasDescriptor);

		// Deploy the AAS on a HTTP server
		BaSyxContext context = new BaSyxContext("/aasserver", "", "localhost", 4009);
		context.addServletMapping("/shells/TestMachineAAS/*", aasServlet);
		context.addServletMapping("/registry/*", registryServlet);
		BaSyxHTTPServer httpServer = new BaSyxHTTPServer(context);

		httpServer.start();
		logger.info("HTTP server started");
	}

	public static void main(String[] args) throws Exception {

		// create product asset and set aas
		Asset machineAsset = createAsset("TestMachine");
		AssetAdministrationShell machineShell = createAAS(machineAsset, "TestMachineAAS",
				"This is a test machine AAS.");

		// create SimpleAAS object for easy storage of the product data for all
		// submodels
        String currentProductReference = "http://localhost:8081/aasServer/shells/TestProductAAS";
        MachineAAS generator = new MachineAAS(List.of("Press", "Milling"), currentProductReference);

		List<Submodel> submodels = generateAndRegisterSubmodels(machineShell, generator);

		MultiSubmodelProvider fullProvider = getFullProvier(machineShell, submodels);

		startServerWithInMemoryRegistry(fullProvider, machineShell);

	}

}
