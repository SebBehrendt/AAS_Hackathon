package sdm_aas;import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProviderHelper;


import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.CustomId;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import New_ProcessModel.Process;
import camundajar.impl.scala.sys.Prop;

import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProviderHelper;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueType;



public class Simple_AAS {
// required objects for a property
	public double value;
	public double raw_material_cost;
	public double weight;
	public List<Double> dimensions;
	public double precision_requirements;
	public List<Process> required_processes;

	/**
	 * create product submodel
	 * 
	 * @return
	 */
	public static Submodel createTestSubmodel() {
        // submodel with id short
		Submodel Simple_SM = new Submodel();
		Simple_SM.setIdShort("Simple_SM");

        Property dummyProperty = new Property();
		dummyProperty.setIdShort("dummy");
		dummyProperty.set(VABLambdaProviderHelper.createSimple(() -> {
			return Math.random() * (20 - 10) + 10;
		}, null), ValueType.Boolean);
		Simple_SM.addSubmodelElement(dummyProperty);

		return Simple_SM;
	}

    public static String return_simple_string(){
        return (Math.random() * (20 - 10) + 10) + "";
    }

	private static final Logger logger = LoggerFactory.getLogger(SubModelProviderResource.class);

	/**
	 * creates shell for product asset on a own server GET request:
	 * http://localhost:4001/handson/product/aas/
	 * information about submodels:
	 * http://localhost:4001/handson/product/aas/submodels/Product1/submodel
	 * http://localhost:4001/handson/product/aas/submodels/Order1/submodel
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// create product asset and set aas
		Asset asset = new Asset("Test_asset", new CustomId("Test_asset"), AssetKind.INSTANCE);
		AssetAdministrationShell test_aas = new AssetAdministrationShell("Test_AAS", new CustomId("Test_AAS"),
				asset);
		// create description for product shell
		LangStrings description_test_aas = new LangStrings("english", "Product with subproducts");
		test_aas.setDescription(description_test_aas);


        HashMap<String, Object> rootElement = new HashMap<>();

		// Create and add a dynamic string property by providing the necessary get and
		// set operations.
		Map<String, Object> dynamicPropertyVal = VABLambdaProviderHelper.createSimple(() -> {
			// A user defined get-operation is provided here
			return return_simple_string();
		}, (Object newValue) -> {
			// A user defined set-operation is provided here
			if (newValue instanceof String) {
				String a = "";
			}
		});
        rootElement.put("dynamic", dynamicPropertyVal);

		// The provider encapsulates the data and realizes the abstract IModelProvider
		// interface






		// create product1submodel and set id and identification
		Submodel test_sm = createTestSubmodel();
		test_sm.setIdShort("Simple_SM");
		test_sm.setIdentification(new ModelUrn("Simple_SM"));
        test_aas.addSubmodel(test_sm);

		List<Submodel> listofSubmodels = new ArrayList<>(); 
		listofSubmodels.add(test_sm); 


		PushAAStoServer.pushAAS(test_aas, "http://193.196.37.23:4001/aasServer", "http://193.196.37.23:4000/registry/api/v1/registry",listofSubmodels);



	}

}
