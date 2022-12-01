package ProductModel_Backup;

public enum ProductionState {
    PRODUCTPLANNING ( "planning"), //1st step in product genesis
    DESIGN ( "Product_design"), //Construction
    PRODUCTION_PLANNING("Production_planning"),
    PRODUCTION ("Production"),
    DELIVERY ( "Delivery"),
    DELIVERED ( "Delivered"),
    USAGE("Usage"),
    EOL ( "End_of_life"),
    REUSAGE ( "Reusage"),

    SELECTED( "selected"), //Instances
    ORDERED( "ordered"),
    AVAILABLE("available"),
    FINAL_STATE( "final_state"),

    //built in
    UNKNOWN( "unknown");
    String value;
    ProductionState (String value)
    {
        this.value = value;
    }
    String getValue()
    {
        return this.value;
    }
}
