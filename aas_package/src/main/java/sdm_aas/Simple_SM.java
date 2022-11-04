package sdm_aas;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueType;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProviderHelper;

/**
 * Dummy Submodel with two dynamic values. A configurable temperature value that randomly
 * returns values in a specific range. And a random boolean value.
 * 
 * @author espen
 *
 */
public class Simple_SM extends Submodel {

	public Simple_SM() {
		setIdShort("Simple_SM");
		setIdentification(IdentifierType.CUSTOM, "Simple_Submodel");
		setDummyProperty();
	}

	private void setDummyProperty() {
		Property dummyProperty = new Property();
		dummyProperty.setIdShort("dummy");
		dummyProperty.set(VABLambdaProviderHelper.createSimple(() -> {
			return Math.random() * (20 - 10) + 10;
		}, null), ValueType.Boolean);
		addSubmodelElement(dummyProperty);
	}
}