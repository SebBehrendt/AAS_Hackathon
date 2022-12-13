package SkateboardExample;

import OrderModel.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class createOrder {

    protected static void createSkateboardOrder()
    {
        Order skateBoardOrder = new Order(ORDER_INSTANCE_NAME);
        // Add sub Components
        skateBoardOrder.setGeneralOrderInformation(createGeneralOrderInformation());
       // skateBoardOrder.setProductInstances(createProductInstances());


        skateBoardOrder.createAAS();
    }
    private static GeneralOrderInformation createGeneralOrderInformation()
    {
        GeneralOrderInformation generalOrderInfoSkateboard =
                new GeneralOrderInformation("0", createCustomerInfos(), createSchedulingInfos(), createOrderFiles(), createMlpDescriptions() );

        return generalOrderInfoSkateboard;
    }
    public static ProductInstances createProductInstances(List<ProductInstance> listInstances) //TODO --> get Instances in there!
    {
        ProductInstances instances = new ProductInstances(listInstances);
        return instances;

    }
    private static CustomerInformation createCustomerInfos()
    {
        CustomerInformation customerInfo = new CustomerInformation(COMPANY_NAME, CONTACT_NAME, CUSTOMER_IDENTIFICATION);
        customerInfo.setAddress(STREET_AND_NUMBER, ZIP_CODE, PLACE, COUNTRY);
        customerInfo.setContactInfo(PHONE_NUMBER_CONTACT, "adam.skilter@skateboars-manufacturing.com");

        return customerInfo;
    }
    private static TimeScheduling createSchedulingInfos()
    {
        TimeScheduling timeScheduling = new TimeScheduling("13.02.2023  00:00:00", "14.02.2023  23:59:59", "14.02.2023  23:59:59");
        timeScheduling.addSchedulingProperties("delivery_buffer", "4 days");

        return timeScheduling;
    }
    private static Map<String,String> createOrderFiles ()
    {
        Map<String,String> orderFiles = new HashMap<>();
        orderFiles.put("OrderFile", "Order_"+ORDER_INSTANCE_NAME+".pdf");

        return orderFiles;
    }
    private static Map<String,String> createMlpDescriptions()
    {
        Map<String,String> mlpDescription = new HashMap<>();
        mlpDescription.put(EN,"Order Insatnce for "+ORDER_INSTANCE_NAME );
        mlpDescription.put(GER, "Auftragsinstanz für "+ ORDER_INSTANCE_NAME);

        return mlpDescription;
    }
    private static ProductInstances createSkateboardInstances()
    {
      return null;
    }

    private static final String COMPANY_NAME = "Skateboard Manufacturing inc.";
    private static final String CONTACT_NAME = "Adam Skilter";
    private static final String CUSTOMER_IDENTIFICATION = "CustomerId:34599697_1";
    private static final String STREET_AND_NUMBER = "Kuestenstrasse 19";
    private static final String ZIP_CODE= "10115";
    private static final String PLACE = "Berlin";
    private static final String COUNTRY = "Germany";
    private static final String ORDER_INSTANCE_NAME = "skateboard_sdm4fzi_20221202_1323";
    private static final String EN = "en";
    private static final String GER = "de";
    private static final String ORDER_FILE = "OrderFile";
    private static final String PHONE_NUMBER_CONTACT = "+49 03031 78993-9";

}

// Order:

/*
Sub Components: Wheels, Axis,
 */