package ResourceModel_backup;

public enum ToolType {
    MILLINGTOOL ("milling_tool"),
    TURNINGTOOL("turning_tool");

    String type;

     ToolType(String toolType)
    {
        this.type = toolType;
    }
}
