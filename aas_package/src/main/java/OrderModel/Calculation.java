package OrderModel;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;

//submodel Calculation
public class Calculation {
    private static final String CALCULATION_SM_ID_SHORT = "Calculation";
    private static final String CALCULATION_SM_IDENTIFIER = "Calculation_SM_Identifier";
    private static final  IdentifierType IDENTIFIER_TYPE = IdentifierType.CUSTOM;


    public Submodel createSubmodelCalculation ()
    {
        Submodel calculationSubmodel = new Submodel(CALCULATION_SM_ID_SHORT, new Identifier(IDENTIFIER_TYPE, CALCULATION_SM_IDENTIFIER));
        // Add submodelelements

        return calculationSubmodel;
    }
}
