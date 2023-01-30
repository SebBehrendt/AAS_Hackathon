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

import New_ProcessModel.AbstractProcess;
import New_ProcessModel.Process;
import New_ProcessModel.ProcessAttribute;
import New_ProcessModel.ProcessModel;
import New_ProcessModel.ElementaryProcess;
import New_ProcessModel.SequentialProcessModel;
import Helper.ServerAASX;

import java.util.ArrayList;

public class ProcessAAS {
	private static final Logger logger = LoggerFactory.getLogger(ProcessAAS.class);

	static List<AssetAdministrationShell> listOfSubComponents = new ArrayList<>();
	static List<Process> listOfProcesses = new ArrayList<>();

	private static List<ProcessAttribute> createProcessAttributesForMillingProcess() {
		List<String> millingSemantics = new ArrayList<String>();
		millingSemantics.add("Milling");

		List<String> millingTechnologySemantics = new ArrayList<String>(millingSemantics);
		millingTechnologySemantics.add("Technology");

		List<String> rotationSemantics = new ArrayList<String>(millingSemantics);
		rotationSemantics.add("Rotation speed");

		List<String> dimensionSemantics = new ArrayList<String>(millingSemantics);
		dimensionSemantics.add("Dimensions");

		ProcessAttribute requiredMillingTechnology = new ProcessAttribute(millingTechnologySemantics,
				"Milling technology", "3 Axes");
		ProcessAttribute requiredMillRotationSpeed = new ProcessAttribute(rotationSemantics,
				"Milling roation speed attribute in rpm", 30.0, "Minimum");
		ProcessAttribute requiredDimensions = new ProcessAttribute(dimensionSemantics,
				"Milling dimensions for x y z in mm", List.of(350.0, 50.0, 40.0), "Minimum");

		List<ProcessAttribute> millingProcessAttributes = List.of(requiredMillingTechnology,
				requiredMillRotationSpeed, requiredDimensions);

		return millingProcessAttributes;

	}

	static Process createMillingProcess() {
		List<ProcessAttribute> millingProcessAttributes = createProcessAttributesForMillingProcess();

		ElementaryProcess milling1 = new ElementaryProcess("millingProcess1", "milling 1",
				millingProcessAttributes);
		ElementaryProcess milling2 = new ElementaryProcess("millingProcess2", "milling 2",
				millingProcessAttributes);

		// Generate new Graph Process Model with elementary processes
		SequentialProcessModel millingProcessModel1 = new SequentialProcessModel("millingProcessModel",
				"Sequential process Model 1", List.of(milling1, milling2));

		SequentialProcessModel millingProcessModel2 = new SequentialProcessModel("millingProcessModel",
		"Sequential process Model 2", List.of(milling2, milling1));

		// Generate new Process Model List
		List<ProcessModel> millingProcessModels = List.of(millingProcessModel1, millingProcessModel2);

		Process millingProcess = new Process("millingProcess", "Milling Process", millingProcessAttributes,
				millingProcessModels);

		return millingProcess;

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

	public static void UseExternalServerAndRegistry(AbstractProcess product) {
		String host = "localhost"; // Enter here IP Adress for Remote host
		// String host = "193.196.37.23";

		ServerAASX.pushAAS(product.createAAS(), product.getSubmodels(), "http://" + host + ":8081/aasServer",
				"http://" + host + ":8082/registry/api/v1/registry");
	}

	public static void main(String[] args) throws Exception {

		Process process = createMillingProcess();

		// // create product aas and submodels
		AssetAdministrationShell shell = process.createAAS();
		List<Submodel> submodels = process.getSubmodels();
		// product.createAndUploadAAStoServer();

		MultiSubmodelProvider fullProvider = getFullProvier(shell, submodels);

		startServerWithInMemoryRegistry(fullProvider, shell);

		startServerAndConnectToExternalRegistry(fullProvider, shell);

		UseExternalServerAndRegistry(process);

	}

}
