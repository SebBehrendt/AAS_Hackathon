package New_ProcessModel;
import java.util.List;
import java.util.ArrayList;

enum ProcessModelType {
    SINGLE,
    SEQUENTIAL,
    GRAPH
}

enum ProcessBorder{
    START,
    END
}

public class ProcessModel{
    String id;
    String description;
    ProcessModelType processModelType;
    List<BaseProcess> nodes = new ArrayList<>();
    List<List<BaseProcess>> edges = new ArrayList<>();

    public void add_node(BaseProcess process){
        if (!this.nodes.contains(process)){
            this.nodes.add(process);
        }
    }

    public void add_edge(BaseProcess origin, BaseProcess target){
        List<BaseProcess> edge = new ArrayList<>();
        edge.add(origin);
        edge.add(target);
        if (!this.edges.contains(edge)){
            this.edges.add(edge);
        }
    }

    public void connectToStart(BaseProcess process){
        List<BaseProcess> edge = new ArrayList<>();
        edge.add(null);
        edge.add(process);
        if (!this.edges.contains(edge)){
            this.edges.add(edge);
        }
    }

    public void connectToEnd(BaseProcess process){
        List<BaseProcess> edge = new ArrayList<>();
        edge.add(process);
        edge.add(null);
        if (!this.edges.contains(edge)){
            this.edges.add(edge);
        }
    }

}

