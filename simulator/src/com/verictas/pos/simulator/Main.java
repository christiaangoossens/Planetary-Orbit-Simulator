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

    public static int version = 1;

    public static void main(String[] args) {
        /**
         * Object definitions
         */

        /**
         * Definitions for the ecliptic plane (by 1st of january 2016)
         */
        Object sun = new Object("Sun", 1.988544E30, AU.convertToMeter(new Vector3d(3.737881713150281E-03,1.402397586692506E-03,-1.612700291840256E-04)), AU.convertToMetersPerSecond(new Vector3d(8.619338996535534E-07,6.895607793642275E-06,-2.794074909231784E-08)));
        Object earth = new Object("Earth", 5.97219E24, AU.convertToMeter(new Vector3d(-1.630229002588497E-01,9.704723344534316E-01,-1.955367328932975E-04)), AU.convertToMetersPerSecond(new Vector3d(-1.723383356491747E-02,-2.969134550063944E-03,-4.433758674928828E-07)));
        Object moon = new Object("The Moon", 734.9E20, AU.convertToMeter(new Vector3d(-1.657103868749121E-01,9.706382026425473E-01,-1.879812512691582E-04)), AU.convertToMetersPerSecond(new Vector3d(-1.728100931961937E-02,-3.525371122447976E-03,4.909148618073602E-05)));
        Object jupiter = new Object("Jupiter", 1898.13E24, AU.convertToMeter(new Vector3d(-5.172279968303672E+00,1.591564562098799E+00,1.090553487095606E-01)), AU.convertToMetersPerSecond(new Vector3d(-2.306423668033420E-03,-6.856869314900905E-03,8.012916249248967E-05)));
        Object saturn = new Object("Saturn", 5.68319E26, AU.convertToMeter(new Vector3d(-3.710637850378867E+00,-9.289569433157130E+00,3.091990731378936E-01)), AU.convertToMetersPerSecond(new Vector3d(4.874750391005278E-03,-2.086615906689840E-03,-1.574898601194673E-04)));
        Object venus = new Object("Venus", 48.685E23, AU.convertToMeter(new Vector3d(-7.130901319004951E-01,-5.719763212192740E-02,4.040076577877051E-02)), AU.convertToMetersPerSecond(new Vector3d(1.525993024372452E-03,-2.024175581604569E-02,-3.656582385749146E-04)));
        Object mars = new Object("Mars", 6.4185E23, AU.convertToMeter(new Vector3d(-1.644664047074283E+00,1.714211195991345E-01,4.385749324150048E-02)), AU.convertFromMetersPerSecond(new Vector3d(-9.128062787682906E-04, -1.271783289037382E-02, -2.442517367300464E-04)));
        Object pluto = new Object("Pluto", 1.307E22, AU.convertToMeter(new Vector3d(8.535178336776600E+00,-3.187687983153820E+01,9.421570822362236E-01)), AU.convertFromMetersPerSecond(new Vector3d(3.105916866228581E-03, 1.759704223757070E-04, -9.146208184741589E-04)));
        Object neptune = new Object("Neptune", 102.41E24, AU.convertToMeter(new Vector3d(2.795458622849629E+01,-1.077602237438394E+01,-4.223299945454949E-01)), AU.convertFromMetersPerSecond(new Vector3d(1.108107308612818E-03, 2.948021656576779E-03, -8.584675894389943E-05)));
        Object uranus = new Object("Uranus", 86.8103E24, AU.convertToMeter(new Vector3d(1.887206485673029E+01,6.554830107743496E+00,-2.201473388797619E-01)), AU.convertFromMetersPerSecond(new Vector3d(-1.319173006464416E-03, 3.532006412470987E-03, 3.002475806591822E-05)));
        Object charon = new Object("Charon", 1.53E21, AU.convertToMeter(new Vector3d(8.535206843097511E+00,-3.187692375327401E+01,9.420370068039806E-01)), AU.convertFromMetersPerSecond(new Vector3d(3.015458707073605E-03, 8.495285732817140E-05, -9.028237165874783E-04)));

        // PWS Objects
        Object object1 = new Object("Sedna", 4E21, AU.convertToMeter(new Vector3d(4.831201219703945E+01, 6.863113643822504E+01, -1.773001247239095E+01)), AU.convertToMetersPerSecond(new Vector3d(-2.401309021644802E-03, 7.269559406640982E-04, 1.704114106899654E-04)));
        Object object2 = new Object("2012 VP113", 2.7E18, AU.convertToMeter(new Vector3d(5.074554081273273E+01, 6.194684521116067E+01, -2.303377758579428E+01)), AU.convertToMetersPerSecond(new Vector3d(-1.390042223661063E-03, 1.919356165611094E-03, 6.083057470436023E-04)));
        Object object3 = new Object("2004 VN112", 0, AU.convertToMeter(new Vector3d(3.338469440683407E+01, 3.296760926256486E+01, -8.176834813898699E+00)), AU.convertToMetersPerSecond(new Vector3d(-1.830443771273609E-03, 2.551493797427650E-03, 1.295080364913495E-03)));
        Object object4 = new Object("2007 TG442", 0, AU.convertToMeter(new Vector3d(-2.216102118938070E+00, -5.957656766688118E-01, -9.228532887388547E-03)), AU.convertToMetersPerSecond(new Vector3d(1.973707536998759E-03, -1.106231446142322E-02, -1.188438173809993E-04)));
        Object object5 = new Object("2013 RF98", 0, AU.convertToMeter(new Vector3d(2.809064890818173E+01, 2.117251775628629E+01, -1.015547278525787E+01)), AU.convertToMetersPerSecond(new Vector3d(-1.408524658517317E-03, 3.354634129283988E-03, 1.461376116722572E-03)));
        Object object6 = new Object("2010 GB174", 0, AU.convertToMeter(new Vector3d(-6.661904379651325E+01, -8.411238128232725E+00, 2.212233193483758E+01)), AU.convertFromMetersPerSecond(new Vector3d(-9.610782795963537E-04, -2.406268777135870E-03, 9.081217152229448E-04)));

        /**
         * Object listing
         */

        Object[] objects = {}; // Fill in the objects to be simulated


        /**
         * Run the simulator for the specified objects
         */
        Simulator.run(objects);
    }
}
