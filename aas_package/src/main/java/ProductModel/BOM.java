package ProductModel;

import Helper.AASHelper;
import Helper.IAAS;
import Helper.ISubmodel;
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
    private static final String SUB_PRODUCT = "Sub_Product_";

    @Override
    public Submodel createSubmodel(IAAS product) {
        Submodel submodelSubcomponentsBOM = new Submodel("BOM", new Identifier(IdentifierType.CUSTOM, "BOM_Identifier"));
        for (Map.Entry<String, AssetAdministrationShell> entry : this.listSubComponents.entrySet())
        {
            //new smc and ref-Element to AAS of subComponent
            SubmodelElementCollection smcSubComponent = new SubmodelElementCollection(AASHelper.nameToIdShort(SUB_PRODUCT +entry.getKey()));
            ReferenceElement refElSubComponent = new ReferenceElement(AASHelper.nameToIdShort(entry.getKey()), new Reference((Identifier)entry.getValue().getIdentification(),
                    KeyElements.ASSETADMINISTRATIONSHELL, false));
            smcSubComponent.addSubmodelElement(refElSubComponent);
            submodelSubcomponentsBOM.addSubmodelElement(smcSubComponent);
        }
        product.addSubmodelToList(submodelSubcomponentsBOM);
        return submodelSubcomponentsBOM;
    }
}
