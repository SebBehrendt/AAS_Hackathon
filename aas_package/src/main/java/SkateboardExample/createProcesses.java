package SkateboardExample;

import java.util.List;
import java.util.ArrayList;

import New_ProcessModel.ProcessAttribute;

import New_ProcessModel.Process;
import New_ProcessModel.ElementaryProcess;
import New_ProcessModel.Procedure;

import New_ProcessModel.ProcessModel;
// import New_ProcessModel.SingleProcessModel;
// import New_ProcessModel.SequentialProcessModel;
import New_ProcessModel.GraphProcessModel;

import New_ProcessModel.MatchProcessProcedure;

import Helper.ServerAASX;

public class createProcesses {

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

        public static List<Procedure> createProcedureAAS() {
                String EXAMPLE_RESOURCE_URI = "http://193.196.37.23:4001/aasServer/shells/ResourceID/aas/";

                List<ProcessAttribute> millingProcessAttributes1 = createProcessAttributesForMillingProcedure1();
                Procedure millingProcedure1 = new Procedure("millingProcedure1", "Milling Procedure 1",
                                millingProcessAttributes1,
                                EXAMPLE_RESOURCE_URI);

                List<ProcessAttribute> millingProcessAttributes2 = createProcessAttributesForMillingProcedure2();
                String EXAMPLE_RESOURCE_URI2 = "http://193.196.37.23:4001/aasServer/shells/ResourceID2/aas/";
                Procedure millingProcedure2 = new Procedure("millingProcedure2", "Milling Procedure 1",
                                millingProcessAttributes2,
                                EXAMPLE_RESOURCE_URI2);

                List<ProcessAttribute> millingProcessAttributes3 = createProcessAttributesForMillingProcedure3();

                String EXAMPLE_RESOURCE_URI3 = "http://193.196.37.23:4001/aasServer/shells/ResourceID3/aas/";
                Procedure millingProcedure3 = new Procedure("millingProcedure3", "Milling Procedure 1",
                                millingProcessAttributes3,
                                EXAMPLE_RESOURCE_URI3);

                return List.of(millingProcedure1, millingProcedure2, millingProcedure3);
        }


        public static Process createMillingProcessData() {

                List<ProcessAttribute> millingProcessAttributes = createProcessAttributesForMillingProcess();

                ElementaryProcess milling1 = new ElementaryProcess("millingProcess1", "milling 1",
                                millingProcessAttributes);
                ElementaryProcess milling2 = new ElementaryProcess("millingProcess2", "milling 2",
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

                Process millingProcess = new Process("millingProcess", "Milling Process", millingProcessAttributes,
                                millingProcessModels);

                return millingProcess;

        }

        public static void createProcessAndProcedureAAS() {
                final String SERVER_URL = "http://193.196.37.23:4001/aasServer";
                final String REGISTRY_URL = "http://193.196.37.23:4000/registry/api/v1/registry";


                Process millingProcess = createMillingProcessData();

                ServerAASX.pushAAS(millingProcess.createAAS(), millingProcess.getSubmodels(), SERVER_URL, REGISTRY_URL);

                
                List<Procedure> millingProcedures = createProcedureAAS();

                Procedure test_procedure = millingProcedures.get(0);
                boolean validProcedure = MatchProcessProcedure.checkProcedureContainsProcess(millingProcess,
                                test_procedure);
                System.out.println("Procedure is valid for process: " + validProcedure);

                List<Procedure> possibleProcedure = MatchProcessProcedure.findValidProcedures(millingProcess,
                                millingProcedures);
                System.out.println(
                                "From " + millingProcedures.size() + " are " + possibleProcedure.size() + " possible.");

                for (Procedure procedure : possibleProcedure){
                        procedure.addProcess(SERVER_URL + "/shells/" + millingProcess.id + "AAS");
                }
                
                for (Procedure procedure: millingProcedures){
                        ServerAASX.pushAAS(procedure.createAAS(), procedure.getSubmodels(), SERVER_URL, REGISTRY_URL);
                }
                


        }

        public static void main(String[] args){
                createProcessAndProcedureAAS();
        }

}
