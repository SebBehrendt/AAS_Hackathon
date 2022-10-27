package ProcessModel;

public enum ProcessState {
    PLANNED(1),
    EXECUTING(2),
    FINISHED(3),
    FINISHED_AND_APPROVED(4);

    int stateNumber;
   ProcessState(int number)
    {
        stateNumber = number;
    }

    void setStateNumberUp()
    {
        this.stateNumber = this.stateNumber++;
    }
    void setStateNumberBack()
    {
        this.stateNumber = this.stateNumber--;
    }
}
