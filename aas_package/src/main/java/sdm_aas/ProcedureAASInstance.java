package sdm_aas;

import java.util.List;

enum AttributeType{
    MATCHING,
    MINIMUM,
    MAXIMUM
}


class ProcessAttribute{
    String ID;
    String description;
    String stringAttributeValue;
    double numericAttributeValue;
    AttributeType attributeType;

    public ProcessAttribute(String ID, String description, String value){
        this.description = description;
        this.stringAttributeValue = value;
        this.attributeType = AttributeType.MATCHING;
    }

    public ProcessAttribute(String ID, String description, double value, String type){
        this.description = description;
        this.numericAttributeValue = value;
        if (type == "Minimum") {
            this.attributeType = AttributeType.MINIMUM;
        }
        else {
            this.attributeType = AttributeType.MAXIMUM;
        }
        
    }
}

abstract class Process{
    List<ProcessAttribute> processAttributes;    
}

class ProcedureInstance extends Process{
    String resourceURI;
    String processURI;


    public ProcedureInstance(List<ProcessAttribute> processAttributes, String resourceURI){
        this.processAttributes = processAttributes;
        this.resourceURI = resourceURI;
    }

    public ProcedureInstance(List<ProcessAttribute> processAttributes, String resourceURI, String processURI){
        this.processAttributes = processAttributes;
        this.resourceURI = resourceURI;
        this.processURI = processURI;
    }

    public void addProcess(String processURI){
        this.processURI = processURI;
    }
}


// public void checkProcedureContainsProcess(Process process, ProcedureInstance procedure){
    
// }



public class ProcedureAASInstance {

    public static void main(String[] args){
        ProcessAttribute millingSpeed = new ProcessAttribute("Milling30", "Milling roation speed attribute in rpm", 30.0, "Minimum");

        System.out.println(millingSpeed.description);
        System.out.println(millingSpeed.numericAttributeValue);
        System.out.println(millingSpeed.numericAttributeValue);
    }
}
