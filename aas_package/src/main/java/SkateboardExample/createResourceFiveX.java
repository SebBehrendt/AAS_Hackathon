package SkateboardExample;

import Helper.ServerAASX;
import ResourceModel.*;
import ResourceModel.InterfaceTypes;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;

import java.util.HashMap;
import java.util.Map;

public class createResourceFiveX {

    public static void createFiveX()
    {
        Machine fiveX = new Machine (new Identification("FiveX", "stuttgarter_Maschinenfabrik_FiveX", "ISW", ResourceType.MACHINE),
                createFiveXHierarchy(), createResourceInterfacesFiveX() );

        AssetAdministrationShell shell =  fiveX.createAAS();
        ServerAASX.uploadAAStoServer(shell, fiveX.getSubmodels());
    }
    private static Hierarchy createFiveXHierarchy ()
    {
        Map<ResourceType,AssetAdministrationShell> fiveXHierarchy = new HashMap<>();

        fiveXHierarchy.put(ResourceType.TOOL, createTools.createMillingToolAAS());

        Hierarchy hierarchy = new Hierarchy(fiveXHierarchy);

        return hierarchy;

    }
    private static ResourceInterfaces createResourceInterfacesFiveX()
    {
        ResourceInterface mainPowerSupply = new ResourceInterface(InterfaceTypes.Electrical,createAttributesElectricalInterface() );
        ResourceInterfaces interfacesCaesar = new ResourceInterfaces(mainPowerSupply);

        return interfacesCaesar;
    }
    private static Map<String,String> createAttributesElectricalInterface()
    {
        Map<String,String> attributes = new HashMap<>();
        attributes.put("nominal_voltage", "380 V");
        attributes.put("nominal_power", "10000 W");

        return attributes;
    }

    public static class createTools {

        public static AssetAdministrationShell createMillingToolAAS()
        {
            MachineComponent millingTool = new MachineComponent(
                    new Identification("End_mill_D12", "End_mill_D12_id230089", "MAS", ResourceType.TOOL));

            return  millingTool.createAAS();
        }

    }
}
