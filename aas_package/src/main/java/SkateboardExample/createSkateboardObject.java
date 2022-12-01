package SkateboardExample;

import Helper.AASHelper;
import ProductModel_Backup.*;
import ProductModel_Backup.Component.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static SkateboardExample.createSkateboardObject.createWheelComponents.*;

/**
 * class used to implement the example object Skateboard (Product) for example.
 */
public class createSkateboardObject {
    private static final String INTERNAL_ID_PREFIX = "ID_Prefix_";
    private static final String INTERNAL_ID_SUFFIX = "_time_stamp_etc"; //Example DUmmy

    protected static Product createSkateboardProductFromOrder()
    {
        //set general Infos
        Product skateBoard = new Product ("Dragon_Skateboard", "123xyz98_20221025");
        skateBoard.setTypeOfProduct("Skateboard");
        skateBoard.setInternalProductIdentification(createInternalIDs(skateBoard.getOrderID(), skateBoard.getTypeOfProduct()));
        // Set order Infos
        setSkateboardOrderInformation(skateBoard);

        return skateBoard;
    }
    static void addPlannedSubComponentsToSkateboard (Product skateBoard)
    {
        // skateBoard.addSubProduct(createBoardProduct.createBoardProductInstance());
        //skateBoard.addSubProduct(createWheelComponents.createWheelTypeComponent());
        skateBoard.addSubProduct(createWheelInstanceComponent("Wheel_1"));
        skateBoard.addSubProduct(createWheelInstanceComponent("Wheel_2"));
        skateBoard.addSubProduct(createWheelInstanceComponent("Wheel_3"));
        skateBoard.addSubProduct(createWheelInstanceComponent("Wheel_4"));

        // skateBoard.addSubProduct(createAxisComponents.createAxisComponentInstance("Axis_1"));
        // skateBoard.addSubProduct(createAxisComponents.createAxisComponentInstance("Axis_2"));

        //when all subcomponents are designed and chosen: CAD-File of "Parent Product"
        skateBoard.setConstructionData(createSkateboardConstructionData());

    }
    private static ConstructionData createSkateboardConstructionData ()
    {
        ConstructionData skateboardConstructionData = new ConstructionData();
        skateboardConstructionData.addConstructionFile("Assembly.stp", "Filepath.../../");

        return skateboardConstructionData;
    }
    private static String createInternalIDs(String nameOfProduct, String typeOfProduct)
    {
        return INTERNAL_ID_PREFIX +typeOfProduct+"_"+ nameOfProduct +INTERNAL_ID_SUFFIX;
    }
    private static void setSkateboardOrderInformation(Product skateBoard)
    {
         final String ORDER_FILE_NAME = "order_file.pdf";
         final String CONFIG_FILE_NAME = "configurationFile.pdf";
         final String ORDER_DATE = "OrderDate";
         final String DEADLINE_DATE = "DeadlineDate";

        skateBoard.addOrderFile(ORDER_FILE_NAME, "path_to_orderFile//...//.../");
        skateBoard.addOrderFile(CONFIG_FILE_NAME, "dummy_url_..//..");

        skateBoard.addOrderInformation(ORDER_DATE, "2022_10_25");
        skateBoard.addOrderInformation(DEADLINE_DATE, "2022_10_31"); //generate Date as function possible

        setOrderDescriptionParametersOfSkateboard(skateBoard);

    }
    private static void setOrderDescriptionParametersOfSkateboard(Product skateBoard)
    {
        skateBoard.addProductDescriptionParameters("SkateboardSize", "2");
        skateBoard.addProductDescriptionParameters("SkateboardColor", "blue");
        skateBoard.addProductDescriptionParameters("BoardMaterial", "Wood");
        skateBoard.addProductDescriptionParameters("Category", "Skateboard for Kids");
        // MLP Description of Product
        skateBoard.addProductDescription("de", "konfiguriertes Skateboard");
    }


    class createWheelComponents{

        protected void  createAllWheelComponentInstances(Map<String, IProduct> listSubComponents)
        {
            Component wheelType = createWheelTypeComponent();

            Component wheelInstance = createWheelInstanceComponent("1");
            listSubComponents.put(AASHelper.nameToIdShort(wheelInstance.getName()),wheelInstance);
            Component wheelInstance2 = createWheelInstanceComponent("2");
            listSubComponents.put(AASHelper.nameToIdShort(wheelInstance.getName()),wheelInstance2);
            Component wheelInstance3 = createWheelInstanceComponent("3");
            listSubComponents.put(AASHelper.nameToIdShort(wheelInstance.getName()),wheelInstance3);
            Component wheelInstance4 = createWheelInstanceComponent("4");
            listSubComponents.put(AASHelper.nameToIdShort(wheelInstance.getName()),wheelInstance4);
        }

        protected List<Component> createWheelComponentInstances(int count, Component typeToMultiply)
        {
            List<Component> instanceComponents = new ArrayList<>();
            int counter = 1;
            if (count <= 1){
                typeToMultiply.setComponentKind(ProductKind.INSTANCE);
                 instanceComponents.add(typeToMultiply);

            }
            //TODO
            while (counter <= count )
            {
               Component multipliedComponent = new Component("short_id");
                        multipliedComponent = typeToMultiply;
                        multipliedComponent.setName(typeToMultiply.getName()+"_"+String.valueOf(counter));
               multipliedComponent.setComponentKind(ProductKind.INSTANCE);
               instanceComponents.add(multipliedComponent);
               counter ++;
            }


             return instanceComponents;

        }
        protected Component createWheelTypeComponent()
        {
            Component wheelComponentType = new Component("Wheel_skateboard", "Wheel_764_8cm_Rubber_White_..");
            wheelComponentType.setComponentKind(ProductKind.TYPE);
            wheelComponentType.setSupplier("Skateboard_Wheels_AG");
            wheelComponentType.setStateOfComponent(ComponentState.SELECTED);

            return wheelComponentType;
        }
        public static Component createWheelInstanceComponent(String Name)
        {
            Component wheelComponentType = new Component(Name, "Wheel_764_8cm_Rubber_White_..");
            wheelComponentType.setComponentKind(ProductKind.INSTANCE);
            wheelComponentType.setSupplier("Skateboard_Wheels_AG");
            wheelComponentType.setStateOfComponent(ComponentState.SELECTED);

            return wheelComponentType;
        }

    }
    class createAxisComponents{
        protected Component createAxisComponentInstance(String Name)
        {
            Component axisComponentInstance = new Component(Name, "axis_22_12cm_3954665");
            axisComponentInstance.setStateOfComponent(ComponentState.SELECTED);
            axisComponentInstance.setSupplier("Muster_Firma");
            axisComponentInstance.setComponentKind(ProductKind.INSTANCE);

            return axisComponentInstance;
        }

    }
    class createBoardProduct{


        protected Product createBoardProductInstance()
        {
            Product boardOfSkateBoard = new Product("Skateboard_Board", "B_12894777_xd7");
            boardOfSkateBoard.setProductLifecycleState(ProductLifecycleState.DESIGN);
            //set description and internal ID
            boardOfSkateBoard.addProductDescription("de", "Board, welches aus Holzplatte hergestellt wird und an welchem die Achsen befestigt werden.");
            boardOfSkateBoard.addProductDescription("en", "Board which is manufactured and on which the Axis are mounted.");
            boardOfSkateBoard.setInternalProductIdentification(createInternalIDs(boardOfSkateBoard.getName(), boardOfSkateBoard.getTypeOfProduct()));

            //add CAD-Files (G-Code in ProductionPlanning)
            boardOfSkateBoard.setConstructionData(createDesignDataOfBoard());

            return boardOfSkateBoard;
        }
        private ConstructionData createDesignDataOfBoard()
        {
            ConstructionData boardDesignData = new ConstructionData();
            boardDesignData.addConstructionFile("Board.stp", "S:../..");
            boardDesignData.addSpecificationAttribute("Material", "Wood");
            boardDesignData.addSpecificationAttribute("Material_Thickness", "11 mm");
            boardDesignData.addSpecificationAttribute("CoatingMaterial", "Type 2");
            return boardDesignData;

        }

    }
}
