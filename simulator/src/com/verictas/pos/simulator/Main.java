package com.verictas.pos.simulator;

import javax.vecmath.*;

public class Main {
    /**
     * PLANETARY ORBIT SIMULATOR
     * Data Simulation Tool
     *
     * Programmed for the PWS "Planeet Negen" for the Stedelijk Gymnasium Nijmegen, the Netherlands.
     *
     * ==================================
     *
     * The MIT License (MIT)
     * Copyright (c) 2016 Christiaan Goossens (Verictas) & Daniel Boutros
     *
     * The full license is included in the git respository as LICENSE.md
     */

    public static void main(String[] args) {
        /**
         * Object definitions
         */
        Object object1 = new Object(1000, new Vector3d(1,2,3), new Vector3d(0,4,3));
        Object object2 = new Object(200, new Vector3d(2,38,2), new Vector3d(3,4,5));
        Object object3 = new Object(200, new Vector3d(2,-20,2), new Vector3d(3,4,5));
        Object object4 = new Object(200, new Vector3d(2,4,2), new Vector3d(3,4,5));

        /**
         * Object listing
         */

        Object[] objects = {object1, object2, object3, object4};

        /**
         * Run the simulator for the specified amount of rounds
         */
        int rounds = 2;
        Simulator.run(rounds, objects);
    }
}
