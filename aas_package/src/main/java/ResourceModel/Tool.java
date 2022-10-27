package ResourceModel;

import ProcessModel.ProcessHeader;

import java.util.HashMap;
import java.util.Map;

public class Tool {
    String tool_id;
    double tool_diameter;
    Map<String,String> machinableMaterial;
    Map<String, ProcessHeader> executableProcesses = new HashMap<>();

    public Tool(String id) {this.tool_id = id;}

    public String getTool_id() {return tool_id;}

    public void setExecutableProcesses(Map<String, ProcessHeader> executableProcesses) {
        this.executableProcesses = executableProcesses;
    }
    public void addExecutableProcesse(String key , ProcessHeader value) {
        this.executableProcesses.put(key, value);
    }
}
