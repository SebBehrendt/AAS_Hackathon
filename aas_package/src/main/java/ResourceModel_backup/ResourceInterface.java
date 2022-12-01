package ResourceModel_backup;

import java.util.HashMap;
import java.util.Map;

public class ResourceInterface {
    InterfaceTypes typeOfInterface;
    Map<String,String> listOfAttributes = new HashMap<>();

    public ResourceInterface(InterfaceTypes type, Map<String,String> listOfAttributes)
    {
        this.typeOfInterface = type;
        //this.listOfAttributes = listOfAttributes;

        switch(this.typeOfInterface){
            case fluid -> this.listOfAttributes = createFluidInterfaceAttributes();
         //   case Electrical -> this.listOfAttributes =
    }
    }
    public void addAttribute (String key, String value)
    {
        this.listOfAttributes.put(key,value);
    }

    public Map<String,String> getListOfAttributes()
    {
        return this.listOfAttributes;
    }
    private static Map<String,String> createFluidInterfaceAttributes()
    {
        Map<String,String> listOfFluidAttributes = new HashMap<>();
        listOfFluidAttributes.put("Key", null);

        return listOfFluidAttributes;
    }


}
