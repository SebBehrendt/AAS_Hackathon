package ProductModel;

import Helper.AASHelper;
import Helper.IAAS;
import Helper.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;

public class DigitalNameplate implements ISubmodel {
    //DUMMY CLASS UNTIL NOW!
    String ProductIdentifier;


    public DigitalNameplate(String productIdentifier)
    {
        this.ProductIdentifier = productIdentifier;
    }
    private static final String SUBMODEL_DIGITAL_NAMEPLATE_ID_SHORT = "DigitalNameplate";
    private static final String SUBMODEL_DIGITAL_NAMEPLATE_IDENTIFIER_PREFIX = "DigitalNameplate_";

    @Override
    public Submodel createSubmodel(IAAS product) {
        Submodel submodelDigitalNameplate = new Submodel(SUBMODEL_DIGITAL_NAMEPLATE_ID_SHORT,
                new Identifier(IdentifierType.CUSTOM, AASHelper.nameToIdShort(SUBMODEL_DIGITAL_NAMEPLATE_IDENTIFIER_PREFIX)));


        product.addSubmodelToList(submodelDigitalNameplate);
        return submodelDigitalNameplate;
    }


}
