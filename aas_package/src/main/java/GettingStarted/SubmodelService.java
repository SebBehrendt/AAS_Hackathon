package GettingStarted;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
// import java.util.function.Supplier;

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
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.DataElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;

public class SubmodelService {
	// public final String REMOTEHOST = "193.196.37.23";
	public final String REMOTEHOST = "localhost";

	// public final String HOSTNAME = "193.196.37.23";
	public final String HOSTNAME = "localhost";

	public final String registryPath = "http://" + REMOTEHOST + ":8082/registry/api/v1/registry";
	public final String AAS_ID = "TestProductAAS";
	public final String SUBMODEL_ID = "ScanForProcesses";
	public final String Exampleproperty = "Exampleproperty";
	
	// public final String PROPERTY_SET_ID = "set_value";
	// public final String PROPERTY_GET_ID = "get_value";


	protected ConnectedAssetAdministrationShellManager getManager() {
		// Create a InMemoryRegistry to be used by the manager
		AASRegistryProxy registry = new AASRegistryProxy(registryPath);

		// Create a ConnectedAASManager with the registry created above
		return new ConnectedAssetAdministrationShellManager(registry);
	}

	private Operation setExamplePropertyValue() {

		Operation operation = new Operation("setExamplePropertyValue");
		Property newRegistryURL = new Property("newPropertyValue", "");
		newRegistryURL.setKind(ModelingKind.TEMPLATE);

		OperationVariable var = new OperationVariable(newRegistryURL);

		operation.setInputVariables(Arrays.asList(var));

		// The consumer called when the Operation is invoked
		Consumer<Object[]> consumer = (Consumer<Object[]>) t -> {
			ConnectedAssetAdministrationShellManager manager = getManager();
			ISubmodel sm = manager.retrieveSubmodel(new ModelUrn(AAS_ID), new ModelUrn(SUBMODEL_ID));
			ISubmodelElement elem = sm.getSubmodelElement(Exampleproperty);
			elem.setValue((String) (t[0]));
		};

		operation.setInvokable(consumer);

		return operation;
	}

	// private Operation setRegistryPropertyValue() {

	// 	Operation operation = new Operation("setRegistryPropertyValue");

	// 	Property newRegistryURL = new Property("newRegistryURL", "");
	// 	newRegistryURL.setKind(ModelingKind.TEMPLATE);

	// 	Property newRegistryPort = new Property("Port", "");
	// 	newRegistryPort.setKind(ModelingKind.TEMPLATE);

	// 	OperationVariable var = new OperationVariable(newRegistryURL);
	// 	OperationVariable var2 = new OperationVariable(newRegistryPort);

	// 	operation.setInputVariables(Arrays.asList(var, var2));

	// 	// The consumer called when the Operation is invoked
	// 	Consumer<Map<String, SubmodelElement>> consumer = (Consumer<Map<String, SubmodelElement>>) t -> {
	// 		System.out.println(t.get("Port"));
	// 		System.out.println(t.get("setRegistryPropertyValue"));
	// 		ConnectedAssetAdministrationShellManager manager = getManager();
	// 		ISubmodel sm = manager.retrieveSubmodel(new ModelUrn(AAS_ID), new ModelUrn(SUBMODEL_ID));
	// 		ISubmodelElement elem = sm.getSubmodelElement("registryURL");
	// 		elem.setValue(t.get("Port").getValue().toString() + t.get("newRegistryURL").getValue().toString());
	// 	};

	// 	// operation.setInvokable(consumer);
	// 	operation.setWrappedInvokable(consumer);

	// 	return operation;
	// }


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


		Property exampleProperty = new Property(Exampleproperty, "Initial");
		// registryProperty.setIdShort(REGISTRY_PATH_ID);
		exampleProperty.setKind(ModelingKind.TEMPLATE);

		Property registryProperty = new Property("registryURL", "");
		registryProperty.setKind(ModelingKind.TEMPLATE);


		Simple_SM.addSubmodelElement(exampleProperty);
		Simple_SM.addSubmodelElement(registryProperty);
		Simple_SM.addSubmodelElement(setExamplePropertyValue());
		// Simple_SM.addSubmodelElement(setRegistryPropertyValue());


		return Simple_SM;
	}

	public SubmodelDescriptor getSubmodelDescriptor() {
		Identifier identifier = new Identifier(IdentifierType.CUSTOM, SUBMODEL_ID);
		return new SubmodelDescriptor(SUBMODEL_ID, identifier, "http://" + HOSTNAME + ":4002/submodelserver/" + SUBMODEL_ID + "/submodel");
	}

	private void startupEdgeServer() {

		// Create a BaSyxConetxt for port 8082 with an empty endpoint
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration("", "", HOSTNAME, 4002);
		// BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration(4002, "");
		BaSyxContext context = contextConfig.createBaSyxContext();
		System.out.println(contextConfig.getHostname());


		// Get the edgeSubmodel from the ComponentFactory
		Submodel testSubmodel = createTestSubmodel();

		// Create a new SubmodelServlet containing the edgeSubmodel
		SubmodelServlet smServlet = new SubmodelServlet(testSubmodel);

		// Add the SubmodelServlet mapping to the context at the path
		context.addServletMapping("/submodelserver/" + SUBMODEL_ID + "/*", smServlet);


		// Create and start a HTTP server with the context created above
		BaSyxHTTPServer Server = new BaSyxHTTPServer(context);
		Server.start();


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
		
		// Identifier id = new Identifier(IdentifierType.IRI, "TestProductAAS");
		Identifier id = new Identifier(IdentifierType.CUSTOM, "TestProductAAS");
		ConnectedAssetAdministrationShell aas = manager.retrieveAAS(id);
		System.out.println(aas.getIdShort());

		registryProxy.register(id, simple.getSubmodelDescriptor());
	}

}
