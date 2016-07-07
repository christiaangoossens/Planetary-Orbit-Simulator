package com.verictas.pos.simulator;
import javax.vecmath.*;
import com.verictas.pos.simulator.mathUtils.Vector3dMatrix;

public class Simulator {
    public static void run(int rounds, Object[] objects) {

        /**
         * Log a debug message to the console to signal the simulation has started
         */
        System.out.println("========== Simulation Started ==========\n");

        /**
         * Start the rounds loop
         */
        for(int t = 0; t < rounds; t++) {
            /**
             * The round has started
             */
            System.out.println("Round " + (t + 1) + " started!");

            /**
             * Define the forces matrix for this round
             */
            Vector3dMatrix matrix = new Vector3dMatrix(objects.length,objects.length);

            for(int i = 0; i < objects.length; i++) {
                /**
                 * For every object: calculate the force upon it.
                 */
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

            /**
             * Print the matrix for this round
             */
            System.out.println("\n");
            System.out.println(matrix);
        }

        /**
         * Log that the simulation has finished
         */
        System.out.println("========== Simulation Finished ==========");
    }
}
