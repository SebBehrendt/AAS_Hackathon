package SkateboardExample;

import ProcessModel.ProcessHeader;
import ProcessModel.ProcessTypeCategory;
import ResourceModel.Machine;
import ResourceModel.TechnicalData;
import ResourceModel.Tool;

public class createResources {

    class createMachineFiveX
    {
        protected static Machine createFiveX()
        {
            Machine fiveX = new Machine("FiveX");
            fiveX.setTechnicalData(createTechnicalDataFiveX());
            fiveX.addMachineCapability(createCapabilitiesFiveX());
            fiveX.addTool(createEndMill());

            return fiveX;
        }
        private static ProcessHeader createCapabilitiesFiveX() {
            ProcessHeader capa = new ProcessHeader();

            ProcessTypeCategory category = new ProcessTypeCategory(ProcessTypeCategory.TopLevelProcess.PRODUCTION_PROCESS);
            capa.setProcessType(category);
            capa.setDetailedProcess("MILLING");
            capa.addNormOfProcessDescription("DIN 8589-3");
            capa.addMultiLanguageProperties("de", "fraesen");
            capa.addMultiLanguageProperties("en", "milling");

            return capa;
        }

        private static TechnicalData createTechnicalDataFiveX()
        {
            TechnicalData technicalData5X = new TechnicalData();
            technicalData5X.addTechnicalParameters("max. Rotationspeed", "15000 rpm");
            //Bauraum

            return technicalData5X;
        }
        private static void addToolsToFiveX (Machine fiveX)
        {

        }
        private static Tool createEndMill()
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
