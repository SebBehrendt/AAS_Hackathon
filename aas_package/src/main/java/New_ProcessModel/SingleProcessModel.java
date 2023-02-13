package New_ProcessModel;

public class SingleProcessModel extends ProcessModel{

    public SingleProcessModel(String id, String description, AbstractProcess process) {
        this.id = id;
        this.description = description;
        this.processModelType = ProcessModelType.SINGLE;
        add_node(process);
        connectToStart(process);
        connectToEnd(process);
    }
}