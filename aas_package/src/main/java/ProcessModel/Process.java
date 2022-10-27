package ProcessModel;

import java.util.LinkedList;
import java.util.List;

public class Process {

    protected String processName;
    ProcessHeader ProcessDescriptionHeader; //Header includes generic information about process Type and standardized description
    ProcessInformation processInformation; //plan, execution,..


    List<Process> listOfSubProcesses = new LinkedList<>();

    /**
     * Default Constructor when initializing a new process
     */
    public Process() {}
    public Process (String nameOfProcess)
    {
        this.processName = nameOfProcess;
    }

    public void setProcessName(String name)
    {this.processName = name; }
    public void setProcessDescriptionHeader(ProcessHeader header)
    {
        ProcessDescriptionHeader = header;
    }

    public ProcessHeader getProcessDescriptionHeader()
    {
        return this.ProcessDescriptionHeader;
    }


}
