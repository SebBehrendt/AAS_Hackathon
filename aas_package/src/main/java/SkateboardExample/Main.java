package SkateboardExample;

import ProductModel_Backup.Product;
import ProductModel_Backup.ProductLifecycleState;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;

/**
 * Main used to execute example
 */
public class Main {
    public static void main(String[] args) {

    createOrder.createSkateboardOrder();
        //Breakpoint Dummy for debugging
        System.out.println("Hello World");



    }
    private static void testing_to_delete()
    {
        //First: based on the configuration and order, create Product Object of Skateboard
        Product skateBoardInstance = createSkateboardObject_backup.createSkateboardProductFromOrder();
        AssetAdministrationShell skateBoardAAS = skateBoardInstance.createAAS();
        // Infrastructure:upload to server

        //Planning gets OrderFiles from AAS (HTTP Client)
        //-> plans components, adds Subcomponents to product
        skateBoardInstance.setProductLifecycleState(ProductLifecycleState.DESIGN);
        createSkateboardObject_backup.addPlannedSubComponentsToSkateboard(skateBoardInstance);
        // Set new submodels --> Update method is TODO
        skateBoardAAS.addSubmodel(skateBoardInstance.createSubmodelConstruction());

        //Production Planning
        skateBoardInstance.setProductLifecycleState((ProductLifecycleState.PRODUCTION_PLANNING));
        skateBoardAAS.addSubmodel(skateBoardInstance.createSubmodelProduction());


    }
}