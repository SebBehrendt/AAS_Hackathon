package SkateboardExample;

import ProcessModel.ProcessHeader;
import ProcessModel.ProcessTypeCategory;
import ResourceModel_backup.Machine;
import ResourceModel_backup.TechnicalData;
import ResourceModel_backup.Tool;

public class createResources {

    class createMachineFiveX
    {
        protected Machine createFiveX()
        {
            Machine fiveX = new Machine("FiveX");
            fiveX.setTechnicalData(createTechnicalDataFiveX());
            fiveX.addMachineCapability(createCapabilitiesFiveX());
            fiveX.addTool(createEndMill());

            return fiveX;
        }
        private ProcessHeader createCapabilitiesFiveX() {
            ProcessHeader capa = new ProcessHeader();

            ProcessTypeCategory category = new ProcessTypeCategory(ProcessTypeCategory.TopLevelProcess.PRODUCTION_PROCESS);
            capa.setProcessType(category);
            capa.setDetailedProcess("MILLING");
            capa.addNormOfProcessDescription("DIN 8589-3");
            capa.addMultiLanguageProperties("de", "fraesen");
            capa.addMultiLanguageProperties("en", "milling");

            return capa;
        }

        private TechnicalData createTechnicalDataFiveX()
        {
            TechnicalData technicalData5X = new TechnicalData();
            technicalData5X.addTechnicalParameters("max. Rotationspeed", "15000 rpm");
            //Bauraum

            return technicalData5X;
        }
        private void addToolsToFiveX (Machine fiveX)
        {

        }
        private Tool createEndMill()
        {
            Tool endMill = new Tool("Endmill_7mm_HSC");

            ProcessHeader capa = new ProcessHeader();

            ProcessTypeCategory category = new ProcessTypeCategory(ProcessTypeCategory.TopLevelProcess.PRODUCTION_PROCESS);
            capa.setProcessType(category);
            capa.setDetailedProcess("END_MILLING");
            capa.addNormOfProcessDescription("DIN 8589-3");
            capa.addNormOfProcessDescription("DIN 8589");
            capa.addMultiLanguageProperties("de", "Schaftfraesen");
            capa.addMultiLanguageProperties("en", "endmilling");

            endMill.addExecutableProcesse("Schaftfraesen", capa);

            return endMill;
        }
    }
}
