package SkateboardExample;

import OrderModel.ProductInstance;
import ProductModel_Backup.Product;
import ProductModel_Backup.ProductLifecycleState;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;

import java.util.ArrayList;
import java.util.List;

/**
 * Main used to execute example
 */
public class Main {
    public static void main(String[] args) {

        // create AAS of Skateboard
       AssetAdministrationShell skateboardShell =  createProduct.createSkateboard();
        //create List of ProductInstance (SkateboardAAS, quantity)
        List<ProductInstance> listInstance = new ArrayList<>();
        listInstance.add(new ProductInstance(skateboardShell, "1"));
        createOrder.createSkateboardOrder(listInstance);


        createResourceCaesar.createCaesarCell();
        createResourceFiveX.createFiveX();


    }

}