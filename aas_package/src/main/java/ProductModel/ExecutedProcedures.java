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

public class ExecutedProcedures {
    List<AssetAdministrationShell> listExecutedProcesses = new ArrayList<>();

    public ExecutedProcedures (List<AssetAdministrationShell> listOfProcesses)
    {
        this.listExecutedProcesses = listOfProcesses;
    }
    public ExecutedProcedures (AssetAdministrationShell processAAS)
    {
        this.listExecutedProcesses.add(processAAS);
    }
    public void addProcess(AssetAdministrationShell processAAS)
    {
        this.listExecutedProcesses.add(processAAS);
    }

    protected SubmodelElementCollection createSMCExecutedProduction()
    {
        SubmodelElementCollection smcExecutedProcesses = new SubmodelElementCollection(AASHelper.nameToIdShort(SMC_EXECUTED_PROCESSES_ID_SHORT));
        for (AssetAdministrationShell executedProcessAAS : this.listExecutedProcesses)
        {
            ReferenceElement refElementExecutedProcess = new ReferenceElement(AASHelper.nameToIdShort(executedProcessAAS.getIdShort()),
                    new Reference((Identifier) executedProcessAAS.getIdentification(),
                            KeyElements.ASSETADMINISTRATIONSHELL, false));
            smcExecutedProcesses.addSubmodelElement(refElementExecutedProcess);
        }

        return smcExecutedProcesses;
    }

    private static final String SMC_EXECUTED_PROCESSES_ID_SHORT = "Executed_Processes";
}


