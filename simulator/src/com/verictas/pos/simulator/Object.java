package com.verictas.pos.simulator;
import javax.vecmath.*;
import java.lang.*;

public class Object {
    public double mass;
    public Vector3d position;
    public Vector3d speed;
    private double gravitationalConstant = 6.67384E-11;

    /**
     * Constructs an object
     * @param mass The mass of the object
     * @param position The position vector of the object
     * @param speed The speed vector of the object
     */
    public Object(double mass, Vector3d position, Vector3d speed) {
        this.mass = mass;
        this.position = position;
        this.speed = speed;
    }

    /**
     * Sets the speed vector of an object
     * @param speed Current speed vector
     */
    public void setSpeed(Vector3d speed) {
        this.speed = speed;
    }

    /**
     * Sets the position vector of an object.
     * @param position Current position vector
     */
    public void setPosition(Vector3d position) {
        this.position = position;
    }

    /**
     * Changes an object into readable form
     * @return String
     */
    public String toString() {
        return "Mass: " + this.mass + " & Position: " + this.position + " & Speed: " + this.speed;
    }

    /**
     * Calculates the force of the passed object on the current object.
     * @param secondObject The passed object
     * @return Vector3d The gravitational force
     */
    public Vector3d getForceOnObject(Object secondObject) {
        double scale = gravitationalConstant * ((this.mass * secondObject.mass) / Math.pow(getDistance(secondObject).length(), 3.0));
        Vector3d force = getDistance(secondObject);
        force.scale(scale);
        return force;
    }

    /**
     * Get the vector distance between the current position vector and the position vector of the passed object.
     * @param secondObject The passed object.
     * @return Vector3d The distance vector
     */
    private Vector3d getDistance(Object secondObject) {
        Vector3d distance = new Vector3d(0,0,0); // Empty
        distance.sub(this.position, secondObject.position);
        return distance;
    }
}
