package GettingStarted;

import java.util.List;
import javax.servlet.http.HttpServlet;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.aas.registration.restapi.AASRegistryModelProvider;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.MultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.restapi.SubmodelProvider;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import AAS_Framework.IAAS;

import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;



import ProductModel.BOM;
import ProductModel.DesignInformation;
import ProductModel.DigitalNameplate;
import ProductModel.Product;
import Helper.ServerAASX;
import Helper.AASHelper;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class ProductAAS {
	private static final Logger logger = LoggerFactory.getLogger(ProductAAS.class);
	static List<AssetAdministrationShell> listOfSubComponents = new ArrayList<>();
	static List<Product> listOfProducts = new ArrayList<>();

	static Product createSkateboard() {
		Product skateBoard = new Product("Skateboard_Getting_Started");

		createSubComponents();

		skateBoard.addSubComponents(new BOM(listOfSubComponents));

		return skateBoard;

	}

	static void createSubComponents() {
		listOfProducts.add(Board.createBoardProduct());
		listOfProducts.add(Axis.createAxis());
	}

	static class Board {

		static Product createBoardProduct() {
			// Digital Nameplate
			Product skateboardBoard = new Product("Board_20221212_1047", createSkateboardDigitalNameplate());
			skateboardBoard.addDesignInformation(createDesignInformation());

			AssetAdministrationShell shell = skateboardBoard.createAAS();
			// skateboardBoard.createAndUploadAAStoServer();
			listOfSubComponents.add(shell);

			return skateboardBoard;

		}

		@NotNull
		@Contract(value = " -> new", pure = true)
		private static DigitalNameplate createSkateboardDigitalNameplate() {
			return new DigitalNameplate("Board_20221212_1047");
		}

		private static DesignInformation createDesignInformation() {
			DesignInformation designInfo = new DesignInformation("Board_CAD_File",
					"S://01_CAD/2022/Boards/Board_20221212_1047.stp");
			return designInfo;
		}

	}

	static class Axis {
		static Product createAxis() {
			//
			Product axisFront = new Product("Axis_front_20221212_1118");
			axisFront.addDesignInformation(
					new DesignInformation("CAD_axis_front", "link_to_objectserver/cad/axis/axis_front.stp"));

			AssetAdministrationShell shell = axisFront.createAAS();
			// axisFront.createAndUploadAAStoServer();
			listOfSubComponents.add(shell);

			return axisFront;
		}

	}

	public static MultiSubmodelProvider getFullProvier(AssetAdministrationShell productShell,
			List<Submodel> submodels) {
		// create aas model provider for all submodels
		MultiSubmodelProvider fullProvider = new MultiSubmodelProvider();

		AASModelProvider aasProvider = new AASModelProvider(productShell);
		fullProvider.setAssetAdministrationShell(aasProvider);

		for (Submodel submodel : submodels) {
			SubmodelProvider submodelProvider = new SubmodelProvider(submodel);
			fullProvider.addSubmodel(submodelProvider);
		}
		return fullProvider;

	}

	public static void startServerWithInMemoryRegistry(MultiSubmodelProvider fullProvider,
			AssetAdministrationShell productShell) {
		// create aas server
		HttpServlet aasServlet = new VABHTTPInterface<IModelProvider>(fullProvider);
		logger.info("Created a servlet for the model");

		// create registry with provider and servlet
		IAASRegistry registry = new InMemoryRegistry();
		IModelProvider registryProvider = new AASRegistryModelProvider(registry);
		HttpServlet registryServlet = new VABHTTPInterface<IModelProvider>(registryProvider);
		logger.info("Created a registry servlet for the model");

		// Register the VAB model at the directory
		AASDescriptor aasDescriptor = new AASDescriptor(productShell,
				"http://localhost:4001/aasserver/shells/" + productShell.getIdShort() + "/aas");
		registry.register(aasDescriptor);

		// Deploy the AAS on a HTTP server
		BaSyxContext context = new BaSyxContext("/aasserver", "", "localhost", 4001);
		context.addServletMapping("/shells/" + productShell.getIdShort() + "/*", aasServlet);
		context.addServletMapping("/registry/*", registryServlet);
		BaSyxHTTPServer httpServer = new BaSyxHTTPServer(context);

		httpServer.start();
		logger.info("HTTP server started");
	}

	public static void startServerAndConnectToExternalRegistry(MultiSubmodelProvider fullProvier,
			AssetAdministrationShell productshell) {
		String host = "localhost"; // Enter here IP Adress for Remote host
		// String host = "193.196.37.23";

		String registryPath = "http://" + host + ":8082/registry/api/v1/registry";
		AASRegistryProxy registryProxy = new AASRegistryProxy(registryPath);
		AASDescriptor aasDescriptor = new AASDescriptor(productshell,
				"http://localhost:4001/aasserver/shells/" + productshell.getIdShort() + "/aas");

		registryProxy.register(aasDescriptor);
	}

	public static void UseExternalServerAndRegistry(Product product) {
		String host = "localhost"; // Enter here IP Adress for Remote host
		// String host = "193.196.37.23";

		ServerAASX.pushAAS(product.createAAS(), product.getSubmodels(), "http://" + host + ":8081/aasServer",
				"http://" + host + ":8082/registry/api/v1/registry");
	}

	public static Submodel createProductReferenceSubmodel(String URIreferenceProcess) {
		Submodel processReferenceSubmodel = new Submodel(
			AASHelper.nameToIdShort("ProcessReferenceSubmodel"),
			new Identifier(IdentifierType.CUSTOM, AASHelper.nameToIdentifier("ProcessReferenceSubmodel"))
		);

        Identifier processIdentifier = new Identifier(IdentifierType.IRI, URIreferenceProcess);
        Reference processReference = new Reference(processIdentifier, KeyElements.ASSETADMINISTRATIONSHELL, false);
        ReferenceElement processReferenceElement = new ReferenceElement("ProcessReference", processReference);
        processReferenceSubmodel.addSubmodelElement(processReferenceElement);

        return processReferenceSubmodel;
	}


	public static void main(String[] args) throws Exception {

		Product product = createSkateboard();

		// // create product aas and submodels
		AssetAdministrationShell shell = product.createAAS();
		List<Submodel> submodels = product.getSubmodels();

		String ProcessReferenceURI = "http://localhost:8081/aasServer/shells/AAS_millingProcess_Identifier/aas";
		// Submodel ProcessReferenceSubmodel = createProductReferenceSubmodel(ProcessReferenceURI);
		// IAAS.listOfSubmodels.add(ProcessReferenceSubmodel);

		MultiSubmodelProvider fullProvider = getFullProvier(shell, submodels);

		startServerWithInMemoryRegistry(fullProvider, shell);

		// startServerAndConnectToExternalRegistry(fullProvider, shell);

		// UseExternalServerAndRegistry(product);
	
		
	}

}
