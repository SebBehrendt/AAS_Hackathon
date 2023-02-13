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
    List<AbstractProcess> nodes = new ArrayList<>();
    List<List<AbstractProcess>> edges = new ArrayList<>();

    public void add_node(AbstractProcess process){
        if (!this.nodes.contains(process)){
            this.nodes.add(process);
        }
    }

    public void add_edge(AbstractProcess origin, AbstractProcess target){
        List<AbstractProcess> edge = new ArrayList<>();
        edge.add(origin);
        edge.add(target);
        if (!this.edges.contains(edge)){
            this.edges.add(edge);
        }
    }

    public void connectToStart(AbstractProcess process){
        List<AbstractProcess> edge = new ArrayList<>();
        edge.add(null);
        edge.add(process);
        if (!this.edges.contains(edge)){
            this.edges.add(edge);
        }
    }

    public void connectToEnd(AbstractProcess process){
        List<AbstractProcess> edge = new ArrayList<>();
        edge.add(process);
        edge.add(null);
        if (!this.edges.contains(edge)){
            this.edges.add(edge);
        }
    }

}

