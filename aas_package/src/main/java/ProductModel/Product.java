package ProductModel;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;

public class Product extends Product_abstract{
    DesignInformation designInformation;
    BOM subComponentsBOM;
    Production productionInformation = null;
    public Product(String productId)
    {
        super(productId);
    }

    public Product (String productId, DigitalNameplate digitalNameplate)
    {
        super(productId, digitalNameplate);
    }
    public Product (String productId, DigitalNameplate digitalNameplate, BOM bom, Production production)
    {
        super(productId, digitalNameplate);

        this.subComponentsBOM = bom;
        listOfSubmodelClasses.add(bom);

        this.productionInformation = production;
        listOfSubmodelClasses.add(production);
    }
    public Product (String productId, DigitalNameplate digitalNameplate, BOM bom)
    {
        super(productId, digitalNameplate);
        this.subComponentsBOM = bom;
        listOfSubmodelClasses.add(this.subComponentsBOM);
    }

    public void addDesignInformation(DesignInformation designInfo)
    {
        this.designInformation = designInfo;
        listOfSubmodelClasses.add(designInfo);
    }
    public void addSubComponents(BOM subComponents)
    {
        this.subComponentsBOM = subComponents;
        listOfSubmodelClasses.add(subComponents);
    }
    public void addProductionInformation(Production productionInformation)
    {
        this.productionInformation = productionInformation;
        listOfSubmodelClasses.add(this.productionInformation);
    }
    public String getIdentification()
    {
        return this.productIdentification;
    }



}


