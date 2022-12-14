package ProductModel_Backup;

public enum ComponentState {
    PLANNED (0, "planned"), //Types
    SELECTED(1, "selected"), //Instances
    ORDERED(2, "ordered"),
    DELIVERED(3, "delivered"),
    AVAILABLE(4, "available"),
    FINAL_STATE(5, "final_state"), //built in
    UNKNOWN(100, "unknown");

    int key;
    String componentState;
     ComponentState(int i, String state)
    {
        this.key = i;
        this.componentState = state;
    }
    public String getState()
    {
        return this.componentState;
    }
}
