package com.verictas.pos.simulator;
import javax.vecmath.*;

import com.verictas.pos.simulator.dataWriter.WritingException;
import com.verictas.pos.simulator.mathUtils.Vector3dMatrix;
import com.verictas.pos.simulator.processor.ProcessingException;
import com.verictas.pos.simulator.processor.Processor;

public class Simulator {
    public static int round = 0; // Stores an global integer value with the current round (as a timestamp)

    /**
     * Run method for the Simulator
     * @param objects
     */
    public static void run(Object[] objects) {

        /**
         * Get variables from the config
         */

        int rounds = SimulatorConfig.rounds;
        double time = SimulatorConfig.time;

        /**
         * Log a debug message to the console to signal the simulation has started
         */
        System.out.println("========== Simulation Started ==========\n");

        /**
         * Create a time to measure runtime
         */
        long startTime = System.currentTimeMillis();

        /**
         * Define the forces matrix and the DataWriter
         */
        Vector3dMatrix matrix = new Vector3dMatrix(objects.length, objects.length);

        try {
            Processor processor = new Processor(objects);

            /**
             * Start the leap frog integration!
             */

            accelerate(objects, matrix);

            /**
             * Start the rounds loop
             */
            for(int t = 0; t != rounds; t++) {
                // Set round
                Simulator.round++;

                /**
                 * The round has started
                 */
                if(SimulatorConfig.logConsole) {
                    if(SimulatorConfig.skipConsole == -1 || Simulator.round % SimulatorConfig.skipConsole == 0 || Simulator.round == 1) {
                        System.out.println("Round " + Simulator.round + " started!");
                    }
                }

                for(int i = 0; i < objects.length; i++) {
                    objects[i].updatePosition(time);
                    objects[i].updateAcceleration();
                }

                accelerate(objects, matrix);

                for(int i = 0; i < objects.length; i++) {
                    objects[i].updateSpeed(time);
                }

                /**
                 * Do the processing on the objects
                 */
                processor.process(objects);

                /**
                 * The round has ended
                 */
            }

            /**
             * Log that the simulation has finished and save info to file
             */
            processor.close();
            System.out.println("========== Simulation Finished ==========");

            /**
             * Display information about the program runtime
             */
            long stopTime = System.currentTimeMillis();
            System.out.println("Simulation took: " + (stopTime - startTime) + "ms");
        } catch(ProcessingException e) {
            System.out.println("\nERROR:: Processing failed.");
            e.printStackTrace();
        } catch(WritingException e) {
            System.out.println("\nERROR:: Writing to file failed.");
            e.printStackTrace();
        }
    }

    /**
     * Accelerates the given objects, puts the results in the given matrix and enacts forces
     * @param objects
     * @param matrix
     */
    private static void accelerate(Object[] objects, Vector3dMatrix matrix) {
        // Loop
        for(int i = 0; i < objects.length; i++) {
            /**
             * For every object: calculate the force upon it.
             */

            // Reset acceleration
            objects[i].setAcceleration(new Vector3d(0, 0, 0));

            for (int o = 0; o < objects.length; o++) {
                /**
                 * Loop through all other objects
                 */
                if (o == i) {
                    break;
                }

                Vector3d force = objects[i].getForceOnObject(objects[o]);
                matrix.setPosition(force, i, o);

                /**
                 * Also put in the opposite force
                 */
                force.scale(-1);
                matrix.setPosition(force, o, i);
            }
        }

        for(int i = 0; i < objects.length; i++) {
            /**
             * Progress forces on the object
             */
            Vector3d forceOnI = matrix.getColumnTotal(i);
            objects[i].enactForceOnObject(forceOnI);
        }
    }
}
