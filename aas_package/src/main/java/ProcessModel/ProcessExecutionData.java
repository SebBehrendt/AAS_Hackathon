package ProcessModel;

import ResourceModel.IResource;
import org.javatuples.Pair;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProcessExecutionData {

    /**
     * ProcessExecutionData describes the Process Instance. It includes the mapping of resources and statetransitions of the
     * product. Goal is to describe the transformation of the workpiece.
     * TODO include recursive mapping
     * TODO Map to Process Type
     */
    ProcessState processState; //
    // Goal: describes Transformation of the workpiece through
    public List<File> listStateTransitionDescriptionFiles  = new ArrayList<>();



    IResource ResourceForProcess; //-> Machine plus Tools
    String ResourceAASReference;

    /**
    Operational Data
     */
    String TimeSeriesDataName;
    List<List<Pair<String,String>>> TimeSeriesData = new ArrayList<>();
    //Quality Processes?


    public Pair<String,String> NewTimeSeriesData (String value){
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date());
        return new Pair<String,String> (timeStamp, value);
        //For single Values
    }
    public Pair<String,String> NewTimeSeriesData (String timestamp, String value){
        return new Pair<String,String> (timestamp, value);
        //For TimeSeries Values
    }

}
