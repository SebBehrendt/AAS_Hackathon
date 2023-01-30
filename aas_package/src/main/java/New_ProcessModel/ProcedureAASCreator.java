package New_ProcessModel;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
public class ProcedureAASCreator {

    public static Map<AssetAdministrationShell, List<Submodel>> createAASfromProcedure(ProcedureData procedure,
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
        ProcessUtil.addProcessAttributesToSubmodel(processAttributesSubmodel, procedure.processAttributes);
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

}
