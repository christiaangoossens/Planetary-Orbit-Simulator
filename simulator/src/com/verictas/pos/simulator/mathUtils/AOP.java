package com.verictas.pos.simulator.mathUtils;

import javax.vecmath.Vector3d;

public class AOP {
    /**
     * Helper class for calculating the argument of periapsis
     */
    public static double calculate(Vector3d ascendingNode, Vector3d perihelion, Vector3d aphelion) {
        Vector3d eccentricity = new Vector3d(0,0,0);
        eccentricity.sub(perihelion, aphelion);
        return ascendingNode.angle(eccentricity);
    }
}
