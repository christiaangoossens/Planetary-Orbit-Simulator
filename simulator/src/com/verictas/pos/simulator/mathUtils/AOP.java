package com.verictas.pos.simulator.mathUtils;
import javax.vecmath.Vector3d;

public class AOP {
    public static double calculate(Vector3d pos, Vector3d speed) {
        // ORBITAL MOMENTUM VECTOR
        Vector3d orbitalMomentum = new Vector3d(0,0,0);
        orbitalMomentum.cross(speed, pos);

        //System.out.println("h = " + orbitalMomentum);

        // ACCENDING NODE VECTOR
        Vector3d ascendingNode = new Vector3d(0,0,0);
        ascendingNode.cross(new Vector3d(0,0,1), orbitalMomentum);

        //System.out.println("n = " + ascendingNode);

        // ECCENTRICITY VECTOR
        double mu = 1.32712440018E20;

        Vector3d upCross = new Vector3d(0,0,0);
        upCross.cross(speed, orbitalMomentum);
        upCross.scale(1/mu);
        //System.out.println("r* x h * 1/mu = " + upCross);

        double posLength = pos.length();
        Vector3d rightPos = new Vector3d(pos);
        rightPos.scale(1/posLength);
        //System.out.println("r/||r|| = " + rightPos);

        Vector3d eccentricity = new Vector3d(0,0,0);
        eccentricity.sub(upCross, rightPos);
        //System.out.println("e = " + eccentricity);

        // AOP
        double aop;
        if (eccentricity.getZ() < 0) {
            aop = (2 * Math.PI) - ascendingNode.angle(eccentricity);
        } else {
            aop = ascendingNode.angle(eccentricity);
        }

        return aop;
    }
}
