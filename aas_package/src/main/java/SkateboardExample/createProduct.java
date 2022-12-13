package SkateboardExample;

import ProductModel.BOM;
import ProductModel.DesignInformation;
import ProductModel.DigitalNameplate;
import ProductModel.Product;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class createProduct {

    //the right Order:
    /*
    create Subcomponents, instanciate AAS and submodels, upload to server, reference in parent Product (Skateboard), upload
     */

   static Map <String, AssetAdministrationShell> subComponents = new HashMap<>(); //Id_short, AAS_subcomponent
    static List<AssetAdministrationShell> listOfSubComponents = new ArrayList<>();
    static List<Submodel> listOfSkateboardSubmodels = new ArrayList<>();

     Product createSkateboard()
    {
        Product skateBoard = new Product("Skateboard_xyz");
        skateBoard.addSubComponents(new BOM(listOfSubComponents));

        return skateBoard;

    }

    static class Board{

        static void createBoardProduct()
        {
            // Digital Nameplate, BOM
            Product skateboardBoard = new Product("Board_20221212_1047", createSkateboardDigitalNameplate());
            // if time: add dummy SM Production
            //add designInformation
            listOfSubComponents.add(skateboardBoard.createAAS());

        }
        static void createSubComponents()
        {
            Axis.createAxis();
            Axis.createAxisAssembly();

        }
        @NotNull
        @Contract(value = " -> new", pure = true)
        private static DigitalNameplate createSkateboardDigitalNameplate()
        {
            return new DigitalNameplate("Board_20221212_1047");
        }
        private static DesignInformation createDesignInformation()
        {
            DesignInformation designInfo = new DesignInformation("Board_CAD_File", "S://01_CAD/2022/Boards/Board_20221212_1047.stp");
            return designInfo;
        }



    }

    static class Axis{
       static void  createAxisAssembly()
        {

        }
        static void createAxis()
        {
            //
            Product axisFront = new Product("Axis_front_20221212_1118");
            axisFront.addDesignInformation(new DesignInformation("CAD_axis_front", "link_to_objectserver/cad/axis/axis_front.stp"));

            listOfSubComponents.add(axisFront.createAAS());
        }

    }

    static class Wheels{
         static void createWheelsForAxisAssembly()
         {
             // wheel 1


             //wheel 2
         }

    }

    static class Screws{

    }
}
