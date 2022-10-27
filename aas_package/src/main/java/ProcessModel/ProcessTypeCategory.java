package ProcessModel;


import java.util.Arrays;

/**
 * ProcessTypeCategory is used to describe the process in the desired level of detail.
 * First, the TopLevelProcess must be chosen.
 *
 */
public class ProcessTypeCategory {

    public String ProcessTypeKey;
    TopLevelProcess topLevelProcess;
   public  IProcessTypes detailedProcessType;

    /** Constructor for ProcessTypeCategory for descriptions
     * @param typeTopLevelProcess:
     * 1: Production Process, 2: Quality Assurance, 3: Maintenance, 4: Handling, 0: Custom / Unknown
     * --> Will set TopLevelProcess according to chosen Type.
     */
   public ProcessTypeCategory(int typeTopLevelProcess)
   {//TBD
       //
       this.topLevelProcess = TopLevelProcess.setProcessTypeKey(typeTopLevelProcess);
       setCategory();
       }

    public ProcessTypeCategory(TopLevelProcess toplevelprocess)
    {
        this.topLevelProcess = toplevelprocess;
        setCategory();
    }
    private void setCategory()
    {
        switch (topLevelProcess.ProcessTypeKey) {
            case 1: // Production Process
            {this.detailedProcessType = new ProcessTypeProductionProcess(); break;}

            case 2: // Quality Assurance
            {this.detailedProcessType = new ProcessTypeQualityProcess(); break;}

            case 3: //Maintenance
            {
                //TODO Complete
            }
            case 4:
            {

            }
            default: throw new RuntimeException("Test");

        }
    }
    public void setDetailedProcessType(IProcessTypes processType)
    {
        detailedProcessType = processType;
    }
    static String getProcessTypeKey()
    {

        return new String ("");
    }

    void setProcessTypeKey()
    {
        String keyTopLevelProcess = topLevelProcess.ProcessKeyToString();
        String SecondLevelProcess = detailedProcessType.getProcessKey();
        this.ProcessTypeKey = (keyTopLevelProcess +"_"+SecondLevelProcess);
    }


    /**
     * Enum for choosing Top Level process. Depending on the selection made, the lower level processes are able to be chosen.
     */
    public static enum TopLevelProcess{

        PRODUCTION_PROCESS ("Production Process",1), //DIN 8580
        QUALITY_ASSURANCE("Quality Assurance Process", 4), //DIN EN ISO 9000:2015
        MAINTENANCE ("Maintenance Process",3), //DIN EN 13306 & DIN 31051
        HANDLING_PROCESS ("Handling Process",2), //VDI 2860
        MACHINE_SETUP_PROCESS("Machine set up process", 5),
        CUSTOM ("Custom Process",0); //Other

         final String ProcessTypeName;
         final int ProcessTypeKey;

        TopLevelProcess(String nameOfProcessType, int keyOfProcess)
        {
            this.ProcessTypeName = nameOfProcessType;
            this.ProcessTypeKey = keyOfProcess;
        }
        public String getProcessTypeName()
        {
            return this.ProcessTypeName;
        }
        public static TopLevelProcess setProcessTypeKey (final int i)
        {
            return Arrays.stream(TopLevelProcess.values()).filter(e -> e.getKeyOfProcess() == i).findFirst().orElse(CUSTOM);

        }
        private int getKeyOfProcess () { return this.ProcessTypeKey; }
        String ProcessKeyToString ()
        {
            return String.valueOf(this.ProcessTypeKey);
        }
    }

    /**
     * If Production Process was chosen as Top Level Process, the detailed Process will be a production process.
     */
    public static class ProcessTypeProductionProcess implements IProcessTypes {

        public ProcessTypeProductionProcessEnum processTypeProductionEnum;

        public  ProcessTypeProductionProcess()
        {

        }

        public IProcessTypes setProcessTypeProductionEnum(ProcessTypeProductionProcessEnum processTypeProductionEnum) {
            this.processTypeProductionEnum = processTypeProductionEnum;
            return this;
        }

        public ProcessTypeProductionProcessEnum getProcessTypeProductionEnum()
        {
            return this.processTypeProductionEnum;
        }

        public String getProcessKey()
        {
            return this.processTypeProductionEnum.ProcessKey;
        }

        @Override
        public String getProcessName() {
            return processTypeProductionEnum.getProcessName();
        }

        public static enum ProcessTypeProductionProcessEnum {

            NO_FURTHER_INFORMATION ("0", "no further info"),
            CUSTOM ("0-0", "custom"),

            FORMING("1", "forming"),
            ADDITIVE_MANUFACTURING("1-1", "additive manufacturing"),
            CASTING("1-2", "casting"),
            VACUUM_CASTING("1-3", "vacuum casting"),
            SINTERING("1-4", "sintering"),
            EXTRUSION("1-4", "extrusion"),

            REFORMING("2", "reforming"),
            BENDING("2-1", "bending"),
            DEEP_DRAWING("2-2", "deep drawing"),
            METAL_SHEET_ROLLING("2-3", "metal sheet rolling"),

            SEPARATING("3", "separating"),
            SAWING("3-1", "sawing"),
            MILLING("3-2", "milling"),
            DRILLING("3-3", "drilling"),
            PUNCHING("3-4", "punching"),
            GRINDING ("3-5", "grinding"),

            JOINING("4", "joining"),
            PUTTING_TOGETHER ("4-0", "putting together"),
            WELDING("4-1", "welding"),
            SOLDERING("4-2", "soldering"),
            GLUING("4-3", "gluing");

         private String ProcessKey;
         private String ProcessName;
        ProcessTypeProductionProcessEnum(String processKey, String name)
        {
            this.ProcessKey = processKey;
            this.ProcessName = name;
        }
        String getProcessName()
        {
            return this.ProcessName;
        }
    }
    }

    /**
     * If Quality Assurance was chosen as top Level Process
     */

    public static class ProcessTypeQualityProcess implements IProcessTypes {

        public ProcessTypeQualityProcessEnum processTypeQualityEnum;

        public  ProcessTypeQualityProcess()
        {

        }
        public String getProcessKey()
        {
            return this.processTypeQualityEnum.ProcessKey;
        }

        @Override
        // TODO
        public String getProcessName() {
            return null;
        }

        public void setProcessTypeQualityEnum(ProcessTypeQualityProcessEnum processTypeProductionEnum) {
            this.processTypeQualityEnum = processTypeProductionEnum;
        }

        public ProcessTypeQualityProcessEnum getProcessTypeProductionEnum()
        {
            return this.processTypeQualityEnum;
        }

        public static enum ProcessTypeQualityProcessEnum {

            NO_FURTHER_INFORMATION ("0"),
            CUSTOM ("0-0"),

            QA_1("1"),
            QA_1_1("1"),


            QA_2("2"),
            QA_2_2("2-1");


            private String ProcessKey;
            ProcessTypeQualityProcessEnum(String processKey)
            {
                this.ProcessKey = processKey;
            }
        }
    }


}
