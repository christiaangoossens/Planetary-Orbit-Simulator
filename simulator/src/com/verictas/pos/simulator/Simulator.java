package com.verictas.pos.simulator;
import javax.vecmath.*;

import com.verictas.pos.simulator.dataWriter.DataWriter;
import com.verictas.pos.simulator.dataWriter.WritingException;
import com.verictas.pos.simulator.mathUtils.Vector3dMatrix;

public class Simulator {
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
        Vector3dMatrix matrix = new Vector3dMatrix(objects.length,objects.length);

        try {
            DataWriter writer = new DataWriter();

            /**
             * Write begin values
             */

            for(int i = 0; i < objects.length; i++) {
                writer.write(objects[i], objects[0]);
            }

            /**
             * Start the leap frog integration!
             */

            accelerate(objects, matrix);

            /**
             * Start the rounds loop
             */
            for(int t = 0; t != rounds; t++) {
                /**
                 * The round has started
                 */
                System.out.println("\nRound " + (t + 1) + " started!");

                for(int i = 0; i < objects.length; i++) {
                    objects[i].updatePosition(time);
                    objects[i].updateAcceleration();
                }

                accelerate(objects, matrix);

                for(int i = 0; i < objects.length; i++) {
                    objects[i].updateSpeed(time);
                    writer.write(objects[i], objects[0]);

                    /**
                     * Do some processing to get the aphelion & perihelion
                     */

                    objects[i].processAphelionAndPerihelion(objects[0]);
                }

                /**
                 * The round has ended
                 */
            }

            /**
             * Log that the simulation has finished and save info to file
             */
            writer.save();
            System.out.println("========== Simulation Finished ==========");

            // TEST

            System.out.println("\n\n============== Simulation data =============");
            System.out.println("Position during aphelion: " + objects[1].aphelion);
            System.out.println("Distance from the sun during aphelion in km: " + objects[1].aphelionDistance);
            System.out.println("Position during perihelion: " + objects[1].perihelion);
            System.out.println("Distance from the sun during perihelion in km: " + objects[1].perihelionDistance);
            System.out.println("===========================================\n\n");

            /**
             * Display information about the program runtime
             */
            long stopTime = System.currentTimeMillis();
            System.out.println("Simulation took: " + (stopTime - startTime) + "ms");
        } catch(WritingException e) {
            e.printStackTrace();
        }
    }

    public static void accelerate(Object[] objects, Vector3dMatrix matrix) {
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
                System.out.println("Force " + (i + 1) + " on " + (o + 1) + " - " + force);

                /**
                 * Also put in the opposite force
                 */
                force.scale(-1);
                matrix.setPosition(force, o, i);
                System.out.println("Force " + (o + 1) + " on " + (i + 1) + " - " + force);
            }
        }


        System.out.println("\n");
        System.out.println(matrix);

        for(int i = 0; i < objects.length; i++) {
            /**
             * Progress forces on the object
             */
            Vector3d forceOnI = matrix.getColumnTotal(i);
            System.out.println("All forces on " + (i + 1) + " - " + forceOnI);
            objects[i].enactForceOnObject(forceOnI);
        }
    }
}
