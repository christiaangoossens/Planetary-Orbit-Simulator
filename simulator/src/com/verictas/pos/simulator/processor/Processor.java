package com.verictas.pos.simulator.processor;

import com.verictas.pos.simulator.Object;
import com.verictas.pos.simulator.Simulator;
import com.verictas.pos.simulator.SimulatorConfig;
import com.verictas.pos.simulator.dataWriter.DataWriter;
import com.verictas.pos.simulator.dataWriter.WritingException;
import com.verictas.pos.simulator.mathUtils.AOP;
import com.verictas.pos.simulator.mathUtils.AU;

import java.util.ArrayList;
import java.util.HashMap;

public class Processor {
    private DataWriter writer;
    public HashMap<String, Object> initialObjectValues = new HashMap<>();
    public HashMap<String, SimpleObjectProcessor> objects = new HashMap<>();
    public HashMap<String, ArrayList<Double>> arguments = new HashMap<>();

    public Processor(Object[] objects) throws ProcessingException, WritingException {
        /**
         * Initialize DataWriter
         */
        this.writer = new DataWriter();

        /**
         * Store the initial values of all the objects in memory (and to a file) for later use
         */
        this.initialObjectValues = objectArrayToHashMap(objects);

        // Write initial values to file
        this.write(initialObjectValues);

        /**
         * Create the object processing array
         */
        for (Object object : initialObjectValues.values()) {
            this.objects.put(object.name, new SimpleObjectProcessor());
        }
    }

    public void process(Object[] objectArray) throws ProcessingException, WritingException {
        HashMap<String, Object> objects = objectArrayToHashMap(objectArray);

        /**
         * Only do the processing for the asked planet(s)
         */
        for(String objectName : SimulatorConfig.objectNames) {
            SimpleObjectProcessor object = this.objects.get(objectName);
            object.setObjectData(objects.get(objectName));
            object.calculateAOP();
            //object.setReferenceObjectData(objects.get(SimulatorConfig.sunName));
            //object.processHistory();

            // Check if the object has gone round last round

            //boolean round = object.processRoundCheck();
            /*if (round) {
                // Process the nodes
                object.processNodes();

                // ECHO:: Object has gone full circle last round!
                System.out.println("\n\n============== ROTATION DATA: " + objectName.toUpperCase() + ", ROUND " + (Simulator.round - 1) + " =============");

                if (SimulatorConfig.outputUnit.equals("AU")) {
                    //System.out.println("Current position (AU): " + AU.convertFromMeter(objects.get(objectName).position) + "\n");
                    //System.out.println("Highest point (z-axis graph) (AU): " + AU.convertFromMeter(object.absoluteMax));
                    //System.out.println("Lowest point (z-axis graph) (AU): " + AU.convertFromMeter(object.absoluteMin));
                    //System.out.println("Calculated reference height (AU) : " + AU.convertFromMeter(object.referenceZ) + "\n");

                    if (object.ascendingNode != null) {
                        System.out.println("Ascending node (AU): " + AU.convertFromMeter(object.ascendingNode));
                    } else {
                        System.out.println("WARNING:: Ascending node not found.");
                    }

                    if (object.descendingNode != null) {
                        System.out.println("Descending node (AU): " + AU.convertFromMeter(object.descendingNode) + "\n");
                    } else {
                        System.out.println("WARNING:: Descending node not found.\n");
                    }

                    //System.out.println("Position during apastron (AU): " + AU.convertFromMeter(object.aphelion));
                    System.out.println("Distance from (the) " + SimulatorConfig.sunName + " during apastron in km: " + object.aphelionDistance / 1000 + "\n");
                    //System.out.println("Position during periastron (AU): " + AU.convertFromMeter(object.perihelion));
                    System.out.println("Distance from (the) " + SimulatorConfig.sunName + " during periastron in km: " + object.perihelionDistance / 1000 + "\n");
                } else {
                    //System.out.println("Current position (m): " + objects.get(objectName).position + "\n");
                   //System.out.println("Highest point (z-axis graph) (m): " + object.absoluteMax);
                    //System.out.println("Lowest point (z-axis graph) (m): " + object.absoluteMin);
                    //System.out.println("Calculated reference height (m) : " + object.referenceZ + "\n");

                    if (object.ascendingNode != null) {
                        System.out.println("Ascending node (m): " + object.ascendingNode);
                    } else {
                        System.out.println("WARNING:: Ascending node not found.");
                    }

                    if (object.descendingNode != null) {
                        System.out.println("Descending node (m): " + object.descendingNode + "\n");
                    } else {
                        System.out.println("WARNING:: Descending node not found.\n");
                    }

                    //System.out.println("Position during apastron (m): " + object.aphelion);
                    System.out.println("Distance from (the) " + SimulatorConfig.sunName + " during apastron in km: " + object.aphelionDistance / 1000);
                    //System.out.println("Position during periastron (m): " + object.perihelion);
                    System.out.println("Distance from (the) " + SimulatorConfig.sunName + " during periastron in km: " + object.perihelionDistance / 1000 + "\n");
                }

                if (object.ascendingNode != null) {
                    System.out.println("Argument of periapsis (radians): " + AOP.calculate(object.ascendingNode, object.perihelion, object.aphelion));
                    //System.out.println("Argument of periapsis (degrees): " + Math.toDegrees(AOP.calculate(object.ascendingNode, object.perihelion, object.aphelion)));

                    if (object.checkNodes()) {
                        // Add the node to the list
                        if (arguments.get(objectName) == null) {
                            // If not defined
                            ArrayList<Double> agmnts = new ArrayList<>();
                            arguments.put(objectName, agmnts);
                        }

                        arguments.get(objectName).add(AOP.calculate(object.ascendingNode, object.perihelion, object.aphelion));
                    }

                } else {
                    System.out.println("ERROR:: Can't calculate the argument of periapsis because the ascending node is missing.");
                }

                System.out.println("=======================================================================================\n\n");

                object.reset();

                // Reset starting position
                this.objects.get(objectName).setStartingPosition(objects.get(objectName).position);
            }*/

            // Process values for this round
            //object.processAphelionAndPerihelion();
            //object.calculateTops();

            this.objects.put(objectName, object);
        }

        this.write(objects);
    }

    private void write(HashMap<String, Object> objects) throws ProcessingException, WritingException {
        if (SimulatorConfig.skipUnnecessary) {
            for (String name : SimulatorConfig.objectNames) {
                this.writer.write(objects.get(name), objects.get(SimulatorConfig.sunName));
            }
        } else {
            for (Object object : objects.values()) {
                this.writer.write(object, objects.get(SimulatorConfig.sunName));
            }
        }
    }

    private HashMap<String, Object> objectArrayToHashMap(Object[] objects) {
        // Create the return map
        HashMap<String, Object> objectMap = new HashMap<>();

        for(int i = 0; i < objects.length; i++) {
            objectMap.put(objects[i].name, objects[i]);
        }

        return objectMap;
    }

    public void close() throws ProcessingException {
        try {
            this.writer.save();

            System.out.println("TOTAL RESULTS: " + arguments);

            for(String objectName : SimulatorConfig.objectNames) {
                ArrayList<Double> arguments = this.arguments.get(objectName);
                double score = 0;

                // Calculate score
                for(int i = 1; i < arguments.size() - 1; i++) {
                    score = score + Math.abs(arguments.get(i-1) - arguments.get(i));
                }

                System.out.println("SCORE (" + objectName + "): " + score);

                // CALCULATE AVERAGE
                double sum = 0;
                for (int i = 0; i < arguments.size(); i++){
                    sum = sum + arguments.get(i);
                }
                // calculate average
                double average = sum / arguments.size();

                System.out.println("AVERAGE (" + objectName + ") (degrees): " + Math.toDegrees(average));
            }
        } catch(WritingException e) {
            throw new ProcessingException("An error occurred during creation of the file writer: " + e.toString());
        }
    }
}
