package com.verictas.pos.simulator.processor;

import com.verictas.pos.simulator.Object;

import javax.vecmath.Vector3d;

public class SimpleObjectProcessor {
    private Object thisObject;
    public void setObjectData(Object object) {
        this.thisObject = object;
    }

    public void calculateAOP() {
        // ORBITAL MOMENTUM VECTOR
        Vector3d orbitalMomentum = new Vector3d(0,0,0);
        Object object = this.thisObject;

        orbitalMomentum.cross(object.speed, object.position);

        // ACCENDING NODE VECTOR
        Vector3d ascendingNode = new Vector3d(0,0,0);
        ascendingNode.cross(new Vector3d(0,0,1), orbitalMomentum);


        // ECCENTRICITY VECTOR
        double mu = 1.32712440018E20;

        Vector3d upCross = new Vector3d(0,0,0);
        upCross.cross(object.speed, orbitalMomentum);
        upCross.scale(1/mu);

        double posLength = object.position.length();
        Vector3d rightPos = object.position;
        rightPos.scale(1/posLength);

        Vector3d eccentricity = new Vector3d(0,0,0);
        eccentricity.sub(upCross, rightPos);

        // AOP
        double aop;
        if (eccentricity.getZ() < 0) {
            aop = (2 * Math.PI) - ascendingNode.angle(eccentricity);
        } else {
            aop = ascendingNode.angle(eccentricity);
        }

        System.out.println("Orbital momentum vector: " + orbitalMomentum + " & accending node: " + ascendingNode + " & eccentricity vector: " + eccentricity + " & aop: " + Math.toDegrees(aop));
    }
}
