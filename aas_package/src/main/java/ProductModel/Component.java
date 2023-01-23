package ProductModel;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;

//Zukaufteil
public class Component extends Product_abstract{
    BOM subComponentsBOM;

    public Component(DigitalNameplate digitalNameplate, BOM bom)
    {
        super(digitalNameplate);
        this.subComponentsBOM = bom;
        listOfSubmodelClasses.add(bom);
    }
    public Component(DigitalNameplate digitalNameplate)
    {
        super(digitalNameplate);
    }


    @Override
    public void addSubmodelToList(Submodel basyxSubmodel) {

    }



    @Override
    public Asset createAsset() {
        return null;
    }

    @Override
    public Asset createAsset(AssetKind kind) {
        return null;
    }


}
