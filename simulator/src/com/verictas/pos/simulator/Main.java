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
     * The full license is included in the git repository as LICENSE.md
     */

    public static void main(String[] args) {
        /**
         * Object definitions
         */

        /**
         * Definitions for the ecliptic plane
         */
        //Object sun = new Object("Sun", 1.988544E30, AU.convertToMeter(new Vector3d(3.621484938699030E-03,3.203347049968909E-03,-1.609087138389905E-04)), AU.convertToMetersPerSecond(new Vector3d(-1.730306264794065E-06,6.909301960615850E-06,3.332250766613383E-08)));
        //Object venus = new Object("Venus", 48.685E23, AU.convertToMeter(new Vector3d(-3.786926829662159E-01,-6.122709221027441E-01,1.346180701578967E-02)), AU.convertToMetersPerSecond(new Vector3d(1.703979708314098E-02,-1.075790617185284E-02,-1.130972411646143E-03)));
        //Object earth = new Object("Earth", 5.97219E24, AU.convertToMeter(new Vector3d(1.000272608326749E+00,-1.305632418724720E-01,-1.614384880329670E-04)), AU.convertToMetersPerSecond(new Vector3d(2.003180730888720E-03,1.698793770993201E-02,5.869001824818362E-08)));
        //Object mars = new Object("Mars", 6.4185E23, AU.convertToMeter(new Vector3d(8.638055532014732E-01,-1.094520306989018E+00,-4.427515002554464E-02)), AU.convertToMetersPerSecond(new Vector3d(1.154235320339802E-02,9.839355267552327E-03,-7.723750026136471E-05)));
        //Object jupiter = new Object("Jupiter", 1898.13E24, AU.convertToMeter(new Vector3d(-5.440309619306835E+00,-2.383659935837559E-01,1.226571001615609E-01)), AU.convertToMetersPerSecond(new Vector3d(2.422143907277735E-04,-7.182284468246539E-03,2.440789748210396E-05)));
        //Object moon = new Object("The Moon", 734.9E20, AU.convertToMeter(new Vector3d(1.002390058141768E+00,-1.318677081380600E-01,-1.051759034600983E-04)), AU.convertToMetersPerSecond(new Vector3d(2.294349896503608E-03,1.752303034437222E-02,-5.522655228080146E-05)));

        // 1 januari startdag
       Object sun = new Object("Sun", 1.988544E30, AU.convertToMeter(new Vector3d(3.737881713150281E-03,1.402397586692506E-03,-1.612700291840256E-04)), AU.convertToMetersPerSecond(new Vector3d(8.619338996535534E-07,6.895607793642275E-06,-2.794074909231784E-08)));
       Object earth = new Object("Earth", 5.97219E24, AU.convertToMeter(new Vector3d(-1.630229002588497E-01,9.704723344534316E-01,-1.955367328932975E-04)), AU.convertToMetersPerSecond(new Vector3d(-1.723383356491747E-02,-2.969134550063944E-03,-4.433758674928828E-07)));
        Object moon = new Object("The Moon", 734.9E20, AU.convertToMeter(new Vector3d(-1.657103868749121E-01,9.706382026425473E-01,-1.879812512691582E-04)), AU.convertToMetersPerSecond(new Vector3d(-1.728100931961937E-02,-3.525371122447976E-03,4.909148618073602E-05)));

        /**
         * Object listing
         */

        Object[] objects = {sun, earth, moon};


        /**
         * Run the simulator for the specified objects
         */
        Simulator.run(objects);
    }
}
