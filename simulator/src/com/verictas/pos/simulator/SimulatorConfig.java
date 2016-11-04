package com.verictas.pos.simulator;

public class SimulatorConfig {
    /**
     * Time settings
     */

    public static int rounds = 526100 * 35; // Amount of rounds to run the simulator for
    public static double time = 60; // Time steps in seconds

    /**
     * Object settings
     */

    public static String sunName = "Sun"; // The name of the sun to calculate values TO
    public static String[] objectNames = { "Earth" };  // The name of the object(s) your want to calculate the values OF

    /**
     * Ascending & descending node detection
     */

    //public static double z = -22439680.6; // Reference z height for the used system in meters
    //public static double zThreshold = 1000; // Threshold value to specify the maximum value (in meters) the z difference can be to be considered close to the reference plane (To eliminate all bending points that are to far away from the z-axis to count as nodes)

    /**
     * Output preferences
     */

    public static String outputUnit = "AU"; // Preferred output unit preference (AU => AU/day, m => m/s)
    public static int outputNumbers = 0; // Preferred way of outputting numbers: (0 => comma for decimals, dot in large numbers OR 1 => comma for large numbers, dot with decimals)
    public static int skipLines = 1440; // Set the skipLines integer to skip lines (for example: every 5th line is written) in the output file (for smaller files), if this is set to 1, it has no effect and all lines will be written.
    public static boolean skipUnnecessary = true; // Skip the unnecessary objects in the export
}
