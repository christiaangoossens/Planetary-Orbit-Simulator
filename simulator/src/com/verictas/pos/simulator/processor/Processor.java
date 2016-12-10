package com.verictas.pos.simulator.processor;

import com.verictas.pos.simulator.Object;
import com.verictas.pos.simulator.Simulator;
import com.verictas.pos.simulator.SimulatorConfig;
import com.verictas.pos.simulator.dataWriter.AOPDataWriter;
import com.verictas.pos.simulator.dataWriter.PosDataWriter;
import com.verictas.pos.simulator.dataWriter.WritingException;
import com.verictas.pos.simulator.mathUtils.AOP;

import javax.vecmath.Vector3d;
import java.util.HashMap;
import java.util.TreeMap;

public class Processor {
    private PosDataWriter writer;
    private AOPDataWriter aopWriter;
    public HashMap<String, Object> initialObjectValues = new HashMap<>();
    public HashMap<String, TreeMap<Integer, Double>> arguments = new HashMap<>();

    public Processor(Object[] objects) throws ProcessingException, WritingException {
        /**
         * Initialize DataWriter
         */
        this.writer = new PosDataWriter();
        this.aopWriter = new AOPDataWriter();

        /**
         * Store the initial values of all the objects in memory (and to a file) for later use
         */
        this.initialObjectValues = objectArrayToHashMap(objects);

        // Write initial values to file
        this.writePos(initialObjectValues);
    }

    public void process(Object[] objectArray) throws ProcessingException, WritingException {
        HashMap<String, Object> objects = objectArrayToHashMap(objectArray);
        this.writePos(objects);


        /**
         * Calculate AOP for specified objects
         */
        for(String objectName : SimulatorConfig.objectNames) {
            // Check if we need to calculate the AOP
            if (Simulator.round % SimulatorConfig.moduloArgument == 0) {
                if (arguments.get(objectName) == null) {
                    // If not defined
                    TreeMap<Integer, Double> agmnts = new TreeMap<>();
                    arguments.put(objectName, agmnts);
                }

                // Calculate AOP and put it in the array
                Object object = objects.get(objectName);
                Vector3d pos = new Vector3d(object.position);
                Vector3d speed = new Vector3d(object.speed);
                arguments.get(objectName).put(Simulator.round, AOP.calculate(pos, speed));
            }
        }
    }

    private void writePos(HashMap<String, Object> objects) throws ProcessingException, WritingException {
        if (SimulatorConfig.skipUnnecessary) {
            for (String name : SimulatorConfig.objectNames) {
                this.writer.write(objects.get(name));
            }
        } else {
            for (Object object : objects.values()) {
                this.writer.write(object);
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
            System.out.println("");
            for(String objectName : SimulatorConfig.objectNames) {
                TreeMap<Integer, Double> arguments = this.arguments.get(objectName);

                this.aopWriter.write(objectName, arguments);

                double score = 0;

                Double[] empty = new Double[arguments.size()];
                Double[] agmnts = arguments.values().toArray(empty);

                // Calculate score
                for(int i = 1; i < agmnts.length - 1; i++) {
                    score = score + Math.abs(agmnts[i-1] - agmnts[i]);
                }

                System.out.println("SCORE (" + objectName + "): " + score);

                // CALCULATE AVERAGE
                double sum = 0;
                for (int i = 0; i < agmnts.length; i++){
                    sum = sum + agmnts[i];
                }
                // calculate average
                double average = sum / agmnts.length;

                System.out.println("AVERAGE (" + objectName + ") (degrees): " + Math.toDegrees(average));
                System.out.println("");
            }

            this.aopWriter.save();
        } catch(WritingException e) {
            throw new ProcessingException("An error occurred during creation of the file writer: " + e.toString());
        }
    }
}
