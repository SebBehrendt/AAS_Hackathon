package QualityData;

import Helper.ServerAASX;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.CustomId;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

import java.util.ArrayList;
import java.util.List;


public class QualityDataHand {
    public static void main(String[] args) {

        // Assets generieren und AAS instanziieren
        Asset QualityDataAsset = new Asset("QualityData", new CustomId("Quality Asset"), AssetKind.INSTANCE);
        AssetAdministrationShell QualityDataShell = new AssetAdministrationShell("QualityDataShell",
                new Identifier(), QualityDataAsset);

        Submodel QualityData = new Submodel();
        List<Submodel> Submodel = new ArrayList<>();
        Submodel.add(QualityData);


        // Procedure Prozedur1 = new Procedure("Id Prozedur 1", "Das ist die Prozedur 1",  );
        SubmodelElementCollection Prozedur1 = new SubmodelElementCollection("Prozedur1");
        SubmodelElementCollection FeaturesProzedur1 = new SubmodelElementCollection("FeaturesProzedur1");


        SubmodelElementCollection QualityFeatureNameProzedur1 = new SubmodelElementCollection("QualityFeatureNameProzedur1");
        Property FeatureTypeProzedur1 = new Property("FeatureTypeProzedur1", "Das ist der Feature Type der Prozedur 1");
        Property FunctionProzedur1 = new Property("FunctionTypeProzedur1", "Das ist die Function der Prozedur 1");
        Property UnitTypeProzedur1 = new Property("UnitTypeProzedur1", "Das ist die Unit Type der Prozedur 1");
        Property TargetValueProzedur1 = new Property("TargetValueProzedur1", "Das ist der Target Value der Prozedur 1");
        Property ToleranceProzedur1 = new Property("ToleranceProzedur1", "Das ist die Tolerance der Prozedur 1");
        Property WarningLimitProzedur1 = new Property("WarningLimitProzedur1", "Das ist das Warning Limit der Prozedur 1");
        Property ControlLimitProzedur1 = new Property("ControlLimitProzedur1", "Das ist das Control Limit der Prozedur 1");
        Property InspectionEquipementProzedur1 = new Property("InspectionEquipementProzedur1", "Das ist das Inspection Equipement der Prozedur 1");
        SubmodelElementCollection ReferencesProzedur1 = new SubmodelElementCollection("ReferencesProzedur1");
        Property PointProzedur1 = new Property("PointProzedur1", "Das ist die Point Reference der Prozedur 1");
        Property LineProzedur1 = new Property("LineProzedur1", "Das ist die Line Reference der Prozedur 1");
        Property SurfaceProzedur1 = new Property("SurfaceProzedur1", "Das ist die Surface Reference der Prozedur 1");
        Property AxisEquipementProzedur1 = new Property("AxisProzedur1", "Das ist die Axis Reference der Prozedur 1");


        SubmodelElementCollection SampleBatchProzedur1 = new SubmodelElementCollection("SampleBatchProzedur1");
        Property SampleSizeProzedur1 = new Property("SampleSizeProzedur1", "Das ist die Sample Size der Prozedur 1");


        SubmodelElementCollection SampleDataProzedur1 = new SubmodelElementCollection("SampleDataProzedur1");
        Property SampleNumberProzedur1 = new Property("SampleNumberProzedur1", "Das ist die Sample Number der Prozedur 1");
        Property SampleDateProzedur1 = new Property("SampleDateProzedur1", "Das ist das Sample Date der Prozedur 1");
        Property PartCounterProzedur1 = new Property("PartCounterProzedur1", "Das ist der Part Counter der Prozedur 1");


        SubmodelElementCollection ResultProzedur1 = new SubmodelElementCollection("ResultProzedur1");
        Property ValueProzedur1 = new Property("ValueProzedur1", "Das ist der Value der Prozedur 1");
        Property MeasureDateProzedur1 = new Property("MeasureDateProzedur1", "Das ist das Measure Date der Prozedur 1");
        Property ResultCheckProzedur1 = new Property("ResultCheckProzedur1", "Das ist der Result Check der Prozedur 1");
        ResultProzedur1.addSubmodelElement(ValueProzedur1);
        ResultProzedur1.addSubmodelElement(MeasureDateProzedur1);
        ResultProzedur1.addSubmodelElement(ResultCheckProzedur1);


        Prozedur1.addSubmodelElement(FeaturesProzedur1);
        FeaturesProzedur1.addSubmodelElement(QualityFeatureNameProzedur1);
        ReferencesProzedur1.addSubmodelElement(PointProzedur1);
        ReferencesProzedur1.addSubmodelElement(LineProzedur1);
        ReferencesProzedur1.addSubmodelElement(SurfaceProzedur1);
        ReferencesProzedur1.addSubmodelElement(AxisEquipementProzedur1);
        SampleBatchProzedur1.addSubmodelElement(SampleSizeProzedur1);
        SampleBatchProzedur1.addSubmodelElement(SampleDataProzedur1);
        SampleDataProzedur1.addSubmodelElement(SampleNumberProzedur1);
        SampleDataProzedur1.addSubmodelElement(SampleDateProzedur1);
        SampleDataProzedur1.addSubmodelElement(PartCounterProzedur1);
        SampleDataProzedur1.addSubmodelElement(ResultProzedur1);
        QualityFeatureNameProzedur1.addSubmodelElement(ReferencesProzedur1);
        QualityFeatureNameProzedur1.addSubmodelElement(FeatureTypeProzedur1);
        QualityFeatureNameProzedur1.addSubmodelElement(FunctionProzedur1);
        QualityFeatureNameProzedur1.addSubmodelElement(UnitTypeProzedur1);
        QualityFeatureNameProzedur1.addSubmodelElement(TargetValueProzedur1);
        QualityFeatureNameProzedur1.addSubmodelElement(ToleranceProzedur1);
        QualityFeatureNameProzedur1.addSubmodelElement(WarningLimitProzedur1);
        QualityFeatureNameProzedur1.addSubmodelElement(ControlLimitProzedur1);
        QualityFeatureNameProzedur1.addSubmodelElement(InspectionEquipementProzedur1);
        QualityData.addSubmodelElement(Prozedur1);
        QualityFeatureNameProzedur1.addSubmodelElement(SampleBatchProzedur1);

        QualityDataShell.addSubmodel(QualityData);



        ServerAASX.pushAAS(QualityDataShell, Submodel, "http://193.196.37.23:4001/aasServer/shells/", "http://193.196.37.23:4000/registry/api/v1/registry");
    }




}








