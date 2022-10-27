package ProcessModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProcessHeader {
    /**
     * Process Types in general: transport, logistic, handling, assembling, productionprocesses, quality, ..
     */
    ProcessTypeCategory ProcessType;
    String topLevelProcess;
    String detailedProcess;
    public List<String> NormsOfProcessDescription = new ArrayList<>(); //V1: Via Database
    public HashMap<String, String> MultiLanguageNameOfProcess = new HashMap<>(); //Multi-Languages Possible!
    public ProcessHeader( )
    {
    }

    public void setProcessType(ProcessTypeCategory processType) {
        ProcessType = processType;
    }

    public void setDetailedProcess(String detailedProcess) {
        this.detailedProcess = detailedProcess;
    }
    public void addNormOfProcessDescription(String norm)
    {
        this.NormsOfProcessDescription.add(norm);
    }

    public void addMultiLanguageProperties(String language, String description)
    {
        MultiLanguageNameOfProcess.put(language, description);
    }

    public void addNormOfProcessDescription (List<String> listOfNorms)
    {
        for (String norm: listOfNorms)
        {
            NormsOfProcessDescription.add(norm);
        }
    }


    private void ExampleOfProcessDescription()
    {
        NormsOfProcessDescription.add("DIN 8589-3");
        MultiLanguageNameOfProcess.put("en", "milling");
        MultiLanguageNameOfProcess.put("ger", "fraesen");

    }
}
