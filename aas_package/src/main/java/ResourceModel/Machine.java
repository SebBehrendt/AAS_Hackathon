package ResourceModel;

import ProcessModel.ProcessHeader;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Machine {
    String Name;
    String id;
    String Manufacturer;
    List<ProcessHeader> machineCapabilities; //toplevel
    Map<String, Tool> toolsOfMachine = new HashMap<>();
    TechnicalData technicalData;

    public Machine (String name, String id)
    {
        this.Name = name;
        this.id= id;
    }
    public Machine (String name)
    {
        this.Name = name;
    }

    public void setTechnicalData(TechnicalData technicalData) {
        this.technicalData = technicalData;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }
    public void addTool(Tool newTool)
    {
        this.toolsOfMachine.put(newTool.getTool_id(), newTool);
    }
    public void addMachineCapability (ProcessHeader header)
    {
        this.machineCapabilities.add(header);
    }

    public AssetAdministrationShell createAAS ()
    {
        AssetAdministrationShell machineAAS = new AssetAdministrationShell();

        return machineAAS;
    }
}
