package New_ProcessModel;

public class GraphProcessModel extends ProcessModel{
    

    public GraphProcessModel(String id, String description) {
        this.id = id;
        this.description = description;
        this.processModelType = ProcessModelType.GRAPH;
    }

    public void connectProcesses(AbstractProcess origin, AbstractProcess target) {
        add_node(origin);
        add_node(target);
        add_edge(origin, target);
    }
}