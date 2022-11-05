package sdm_aas;import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.Blob;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.File;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.AASLambdaPropertyHelper;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;

import New_ProcessModel.Process;

/**
 * creates submodels for resource 1 and 2
 * 
 * @author marieke
 *
 */
public class CreateResourceSubmodel {
	public static Resource resource1;
	public static Resource resource2;

	/**
	 * creates a submodel for resource 1
	 * 
	 * @return submodel for resource1
	 */
	public static Submodel createResource1Submodel() {
		resource1 = new Resource(1);

		// for clear identification set idShort, description and identification
		Submodel resource1Submodel = new Submodel();
		resource1Submodel.setIdShort("R1");
		LangStrings resource1Description = new LangStrings("english", "Resource 1");
		resource1Submodel.setDescription(resource1Description);
		resource1Submodel.setIdentification(new ModelUrn("resource1"));
		// set a category for the resource
		resource1Submodel.setCategory((ResourceType.batch).toString());

		// set coordinates as a static Property
		resource1.location = Arrays.asList((float) 10, (float) 10);
		Property coordinateProperty = new Property("coordinates", resource1.location.toString());
		coordinateProperty.setModelingKind(ModelingKind.TEMPLATE);
		resource1Submodel.addSubmodelElement(coordinateProperty);

		// set a reference property to show dependencies
		Reference refv = new Reference(Entity.entityIdentifier, KeyElements.ENTITY, true);
		ReferenceElement test = new ReferenceElement("Entity", refv);
		resource1Submodel.addSubmodelElement(test);

		// set the capacity as a static Property
		resource1.capacity = 2;
		Property capacity = new Property("capacity", resource1.capacity);
		resource1Submodel.addSubmodelElement(capacity);

		// create a dynamic property to show the capabilities of the resource
		// capabilities = possible processes of a resource
		Supplier<Object> get_capabilitiesFunction = () -> resource1.get_capabilitiesString();
		Property get_capabilitiesProperty = new Property();
		get_capabilitiesProperty.setIdShort("capabilities");
		AASLambdaPropertyHelper.setLambdaValue(get_capabilitiesProperty, get_capabilitiesFunction, null);
		resource1Submodel.addSubmodelElement(get_capabilitiesProperty);

		// create dynamic Property for the time of the process 1 of resource1
		Supplier<Object> process_time1ReadFunction = () -> resource1.process1.get_next_time();
		Property process_time1Property = new Property();
		process_time1Property.setIdShort("process1_time");
		AASLambdaPropertyHelper.setLambdaValue(process_time1Property, process_time1ReadFunction, null);
		resource1Submodel.addSubmodelElement(process_time1Property);

		// create dynamic Property for the time of the process 1 of resource2
		Supplier<Object> process_time2ReadFunction = () -> resource1.process2.get_next_time();
		Property process_time2Property = new Property();
		process_time2Property.setIdShort("process2_time");
		AASLambdaPropertyHelper.setLambdaValue(process_time2Property, process_time2ReadFunction, null);
		resource1Submodel.addSubmodelElement(process_time2Property);

		// add a operation to perform a specific process with a certain id
		// the function implements the logic of the operation
		// the operation encapsulates the function in the AAS format
		Function<Object[], Object> perform_processFunction = (args) -> {
			String input = args[0].toString();
			LinkedHashMap<String, Integer> map = (LinkedHashMap<String, Integer>) args[0];
			int id = map.get("id");
			if (id == 1 || id == 2) {
				if (resource1.process1.isActive == false && id == 1) {
					resource1.perform_process(resource1.process1);
					System.out.println("The process with id " + resource1.process1.id + " has started ");
					return "Process with id " + resource1.process1.id + " has started";
				} else if (resource1.process2.isActive == false && id == 2) {
					resource1.perform_process(resource1.process2);
					System.out.println("The process with id " + resource1.process2.id + " has started ");
					return "Process with id " + resource1.process2.id + " has started";
				} else
					System.out.println("The process with id " + id + " is already active");
				return "The process with id " + id + " is already active";
			} else {
				System.out.println("The process with id " + id + " does not exist");
				return "The process with id " + id + " does not exist";
			}
		};
		Operation perform_processOperation = new Operation(perform_processFunction);
		perform_processOperation.setIdShort("perform_process");
		Property start = new Property("start_input", 0);
		start.setModelingKind(ModelingKind.TEMPLATE);
		perform_processOperation.setInputVariables(Collections.singletonList(new OperationVariable(start)));
		resource1Submodel.addSubmodelElement(perform_processOperation);

		// add a operation to stop a specific process with a certain id
		Function<Object[], Object> stop_processFunction = (args) -> {
			String input = args[0].toString();
			LinkedHashMap<String, Integer> map = (LinkedHashMap<String, Integer>) args[0];
			int id = map.get("id");
			if (id == 1 || id == 2) {
				if (resource1.process1.isActive != false && id == 1) {
					resource1.stop_process(resource1.process1);
					System.out.println("The process with id " + resource1.process1.id + " has stopped ");
					return "Process with id " + resource1.process1.id + " has stopped";
				} else if (resource1.process2.isActive != false && id == 2) {
					resource1.stop_process(resource1.process2);
					System.out.println("The process with id " + resource1.process2.id + " has stopped ");
					return "Process with id " + resource1.process2.id + " has stopped";
				} else
					System.out.println("The process with id " + id + " is already inactive");
				return "The process with id " + id + " is already inactive";
			} else {
				System.out.println("The process with id " + id + " does not exist");
				return "The process with id " + id + " does not exist";
			}
		};
		Operation stop_processOperation = new Operation(stop_processFunction);
		stop_processOperation.setIdShort("stop_process");
		Property stop = new Property("stop_input", 1);
		stop.setModelingKind(ModelingKind.TEMPLATE);
		stop_processOperation.setInputVariables(Collections.singletonList(new OperationVariable(stop)));
		resource1Submodel.addSubmodelElement(stop_processOperation);

		// add a operation to get available processes of the resource
		// available processes = list of processes which are not active
		Function<Object[], Object> get_available_processFunction = (args) -> {
			List<Process> availableProcessesList = resource1.get_available_processes();
			if (availableProcessesList.size() == 0)
				return ("There are no available processes");
			List<Integer> availableList = new ArrayList<>();
			for (int i = 0; i < availableProcessesList.size(); i++) {
				Process process = availableProcessesList.get(i);
				availableList.add(process.id);
			}
			return "ID's of available Processes: " + availableList.toString();
		};
		Operation get_available_processOperation = new Operation(get_available_processFunction);
		get_available_processOperation.setIdShort("get_available_processes");
		Property av_input = new Property("available_input", 1);
		av_input.setModelingKind(ModelingKind.TEMPLATE);
		get_available_processOperation.setInputVariables(Collections.singletonList(new OperationVariable(av_input)));
		resource1Submodel.addSubmodelElement(get_available_processOperation);

		return resource1Submodel;
	}

	/**
	 * creates a submodel for resource2
	 * 
	 * @return submodel for resource 2
	 */
	public static Submodel createResource2Submodel() {
		resource2 = new Resource(2);
		Submodel resource2Submodel = new Submodel();
		resource2Submodel.setIdShort("R2");
		Identifier r2 = new ModelUrn("resource2");
		resource2Submodel.setIdentification(r2);
		LangStrings resource2Description = new LangStrings("english", "Resource 2");
		resource2Submodel.setDescription(resource2Description);

		resource2Submodel.setCategory((ResourceType.pipeline_unclocked).toString());

		// set coordinates as a static Property
		resource2.location = Arrays.asList((float) 20, (float) 10);
		Property coordinateProperty = new Property("coordinates", resource2.location.toString());
		resource2Submodel.addSubmodelElement(coordinateProperty);
		coordinateProperty.setModelingKind(ModelingKind.TEMPLATE);

		// set the capacity as a static Property
		resource2.capacity = 4;
		Property capacity = new Property("capacity", resource2.capacity);
		resource2Submodel.addSubmodelElement(capacity);

		// create a dynamic property to show the capabilities of the resource
		Supplier<Object> get_capabilitiesFunction = () -> resource2.get_capabilitiesString();
		Property get_capabilitiesProperty = new Property();
		get_capabilitiesProperty.setIdShort("capabilities");
		AASLambdaPropertyHelper.setLambdaValue(get_capabilitiesProperty, get_capabilitiesFunction, null);
		resource2Submodel.addSubmodelElement(get_capabilitiesProperty);

		// create dynamic Property for the time of the 2 processes of resource2
		Supplier<Object> process_time2ReadFunction = () -> resource2.process2.get_next_time();
		Property process_time2Property = new Property();
		process_time2Property.setIdShort("process2_time");
		AASLambdaPropertyHelper.setLambdaValue(process_time2Property, process_time2ReadFunction, null);
		resource2Submodel.addSubmodelElement(process_time2Property);

		Supplier<Object> process_time3ReadFunction = () -> resource2.process3.get_next_time();
		Property process_time3Property = new Property();
		process_time3Property.setIdShort("process3_time");
		AASLambdaPropertyHelper.setLambdaValue(process_time3Property, process_time3ReadFunction, null);
		resource2Submodel.addSubmodelElement(process_time3Property);

		// add the operation to perform a specific process with a certain id
		Function<Object[], Object> perform_processFunction = (args) -> {
			String input = args[0].toString();
			LinkedHashMap<String, Integer> map = (LinkedHashMap<String, Integer>) args[0];
			int id = map.get("id");
			if (id == 2 || id == 3) {
				if (resource2.process2.isActive == false && id == 2) {
					resource2.perform_process(resource2.process2);
					System.out.println("The process with id " + resource2.process2.id + " has started ");
					return "Process with id " + resource2.process2.id + " has started";
				} else if (resource2.process3.isActive == false && id == 3) {
					resource2.perform_process(resource2.process3);
					System.out.println("The process with id " + resource2.process3.id + " has started ");
					return "Process with id " + resource2.process3.id + " has started";
				} else
					System.out.println("The process with id " + id + " is already active");
				return "The process with id " + id + " is already active";
			} else {
				System.out.println("The process with id " + id + " does not exist");
				return "The process with id " + id + " does not exist";
			}
		};
		Operation perform_processOperation = new Operation(perform_processFunction);
		perform_processOperation.setIdShort("perform_process");
		Property start_input = new Property("start_input", 0);
		start_input.setModelingKind(ModelingKind.TEMPLATE);
		perform_processOperation.setInputVariables(Collections.singletonList(new OperationVariable(start_input)));
		resource2Submodel.addSubmodelElement(perform_processOperation);

		// add the operation to stop a specific process with a certain id
		Function<Object[], Object> stop_processFunction = (args) -> {
			String input = args[0].toString();
			LinkedHashMap<String, Integer> map = (LinkedHashMap<String, Integer>) args[0];
			int id = map.get("id");
			if (id == 3 || id == 2) {
				if (resource2.process3.isActive != false && id == 3) {
					resource2.stop_process(resource2.process3);
					System.out.println("The process with id " + resource2.process3.id + " has stopped ");
					return "Process with id " + resource2.process3.id + " has stopped";
				} else if (resource2.process2.isActive != false && id == 2) {
					resource2.stop_process(resource2.process2);
					System.out.println("The process with id " + resource2.process2.id + " has stopped ");
					return "Process with id " + resource2.process2.id + " has stopped";
				} else
					System.out.println("The process with id " + id + " is already inactive");
				return "The process with id " + id + " is already inactive";
			} else {
				System.out.println("The process with id " + id + " does not exist");
				return "The process with id " + id + " does not exist";
			}
		};
		Operation stop_processOperation = new Operation(stop_processFunction);
		stop_processOperation.setIdShort("stop_process");
		Property stop = new Property("stop_input", 1);
		stop.setModelingKind(ModelingKind.TEMPLATE);
		stop_processOperation.setInputVariables(Collections.singletonList(new OperationVariable(stop)));
		resource2Submodel.addSubmodelElement(stop_processOperation);

		// add the operation to get available processes of the resource
		Function<Object[], Object> get_available_processFunction = (args) -> {
			List<Process> availableProcessesList = resource2.get_available_processes();
			if (availableProcessesList.size() == 0)
				return ("There are no available processes");
			List<Integer> availableList = new ArrayList<>();
			for (int i = 0; i < availableProcessesList.size(); i++) {
				Process process = availableProcessesList.get(i);
				availableList.add(process.id);
			}
			return "ID's of available Processes: " + availableList.toString();
		};
		Operation get_available_processOperation = new Operation(get_available_processFunction);
		get_available_processOperation.setIdShort("get_available_processes");
		Property av_input = new Property("available_input", 1);
		av_input.setModelingKind(ModelingKind.TEMPLATE);
		get_available_processOperation.setInputVariables(Collections.singletonList(new OperationVariable(av_input)));
		resource2Submodel.addSubmodelElement(get_available_processOperation);

		return resource2Submodel;
	}

	/**
	 * creates a availability submodel retrieves data from a postgres sql database
	 * 
	 * @return
	 */
	public static Submodel createAvailabilitySubmodel() {
		Submodel availabilitySubmodel = new Submodel();
		availabilitySubmodel.setIdShort("Availability");
		Identifier av = new ModelUrn("availability");
		availabilitySubmodel.setIdentification(av);

		// Operation to retrieve all data of a specific resource from a sql table
		Function<Object[], Object> get_data_for_resourceFunction = (args) -> {
			String input = args[0].toString();
			LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) args[0];
			String id = map.get("id");
			String output = null;
			try {
				output = AvailabilitySQLValues.getData(id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return output;
		};

		Operation get_data_for_resourceOperation = new Operation(get_data_for_resourceFunction);
		get_data_for_resourceOperation.setIdShort("get_resource_data");
		Property data_input = new Property("data_input", 1);
		data_input.setModelingKind(ModelingKind.TEMPLATE);
		get_data_for_resourceOperation.setInputVariables(Collections.singletonList(new OperationVariable(data_input)));
		availabilitySubmodel.addSubmodelElement(get_data_for_resourceOperation);

		// Operation to retrieve the MTTR from a sql table for a resource
		Function<Object[], Object> get_MTTRFunction = (args) -> {
			LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) args[0];
			// get the id from the input data in raw - body {id: "r1"} or {id: "r2"} in a
			// POST request
			String id = map.get("id");
			double output = 0;
			try {
				output = AvailabilitySQLValues.getMTTR(id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return "MTTR of resource with the ID " + id + ": " + output;
		};

		Operation get_MTTROperation = new Operation(get_MTTRFunction);
		get_MTTROperation.setIdShort("get_MTTR");
		Property MTTR_input = new Property("MTTR_input", 1);
		MTTR_input.setModelingKind(ModelingKind.TEMPLATE);
		get_MTTROperation.setInputVariables(Collections.singletonList(new OperationVariable(MTTR_input)));
		availabilitySubmodel.addSubmodelElement(get_MTTROperation);

		// Operation to retrieve the MTTF from a sql table for a resource
		Function<Object[], Object> get_MTTFFunction = (args) -> {
			LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) args[0];
			// get the id from the input data in raw - body {id: "r1"} {id: "r2"} in a POST
			// request
			String id = map.get("id");
			double output = 0;
			try {
				output = AvailabilitySQLValues.getMTTF(id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return "MTTF of resource with the ID " + id + ": " + output;
		};

		Operation get_MTTFOperation = new Operation(get_MTTFFunction);
		get_MTTFOperation.setIdShort("get_MTTF");
		Property MTTF_input = new Property("MTTF_input", 1);
		MTTF_input.setModelingKind(ModelingKind.TEMPLATE);
		get_MTTFOperation.setInputVariables(Collections.singletonList(new OperationVariable(MTTF_input)));
		availabilitySubmodel.addSubmodelElement(get_MTTFOperation);

		return availabilitySubmodel;
	}

	/**
	 * creates ReourceComplexSumbodel to create references or links to
	 * documentations
	 * 
	 * @return
	 */
	public static Submodel createResourceComplexSubmodel() {
		Submodel resourceComplex = new Submodel("ResourceComplex", new ModelUrn("RComplex"));
		// A BLOB is a data element that represents a file that is contained with its
		// source code in the value attribute.
		Blob blob = new Blob("overview", "image/handson/resource/aas/");
		blob.setValue("ResourceOverview");
		resourceComplex.addSubmodelElement(blob);

		// String value: path of file Sting mimeType like in the standard RFC2046
		File file = new File();
		file.setMimeType("image/https://www.is.inf.uni-due.de/courses/mod_ws05/skript/kapitel405.pdf");
		file.setIdShort("petrinet");
		file.setValue("petrinet");
		resourceComplex.addSubmodelElement(file);

		return resourceComplex;
	}
}