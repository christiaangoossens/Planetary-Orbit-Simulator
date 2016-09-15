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
        Object object1 = new Object(1/6.67384E-11, new Vector3d(1,0,0), new Vector3d(0,0,0));
        Object object2 = new Object(1, new Vector3d(0,0,0), new Vector3d(0,0,0));

        /**
         * Object listing
         */

        Object[] objects = {object1, object2};

        /**
         * Run the simulator for the specified objects
         */
        Simulator.run(objects);
    }
}
