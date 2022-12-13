package ResourceModel;

import ResourceModel_backup.InterfaceTypes;

import java.util.HashMap;
import java.util.Map;

public class ResourceInterface {
    InterfaceTypes typeOfInterface;
    Map<String,String> listOfAttributes;

    public ResourceInterface(InterfaceTypes type, Map<String,String> listOfAttributes)
    {
        this.typeOfInterface = type;
        this.listOfAttributes = listOfAttributes;

    }
    public void addAttribute (String key, String value)
    {
        this.listOfAttributes.put(key,value);
    }

    public Map<String,String> getListOfAttributes()
    {
        return this.listOfAttributes;
    }



    //FIXME: Add sometime later
    private static Map<String,String> createFluidInterfaceAttributes()
    {
        Map<String,String> listOfFluidAttributes = new HashMap<>();
        listOfFluidAttributes.put("Key", null);

        return listOfFluidAttributes;
    }
    public String getInterfaceType()
    {
        return this.typeOfInterface.getInterfaceName();
    }






/*
    public ResourceInterface(InterfaceTypes type, Map<String,String> listOfAttributes)
    {
        this.typeOfInterface = type;
        //this.listOfAttributes = listOfAttributes;

        switch(this.typeOfInterface){
            case fluid -> this.listOfAttributes = createFluidInterfaceAttributes();
            //   case Electrical -> this.listOfAttributes =
        }


 */

}
