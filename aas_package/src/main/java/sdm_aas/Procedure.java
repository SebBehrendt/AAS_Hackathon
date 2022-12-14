package sdm_aas;import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import java.util.function.Supplier;


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
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.restapi.SubmodelProvider;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.AASLambdaPropertyHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractProcedure{
    
}


public class Procedure {
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
	public static Submodel createProductSubmodel() {
		// submodel with id short
		Product produkt = new Product();
		Submodel ProductSubmodel = new Submodel();
		ProductSubmodel.setIdShort("P1");

		// set static property with id and value
		produkt.value = (double) 12;
		Property value = new Property();
		value.setIdShort("ProductValue");
		value.setValue(produkt.value);
		ProductSubmodel.addSubmodelElement(value);

		// set static property with id short and value of raw material costs
		produkt.raw_material_cost = 12;
		Property raw_material_cost = new Property();
		raw_material_cost.setIdShort("RawMaterialCostsOfProduct");
		raw_material_cost.setValue(produkt.raw_material_cost);
		ProductSubmodel.addSubmodelElement(raw_material_cost);

		// set static property with id short and weight
		produkt.weight = 12;
		Property weight = new Property();
		weight.setIdShort("ProductWeight");
		weight.setValue(produkt.weight);
		ProductSubmodel.addSubmodelElement(weight);
		weight.keySet();

		// set static property with id short and dimensions
		produkt.dimensions = Arrays.asList((double) 12, (double) 12, (double) 12);
		Property dimensions = new Property();
		dimensions.setIdShort("ProductDimensions");
		dimensions.setValue((produkt.dimensions).toString());
		ProductSubmodel.addSubmodelElement(dimensions);

		return ProductSubmodel;
	}

	// returns random changing numbers for the time of a process
	public static double get_next_time() {
		while (true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return Math.random() * 10 + 10;
		}
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
		Asset asset = new Asset("Product", new CustomId("ProductAsset"), AssetKind.INSTANCE);
		AssetAdministrationShell productShell = new AssetAdministrationShell("product", new CustomId("ProductAssetID"),
				asset);
		// create description for product shell
		LangStrings descriptionProduct = new LangStrings("english", "Product with subproducts");
		productShell.setDescription(descriptionProduct);

		// create product1submodel and set id and identification
		Submodel product1Submodel = createProductSubmodel();
		product1Submodel.setIdShort("Product1");
		product1Submodel.setIdentification(new ModelUrn("product1"));

		// create order1Submodel and set id and identification
		Submodel order1Submodel = Order.createOrderSubmodel();
		order1Submodel.setIdShort("Order1");
		order1Submodel.setIdentification(new ModelUrn("order1"));

		// create aas model provider for all submodels
		AASModelProvider aasProvider = new AASModelProvider(productShell);

		SubmodelProvider product1SMProvider = new SubmodelProvider(product1Submodel);
		SubmodelProvider order1SMProvider = new SubmodelProvider(order1Submodel);

		MultiSubmodelProvider fullProvider = new MultiSubmodelProvider();
		fullProvider.setAssetAdministrationShell(aasProvider);

		fullProvider.addSubmodel(product1SMProvider);
		fullProvider.addSubmodel(order1SMProvider);

		HttpServlet aasServlet = new VABHTTPInterface<IModelProvider>(fullProvider);
		logger.info("Created a servlet for the model");

		IAASRegistry registry = new InMemoryRegistry();
		IModelProvider registryProvider = new AASRegistryModelProvider(registry);
		HttpServlet registryServlet = new VABHTTPInterface<IModelProvider>(registryProvider);
		logger.info("Created a registry servlet for the model");

		productShell.addSubmodel(product1Submodel);
		productShell.addSubmodel(order1Submodel);

		// // Explicitly create and add submodel descriptors
		SubmodelDescriptor product1SMDescriptor = new SubmodelDescriptor(product1Submodel,
				"http://localhost:4001/handson/product/aas/submodels/Product1");
		SubmodelDescriptor order1SMDescriptor = new SubmodelDescriptor(order1Submodel,
				"http://localhost:4001/handson/product/aas/submodels/Order1");

		// Register the VAB model at the directory
		AASDescriptor aasDescriptor = new AASDescriptor(productShell, "http://localhost:4001/handson/product/aas");

		aasDescriptor.addSubmodelDescriptor(product1SMDescriptor);
		aasDescriptor.addSubmodelDescriptor(order1SMDescriptor);
		registry.register(aasDescriptor);

		// create dynamic Property for the time of the process 1 of resource2
		Supplier<Object> process_time2ReadFunction = () -> Product.get_next_time();
		Property process_time2Property = new Property();
		process_time2Property.setIdShort("process_time");
		AASLambdaPropertyHelper.setLambdaValue(process_time2Property, process_time2ReadFunction, null);
		product1Submodel.addSubmodelElement(process_time2Property);



		List<Submodel> listofSubmodels = new ArrayList<>(); 
		listofSubmodels.add(product1Submodel); 
		listofSubmodels.add(order1Submodel);

		Map<AssetAdministrationShell, List<Submodel>> map = new HashMap<>();
		map.put(productShell, listofSubmodels);


		PushAAStoServer.pushAAS(map, "http://193.196.37.23:4001/aasServer", "http://193.196.37.23:4000/registry/api/v1/registry");





		// // Deploy the AAS on a HTTP server
		// BaSyxContext context = new BaSyxContext("/handson", "", "localhost", 4001);
		// context.addServletMapping("/product/*", aasServlet);
		// context.addServletMapping("/registry/*", registryServlet);
		// BaSyxHTTPServer httpServer = new BaSyxHTTPServer(context);

		// httpServer.start();
		// logger.info("HTTP server started");

	}

}
