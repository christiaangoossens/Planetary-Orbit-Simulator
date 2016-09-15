package com.verictas.pos.simulator;
import javax.vecmath.*;
import com.verictas.pos.simulator.mathUtils.Vector3dMatrix;

public class Simulator {
    public static void run(Object[] objects) {

        /**
         * Get variables from the config
         */

        int rounds = SimulatorConfig.rounds;

        /**
         * Log a debug message to the console to signal the simulation has started
         */
        System.out.println("========== Simulation Started ==========\n");

        /**
         * Define the forces matrix for this round
         */
        Vector3dMatrix matrix = new Vector3dMatrix(objects.length,objects.length);

        accelerate(objects, matrix);

        /**
         * Start the rounds loop
         */
        for(int t = 0; t != rounds; t++) {
            /**
             * The round has started
             */
            System.out.println("\nRound " + (t + 1) + " started!");
            /**
             * Define the initial values
             */
            double time = SimulatorConfig.time;

            for(int i = 0; i < objects.length; i++) {
                System.out.println("Object " + (i+1) + " was at " + objects[i].position);
                objects[i].updatePosition(time);
                objects[i].updateAcceleration();
                System.out.println("Object " + (i+1) + " is at " + objects[i].position);
            }

            accelerate(objects, matrix);

            /**
             * Print the matrix for this round
             */

            for(int i = 0; i < objects.length; i++) {
                objects[i].updateSpeed(time);
            }

        }

        /**
         * Log that the simulation has finished
         */
        System.out.println("========== Simulation Finished ==========");
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
