package ResourceModel;

import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;

public class MachineComponent extends Resource{
    public MachineComponent(Identification resourceIdent) {
        super(resourceIdent);
    }

    @Override
    public void addSubmodelToList(Submodel basyxSubmodel) {

    }

    @Override
    public String getIdentification() {
        return null;
    }

    @Override
    Asset createAsset() {
        return null;
    }
}
