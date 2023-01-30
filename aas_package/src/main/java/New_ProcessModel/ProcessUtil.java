package New_ProcessModel;

import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import java.util.List;

public class ProcessUtil {
    public static void addProcessAttributesToSubmodel(ISubmodel submodel, List<ProcessAttribute> processAttributes) {
        // TODO: Type of process attribute is still missing
        for (ProcessAttribute processAttribute : processAttributes) {
            SubmodelElementCollection processAttributesCollection = new SubmodelElementCollection(
                    processAttribute.description.replaceAll("\\s+", ""));
            Property semanticsProperty = new Property("semantics", processAttribute.semantics.toString());
            processAttributesCollection.addSubmodelElement(semanticsProperty);

            Property descriptionProperty = new Property("description", processAttribute.description);
            processAttributesCollection.addSubmodelElement(descriptionProperty);

            String value;
            if (processAttribute.stringAttributeValue != null) {
                value = processAttribute.stringAttributeValue.toString();
            } else if (processAttribute.numericAttributeValue != null) {
                value = processAttribute.numericAttributeValue.toString();
            } else {
                value = processAttribute.dimensionalAttributeValue.toString();
            }
            Property processAttributeProperty = new Property("attribute_value", value);
            processAttributesCollection.addSubmodelElement(processAttributeProperty);
            
            Property processAttributeType = new Property("attribute_type", processAttribute.attributeType.toString());
            processAttributesCollection.addSubmodelElement(processAttributeType);

            submodel.addSubmodelElement(processAttributesCollection);
        }
    }

    public static void addProcessModelsToSubmodel(ISubmodel processModelsSubmodel,
            ProcessData processInstance) {

        // Method adds all attributes of each ProcessModel to Submodel of
        // processModelSubmodel and that to processModelsSubmodel

        for (ProcessModel processModel : processInstance.processModels) {

            SubmodelElementCollection processModelCollection = new SubmodelElementCollection(
                    processModel.description.replaceAll("\\s+", ""));

            Property descriptionProperty = new Property("description", processModel.description);
            processModelCollection.addSubmodelElement(descriptionProperty);

            Property typeProperty = new Property("Type Property", processModel.processModelType.name());
            processModelCollection.addSubmodelElement(typeProperty);

            Property nodesProperty = new Property("Nodes Property", processModel.nodes.toString());
            processModelCollection.addSubmodelElement(nodesProperty);

            // SMEC
            Property edgesProperty = new Property("Edges Property", processModel.edges.toString());
            processModelCollection.addSubmodelElement(edgesProperty);

            processModelCollection.addSubmodelElement(processModelCollection);
        }
    }
}
