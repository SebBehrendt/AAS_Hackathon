package New_ProcessModel;
import java.util.List;
import java.util.ArrayList;

public class MatchProcessProcedure {

    public static boolean checkMatchingStringList(List<String> processSemantics, List<String> procedureSemantics) {
        for (String semantic : processSemantics) {
            if (!procedureSemantics.contains(semantic)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkMatchingObjectList(List<Object> processValue, List<Object> procedureValue) {
        for (Object semantic : processValue) {
            if (!procedureValue.contains(semantic)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkMinimumObjectList(List<Object> processValue, List<Object> procedureValue) {
        if (processValue.size() != procedureValue.size()) {
            return false;
        }
        for (int i = 0; i < processValue.size(); i++) {
            if ((Double) processValue.get(i) > (Double) procedureValue.get(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkMaximumObjectList(List<Object> processValue, List<Object> procedureValue) {
        if (processValue.size() != procedureValue.size()) {
            return false;
        }
        for (int i = 0; i < processValue.size(); i++) {
            if ((Double) processValue.get(i) < (Double) procedureValue.get(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkProcedureContainsProcess(BaseProcess process, BaseProcess procedure) {
        for (ProcessAttribute processAttribute : process.processAttributes) {
            for (ProcessAttribute procedureAttribute : procedure.processAttributes) {
                if (!checkMatchingStringList(processAttribute.semantics, procedureAttribute.semantics)) {
                    return false;
                }
                if (processAttribute.attributeType == AttributeType.MATCHING) {
                    if (processAttribute.stringAttributeValue != null) {
                        return processAttribute.stringAttributeValue == procedureAttribute.stringAttributeValue;
                    }
                    return checkMatchingObjectList(processAttribute.dimensionalAttributeValue,
                            procedureAttribute.dimensionalAttributeValue);

                } else if (processAttribute.attributeType == AttributeType.MINIMUM) {
                    if (processAttribute.numericAttributeValue != null) {
                        return processAttribute.numericAttributeValue <= procedureAttribute.numericAttributeValue;
                    }
                    return checkMinimumObjectList(processAttribute.dimensionalAttributeValue,
                            procedureAttribute.dimensionalAttributeValue);
                } else if (processAttribute.attributeType == AttributeType.MAXIMUM) {
                    if (processAttribute.numericAttributeValue != null) {
                        return processAttribute.numericAttributeValue >= procedureAttribute.numericAttributeValue;
                    }
                    return checkMaximumObjectList(processAttribute.dimensionalAttributeValue,
                            procedureAttribute.dimensionalAttributeValue);
                }
            }
        }

        return false;
    }

    public static List<ProcedureData> findValidProcedures(BaseProcess process, List<ProcedureData> procedures) {
        List<ProcedureData> validProcedures = new ArrayList<ProcedureData>();
        for (ProcedureData procedure : procedures) {
            if (checkProcedureContainsProcess(process, procedure)) {
                validProcedures.add(procedure);
            }
        }
        return validProcedures;
    }


    public static void main(String[] args) {

    }
}
