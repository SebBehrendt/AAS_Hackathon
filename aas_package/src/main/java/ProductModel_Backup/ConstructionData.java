package ProductModel_Backup;

import Helper.AASHelper;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

import java.util.HashMap;
import java.util.Map;

public class ConstructionData { //To SM Geometry
    private static final String SUBMODEL_CONSTRUCTION_SHORT_ID = "Geometry";
    private static final String SMC_CAD_ID = "CAD_Files";
    private static final String SMC_SPECIFICATION_ID = "Specification_Information";
    private static final String SUBMODEL_CONSTRUCTION_IDENTIFIER = "Construction_Data_";
    Map<String,String> CADFiles = new HashMap<String, String>(); //implement other features like versions later -Filename / path
    Map<String, String> specificationInformation = new HashMap<>(); // example: technical Drawings and K/V-Pairs of tech details

    public ConstructionData(Map <String,String> cadFiles, Map<String,String> specificationInformation)
    {
        this.CADFiles = cadFiles;
        this.specificationInformation = specificationInformation;
    }
    public ConstructionData(){}
    protected Map getCADFiles() {
        return CADFiles;
    }
    protected Map<String, String> getSpecificationInformation() {return specificationInformation;}
    public void addConstructionFiles(String nameOfCADFile, String linkToFile) {this.CADFiles.put(nameOfCADFile, linkToFile);}
    public void addConstructionFiles (Map<String, String> mapCADFiles)
    {
        this.CADFiles.putAll(mapCADFiles);
    }
    public void addConstructionFile(String filename, String path) {this.CADFiles.put(filename, path);}
    public void addSpecificationAttribute (String key, String value) {this.specificationInformation.put(key, value);}
    protected Submodel createSubmodelConstruction(String instanceNumber)
    {
        Submodel submodelConstruction = new Submodel(SUBMODEL_CONSTRUCTION_SHORT_ID,
                new Identifier(IdentifierType.CUSTOM,SUBMODEL_CONSTRUCTION_IDENTIFIER+instanceNumber ));

        if (this.CADFiles != null)
        {
            submodelConstruction.addSubmodelElement(createCADFileSMC());
        }
        if (this.specificationInformation != null)
        {
            submodelConstruction.addSubmodelElement(createSpecificationFilesSMC());
        }
        return submodelConstruction;
    }
    private SubmodelElementCollection createCADFileSMC()
    {
        SubmodelElementCollection CAD_SMC = new SubmodelElementCollection(SMC_CAD_ID);
        for (Map.Entry<String, String> set : this.CADFiles.entrySet())
        {
            CAD_SMC.addSubmodelElement(new Property(AASHelper.nameToIdShort(set.getKey()), set.getValue()));
        }
        return CAD_SMC;
    }
    private SubmodelElementCollection createSpecificationFilesSMC()
    {
        SubmodelElementCollection specification_SMC = new SubmodelElementCollection(SMC_SPECIFICATION_ID);
        for (Map.Entry<String, String> set : this.specificationInformation.entrySet())
        {
            specification_SMC.addSubmodelElement(new Property(AASHelper.nameToIdShort(set.getKey()), set.getValue()));
        }
        return specification_SMC;

    }

}
