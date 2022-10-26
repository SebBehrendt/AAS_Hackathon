package sdm_aas;import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

/**
 * creates submodel with legal guide lines
 * 
 * @author marieke
 *
 */
public class LegalGuidelines {

	public static String importregulations;
	public static String duties;
	public static String emission_laws;

	public static Submodel createLegalGuidelinesSubmodel() {
		Submodel legalGuidelinesSubmodel = new Submodel("LegalGuidelines", new ModelUrn("Guidelines"));
		LegalGuidelines.importregulations = "Guidelines ...";
		Property importReg = new Property("ImportRegulations", LegalGuidelines.importregulations);
		legalGuidelinesSubmodel.addSubmodelElement(importReg);

		// expandable by more properties 

		return legalGuidelinesSubmodel;
	}
}
