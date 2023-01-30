package New_ProcessModel;
import java.util.List;

public class ElementaryProcessData extends BaseProcess {

    public ElementaryProcessData(String id, String description, List<ProcessAttribute> processAttributes) {
        this.id = id;
        this.description = description;
        this.processAttributes = processAttributes;
    }
}