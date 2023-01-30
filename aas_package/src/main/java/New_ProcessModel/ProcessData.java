package New_ProcessModel;
import java.util.List;

public class ProcessData extends BaseProcess {
    List<ProcessModel> processModels;

    public ProcessData(String id, String description, List<ProcessAttribute> processAttributes, List<ProcessModel> processModels) {

        this.id = id;
        this.description = description;
        this.processAttributes = processAttributes;
        this.processModels = processModels;
    }
}