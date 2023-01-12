package ProductModel;

import Helper.AASHelper;
import AAS_Framework.IAAS;
import AAS_Framework.ISubmodel;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BOM implements ISubmodel {
    Map<String,AssetAdministrationShell> listSubComponents = new HashMap<>(); //

    public BOM(List<AssetAdministrationShell> listSubComponentShells)
    {
        for (AssetAdministrationShell shell : listSubComponentShells) {
            this.listSubComponents.put(AASHelper.nameToIdShort(shell.getIdShort()), shell);
        }
    }
    @Override
    public Submodel createSubmodel(IAAS abstractShellObject) {
        Submodel submodelSubcomponentsBOM = new Submodel(AASHelper.nameToIdShort(SUBMODEL_BOM_ID_SHORT),
                new Identifier(IdentifierType.CUSTOM, SUBMODEL_BOM_IDENTIFIER));

        for (Map.Entry<String, AssetAdministrationShell> entry : this.listSubComponents.entrySet())
        {
            //new smc and ref-Element to AAS of subComponent
            SubmodelElementCollection smcSubComponent = new SubmodelElementCollection(AASHelper.nameToIdShort(SUB_PRODUCT +entry.getKey()));
            ReferenceElement refElSubComponent = new ReferenceElement(AASHelper.nameToIdShort(entry.getKey()),
                    new Reference((Identifier)entry.getValue().getIdentification(),
                    KeyElements.ASSETADMINISTRATIONSHELL, false));

            smcSubComponent.addSubmodelElement(refElSubComponent);
            submodelSubcomponentsBOM.addSubmodelElement(smcSubComponent);
        }
        abstractShellObject.addSubmodelToList(submodelSubcomponentsBOM);

        return submodelSubcomponentsBOM;
    }
    private static final String SUBMODEL_BOM_ID_SHORT = "BOM";
    private static final String SUBMODEL_BOM_IDENTIFIER= "BOM_Identifier";
    private static final String SUB_PRODUCT = "Sub_Product_";
}
