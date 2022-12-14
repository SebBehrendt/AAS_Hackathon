package ResourceModel;

public enum ResourceType {
    MACHINE ("Machine"),
    LOCATION ("Location"),
    SUBSYSTEM ("Subsystem"),
    COMPONENT ("Component"),
    TOOL ("Tool"),
    PRODUCTION_SITE ("ProductionType"),
    PRODUCTION_NETWORK("ProductionNetwork"),
    OTHER ("Other");

    String name;

    ResourceType (String resourceTypeName)
    {
        this.name = resourceTypeName;
    }
    public String getResourceTypeName()
    {
        return this.name;
    }
    public ResourceType getResourceType()
    {
        return this;
    }
}
