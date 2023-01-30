package New_ProcessModel;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class ProcessAASCreator {

    public static Map<AssetAdministrationShell, List<Submodel>> createAASfromProcess(ProcessData processInstance, 
    String idShort, String description) {

        Asset processAsset = new Asset(idShort, new ModelUrn(idShort), AssetKind.INSTANCE);
        AssetAdministrationShell processAAS = new AssetAdministrationShell(idShort + "AAS",
                new ModelUrn(idShort + "AAS"), processAsset);
       
        LangStrings descriptionProcessAAS = new LangStrings("english", description);
        processAAS.setDescription(descriptionProcessAAS);
        
        // Submodel ProcessAttributes
        // TODO: ProcessAttributes über Konstruktor
        Submodel processAttributesSubmodel = new Submodel(idShort + "ProcessAttributes",
        new ModelUrn(idShort + "Submodel"));
        ProcessUtil.addProcessAttributesToSubmodel(processAttributesSubmodel, processInstance.processAttributes);
        processAAS.addSubmodel(processAttributesSubmodel);

        //Submodel ProcessModels with description, type, edges and nodes
        Submodel processModelsSubmodel = new Submodel(idShort + "ProcessModels", new ModelUrn(idShort + "Submodel"));
        Property descriptionProperty = new Property("description", processInstance.description);
        ProcessUtil.addProcessModelsToSubmodel(processModelsSubmodel, processInstance);
        processModelsSubmodel.addSubmodelElement(descriptionProperty);
            // TODO Modellierung über SMEC
        Map<AssetAdministrationShell, List<Submodel>> processAASMap = new HashMap<AssetAdministrationShell, List<Submodel>>();
        List<Submodel> submodels = new ArrayList<Submodel>();

        submodels.add(processModelsSubmodel);
        submodels.add(processAttributesSubmodel);
        processAASMap.put(processAAS, submodels);

        return processAASMap;
    }
    
}
