package ResourceModel;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of technical Data of IDTA SMT Technical Data
 * Structure of SM: https://github.com/admin-shell-io/submodel-templates/blob/main/published/Technical_Data/1/1/SMT_Technical_Data_V11.pdf
 * SMC GeneralInformation
 * SMC ProductClassifications
 * SMC TechnicalProperties
 * SMC FurtherInformation
 */

public class TechnicalData {
    private static final String IRI_DESCRIPTION_SMC_GENERALINFO = "https://admin-shell.io/ZVEI/TechnicalData/GeneralInformation/1/1";
    Map<String, String> technicalParameters = new HashMap<>(); //

    public TechnicalData(){}
    public void addTechnicalParameters(String key, String value)
    {
        this.technicalParameters.put(key, value);
    }

    public void setTechnicalParameters(Map<String, String> technicalParameters) {
        this.technicalParameters = technicalParameters;
    }
    public Map<String, String> getTechnicalParameters() {
        return technicalParameters;
    }

    /**
     * Methods of AAS Environment
     */
    protected Submodel createSubmodelTechnicalData()
    {
        Submodel technicalDataSubmodel = new Submodel();


        return technicalDataSubmodel;
    }
    private SubmodelElementCollection createSmcGeneralInformation ()
    {
        SubmodelElementCollection generalInfoSmc = new SubmodelElementCollection();
        generalInfoSmc.setSemanticId(new Reference(new Identifier(IdentifierType.IRI, IRI_DESCRIPTION_SMC_GENERALINFO ),
                KeyElements.CONCEPTDESCRIPTION, true));



        return generalInfoSmc;
    }
}
