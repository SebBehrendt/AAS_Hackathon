package New_ProcessModel;

import java.util.List;

enum AttributeType {
    MATCHING,
    MINIMUM,
    MAXIMUM
}

public class ProcessAttribute {
    public List<String> semantics;
    public String description;
    public String stringAttributeValue;
    public Double numericAttributeValue;
    public List<Object> dimensionalAttributeValue;
    public AttributeType attributeType;

    public ProcessAttribute(List<String> semantics, String description, String value) {
        this.semantics = semantics;
        this.description = description;
        this.stringAttributeValue = value;

        this.attributeType = AttributeType.MATCHING;
    }

    public ProcessAttribute(List<String> semantics, String description, Double value, String type) {
        this.semantics = semantics;
        this.description = description;
        this.numericAttributeValue = value;
        if (type == "Minimum") {
            this.attributeType = AttributeType.MINIMUM;
        } else {
            this.attributeType = AttributeType.MAXIMUM;
        }

    }

    public ProcessAttribute(List<String> semantics, String description, List<Object> value, String type) {
        this.semantics = semantics;
        this.description = description;
        this.dimensionalAttributeValue = value;
        if (type == "Minimum") {
            this.attributeType = AttributeType.MINIMUM;
        } else if (type == "Maximum") {
            this.attributeType = AttributeType.MAXIMUM;
        } else {
            this.attributeType = AttributeType.MATCHING;
        }
    }

}
