package New_ProcessModel;
import java.util.List;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;


import Helper.AASHelper;


public class Process extends AbstractProcess {
    List<ProcessModel> processModels;

    public static final String SM_PROCESS_MODEL_ID_SHORT = "ProcessModelSubmodel";
    public static final String SME_PROCESS_MODEL_DESCRIPTION = "ProcessModelDescription";
    public static final String SME_PROCESS_MODEL_TYPE = "ProcessModelType";
    public static final String SME_PROCESS_MODEL_NODES = "ProcessModelNodes";
    public static final String SME_PROCESS_MODEL_EDGES = "ProcessModelEdges";


    public Process(String id, String description, List<ProcessAttribute> processAttributes, List<ProcessModel> processModels) {

        this.id = id;
        this.description = description;
        this.processAttributes = processAttributes;
        this.processModels = processModels;
    }

    public void createProcessModelsToSubmodel(Submodel processModelsSubmodel) {

        // Method adds all attributes of each ProcessModel to Submodel of
        // processModelSubmodel and that to processModelsSubmodel

        for (ProcessModel processModel : this.processModels) {

            SubmodelElementCollection processModelCollection = new SubmodelElementCollection(
                    processModel.description.replaceAll("\\s+", ""));

            Property descriptionProperty = new Property(AASHelper.nameToIdShort(SME_PROCESS_MODEL_DESCRIPTION), processModel.description);
            processModelCollection.addSubmodelElement(descriptionProperty);

            Property typeProperty = new Property(AASHelper.nameToIdShort(SME_PROCESS_MODEL_TYPE), processModel.processModelType.toString());
            processModelCollection.addSubmodelElement(typeProperty);
            

            // TODO: Represent Nodes as ReferenceElements not as strings 
            Property nodesProperty = new Property(AASHelper.nameToIdShort(SME_PROCESS_MODEL_NODES), processModel.nodes.toString());
            processModelCollection.addSubmodelElement(nodesProperty);

            // TODO: use SMEC here for edges
            // TODO: Represent Edges as ReferenceElements not as strings 
            Property edgesProperty = new Property(AASHelper.nameToIdShort(SME_PROCESS_MODEL_EDGES), processModel.edges.toString());
            processModelCollection.addSubmodelElement(edgesProperty);

            processModelsSubmodel.addSubmodelElement(processModelCollection);
        }
    }

    public void createProcessModelSubmodel(AssetAdministrationShell procedureAAS) {

        Submodel processModelsSubmodel = new Submodel(AASHelper.nameToIdShort(SM_PROCESS_MODEL_ID_SHORT), 
        new Identifier(IDENTIFIER_TYPE_AAS, AASHelper.nameToIdentifier(SM_PROCESS_MODEL_ID_SHORT)));

        //Submodel ProcessModels with description, type, edges and nodes
        Property descriptionProperty = new Property("description", this.description);
        processModelsSubmodel.addSubmodelElement(descriptionProperty);

        this.createProcessModelsToSubmodel(processModelsSubmodel);
        this.listOfSubmodels.add(processModelsSubmodel);
    }

    @Override
    public AssetAdministrationShell createAAS() {

        AssetAdministrationShell processAAS = new AssetAdministrationShell(
            AASHelper.nameToIdShort(AAS_PREFIX + this.id),
            new Identifier(IDENTIFIER_TYPE_AAS, AASHelper.nameToIdentifier(AAS_IDENTIFIER_PREFIX +
                    this.id + AAS_IDENTIFIER_SUFFIX)),
                    this.createAsset());

        // TODO: Add Digital NamePlate

        LangStrings descriptionProcessAAS = new LangStrings("english", description);
        processAAS.setDescription(descriptionProcessAAS);
        
        this.createProcessAttributesSubmodel(processAAS);
        this.createProcessModelSubmodel(processAAS);

        return processAAS;
    }
}