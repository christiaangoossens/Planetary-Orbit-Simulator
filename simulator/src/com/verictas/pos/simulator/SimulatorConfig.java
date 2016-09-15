package com.verictas.pos.simulator;

public class SimulatorConfig {
    // The amount of rounds and the time step
    public static int rounds = 525960;
    public static double time = 60; // in seconds

    // Set the skipLines integer to skip lines (for example: every 5th line is written) in the output file (for smaller files), if this is set to 1, it has no effect and all lines will be written.
    public static int skipLines = 5;
}
