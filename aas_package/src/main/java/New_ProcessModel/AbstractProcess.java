package New_ProcessModel;
import java.util.List;


import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;

import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import java.util.ArrayList;

import Helper.AASHelper;



public abstract class AbstractProcess {
    public String id;
    public String description;
    public List<ProcessAttribute> processAttributes;
    List<Submodel> listOfSubmodels = new ArrayList<>();

    public static final IdentifierType IDENTIFIER_TYPE_AAS = IdentifierType.CUSTOM;
    public static final String AAS_PREFIX = "AAS_";
    public static final String AAS_IDENTIFIER_PREFIX = "AAS_";
    public static final String AAS_IDENTIFIER_SUFFIX = "_Identifier";
    public static final AssetKind DEFAULT_ASSET_KIND = AssetKind.INSTANCE;
    public static final String ASSET_IDENTIFIER_PREFIX = "asset_";


    public static final String SM_PROCESS_ATTRIBUTE_ID_SHORT = "ProcessAttributes";

    public static final String SME_PROCESS_ATTRIBUTE_SEMANTICS = "ProcessAttributeSemantics";
    public static final String SME_PROCESS_ATTRIBUTE_DESCRIPTION = "ProcessAttributeSemantics";
    public static final String SME_PROCESS_ATTRIBUTE_VALUE = "ProcessAttributeValue";
    public static final String SME_PROCESS_ATTRIBUTE_TYPE = "ProcessAttributeType";


    public Asset createAsset(){
        return new Asset(AASHelper.nameToIdShort(ASSET_IDENTIFIER_PREFIX + this.id),
        new Identifier(IDENTIFIER_TYPE_AAS, AASHelper.nameToIdentifier(ASSET_IDENTIFIER_PREFIX + this.id)),
        AssetKind.INSTANCE);
    }


    public List<Submodel> getSubmodels() {
        return listOfSubmodels;
    }

    public void addProcessAttributesToSubmodel(Submodel submodel) {
        for (ProcessAttribute processAttribute : this.processAttributes) {
            SubmodelElementCollection processAttributesCollection = new SubmodelElementCollection(
                    processAttribute.description.replaceAll("\\s+", ""));
            Property semanticsProperty = new Property(AASHelper.nameToIdShort(SME_PROCESS_ATTRIBUTE_SEMANTICS), processAttribute.semantics.toString());
            processAttributesCollection.addSubmodelElement(semanticsProperty);

            Property descriptionProperty = new Property(AASHelper.nameToIdShort(AASHelper.nameToIdShort(SME_PROCESS_ATTRIBUTE_DESCRIPTION)), processAttribute.description);
            processAttributesCollection.addSubmodelElement(descriptionProperty);

            String value;
            if (processAttribute.stringAttributeValue != null) {
                value = processAttribute.stringAttributeValue.toString();
            } else if (processAttribute.numericAttributeValue != null) {
                value = processAttribute.numericAttributeValue.toString();
            } else {
                value = processAttribute.dimensionalAttributeValue.toString();
            }
            Property processAttributeProperty = new Property(AASHelper.nameToIdShort(AASHelper.nameToIdShort(SME_PROCESS_ATTRIBUTE_VALUE)), value);
            processAttributesCollection.addSubmodelElement(processAttributeProperty);
            
            Property processAttributeType = new Property(AASHelper.nameToIdShort(SME_PROCESS_ATTRIBUTE_TYPE), processAttribute.attributeType.toString());
            processAttributesCollection.addSubmodelElement(processAttributeType);

            submodel.addSubmodelElement(processAttributesCollection);
        }
    }

    public void createProcessAttributesSubmodel(AssetAdministrationShell procedureAAS) {
        Submodel processAttributesSubmodel = new Submodel(AASHelper.nameToIdShort(SM_PROCESS_ATTRIBUTE_ID_SHORT),
                new Identifier(IdentifierType.CUSTOM, AASHelper.nameToIdentifier(SM_PROCESS_ATTRIBUTE_ID_SHORT)));
        this.addProcessAttributesToSubmodel(processAttributesSubmodel);
        procedureAAS.addSubmodel(processAttributesSubmodel);
        this.listOfSubmodels.add(processAttributesSubmodel);
    }

    public AssetAdministrationShell createAAS() {
        AssetAdministrationShell processAAS = new AssetAdministrationShell(AASHelper.nameToIdShort(AAS_PREFIX + this.id),
                new Identifier(IDENTIFIER_TYPE_AAS, AASHelper.nameToIdentifier(AAS_IDENTIFIER_PREFIX + this.id + AAS_IDENTIFIER_SUFFIX)),
                this.createAsset());

        LangStrings descriptionProcessAAS = new LangStrings("english", description);
        processAAS.setDescription(descriptionProcessAAS);

        this.createProcessAttributesSubmodel(processAAS);
        return processAAS;
    }
}

