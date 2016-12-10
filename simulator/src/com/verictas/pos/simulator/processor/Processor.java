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

            // Check if we need to calculate the AOP
            if (Simulator.round % SimulatorConfig.moduloArgument == 0) {
                if (arguments.get(objectName) == null) {
                    // If not defined
                    ArrayList<Double> agmnts = new ArrayList<>();
                    arguments.put(objectName, agmnts);
                }

                arguments.get(objectName).add(object.calculateAOP());
            }

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
