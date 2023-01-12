package OrderModel;

import AAS_Framework.IAAS;
import AAS_Framework.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;

import java.util.HashMap;
import java.util.Map;

//submodel Calculation
public class Calculation implements ISubmodel {
    String calculationUnit;
    Map<String,Number> costCalculations = new HashMap< >(); //key: reason value: value in Unit

    public Calculation(){}
    public Calculation(String calculationUnit, Map<String,Number> costCalculations)
    {
        this.calculationUnit = calculationUnit;
        this.costCalculations = costCalculations;
    }
    public Calculation(String calculationUnit)
    {
        this.calculationUnit = calculationUnit;
    }
    public void addCostPosition (String key, Number value){
        this.costCalculations.put(key,value);
    }
    public void addCostPosition (String key, int value){
        this.costCalculations.put(key,Double.valueOf(value));
    }

    private Number calculateSum ()
    {
        Number sum = 0.0;
        for (Map.Entry<String,Number> entry: this.costCalculations.entrySet())
        {
           //TODO
        }
        return sum;
    }
    @Override
    public Submodel createSubmodel(IAAS abstractShellObject) {
        Submodel calculationSubmodel = new Submodel(CALCULATION_SM_ID_SHORT, new Identifier(IDENTIFIER_TYPE, CALCULATION_SM_IDENTIFIER));


        return calculationSubmodel;
    }

    private static final String CALCULATION_SM_ID_SHORT = "Calculation";
    private static final String CALCULATION_SM_IDENTIFIER = "Calculation_SM_Identifier";
    private static final  IdentifierType IDENTIFIER_TYPE = IdentifierType.CUSTOM;
    private static final String CALCULATION_UNIT_ID_SHORT = "Calculation_Unit";
}
