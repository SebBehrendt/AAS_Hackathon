package New_ProcessModel;
import java.util.List;


public class ProcedureData extends BaseProcess {
    public String resourceURI;
    public String processURI;

    public ProcedureData(String id, String description, List<ProcessAttribute> processAttributes, String resourceURI) {
        this.id = id;
        this.description = description;
        this.processAttributes = processAttributes;
        this.resourceURI = resourceURI;
    }

    public ProcedureData(String id, String description, List<ProcessAttribute> processAttributes, String resourceURI, String processURI) {
        this.id = id;
        this.description = description;
        this.processAttributes = processAttributes;
        this.resourceURI = resourceURI;
        this.processURI = processURI;
    }

    public void addProcess(String processURI) {
        this.processURI = processURI;
    }
}
