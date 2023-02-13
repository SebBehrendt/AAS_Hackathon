package New_ProcessModel;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;

import Helper.AASHelper;

import java.util.List;

public class Procedure extends AbstractProcess {
    public String resourceURI;
    public String processURI;

    public static final String SM_REFERENCE_ID_SHORT = "ReferenceSubmodel";
    public static final String SME_PROCESS_REFERENCE_ID_SHORT = "ProcessReferenceSubmodelElement";
    public static final String SME_RESOURCE_REFERENCE_ID_SHORT = "ResourceReferenceSubmodelElement";

    public Procedure(String id, String description, List<ProcessAttribute> processAttributes, String resourceURI) {
        this.id = id;
        this.description = description;
        this.processAttributes = processAttributes;
        this.resourceURI = resourceURI;
    }

    public Procedure(String id, String description, List<ProcessAttribute> processAttributes, String resourceURI,
            String processURI) {
        this.id = id;
        this.description = description;
        this.processAttributes = processAttributes;
        this.resourceURI = resourceURI;
        this.processURI = processURI;
    }

    public void addProcess(String processURI) {
        this.processURI = processURI;
    }


    public void createProcessReferenceSubmodel(AssetAdministrationShell procedureAAS) {

        Submodel procedureReferencSubmodel = new Submodel(AASHelper.nameToIdShort(SM_REFERENCE_ID_SHORT), 
        new Identifier(IDENTIFIER_TYPE_AAS, AASHelper.nameToIdentifier(SM_REFERENCE_ID_SHORT)));

        Identifier resourceIdentifier = new Identifier(IdentifierType.IRI, this.resourceURI);
        Reference resourceReference = new Reference(resourceIdentifier, KeyElements.ASSETADMINISTRATIONSHELL, false);
        ReferenceElement resourceReferenceElement = new ReferenceElement(SME_RESOURCE_REFERENCE_ID_SHORT,
                resourceReference);
        procedureReferencSubmodel.addSubmodelElement(resourceReferenceElement);

        Identifier processIdentifier = new Identifier(IdentifierType.IRI, this.processURI);
        Reference processReference = new Reference(processIdentifier, KeyElements.ASSETADMINISTRATIONSHELL, false);
        ReferenceElement processReferenceElement = new ReferenceElement(SME_PROCESS_REFERENCE_ID_SHORT,
                processReference);
        procedureReferencSubmodel.addSubmodelElement(processReferenceElement);

        procedureAAS.addSubmodel(procedureReferencSubmodel);
        this.listOfSubmodels.add(procedureReferencSubmodel);
    }

    @Override
    public AssetAdministrationShell createAAS() {

        AssetAdministrationShell procedureAAS = new AssetAdministrationShell(
                AASHelper.nameToIdShort(AAS_PREFIX + this.id),
                new Identifier(IDENTIFIER_TYPE_AAS, AASHelper.nameToIdentifier(AAS_IDENTIFIER_PREFIX +
                        this.id + AAS_IDENTIFIER_SUFFIX)),
                this.createAsset());

        // TODO: Add Digital NamePlate

        // create description for product shell
        LangStrings descriptionProcedureAAS = new LangStrings("english", this.description);
        procedureAAS.setDescription(descriptionProcedureAAS);

        this.createProcessAttributesSubmodel(procedureAAS);
        this.createProcessReferenceSubmodel(procedureAAS);

        return procedureAAS;
    }

}
