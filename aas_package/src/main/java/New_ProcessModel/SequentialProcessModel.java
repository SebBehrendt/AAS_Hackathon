package New_ProcessModel;
import java.util.List;


public class SequentialProcessModel extends ProcessModel{

    public SequentialProcessModel(String id, String description, List<BaseProcess> processes) {
        this.id = id;
        this.description = description;
        this.processModelType = ProcessModelType.SEQUENTIAL;

        for (BaseProcess process : processes){
            add_node(process);
        }
        connectToStart(processes.get(0));
        connectToEnd(processes.get(processes.size()-1));
        for (int i = 0; i < processes.size()-1; i++){
            add_edge(processes.get(i), processes.get(i+1));
        }
    }
}