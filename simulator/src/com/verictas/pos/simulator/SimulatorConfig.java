package com.verictas.pos.simulator;

public class SimulatorConfig {

    /**
     * (Example) Settings for the EARTH
     * Rounds: 1051896 * (amount of years to run)
     * Time: 30
     * Mod arg: 1051896 (1 Earth year)
     */

    /**
     * (Example) Settings for SEDNA
     * Rounds: 184000000 (approx. 1 million years)
     * Time: 172800 (2 days)
     * Modulo argument: 2101968 (1 Sedna year)
     */

    /**
     * (Example) Settings for 2012 VP113
     * Rounds: 184000000 (approx. 1 million years)
     * Time: 172800 (2 days)
     * Modulo argument: 788923 (1 2012VP113 year)
     */

    /**
     * Time settings
     */

    public static int rounds = 0; // Amount of rounds to run the simulator for
    public static double time = 0; // Time steps in seconds

    /**
     * Object settings
     */

    public static String sunName = "Sun"; // The name of the sun to calculate values TO
    public static String[] objectNames = {};  // The name of the object(s) your want to calculate the values OF

    /**
     * Output preferences
     */

    public static String outputUnit = "AU"; // Preferred output unit preference (AU => AU/day, m => m/s)
    public static int outputNumbers = 0; // Preferred way of outputting numbers: (0 => comma for decimals, dot in large numbers OR 1 => comma for large numbers, dot with decimals)
    public static int skipLines = 1; // Set the skipLines integer to skip lines (for example: every 5th line is written) in the output file (for smaller files), if this is set to 1, it has no effect and all lines will be written.
    public static boolean skipUnnecessary = true; // Skip the unnecessary objects in the export

    /**
     * Console settings
     */
    public static boolean logConsole = true;
    public static int skipConsole = 1;

    /**
     * Processor settings
     */
    public static boolean autoModulo = true;
    public static int moduloArgument = 1;
}
