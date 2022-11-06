package sdm_aas;
import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.CustomId;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.aas.registration.restapi.AASRegistryModelProvider;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.MultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.restapi.SubmodelProvider;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubModelProviderResource {

	public static final IIdentifier ResourcesID = new CustomId("ResourcesID");
	
	// Initializes a logger for the output
	private static final Logger logger = LoggerFactory.getLogger(SubModelProviderResource.class);
/**
 * creates a shell for the resource asset with different submodels 
 * exemplary GET requests: 
 * http://localhost:4000/handson/resource/aas/
 * for more information of submodels:
 * http://localhost:4000/handson/resource/aas/submodels/ResourceComplex/submodel
 * http://localhost:4000/handson/resource/aas/submodels/Availability/submodel
 * http://localhost:4000/handson/resource/aas/submodels/R1/submodel
 * http://localhost:4000/handson/resource/aas/submodels/R2/submodel
 * 
 * exemplary POST requests:
 * http://localhost:4000/handson/resource/aas/submodels/R1/submodel/submodelElements/perform_process/invoke
 * -> {id: 1} / {id: 2}
 * http://localhost:4000/handson/resource/aas/submodels/R1/submodel/submodelElements/stop_process/invoke
 * -> {id: 1} / {id: 2}
 * http://localhost:4000/handson/resource/aas/submodels/R1/submodel/submodelElements/get_available_processes/invoke
 * http://localhost:4000/handson/resource/aas/submodels/R1/submodel/submodelElements/process2_time
 * -> lambda property to get changing process times
 * or for submodel R2 the same options
 * 
 * http://localhost:4000/handson/resource/aas/submodels/Availability/submodel/submodelElements/get_MTTF/invoke
 * -> {id: "r1"} or {id: "r2"}
 * http://localhost:4000/handson/resource/aas/submodels/Availability/submodel/submodelElements/get_MTTR/invoke
 * -> {id: "r1"} or {id: "r2"}
 * http://localhost:4000/handson/resource/aas/submodels/Availability/submodel/submodelElements/get_resource_data/invoke
 * -> {id: "r1"} or {id: "r2"}
 * 
 * http://localhost:4000/handson/resource/aas/submodels/ResourceComplex/submodel/submodelElements/petrinet
 * -> possibility to refer to documentations 
 * 
 * @param args
 * @throws Exception
 */
	public static void main(String[] args) throws Exception {
		// create asset and set an aas
		Asset asset = new Asset("Resources", new CustomId("Resources Asset"), AssetKind.INSTANCE);
		AssetAdministrationShell productionlineShell = new AssetAdministrationShell("resources", ResourcesID, asset);
		
		// add a description in english to the shell
		LangStrings descriptionS1en = new LangStrings("english", "different resources");
		productionlineShell.setDescription(descriptionS1en);
		
		// create submodels for the resource shell 
		Submodel resource1Submodel = CreateResourceSubmodel.createResource1Submodel();
		Submodel resource2Submodel = CreateResourceSubmodel.createResource2Submodel();
		Submodel availabilitySubmodel = CreateResourceSubmodel.createAvailabilitySubmodel();
		Submodel resourceComplexSubmodel = CreateResourceSubmodel.createResourceComplexSubmodel();
				
		AASModelProvider aasProvider = new AASModelProvider(productionlineShell);		
		
		SubmodelProvider resource1SMProvider = new SubmodelProvider(resource1Submodel);
		SubmodelProvider resource2SMProvider = new SubmodelProvider(resource2Submodel);
		SubmodelProvider avSMProvider = new SubmodelProvider(availabilitySubmodel);
		SubmodelProvider rcSMProvider = new SubmodelProvider(resourceComplexSubmodel);		
		
		MultiSubmodelProvider fullProvider = new MultiSubmodelProvider();
		fullProvider.setAssetAdministrationShell(aasProvider);
				
		fullProvider.addSubmodel(resource1SMProvider);
		fullProvider.addSubmodel(resource2SMProvider);	
		fullProvider.addSubmodel(avSMProvider);	
		fullProvider.addSubmodel(rcSMProvider);					

		HttpServlet aasServlet = new VABHTTPInterface<IModelProvider>(fullProvider);
		
		logger.info("Created a servlet for the model");

		IAASRegistry registry = new InMemoryRegistry();
		IModelProvider registryProvider = new AASRegistryModelProvider(registry);
		HttpServlet registryServlet = new VABHTTPInterface<IModelProvider>(registryProvider);
		logger.info("Created a registry servlet for the model");

		productionlineShell.addSubmodel(resource1Submodel);
		productionlineShell.addSubmodel(resource2Submodel);
		productionlineShell.addSubmodel(availabilitySubmodel);
		productionlineShell.addSubmodel(resourceComplexSubmodel);
			
		// Register the VAB model at the directory
		AASDescriptor aasDescriptor = new AASDescriptor(productionlineShell, "http://localhost:4000/handson/resource/aas");
		
		// Explicitly create and add submodel descriptors
		SubmodelDescriptor resource1SMDescriptor = new SubmodelDescriptor(resource1Submodel,
				"http://localhost:4000/handson/resource/aas/submodels/Resource1");
		SubmodelDescriptor resource2SMDescriptor = new SubmodelDescriptor(resource2Submodel,
				"http://localhost:4000/handson/resource/aas/submodels/Resource2");
		SubmodelDescriptor avSMDescriptor = new SubmodelDescriptor(resource2Submodel,
				"http://localhost:4000/handson/resource/aas/submodels/AV");
		SubmodelDescriptor rcSMDescriptor = new SubmodelDescriptor(resource2Submodel,
				"http://localhost:4000/handson/resource/aas/submodels/ResourceComplex");

		aasDescriptor.addSubmodelDescriptor(resource1SMDescriptor);
		aasDescriptor.addSubmodelDescriptor(resource2SMDescriptor);
		aasDescriptor.addSubmodelDescriptor(avSMDescriptor);
		aasDescriptor.addSubmodelDescriptor(rcSMDescriptor);	
		registry.register(aasDescriptor);

		List<Submodel> listofSubmodels = new ArrayList<>(); 
		listofSubmodels.add(resource1Submodel); 
		listofSubmodels.add(resource2Submodel);
		listofSubmodels.add(availabilitySubmodel);
		listofSubmodels.add(resourceComplexSubmodel);

		Map<AssetAdministrationShell, List<Submodel>> map = new HashMap<>();
		map.put(productionlineShell, listofSubmodels);

		PushAAStoServer.pushAAS(map, "http://193.196.37.23:4001/aasServer", "http://193.196.37.23:4000/registry/api/v1/registry");

		
		// Deploy the AAS on a HTTP server
		BaSyxContext context = new BaSyxContext("/handson", "", "localhost", 4000);
		context.addServletMapping("/resource/*", aasServlet);
		
		context.addServletMapping("/registry/*", registryServlet);
		BaSyxHTTPServer httpServer = new BaSyxHTTPServer(context);

		httpServer.start();
		logger.info("HTTP server started");
	}
}
