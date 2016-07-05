package com.verictas.pos.simulator;
import javax.vecmath.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        Object object1 = new Object(10E8, new Vector3f(1,2,3), new Vector3f(0,4,3));
        Object object2 = new Object(20E4, new Vector3f(2,38,2), new Vector3f(3,4,5));

        System.out.println(object1.toString());
        System.out.println(object2.toString());
        System.out.println(object1.getForceOnObject(object2));
    }
}
