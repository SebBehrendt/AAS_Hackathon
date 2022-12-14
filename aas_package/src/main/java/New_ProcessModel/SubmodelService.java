package New_ProcessModel;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProviderHelper;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.servlet.submodel.SubmodelServlet;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;



public class SubmodelService {
	public final String REMOTEHOST = "193.196.37.23";

	// public final String HOSTNAME = "193.196.37.23";
	public final String HOSTNAME = "localhost";

	public final String registryPath = "http://" + REMOTEHOST + ":4000/registry/api/v1/registry";
	public final String AAS_ID = "MillingProcedureAAS";
	public final String SUBMODEL_ID = "ScanForProcesses";
	public final String REGISTRY_PATH_ID = "Registry";
	
	public final String PROPERTY_SET_ID = "set_value";
	public final String PROPERTY_GET_ID = "get_value";


	protected ConnectedAssetAdministrationShellManager getManager() {
		// Create a InMemoryRegistry to be used by the manager
		AASRegistryProxy registry = new AASRegistryProxy(registryPath);

		// Create a ConnectedAASManager with the registry created above
		return new ConnectedAssetAdministrationShellManager(registry);
	}
	
	private Operation simpleSetOperation() {

		Operation operation = new Operation("setValue");

		// Create a Property to be used as input variable
		// The ValueType is determined by the type of the given value
		// here 0, which results in ValueType Integer
		Property newTargetTemp = new Property(PROPERTY_SET_ID, 0);
		newTargetTemp.setKind(ModelingKind.TEMPLATE);

		OperationVariable var = new OperationVariable(newTargetTemp);

		operation.setInputVariables(Arrays.asList(var));

		// The consumer called when the Operation is invoked
		Consumer<Object[]> consumer = (Consumer<Object[]>) t -> {
			ConnectedAssetAdministrationShellManager manager = getManager();
			ISubmodel sm = manager.retrieveSubmodel(new ModelUrn(AAS_ID), new ModelUrn(SUBMODEL_ID));
			ISubmodelElement elem = sm.getSubmodelElement(PROPERTY_GET_ID);
			elem.setValue((int) (t[0]));
		};

		operation.setInvokable(consumer);

		return operation;
	}

	private Operation registerRegistry() {

		Operation operation = new Operation("setRegistry");

		// Create a Property to be used as input variable
		// The ValueType is determined by the type of the given value
		// here 0, which results in ValueType Integer
		// Property newRegistryURL = new Property();
		// newRegistryURL.setIdShort("registryURL");
		Property newRegistryURL = new Property("registryURL", "");
		newRegistryURL.setKind(ModelingKind.TEMPLATE);

		OperationVariable var = new OperationVariable(newRegistryURL);

		operation.setInputVariables(Arrays.asList(var));

		// The consumer called when the Operation is invoked
		Consumer<Object[]> consumer = (Consumer<Object[]>) t -> {
			ConnectedAssetAdministrationShellManager manager = getManager();
			ISubmodel sm = manager.retrieveSubmodel(new ModelUrn(AAS_ID), new ModelUrn(SUBMODEL_ID));
			ISubmodelElement elem = sm.getSubmodelElement(REGISTRY_PATH_ID);
			elem.setValue((String) (t[0]));
		};

		operation.setInvokable(consumer);

		return operation;
	}

	private Operation simpleGetOperation() {
		Operation operation = new Operation("getValue");

		// Create a Property to be used as output variable
		// The ValueType is determined by the type of the given value
		// here 0, which results in ValueType Integer
		Property currentTemp = new Property(PROPERTY_GET_ID, 0);
		currentTemp.setKind(ModelingKind.TEMPLATE);

		OperationVariable var = new OperationVariable(currentTemp);

		operation.setOutputVariables(Arrays.asList(var));

		// The supplier called when the Operation is invoked
		Supplier<Object> supplier = (Supplier<Object>) () -> {
			ConnectedAssetAdministrationShellManager manager = getManager();
			ISubmodel sm = manager.retrieveSubmodel(new ModelUrn(AAS_ID), new ModelUrn(SUBMODEL_ID));
			ISubmodelElement elem = sm.getSubmodelElement(PROPERTY_GET_ID);
			return elem.getValue();
		};

		operation.setInvokable(supplier);

		return operation;
	}

	private Operation getMatchableProcesses() {
		Operation operation = new Operation("getMatchableProcesses");

		// Create a Property to be used as output variable
		// The ValueType is determined by the type of the given value
		// here 0, which results in ValueType Integer
		Property matchableProcesses = new Property();
		matchableProcesses.setIdShort("MatchableProcesses");
		matchableProcesses.setKind(ModelingKind.TEMPLATE);

		OperationVariable var = new OperationVariable(matchableProcesses);

		operation.setOutputVariables(Arrays.asList(var));

		// The supplier called when the Operation is invoked
		Supplier<Object> supplier = (Supplier<Object>) () -> {
			ConnectedAssetAdministrationShellManager manager = getManager();
			ISubmodel sm = manager.retrieveSubmodel(new ModelUrn(AAS_ID), new ModelUrn(SUBMODEL_ID));
			ISubmodelElement elem = sm.getSubmodelElement("MatchableProcesses");
			elem.setValue("Possible processes: 1, 2, 3");
			return elem.getValue();
		};

		operation.setInvokable(supplier);

		return operation;
	}


	/**
	 * create product submodel
	 * 
	 * @return
	 */
	public Submodel createTestSubmodel() {
        // submodel with id short
		Submodel Simple_SM = new Submodel();
		Simple_SM.setIdShort(SUBMODEL_ID);
		Simple_SM.setIdentification(new ModelUrn(SUBMODEL_ID));


        // Property dummyProperty = new Property();
		// dummyProperty.setIdShort("dummy");
		// dummyProperty.set(VABLambdaProviderHelper.createSimple(() -> {
		// 	return Math.random() * (20 - 10) + 10;
		// }, null), ValueType.Boolean);
		// Simple_SM.addSubmodelElement(dummyProperty);




		Property registryProperty = new Property(REGISTRY_PATH_ID, "");
		// registryProperty.setIdShort(REGISTRY_PATH_ID);
		registryProperty.setKind(ModelingKind.TEMPLATE);



		Simple_SM.addSubmodelElement(registryProperty);
		Simple_SM.addSubmodelElement(simpleSetOperation());
		Simple_SM.addSubmodelElement(simpleGetOperation());
		Simple_SM.addSubmodelElement(registerRegistry());
		Simple_SM.addSubmodelElement(getMatchableProcesses());
		


		return Simple_SM;
	}

	public SubmodelDescriptor getSubmodelDescriptor() {
		Identifier identifier = new Identifier(IdentifierType.CUSTOM, SUBMODEL_ID);
		return new SubmodelDescriptor(SUBMODEL_ID, identifier, "http://" + HOSTNAME + ":4002/submodelserver/" + SUBMODEL_ID + "/submodel");
	}

	private void startupEdgeServer() {

		// Create a BaSyxConetxt for port 8082 with an empty endpoint
		// BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration(4002, "");
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration("", "", HOSTNAME, 4002);
		// BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration(4002, "");
		BaSyxContext context = contextConfig.createBaSyxContext();
		System.out.println(1);
		System.out.println(contextConfig.getHostname());


		// Get the edgeSubmodel from the ComponentFactory
		Submodel testSubmodel = createTestSubmodel();

		// Create a new SubmodelServlet containing the edgeSubmodel
		SubmodelServlet smServlet = new SubmodelServlet(testSubmodel);
		System.out.println(2);


		// Add the SubmodelServlet mapping to the context at the path
		context.addServletMapping("/submodelserver/" + SUBMODEL_ID + "/*", smServlet);
		System.out.println(3);


		// Create and start a HTTP server with the context created above
		BaSyxHTTPServer Server = new BaSyxHTTPServer(context);
		Server.start();

		System.out.println(4);

	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		SubmodelService simple = new SubmodelService();
		simple.startupEdgeServer();

		AASRegistryProxy registryProxy = new AASRegistryProxy(simple.registryPath);
        ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(registryProxy);
		
		Identifier id = new Identifier(IdentifierType.IRI, "MillingProcedureAAS");
		ConnectedAssetAdministrationShell aas = manager.retrieveAAS(id);
		System.out.println(aas.getIdShort());

		registryProxy.register(id, simple.getSubmodelDescriptor());



	}

}
