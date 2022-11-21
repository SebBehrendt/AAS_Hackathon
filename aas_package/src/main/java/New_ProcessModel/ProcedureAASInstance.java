package New_ProcessModel;

import org.apache.commons.math3.ode.nonstiff.AdamsBashforthFieldIntegrator;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import sdm_aas.PushAAStoServer;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

enum AttributeType {
    MATCHING,
    MINIMUM,
    MAXIMUM
}

class ProcessAttribute {
    List<String> semantics;
    String description;
    String stringAttributeValue;
    Double numericAttributeValue;
    List<Object> dimensionalAttributeValue;
    AttributeType attributeType;

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

enum ProcessModelType {
    SINGLE,
    SEQUENTIAL,
    GRAPH
}

enum ProcessBorder{
    START,
    END
}

class ProcessModel{
    String id;
    String description;
    ProcessModelType type;
    List<Process> nodes;
    List<List<Process>> edges;

    public void add_node(Process process){
        if (!this.nodes.contains(process)){
            this.nodes.add(process);
        }
    }

    public void add_edge(Process origin, Process target){
        List<Process> edge = new ArrayList<>();
        edge.add(origin);
        edge.add(target);
        if (!this.edges.contains(edge)){
            this.edges.add(edge);
        }
    }

    public void connectToStart(Process process){
        List<Process> edge = new ArrayList<>();
        edge.add(null);
        edge.add(process);
        if (!this.edges.contains(edge)){
            this.edges.add(edge);
        }
    }

    public void connectToEnd(Process process){
        List<Process> edge = new ArrayList<>();
        edge.add(process);
        edge.add(null);
        if (!this.edges.contains(edge)){
            this.edges.add(edge);
        }
    }

}

class SingleProcessModel extends ProcessModel{
    ProcessModelType type = ProcessModelType.SINGLE;

    public SingleProcessModel(String id, String description, Process process) {
        this.id = id;
        this.description = description;
        add_node(process);
        connectToStart(process);
        connectToEnd(process);
    }
}

class SequentialProcessModel extends ProcessModel{
    ProcessModelType type = ProcessModelType.SEQUENTIAL;

    public SequentialProcessModel(String id, String description, List<Process> processes) {
        this.id = id;
        this.description = description;
        for (Process process : processes){
            add_node(process);
        }
        connectToStart(processes.get(0));
        connectToEnd(processes.get(processes.size()-1));
        for (int i = 0; i < processes.size()-1; i++){
            add_edge(processes.get(i), processes.get(i+1));
        }
    }
}

class GraphProcessModel extends ProcessModel{
    ProcessModelType type = ProcessModelType.GRAPH;

    public GraphProcessModel(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public void connectProcesses(Process origin, Process target) {
        add_node(origin);
        add_node(target);
        add_edge(origin, target);
    }
}

abstract class Process {
    // TODO: ID has to be added here already
    String id;
    String description;
    List<ProcessAttribute> processAttributes;
}

class ProcessInstance extends Process {
    List<ProcessModel> processModels;

    // TODO: process model has to be added in constructors and tested in main
    public ProcessInstance(List<ProcessAttribute> processAttributes) {
        this.processAttributes = processAttributes;
    }
}

class ProcedureInstance extends Process {
    String resourceURI;
    String processURI;

    public ProcedureInstance(List<ProcessAttribute> processAttributes, String resourceURI) {
        this.processAttributes = processAttributes;
        this.resourceURI = resourceURI;
    }

    public ProcedureInstance(List<ProcessAttribute> processAttributes, String resourceURI, String processURI) {
        this.processAttributes = processAttributes;
        this.resourceURI = resourceURI;
        this.processURI = processURI;
    }

    public void addProcess(String processURI) {
        this.processURI = processURI;
    }
}

public class ProcedureAASInstance {

    public static void createAASfromProcess(ProcessInstance processInstance){
        // TODO: implement method here
    }

    public static void addProcessAttributesToSubmodel(ISubmodel submodel, List<ProcessAttribute> processAttributes) {
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
            submodel.addSubmodelElement(processAttributesCollection);
        }
    }

    public static Map<AssetAdministrationShell, List<Submodel>> createAASfromProcedure(ProcedureInstance procedure,
            String idShort,
            String description) {
        Asset procedureAsset = new Asset(idShort, new ModelUrn(idShort), AssetKind.INSTANCE);
        AssetAdministrationShell procedureAAS = new AssetAdministrationShell(idShort + "AAS",
                new ModelUrn(idShort + "AAS"), procedureAsset);
        // create description for product shell
        LangStrings descriptionProcedureAAS = new LangStrings("english", description);
        procedureAAS.setDescription(descriptionProcedureAAS);

        Submodel processAttributesSubmodel = new Submodel(idShort + "ProcessAttributes",
                new ModelUrn(idShort + "Submodel"));
        addProcessAttributesToSubmodel(processAttributesSubmodel, procedure.processAttributes);
        procedureAAS.addSubmodel(processAttributesSubmodel);

        Submodel procedureReferencSubmodel = new Submodel(idShort + "References", new ModelUrn(idShort + "References"));
        Identifier resourceIdentifier = new Identifier(IdentifierType.IRI, procedure.resourceURI);
        Reference resourceReference = new Reference(resourceIdentifier, KeyElements.ASSETADMINISTRATIONSHELL, false);
        ReferenceElement resourceReferenceElement = new ReferenceElement("Resource_reference", resourceReference);
        procedureReferencSubmodel.addSubmodelElement(resourceReferenceElement);

        Identifier processIdentifier = new Identifier(IdentifierType.IRI, procedure.processURI);
        Reference processReference = new Reference(processIdentifier, KeyElements.ASSETADMINISTRATIONSHELL, false);
        ReferenceElement processReferenceElement = new ReferenceElement("Process_reference", processReference);
        procedureReferencSubmodel.addSubmodelElement(processReferenceElement);

        procedureAAS.addSubmodel(procedureReferencSubmodel);

        Map<AssetAdministrationShell, List<Submodel>> procedureAASMap = new HashMap<AssetAdministrationShell, List<Submodel>>();
        List<Submodel> submodels = new ArrayList<Submodel>();
        submodels.add(processAttributesSubmodel);
        submodels.add(procedureReferencSubmodel);
        procedureAASMap.put(procedureAAS, submodels);

        return procedureAASMap;
    }

    public static void main(String[] args) {
        List<String> millingSemantics = new ArrayList<String>();
        millingSemantics.add("Milling");

        List<String> millingTechnologySemantics = new ArrayList<String>(millingSemantics);
        millingTechnologySemantics.add("Technology");

        List<String> rotationSemantics = new ArrayList<String>(millingSemantics);
        rotationSemantics.add("Rotation speed");

        List<String> dimensionSemantics = new ArrayList<String>(millingSemantics);
        dimensionSemantics.add("Dimensions");

        ProcessAttribute requiredMillingTechnology = new ProcessAttribute(millingTechnologySemantics,
                "Milling technology", "3 Axes");
        ProcessAttribute requiredMillRotationSpeed = new ProcessAttribute(rotationSemantics,
                "Milling roation speed attribute in rpm", 30.0, "Minimum");
        ProcessAttribute requiredDimensions = new ProcessAttribute(dimensionSemantics,
                "Milling dimensions for x y z in mm", List.of(350.0, 50.0, 40.0), "Minimum");

        ProcessInstance millingProcess = new ProcessInstance(
                List.of(requiredMillingTechnology, requiredMillRotationSpeed, requiredDimensions));

        ProcessAttribute actualMillingTechnology3 = new ProcessAttribute(millingTechnologySemantics,
                "Milling technology", "3 Axes");
        ProcessAttribute actualMillingTechnology5 = new ProcessAttribute(millingTechnologySemantics,
                "Milling technology 2", "5 Axes");
        ProcessAttribute acutalMillRotationSpeed = new ProcessAttribute(rotationSemantics,
                "Milling roation speed attribute in rpm", 12000.0, "Minimum");
        ProcessAttribute actualDimensions = new ProcessAttribute(dimensionSemantics,
                "Milling dimensions for x y z in mm", List.of(600.0, 600.0, 150.0), "Minimum");

        String EXAMPLE_RESOURCE_URI = "http://193.196.37.23:4001/aasServer/shells/ResourceID/aas/";
        ProcedureInstance millingProcedure = new ProcedureInstance(
                List.of(actualMillingTechnology3, actualMillingTechnology5, acutalMillRotationSpeed, actualDimensions),
                EXAMPLE_RESOURCE_URI);

        ProcessAttribute actualMillingTechnology2 = new ProcessAttribute(millingTechnologySemantics,
                "Milling technology", "2 Axes");
        ProcessAttribute acutalMillRotationSpeed2 = new ProcessAttribute(rotationSemantics,
                "Milling roation speed attribute in rpm", 19000.0, "Minimum");
        ProcessAttribute actualDimensions2 = new ProcessAttribute(dimensionSemantics,
                "Milling dimensions for x y z in mm", List.of(1200.0, 1200.0, 150.0), "Minimum");

        String EXAMPLE_RESOURCE_URI2 = "http://193.196.37.23:4001/aasServer/shells/ResourceID2/aas/";
        ProcedureInstance millingProcedure2 = new ProcedureInstance(
                List.of(actualMillingTechnology2, acutalMillRotationSpeed2, actualDimensions2),
                EXAMPLE_RESOURCE_URI2);

        ProcessAttribute acutalMillRotationSpeed3 = new ProcessAttribute(rotationSemantics,
                "Milling roation speed attribute in rpm", 20000.0, "Minimum");
        ProcessAttribute actualDimensions3 = new ProcessAttribute(dimensionSemantics,
                "Milling dimensions for x y z in mm", List.of(15.0, 10.0, 15.0), "Minimum");

        String EXAMPLE_RESOURCE_URI3 = "http://193.196.37.23:4001/aasServer/shells/ResourceID3/aas/";
        ProcedureInstance millingProcedure3 = new ProcedureInstance(
                List.of(actualMillingTechnology3, acutalMillRotationSpeed3, actualDimensions3),
                EXAMPLE_RESOURCE_URI3);

        boolean validProcedure = MatchProcessProcedure.checkProcedureContainsProcess(millingProcess, millingProcedure);
        System.out.println("Procedure is valid for process: " + validProcedure);

        List<ProcedureInstance> allProcedures = List.of(millingProcedure, millingProcedure2, millingProcedure3);
        List<ProcedureInstance> possibleProcedure = MatchProcessProcedure.findValidProcedures(millingProcess, allProcedures);
        System.out.println("From " + allProcedures.size() + " are " + possibleProcedure.size() + " possible.");

        Map<AssetAdministrationShell, List<Submodel>> aas = createAASfromProcedure(millingProcedure, "MillingProcedure",
                "Milling procedure");
        String SERVER_URL = "http://193.196.37.23:4001/aasServer";
        String REGISTRY_URL = "http://193.196.37.23:4000/registry/api/v1/registry";

        PushAAStoServer.pushAAS(aas, SERVER_URL, REGISTRY_URL);

    }
}
