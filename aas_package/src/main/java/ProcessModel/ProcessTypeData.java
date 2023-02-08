package ProcessModel;


import ResourceModel.Machine;
import org.javatuples.Tuple;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class ProcessTypeData is used to describe the state transition of the workpiece before the process is executed.
 * It is NOT the header with the "Template"-Type data but with specific information about the resource and product combination with the
 * knowledge about necessary state transformation.
 * It includes information about ONE state-transformation. It does not directly bundle the information of several transformations.
 * */
public class ProcessTypeData {

    // One StateTransition Description for several processes and several State transitions for one process must be possible-
    // How do i represent one process consisting of several processes? Recursive description.
    List<ProcessTypeData> ListProcessTypes = new ArrayList<>(); //compound processes -> recursive description

    List<Tuple> listOfProcessDescriptionParameters = new ArrayList<>(); // Test as option to ListProcessTypeParameter

    //Reference to Productmodel and State!
    Object ProductInputState; //Cast to AAS / File / Reference later
    Object ProductOutputState;
    Machine Resource;
    List<File> ListStateTransitionDescription = new ArrayList<>(); // List<? extends Object> ? --> eg G-Code, CAD, ..
    // List<? extends IDataType > ListStateTransitionDescription = new ArrayList<>(); //
    List<String> listDUMMYStateTransitionDescriptions = new ArrayList<>();

    public double executingTime; //get from Machine

    public void addListOfProcessDescriptionParameters(List<String> dummyList)
    {

    }
    public ProcessTypeData(){

    }
    public void setMachine (Machine machineForProcess)
    {
        this.Resource = machineForProcess;
    }
    public void addDUMMYStateTransitions(String gCodeExample)
    {
        listDUMMYStateTransitionDescriptions.add(gCodeExample);
    }

    /*
    public double getExecutionTime(IResource resource, Process model)
    {
        //executionTime of Resource in this processcategory
        return 0;
    }


     */
    void addProcessTypeDataToList () //Dummy!

    {
        ListProcessTypes.add(new ProcessTypeData());
    }

    /*
    public List<Triplet<String, String, IDataType>> getProcessTypeParameters (){
        return ListProcessTypeParameter;
    }


     */

    private void addStateTransitionFiles(List<String> listOfFilePaths) throws FileNotFoundException {
        for ( String cadFilePath : listOfFilePaths)
        {
            File stateTransitionFile = new File(cadFilePath);
            if(!stateTransitionFile.exists())
            {
                throw new FileNotFoundException("Filepath does not exist in "+ stateTransitionFile.toString());
            }
            else
            {
                ListStateTransitionDescription.add(stateTransitionFile);
            }
        }
    }
    private void addStateTransitionFiles(String filePath) throws FileNotFoundException
    {
            File stateTransitionFile = new File(filePath);
            if(!stateTransitionFile.exists())
            {
                throw new FileNotFoundException("Filepath does not exist in "+ stateTransitionFile.toString());
            }
            else
            {
                ListStateTransitionDescription.add(stateTransitionFile);
            }

    }





}
