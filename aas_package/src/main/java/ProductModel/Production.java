package ProductModel;

import Helper.IAAS;
import Helper.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;

public class Production implements ISubmodel {

    PlannedProduction plannedProduction = null; //Production plan
    ExecutedProcedures executedProduction = null;

    public Production(PlannedProduction planned, ExecutedProcedures executed) {
        this.plannedProduction = planned;
        this.executedProduction = executed;
    }

    public Production(PlannedProduction planned) {
        this.plannedProduction = planned;
    }

    public void addExecutedProcesses(ExecutedProcedures executed) {
        this.executedProduction = executed;
    }

    @Override
    public Submodel createSubmodel(IAAS product) {
        Submodel submodelProduction = new Submodel();
        if (this.plannedProduction != null)
        {
            submodelProduction.addSubmodelElement(this.plannedProduction.createSMCPlannedProduction());
        }
        if (this.executedProduction != null)
        {
            submodelProduction.addSubmodelElement(this.executedProduction.createSMCExecutedProduction());
        }

        product.addSubmodelToList(submodelProduction);
        return submodelProduction;
    }
}






