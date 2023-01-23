package ProductModel;

import Helper.AASHelper;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;

import java.util.ArrayList;
import java.util.List;

    public class PlannedProduction {
    List<AssetAdministrationShell> listOfPlannedProcedures;

    public PlannedProduction (List<AssetAdministrationShell> listProcesses) {
    this.listOfPlannedProcedures = listProcesses;
    }

    protected SubmodelElementCollection createSMCPlannedProduction() {
        SubmodelElementCollection smcPlannedProduction = new SubmodelElementCollection(SMC_PLANNED_PRODUCTION_ID_SHORT );
        for (AssetAdministrationShell plannedProcessAAS : this.listOfPlannedProcedures) {
            ReferenceElement refElementplannedProcess = new ReferenceElement(AASHelper.nameToIdShort(plannedProcessAAS.getIdShort()),
                    new Reference((Identifier) plannedProcessAAS.getIdentification(),
                            KeyElements.ASSETADMINISTRATIONSHELL, false));
            smcPlannedProduction.addSubmodelElement(refElementplannedProcess);
        }
        return smcPlannedProduction;
    }

    private static final String SMC_PLANNED_PRODUCTION_ID_SHORT = "SMC_planned_Production";
}
