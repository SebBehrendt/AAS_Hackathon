package New_ProcessModel;
import java.util.List;

public class ElementaryProcess extends AbstractProcess {

    public ElementaryProcess(String id, String description, List<ProcessAttribute> processAttributes) {
        this.id = id;
        this.description = description;
        this.processAttributes = processAttributes;
    }
}