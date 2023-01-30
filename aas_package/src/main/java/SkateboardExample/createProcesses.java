package SkateboardExample;

import java.util.List;
import java.util.ArrayList;

import ProcessModel.Process;
import New_ProcessModel.ProcessAttribute;

import New_ProcessModel.ProcessData;
import New_ProcessModel.ElementaryProcessData;
import New_ProcessModel.ProcedureData;

import New_ProcessModel.ProcessModel;
// import New_ProcessModel.SingleProcessModel;
// import New_ProcessModel.SequentialProcessModel;
import New_ProcessModel.GraphProcessModel;

import New_ProcessModel.MatchProcessProcedure;

import New_ProcessModel.ProcessAASCreator;
import New_ProcessModel.ProcedureAASCreator;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import sdm_aas.PushAAStoServer;

import java.util.Map;

public class createProcesses {

        static Process createMillingProcessfor5X() {
                Process millingProcess = new Process();

                return millingProcess;
        }

        private static List<ProcessAttribute> createProcessAttributesForMillingProcess() {
                List<String> millingSemantics = new ArrayList<String>();
                millingSemantics.add("Milling");

                List<String> millingTechnologySemantics = new ArrayList<String>(millingSemantics);
                millingTechnologySemantics.add("Technology");

                List<String> rotationSemantics = new ArrayList<String>(millingSemantics);
                rotationSemantics.add("Rotation speed");

                List<String> dimensionSemantics = new ArrayList<String>(millingSemantics);
                dimensionSemantics.add("Dimensions");

                ProcessAttribute requiredMillingTechnology = new ProcessAttribute(millingTechnologySemantics,
                                "Milling technology", "3 Axes");
                ProcessAttribute requiredMillRotationSpeed = new ProcessAttribute(rotationSemantics,
                                "Milling roation speed attribute in rpm", 30.0, "Minimum");
                ProcessAttribute requiredDimensions = new ProcessAttribute(dimensionSemantics,
                                "Milling dimensions for x y z in mm", List.of(350.0, 50.0, 40.0), "Minimum");

                List<ProcessAttribute> millingProcessAttributes = List.of(requiredMillingTechnology,
                                requiredMillRotationSpeed, requiredDimensions);

                return millingProcessAttributes;
        }

        private static List<ProcessAttribute> createProcessAttributesForMillingProcedure1() {
                List<String> millingSemantics = new ArrayList<String>();
                millingSemantics.add("Milling");

                List<String> millingTechnologySemantics = new ArrayList<String>(millingSemantics);
                millingTechnologySemantics.add("Technology");

                List<String> rotationSemantics = new ArrayList<String>(millingSemantics);
                rotationSemantics.add("Rotation speed");

                List<String> dimensionSemantics = new ArrayList<String>(millingSemantics);
                dimensionSemantics.add("Dimensions");
                ProcessAttribute actualMillingTechnology3 = new ProcessAttribute(millingTechnologySemantics,
                                "Milling technology", "3 Axes");
                ProcessAttribute actualMillingTechnology5 = new ProcessAttribute(millingTechnologySemantics,
                                "Milling technology 2", "5 Axes");
                ProcessAttribute acutalMillRotationSpeed = new ProcessAttribute(rotationSemantics,
                                "Milling roation speed attribute in rpm", 12000.0, "Minimum");
                ProcessAttribute actualDimensions = new ProcessAttribute(dimensionSemantics,
                                "Milling dimensions for x y z in mm", List.of(600.0, 600.0, 150.0), "Minimum");
                List<ProcessAttribute> millingProcessAttributes = List.of(actualMillingTechnology3,
                                actualMillingTechnology5,
                                acutalMillRotationSpeed, actualDimensions);
                return millingProcessAttributes;
        }

        private static List<ProcessAttribute> createProcessAttributesForMillingProcedure2() {
                List<String> millingSemantics = new ArrayList<String>();
                millingSemantics.add("Milling");

                List<String> millingTechnologySemantics = new ArrayList<String>(millingSemantics);
                millingTechnologySemantics.add("Technology");

                List<String> rotationSemantics = new ArrayList<String>(millingSemantics);
                rotationSemantics.add("Rotation speed");

                List<String> dimensionSemantics = new ArrayList<String>(millingSemantics);
                dimensionSemantics.add("Dimensions");

                ProcessAttribute actualMillingTechnology = new ProcessAttribute(millingTechnologySemantics,
                                "Milling technology", "2 Axes");
                ProcessAttribute acutalMillRotationSpeed = new ProcessAttribute(rotationSemantics,
                                "Milling roation speed attribute in rpm", 19000.0, "Minimum");
                ProcessAttribute actualDimensions = new ProcessAttribute(dimensionSemantics,
                                "Milling dimensions for x y z in mm", List.of(1200.0, 1200.0, 150.0), "Minimum");

                List<ProcessAttribute> millingProcessAttributes = List.of(actualMillingTechnology,
                                acutalMillRotationSpeed, actualDimensions);
                return millingProcessAttributes;

        }

        private static List<ProcessAttribute> createProcessAttributesForMillingProcedure3() {
                List<String> millingSemantics = new ArrayList<String>();
                millingSemantics.add("Milling");

                List<String> millingTechnologySemantics = new ArrayList<String>(millingSemantics);
                millingTechnologySemantics.add("Technology");

                List<String> rotationSemantics = new ArrayList<String>(millingSemantics);
                rotationSemantics.add("Rotation speed");

                List<String> dimensionSemantics = new ArrayList<String>(millingSemantics);
                dimensionSemantics.add("Dimensions");
                ProcessAttribute actualMillingTechnology3 = new ProcessAttribute(millingTechnologySemantics,
                                "Milling technology", "3 Axes");
                ProcessAttribute acutalMillRotationSpeed = new ProcessAttribute(rotationSemantics,
                                "Milling roation speed attribute in rpm", 20000.0, "Minimum");
                ProcessAttribute actualDimensions = new ProcessAttribute(dimensionSemantics,
                                "Milling dimensions for x y z in mm", List.of(15.0, 10.0, 15.0), "Minimum");
                List<ProcessAttribute> millingProcessAttributes = List.of(actualMillingTechnology3,
                                acutalMillRotationSpeed, actualDimensions);
                return millingProcessAttributes;

        }

        public static List<ProcedureData> createProcedureAAS() {
                String EXAMPLE_RESOURCE_URI = "http://193.196.37.23:4001/aasServer/shells/ResourceID/aas/";

                List<ProcessAttribute> millingProcessAttributes1 = createProcessAttributesForMillingProcedure1();
                ProcedureData millingProcedure1 = new ProcedureData("millingProcedure1", "Milling Procedure 1",
                                millingProcessAttributes1,
                                EXAMPLE_RESOURCE_URI);

                List<ProcessAttribute> millingProcessAttributes2 = createProcessAttributesForMillingProcedure2();
                String EXAMPLE_RESOURCE_URI2 = "http://193.196.37.23:4001/aasServer/shells/ResourceID2/aas/";
                ProcedureData millingProcedure2 = new ProcedureData("millingProcedure2", "Milling Procedure 1",
                                millingProcessAttributes2,
                                EXAMPLE_RESOURCE_URI2);

                List<ProcessAttribute> millingProcessAttributes3 = createProcessAttributesForMillingProcedure3();

                String EXAMPLE_RESOURCE_URI3 = "http://193.196.37.23:4001/aasServer/shells/ResourceID3/aas/";
                ProcedureData millingProcedure3 = new ProcedureData("millingProcedure3", "Milling Procedure 1",
                                millingProcessAttributes3,
                                EXAMPLE_RESOURCE_URI3);

                return List.of(millingProcedure1, millingProcedure2, millingProcedure3);
        }

        public static ProcessData createMillingProcessData() {

                List<ProcessAttribute> millingProcessAttributes = createProcessAttributesForMillingProcess();

                ElementaryProcessData milling1 = new ElementaryProcessData("millingProcess1", "milling 1",
                                millingProcessAttributes);
                ElementaryProcessData milling2 = new ElementaryProcessData("millingProcess2", "milling 1",
                                millingProcessAttributes);

                // Generate new Graph Process Model with elementary processes
                GraphProcessModel millingProcessModel1 = new GraphProcessModel("millingProcessModel", "graph process Model 1");

                // Add Nodes and Edges to Process Model
                millingProcessModel1.add_node(milling1);
                millingProcessModel1.add_node(milling2);

                millingProcessModel1.add_edge(milling1, milling2);
                millingProcessModel1.add_edge(milling2, milling1);

                // TODO: Start und end verknüpfen

                // Generate new Process Model List
                List<ProcessModel> millingProcessModels = List.of(millingProcessModel1);

                ProcessData millingProcess = new ProcessData("millingProcess", "Milling Process", millingProcessAttributes,
                                millingProcessModels);

                return millingProcess;

        }

        public static void createProcessAndProcedureAAS() {
                String SERVER_URL = "http://193.196.37.23:4001/aasServer";
                String REGISTRY_URL = "http://193.196.37.23:4000/registry/api/v1/registry";


                ProcessData millingProcess = createMillingProcessData();

                Map<AssetAdministrationShell, List<Submodel>> processAAS = ProcessAASCreator
                                .createAASfromProcess(millingProcess, millingProcess.id, millingProcess.description);
                PushAAStoServer.pushAAS(processAAS, SERVER_URL, REGISTRY_URL);

                
                List<ProcedureData> millingProcedures = createProcedureAAS();

                ProcedureData test_procedure = millingProcedures.get(0);
                boolean validProcedure = MatchProcessProcedure.checkProcedureContainsProcess(millingProcess,
                                test_procedure);
                System.out.println("Procedure is valid for process: " + validProcedure);

                List<ProcedureData> possibleProcedure = MatchProcessProcedure.findValidProcedures(millingProcess,
                                millingProcedures);
                System.out.println(
                                "From " + millingProcedures.size() + " are " + possibleProcedure.size() + " possible.");

                for (ProcedureData procedure : possibleProcedure){
                        procedure.addProcess(SERVER_URL + "/shells/" + millingProcess.id + "AAS");
                }
                
                for (ProcedureData procedure: millingProcedures){
                        Map<AssetAdministrationShell, List<Submodel>> aas = ProcedureAASCreator.createAASfromProcedure(
                                procedure, procedure.id,
                                procedure.description);
                        PushAAStoServer.pushAAS(aas, SERVER_URL, REGISTRY_URL);
                }
                


        }

        public static void main(String[] args){
                createProcessAndProcedureAAS();
        }

}
