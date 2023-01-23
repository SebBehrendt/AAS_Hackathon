package SkateboardExample;

import Helper.AASHelper;
import Helper.ServerAASX;
import ResourceModel.*;
import ResourceModel_backup.InterfaceTypes;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;

import java.util.HashMap;
import java.util.Map;

public class createResourceCaesar {

    public static void createCaesarCell()
    {
        Machine caesarCell = new Machine (new Identification("Caesar_base_cell", "caesar_id", "Bosch", ResourceType.MACHINE),
                createCaesarHierarchy(), createResourceInterfacesCaesar() );

        AssetAdministrationShell shell =  caesarCell.createAAS();
        ServerAASX.uploadAAStoServer(shell, caesarCell.getSubmodels());
    }
    private static Hierarchy createCaesarHierarchy ()
    {
        Map<ResourceType,AssetAdministrationShell> caesarHierarchy = new HashMap<>();

        caesarHierarchy.put(ResourceType.COMPONENT, createScrewProvisionMo.createScrewProvisionMoAAS());
        caesarHierarchy.put(ResourceType.COMPONENT, createRobot.createRobotAAS());

        Hierarchy hierarchy = new Hierarchy(caesarHierarchy);

        return hierarchy;

    }
    private static ResourceInterfaces createResourceInterfacesCaesar()
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

    public static class createScrewProvisionMo {

        public static AssetAdministrationShell createScrewProvisionMoAAS()
        {
            MachineComponent screwProvisionMo = new MachineComponent(
                    new Identification("Mo_Screw_provision", "Mo_screq_provision_1", "Bosch", ResourceType.COMPONENT));

           return  screwProvisionMo.createAAS();
        }

    }
    static class createRobot {

        static AssetAdministrationShell createRobotAAS()
        {
            MachineComponent robot = new MachineComponent(
                    new Identification("Mo_Robot", "Mo_Robot_1", "Kuka", ResourceType.COMPONENT));
            robot.addHierarchy(createRobotToolHierarchy());

            return robot.createAAS();
        }
       static Hierarchy createRobotToolHierarchy()
        {
            MachineComponent robotScrewingTool = new MachineComponent(
                    new Identification("T_Robot_screwing_tool", "T_Robot_screwing_tool_1", "Demag", ResourceType.TOOL) );
            AssetAdministrationShell robotScrewingToolAAS = robotScrewingTool.createAAS();
            Map<ResourceType, AssetAdministrationShell> mapRobotHierarchy = new HashMap<>();
            mapRobotHierarchy.put(robotScrewingTool.getResourceType(), robotScrewingToolAAS);

           return new Hierarchy(mapRobotHierarchy);

        }

    }
}
