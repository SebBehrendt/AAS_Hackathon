package sdm_aas;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import javax.servlet.http.HttpServlet;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.CustomId;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.aas.registration.restapi.AASRegistryModelProvider;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.MultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;
import org.eclipse.basyx.submodel.restapi.SubmodelProvider;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Entity {
	public EntityType type;
	public String location;
	public double area;
	// list of resources which the entity contains
	public List<String> deployed_resources;
	// list of products which can be produced in the entity
	public List<String> produced_products;
	public int quantity_of_products;
	public double production_costs;
	public double accident_rate;

	static Identifier entityIdentifier = (new ModelUrn("Entity"));

	/**
	 * creates a submodel for the entity
	 * 
	 * @return entity submodel
	 */
	public static Submodel createEntitySubmodel() {
		// sets id and identification
		Entity entity = new Entity();
		Submodel entitySubmodel = new Submodel();
		entitySubmodel.setIdShort("Entity");
		entitySubmodel.setIdentification(entityIdentifier);

		// sets a static property for the location of the entity
		entity.location = "Factory in Stuttgart";
		Property location = new Property("location", (entity.location).toString());
		entitySubmodel.addSubmodelElement(location);

		// sets a static property for the area
		entity.area = 1200;
		Property area = new Property("area", entity.area);
		entitySubmodel.addSubmodelElement(area);

		// creates a static property for the deployed resources
		Submodel resource1submodel = CreateResourceSubmodel.createResource1Submodel();
		String idr1 = (resource1submodel.getIdShort());
		Submodel resource2submodel = CreateResourceSubmodel.createResource2Submodel();
		String idr2 = (resource2submodel.getIdShort());
		entity.deployed_resources = Arrays.asList(idr1, idr2);
		Property resources = new Property("deployed_resources", (entity.deployed_resources).toString());
		entitySubmodel.addSubmodelElement(resources);

		// creates a static property for the produced products
		Submodel productSubmodel = sdm_aas.Product.createProductSubmodel();
		String idp1 = productSubmodel.getIdShort();
		entity.produced_products = Arrays.asList(idp1);
		Property productList = new Property("produced_products", (entity.produced_products).toString());
		entitySubmodel.addSubmodelElement(productList);

		// creates a static property for the costs of the production which refers to the
		// costs of the produced products
		sdm_aas.Product product1 = new sdm_aas.Product();
		entity.production_costs = product1.value;
		Property production_costs = new Property("production_costs", entity.production_costs);
		entitySubmodel.addSubmodelElement(production_costs);

		// creates a static property for the accident rate
		entity.accident_rate = 0.3;
		Property accident_rate = new Property("accident_rate", entity.accident_rate);
		entitySubmodel.addSubmodelElement(accident_rate);

		// creates a function encapsulated by a operation to invoke the function to
		// create a json file
		Function<Object[], Object> createData_json = (args) -> {
			DataJson.createDataJson();
			return "Data.json file successfully created. ";
		};
		Operation create_datajosnOperation = new Operation(createData_json);
		create_datajosnOperation.setIdShort("create_data.json");
		Property data_input = new Property("data_input", 0);
		data_input.setModelingKind(ModelingKind.TEMPLATE);
		create_datajosnOperation.setInputVariables(Collections.singletonList(new OperationVariable(data_input)));
		entitySubmodel.addSubmodelElement(create_datajosnOperation);

		return entitySubmodel;
	}

	/**
	 * creates a DataLoggingSimulationSubmodel
	 * 
	 * @return DataLoggingSimulationSubmodel
	 */
	public static Submodel createDataLoggingSimulationSubmodel() {
		// create a a submodel with id and identification
		Submodel DataLoggingSimulationSubmodel = new Submodel();
		DataLoggingSimulationSubmodel.setIdShort("DataLoggingSimulation");
		DataLoggingSimulationSubmodel.setIdentification(new ModelUrn("DataLoggingSimulation"));

		// add static property kpi to submodel
		Property kpi = new Property("KPI", 12);
		DataLoggingSimulationSubmodel.addSubmodelElement(kpi);

		// add static property SimulationEventLog to submodel
		Property log = new Property("SimulationEventLog", -1);
		DataLoggingSimulationSubmodel.addSubmodelElement(log);

		return DataLoggingSimulationSubmodel;
	}

	/**
	 * create DataLoggingRealProductionSubmodel
	 * 
	 * @return DataLoggingRealProductionSubmodel
	 */
	public static Submodel createDataLoggingRealProductionSubmodel() {
		// create submodel with id and identification
		Submodel DataLoggingRealPRoductionSubmodel = new Submodel();
		DataLoggingRealPRoductionSubmodel.setIdShort("DataLoggingRealProduction");
		DataLoggingRealPRoductionSubmodel.setIdentification(new ModelUrn("DataLoggingRealProduction"));

		// add static property kpi to submodel
		Property kpi = new Property("KPI", 12);
		DataLoggingRealPRoductionSubmodel.addSubmodelElement(kpi);

		// add static property SimulationEventLog to submodel
		Property log = new Property("RealEventLog", -1);
		DataLoggingRealPRoductionSubmodel.addSubmodelElement(log);

		return DataLoggingRealPRoductionSubmodel;
	}

	private static final Logger logger = LoggerFactory.getLogger(SubModelProviderResource.class);

	/**
	 * create shell on a server for the asset entity with the submodels defined
	 * above
	 * 
	 * retrieve information via rest api exemplary POST requests:
	 * http://localhost:4004/handson/entity/aas -> overview of whole entity asset
	 * more information of submodels:
	 * http://localhost:4004/handson/entity/aas/submodels/DataLoggingSimulation/submodel
	 * http://localhost:4004/handson/entity/aas/submodels/DataLoggingRealProduction/submodel
	 * http://localhost:4004/handson/entity/aas/submodels/Entity/submodel
	 * 
	 * GET request to create a json data file:
	 * http://localhost:4004/handson/entity/aas/submodels/Entity/submodel/submodelElements/create_data.json/invoke
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// set AAS for entity asset with factory category
		Asset asset = new Asset("Entity", new CustomId("EntityAsset"), AssetKind.INSTANCE);
		asset.setCategory((EntityType.factory).toString());
		AssetAdministrationShell entityShell = new AssetAdministrationShell("entity", new CustomId("EntityID"), asset);

		// set english description for the entity
		LangStrings descriptionProduct = new LangStrings("english", "The Entity");
		entityShell.setDescription(descriptionProduct);

		// create entity submodel with id and identification
		Submodel entitySubmodel = createEntitySubmodel();
		entitySubmodel.setIdShort("Entity");
		entitySubmodel.setIdentification(new ModelUrn("Entity"));

		// create DataLoggingSimulationSubmodel with id and identification
		Submodel DataLoggingSimulationSubmodel = createDataLoggingSimulationSubmodel();
		DataLoggingSimulationSubmodel.setIdShort("DataLoggingSimulation");
		DataLoggingSimulationSubmodel.setIdentification(new ModelUrn("DataLoggingSimulation"));

		// create DataLoggingRealProductionSubmodel with id and identification
		Submodel DataLoggingRealProductionSubmodel = createDataLoggingRealProductionSubmodel();
		DataLoggingRealProductionSubmodel.setIdShort("DataLoggingRealProduction");
		DataLoggingRealProductionSubmodel.setIdentification(new ModelUrn("DataLoggingRealProduction"));

		// create provider for the entity aas
		AASModelProvider aasProvider = new AASModelProvider(entityShell);

		// create a provider for each submodel
		SubmodelProvider entitySMProvider = new SubmodelProvider(entitySubmodel);
		SubmodelProvider dlsSMProvider = new SubmodelProvider(DataLoggingSimulationSubmodel);
		SubmodelProvider dlrSMProvider = new SubmodelProvider(DataLoggingRealProductionSubmodel);

		// create a multisubmodelprovider to add all submodels to the AAS
		MultiSubmodelProvider fullProvider = new MultiSubmodelProvider();
		fullProvider.setAssetAdministrationShell(aasProvider);

		fullProvider.addSubmodel(entitySMProvider);
		fullProvider.addSubmodel(dlsSMProvider);
		fullProvider.addSubmodel(dlrSMProvider);

		HttpServlet aasServlet = new VABHTTPInterface<IModelProvider>(fullProvider);
		logger.info("Created a servlet for the model");

		IAASRegistry registry = new InMemoryRegistry();
		IModelProvider registryProvider = new AASRegistryModelProvider(registry);
		HttpServlet registryServlet = new VABHTTPInterface<IModelProvider>(registryProvider);
		logger.info("Created a registry servlet for the model");

		entityShell.addSubmodel(entitySubmodel);
		entityShell.addSubmodel(DataLoggingSimulationSubmodel);
		entityShell.addSubmodel(DataLoggingRealProductionSubmodel);

		// Register the VAB model at the directory
		AASDescriptor aasDescriptor = new AASDescriptor(entityShell, "http://localhost:4004/handson/entity/aas");

		// Explicitly create and add submodel descriptors
		SubmodelDescriptor entitySMDescriptor = new SubmodelDescriptor(entitySubmodel,
				"http://localhost:4004/handson/entity/aas/submodels/Entity");
		SubmodelDescriptor dlsSMDescriptor = new SubmodelDescriptor(DataLoggingSimulationSubmodel,
				"http://localhost:4004/handson/entity/aas/submodels/DataLoggingSimulation");
		SubmodelDescriptor dlrSMDescriptor = new SubmodelDescriptor(DataLoggingSimulationSubmodel,
				"http://localhost:4004/handson/entity/aas/submodels/DataLoggingRealProduction");

		aasDescriptor.addSubmodelDescriptor(entitySMDescriptor);
		aasDescriptor.addSubmodelDescriptor(dlsSMDescriptor);
		aasDescriptor.addSubmodelDescriptor(dlrSMDescriptor);

		registry.register(aasDescriptor);

		// Deploy the AAS on a HTTP server
		BaSyxContext context = new BaSyxContext("/handson", "", "localhost", 4004);
		context.addServletMapping("/entity/*", aasServlet);
		context.addServletMapping("/registry/*", registryServlet);
		BaSyxHTTPServer httpServer = new BaSyxHTTPServer(context);

		httpServer.start();
		logger.info("HTTP server started");

	}

}
