package SkateboardExample;

import OrderModel.ProductInstance;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;

import java.util.ArrayList;
import java.util.List;

/**
 * Main used to execute example
 */
public class Main {
    public static void main(String[] args) {


        createResourceCaesar.createCaesarCell();
        createResourceFiveX.createFiveX();

        // Create AAS for processes and procedures and push to server
        // TODO: URLs of Resources neet to be inputted to procedures
        createProcesses.createProcessAndProcedureAAS();

        // create AAS of Skateboard
        // TODO: Reference processes in product
        AssetAdministrationShell skateboardShell =  createProduct.createSkateboard();
        //create List of ProductInstance (SkateboardAAS, quantity)
        List<ProductInstance> listInstance = new ArrayList<>();
        listInstance.add(new ProductInstance(skateboardShell, "1"));
        createOrder.createSkateboardOrder(listInstance);






    }

}