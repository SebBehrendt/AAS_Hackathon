package OrderModel;

import Helper.AASHelper;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class TimeScheduling {
    private static final String EARLIEST_BEGIN_DATE = "earliest_begin_date";
    private static final String TARGET_DELIVERY_DATE = "target_delivery_date";
    private static final String LATEST_DELIVERY_DATE = "latest_delivery_date";
    private static final String SMC_TIME_SCHEDULING_ID_SHORT = "Time_Scheduling";

    Map<String,String> schedulingProperties = new HashMap<>();

    public TimeScheduling () {}
    public TimeScheduling (String earliestBeginDate, String targetDeliveryDate, String latestDeliveryDate)
    {
        this.schedulingProperties.put(AASHelper.nameToIdShort(EARLIEST_BEGIN_DATE), earliestBeginDate);
        this.schedulingProperties.put(AASHelper.nameToIdShort(TARGET_DELIVERY_DATE), targetDeliveryDate);
        this.schedulingProperties.put(AASHelper.nameToIdShort(LATEST_DELIVERY_DATE), latestDeliveryDate);

    }
    public void addSchedulingProperties (@NotNull Map<String,String> listOfProperties)
    {
        for (Map.Entry<String,String> property : listOfProperties.entrySet())
        {
            this.schedulingProperties.put(AASHelper.nameToIdShort(property.getKey()), property.getValue());
        }
    }
    public void addSchedulingProperties (String propertyKey, String propertyValue)
    {
        this.schedulingProperties.put(AASHelper.nameToIdShort(propertyKey), propertyValue);
    }

    /**
     * AAS ENVIRONMENT
     */
    protected SubmodelElementCollection createSMCTimeScheduling()
    {
        SubmodelElementCollection smcTimeScheduling = new SubmodelElementCollection(SMC_TIME_SCHEDULING_ID_SHORT);
        for (Map.Entry<String,String> property : this.schedulingProperties.entrySet())
        {
            smcTimeScheduling.addSubmodelElement(new Property(AASHelper.nameToIdShort(property.getKey()), property.getValue()));
        }
        return smcTimeScheduling;
    }
}
