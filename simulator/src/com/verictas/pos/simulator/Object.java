package com.verictas.pos.simulator;
import javax.vecmath.*;
import java.lang.*;

public class Object {
    public double mass;
    public Vector3f position;
    public Vector3f speed;
    private double gravitationalConstant = 6.67384E-11;

    public Object(double mass, Vector3f position, Vector3f speed) {
        this.mass = mass;
        this.position = position;
        this.speed = speed;
    }

    public void setSpeed(Vector3f speed) {
        this.speed = speed;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public String toString() {
        return "Mass: " + this.mass + " & Position: " + this.position + " & Speed: " + this.speed;
    }

    public Vector3f getForceOnObject(Object secondObject) {
        double scale = gravitationalConstant * ((this.mass * secondObject.mass) / Math.pow(getDistance(secondObject).length(), 3.0));
        Vector3f force = getDistance(secondObject);
        force.scale((float) scale);
        return force;
    }

    private Vector3f getDistance(Object secondObject) {
        Vector3f distance = new Vector3f(0,0,0); // Empty
        distance.sub(this.position, secondObject.position);
        return distance;
    }
}
