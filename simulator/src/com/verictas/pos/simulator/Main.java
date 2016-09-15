package com.verictas.pos.simulator;

import com.verictas.pos.simulator.mathUtils.AU;

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
         * Object definitions (in 1990)
         */
        Object sun = new Object("Sun", 1.988544E30, AU.convertToMeter(new Vector3d(3.621484938699030E-03,3.203347049968909E-03,-1.609087138389905E-04)), AU.convertToMetersPerSecond(new Vector3d(-1.730306264794065E-06,6.909301960615850E-06,3.332250766613383E-08)));
        Object venus = new Object("Venus", 48.685E23, AU.convertToMeter(new Vector3d(-3.786926829662159E-01,-6.122709221027441E-01,1.346180701578967E-02)), AU.convertToMetersPerSecond(new Vector3d(1.703979708314098E-02,-1.075790617185284E-02,-1.130972411646143E-03)));
        Object earth = new Object("Earth", 5.97219E24, AU.convertToMeter(new Vector3d(1.000272608326749E+00,-1.305632418724720E-01,-1.614384880329670E-04)), AU.convertToMetersPerSecond(new Vector3d(2.003180730888720E-03,1.698793770993201E-02,5.869001824818362E-08)));
        Object mars = new Object("Mars", 6.4185E23, AU.convertToMeter(new Vector3d(8.638055532014732E-01,-1.094520306989018E+00,-4.427515002554464E-02)), AU.convertToMetersPerSecond(new Vector3d(1.154235320339802E-02,9.839355267552327E-03,-7.723750026136471E-05)));
        Object jupiter = new Object("Jupiter", 1898.13E24, AU.convertToMeter(new Vector3d(-5.440309619306835E+00,-2.383659935837559E-01,1.226571001615609E-01)), AU.convertToMetersPerSecond(new Vector3d(2.422143907277735E-04,-7.182284468246539E-03,2.440789748210396E-05)));

        /**
         * Object listing
         */

        Object[] objects = {sun, venus, earth, mars, jupiter};

        /**
         * Run the simulator for the specified objects
         */
        Simulator.run(objects);
    }
}
