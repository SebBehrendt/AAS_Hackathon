package sdm_aas;import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.basyx.submodel.metamodel.map.Submodel;

import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;

/**
 * DataJson creates a JSON file accordingly to data.json from ProdSim can be
 * invoked via
 * http://localhost:4004/handson/entity/aas/submodels/Entity/submodel/submodelElements/create_data.json/invoke
 * 
 * @author marieke
 *
 */
public class DataJson {
	static JSONObject JSONData = new JSONObject();

	static void createDataJson() {

		Submodel r1 = CreateResourceSubmodel.createResource1Submodel();
		Submodel r2 = CreateResourceSubmodel.createResource2Submodel();

		// create JSON Object for resource 1
		JSONObject jsonObjectR1 = new JSONObject();

		jsonObjectR1.put("ID", r1.getIdShort());
		jsonObjectR1.put("description", r1.getDescription());
		jsonObjectR1.put("capacity", r1.getSubmodelElement("capacity").getValue());
		jsonObjectR1.put("processes", "[P1,P2]");
		jsonObjectR1.put("states", "ade5d672-d3ca-11ec-9d64-0242ac120002");

		// create JSON Object for resource 1
		JSONObject jsonObjectR2 = new JSONObject();

		jsonObjectR2.put("ID", r2.getIdShort());
		jsonObjectR2.put("description", r2.getDescription());
		jsonObjectR2.put("capacity", r2.getSubmodelElement("capacity").getValue());
		jsonObjectR2.put("processes", "[P2,P3]");
		jsonObjectR2.put("states", "ade5d672-d3ca-11ec-9d64-0242ac120002");

		// combine all resources
		JSONArray resourceList = new JSONArray();
		resourceList.add(jsonObjectR1);
		resourceList.add(jsonObjectR2);

		// create process data in JSON from the process submodel
		Submodel processes = Process.createProcessSubmodel();

		JSONObject jsonObjectProcesses = new JSONObject();
		jsonObjectProcesses.put("processes", processes.getProperties().toString());

		// create time model ft1 in JSON
		JSONObject jsonObjectft1 = new JSONObject();

		jsonObjectft1.put("ID", "7e41f696-d3c3-11ec-9d64-0242ac120002");
		jsonObjectft1.put("description", "normal distribution time model with 20 minutes");
		jsonObjectft1.put("parameters", "[20, 5]");
		jsonObjectft1.put("batch_size", "100");
		jsonObjectft1.put("distribution_function", "normal");

		JSONObject jsonft1 = new JSONObject();
		jsonft1.put("ft1", jsonObjectft1);

		// create time model ft2 in JSON
		JSONObject jsonObjectft2 = new JSONObject();

		jsonObjectft2.put("ID", "7e41f8f8-d3c3-11ec-9d64-0242ac120002");
		jsonObjectft2.put("description", "constant distribution time model with 10 minutes");
		jsonObjectft2.put("parameters", "[10, 5]");
		jsonObjectft2.put("batch_size", "100");
		jsonObjectft2.put("distribution_function", "constant");

		JSONObject jsonft2 = new JSONObject();
		jsonft2.put("ft2", jsonObjectft2);

		// create time model ft3 in JSON
		JSONObject jsonObjectft3 = new JSONObject();

		jsonObjectft3.put("ID", "ft3");
		jsonObjectft3.put("description", "constant distribution time model with 17 minutes");
		jsonObjectft3.put("parameters", "[17, 5]");
		jsonObjectft3.put("batch_size", "100");
		jsonObjectft3.put("distribution_function", "constant");

		JSONObject jsonft3 = new JSONObject();
		jsonft3.put("ft3", jsonObjectft3);

		// combine all JSON time models
		JSONArray ftList = new JSONArray();
		ftList.add(jsonft1);
		ftList.add(jsonft2);
		ftList.add(jsonft3);

		// create History Time Model in JSON
		JSONObject jsonObjecthtm = new JSONObject();

		jsonObjecthtm.put("ID", "7e41fa2e-d3c3-11ec-9d64-0242ac120002");
		jsonObjecthtm.put("description", "history time model");
		jsonObjecthtm.put("History", "[1, 2, 3, 4, 5, 6, 7, 8, 9]");

		// combine all time models
		JSONObject allFunctionalTimeModels = new JSONObject();
		allFunctionalTimeModels.put("FunctionTimeModels", ftList);
		allFunctionalTimeModels.put("HistoryTimeModels", jsonObjecthtm);

		// create JSON objects for break down states
		JSONObject jsonObjectbs1 = new JSONObject();

		jsonObjectbs1.put("ID", "ade5d672-d3ca-11ec-9d64-0242ac120002");
		jsonObjectbs1.put("description", "Breakdown state machine 1");
		jsonObjectbs1.put("time_model_id", "7e41f8f8-d3c3-11ec-9d64-0242ac120002");

		JSONObject jsonbs1 = new JSONObject();
		jsonbs1.put("BS1", jsonObjectbs1);

		JSONObject jsonObjectbs2 = new JSONObject();

		jsonObjectbs2.put("ID", "ade5e36a-d3ca-11ec-9d64-0242ac120002");
		jsonObjectbs2.put("description", "Breakdown state machine 2");
		jsonObjectbs2.put("time_model_id", "7e41f696-d3c3-11ec-9d64-0242ac120002");

		JSONObject jsonbs2 = new JSONObject();
		jsonbs2.put("BS2", jsonObjectbs2);

		JSONArray bsList = new JSONArray();
		bsList.add(jsonbs1);
		bsList.add(jsonbs2);

		JSONObject allBreakdownStates = new JSONObject();
		allBreakdownStates.put("BreakDownStates", bsList);

		// create full Data.json file
		JSONData = new JSONObject();
		JSONData.put("time_models", allFunctionalTimeModels);
		JSONData.put("states", allBreakdownStates);
		JSONData.put("resources", resourceList);
		JSONData.put("processes", jsonObjectProcesses);

		try {
			FileWriter file = new FileWriter("/Users/marieke/Desktop/data.json");
			file.write(JSONData.toJSONString());
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("JSON file created: " + JSONData);
	}
}
