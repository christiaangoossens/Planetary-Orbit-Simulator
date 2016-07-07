package com.verictas.pos.simulator;

import javax.vecmath.*;

public class Main {

    public static void main(String[] args) {
        Object object1 = new Object(1000, new Vector3d(1,2,3), new Vector3d(0,4,3));
        Object object2 = new Object(200, new Vector3d(2,38,2), new Vector3d(3,4,5));
        Object object3 = new Object(200, new Vector3d(2,-20,2), new Vector3d(3,4,5));
        Object object4 = new Object(200, new Vector3d(2,4,2), new Vector3d(3,4,5));

        // Make a list of all the objects
        Object[] objects = {object1, object2, object3, object4};

        // Start the simulation
        Simulator.run(2, objects);
    }
}
