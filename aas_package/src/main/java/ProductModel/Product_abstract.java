package ProductModel;

import AAS_Framework.AAS_abstract;
import Helper.AASHelper;
import AAS_Framework.IAAS;
import AAS_Framework.ISubmodel;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;

import java.util.List;

public abstract class Product_abstract extends AAS_abstract {

    DigitalNameplate digitalNameplate;
    String productIdentification;

    public Product_abstract(DigitalNameplate digitalNameplate)
    {
        this.digitalNameplate = digitalNameplate;
        listOfSubmodelClasses.add(digitalNameplate);
    }
    public Product_abstract(String productId)
    {
        this.productIdentification = productId;
    }
    public Product_abstract(String productId, DigitalNameplate digitalNameplate)
    {
        this.productIdentification = productId;
        this.digitalNameplate = digitalNameplate;
        listOfSubmodelClasses.add(digitalNameplate);
    }
    public String getIdentification()
    {
        return this.productIdentification;
    }

    public String createAASIdentifier(){return AAS_IDENTIFIER_PREFIX + this.productIdentification;}

    private static final String AAS_IDENTIFIER_PREFIX = "AAS_";

}


