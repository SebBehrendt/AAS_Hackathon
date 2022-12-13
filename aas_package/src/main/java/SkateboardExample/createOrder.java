package SkateboardExample;

import OrderModel.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class createOrder {

    protected static void createSkateboardOrder()
    {
        Order skateBoardOrder = new Order("skateboard_sdm4fzi_20221202_1323");
        // Add sub Components
        skateBoardOrder.setGeneralOrderInformation(createGeneralOrderInformation());
       // skateBoardOrder.setProductInstances(createProductInstances());


        skateBoardOrder.createProductAAS();
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

        return customerInfo;
    }
    private static TimeScheduling createSchedulingInfos()
    {
        TimeScheduling timeScheduling = new TimeScheduling();
        //TODO
        return timeScheduling;
    }
    private static Map<String,String> createOrderFiles ()
    {
        Map<String,String> orderFiles = new HashMap<>();
        orderFiles.put("1", "2");
        // TODO
        return orderFiles;
    }
    private static Map<String,String> createMlpDescriptions()
    {
        Map<String,String> mlpDescription = new HashMap<>();
        //TODO
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
}

// Order:

/*
Sub Components: Wheels, Axis,
 */