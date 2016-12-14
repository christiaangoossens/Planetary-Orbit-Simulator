package com.verictas.pos.simulator;

import com.verictas.pos.simulator.mathUtils.AOP;

import javax.vecmath.Vector3d;

public class Test {
    public static void main(String[] args) {
        Vector3d fakePos = new Vector3d(1E20,2E20,3E20);
        Vector3d fakeSpeed = new Vector3d(4E20,5E20,6E20);

        double aop = AOP.calculate(fakePos, fakeSpeed);
        System.out.println("END:" + fakePos);
        System.out.println("END:" + fakeSpeed);
        System.out.println("END:" + aop);
    }
}
