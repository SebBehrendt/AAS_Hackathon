package New_ProcessModel;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import sdm_aas.PushAAStoServer;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

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

    public static boolean checkProcedureContainsProcess(Process process, Process procedure) {
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

    public static List<ProcedureInstance> findValidProcedures(Process process, List<ProcedureInstance> procedures) {
        List<ProcedureInstance> validProcedures = new ArrayList<ProcedureInstance>();
        for (ProcedureInstance procedure : procedures) {
            if (checkProcedureContainsProcess(process, procedure)) {
                validProcedures.add(procedure);
            }
        }
        return validProcedures;
    }


    public static void main(String[] args) {

    }
}
