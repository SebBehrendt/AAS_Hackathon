package ProductModel;

import Helper.AASHelper;
import Helper.IAAS;
import Helper.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.Map;

public class DesignInformation implements ISubmodel {

    Map<String, String> designData = new HashMap<>(); //First step: no nested classes


    public DesignInformation() {}
    public DesignInformation (Map<String,String> designData)
    {
        this.designData = designData;
    }
    public DesignInformation(String key, String value)
    {
        this.designData.put(key, value);
    }
    public void addDesignData(String key, String value)
    {
        this.designData.put(key, value);
    }

    /**
     * AAS Environment
      */
  

    private static final String SM_DESIGN_DATA_ID_SHORT = "Design_Data";
    private static IdentifierType SM_DESIGN_ID_TYPE = IdentifierType.CUSTOM;
    private static final String SM_DESIGN_DATA_IDENTIFER = "Identifier_Design_Data_";

    @Override
    public Submodel createSubmodel(IAAS product) {
        Submodel submodelDesignData = new Submodel(SM_DESIGN_DATA_ID_SHORT, new Identifier(SM_DESIGN_ID_TYPE, SM_DESIGN_DATA_IDENTIFER ));
        for (Map.Entry<String,String> entry: this.designData.entrySet())
        {
            submodelDesignData.addSubmodelElement(new Property(AASHelper.nameToIdShort(entry.getKey()), entry.getValue()));
        }
        product.addSubmodelToList(submodelDesignData);
        return submodelDesignData;
    }



}
