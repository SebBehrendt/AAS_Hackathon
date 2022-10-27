package ResourceModel;

import java.util.HashMap;
import java.util.Map;

public class TechnicalData {

    Map<String, String> technicalParameters = new HashMap<>();
    public TechnicalData(){

    }
    public void addTechnicalParameters(String key, String value)
    {
        this.technicalParameters.put(key, value);
    }

    public void setTechnicalParameters(Map<String, String> technicalParameters) {
        this.technicalParameters = technicalParameters;
    }

    public Map<String, String> getTechnicalParameters() {
        return technicalParameters;
    }
}
