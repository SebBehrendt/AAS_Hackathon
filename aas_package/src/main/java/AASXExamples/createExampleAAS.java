package AASXExamples;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

public class createExampleAAS {
    private static final String GLOBAL_ASSET_ID = "global_id_asset";
    private static final String ASSET_ID_SHORT = "name_of_asset";
    private static final String GLOBAL_AAS_ID = "global_id_aas";
    private static final String AAS_ID_SHORT = "name_of_aas";
    private static final String SM_ID_SHORT = "first_Submodel";
    private static final String SM_IDENTIFIER = "first_submodel_id";
    private static final String SMC_ID_SHORT = "smc";
    private static final String PROPERTY_ID_SHORT= "name_of_property";
    private static final String VALUE = "this is the property value";
    private static final String PROPERTY_2_ID_SHORT= "name_of_second_property";
    private static final String VALUE_2 = "value 2";

    /**
     * Executable method (es example) to create an AAS with Submodels, SMCs and Properties.
     * Since Properties are one form of DataElements, using Files, References, Ranges, etc. works the same way.
     */
    public static void main(String[] args) {

        //create Asset and AAS
        //Reminder: according to IDTA the type of the AAS (type/instance) is defined by type or instance
        // declaration of the asset which is referenced of the asset which is referenced. Default: INSTANCE

        Identifier assetIdentifier = new Identifier(IdentifierType.CUSTOM, GLOBAL_ASSET_ID);
        Asset myAsset = new Asset(ASSET_ID_SHORT, assetIdentifier, AssetKind.INSTANCE);

        Identifier aasIdentifier = new Identifier(IdentifierType.CUSTOM, GLOBAL_AAS_ID);
        AssetAdministrationShell myAAS = new AssetAdministrationShell(AAS_ID_SHORT, aasIdentifier, myAsset);

        //create submodel and submodelelements
        Submodel myFirstSubmodel = new Submodel(SM_ID_SHORT, new Identifier(IdentifierType.CUSTOM, SM_IDENTIFIER));

        SubmodelElementCollection smcOfSubmodel = new SubmodelElementCollection(SMC_ID_SHORT);
        Property propertyOfFirstSubmodel = new Property(PROPERTY_ID_SHORT, VALUE);
        Property secondPropertyOfFirstSubmodel = new Property(PROPERTY_2_ID_SHORT, VALUE_2);

        //add both propertys to AAS
        smcOfSubmodel.addSubmodelElement(secondPropertyOfFirstSubmodel);
        myFirstSubmodel.addSubmodelElement(smcOfSubmodel);

        myFirstSubmodel.addSubmodelElement(propertyOfFirstSubmodel);

        myAAS.addSubmodel(myFirstSubmodel);

        //Now the AAS has one submodel with two properties and one SMC.
        // One property is in the SMC, the other one directly in the submodel, the other one in the SMC.

    }


}
