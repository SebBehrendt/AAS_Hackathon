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

    static List<AssetAdministrationShell> listOfSubComponents = new ArrayList<>();

     static AssetAdministrationShell createSkateboard()
    {
        Product skateBoard = new Product("Skateboard_xyz");

        createSubComponents();

        skateBoard.addSubComponents(new BOM(listOfSubComponents));

        AssetAdministrationShell shell = skateBoard.createAAS();
        skateBoard.createAndUploadAAStoServer();

        System.out.println("Test");

        return shell;



    }
    static void createSubComponents()
    {
        Board.createBoardProduct();
        Axis.createAxis();
        //  Axis.createAxisAssembly();

    }

    static class Board{

        static void createBoardProduct()
        {
            // Digital Nameplate
            Product skateboardBoard = new Product("Board_20221212_1047", createSkateboardDigitalNameplate());
            skateboardBoard.addDesignInformation(createDesignInformation());

            // if time: add dummy SM Production
            //add designInformation

            AssetAdministrationShell shell = skateboardBoard.createAAS();
            skateboardBoard.createAndUploadAAStoServer();
            listOfSubComponents.add(shell);

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

            AssetAdministrationShell shell = axisFront.createAAS();
            axisFront.createAndUploadAAStoServer();
            listOfSubComponents.add(shell);
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
