package com.verictas.pos.simulator;
import javax.vecmath.*;
import java.lang.*;

public class Object {
    public double mass;
    public Vector3d position;
    public Vector3d speed;

    public Vector3d acceleration;
    public Vector3d oldAcceleration;

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
    public void setSpeed(double[] speed) {
        this.speed = new Vector3d(speed[0], speed[1], speed[2]);
    }

    /**
     * Gets the speed into a double[3]
     * @return double[3]
     */
    public double[] getSpeed() {
        double[] v = new double[3];
        this.speed.get(v);
        return v;
    }

    /**
     * Sets the position vector of an object.
     * @param position Current position vector
     */
    public void setPosition(Vector3d position) {
        this.position = position;
    }
    public void setPosition(double[] position) {
        this.position = new Vector3d(position[0], position[1], position[2]);
    }

    /**
     * Gets the position into a double[3]
     * @return double[3]
     */
    public double[] getPosition() {
        double[] r = new double[3];
        this.position.get(r);
        return r;
    }

    /**
     * Sets the acceleration vector of an object
     * @param acceleration Current acceleration vector
     */
    public void setAcceleration(Vector3d acceleration) { this.acceleration = acceleration; }
    public void setAcceleration(double[] acceleration) {
        this.acceleration = new Vector3d(acceleration[0], acceleration[1], acceleration[2]);
    }

    /**
     * Gets the acceleration into a double[3]
     * @return double[3]
     */
    public double[] getAcceleration() {
        double[] a = new double[3];
        this.acceleration.get(a);
        return a;
    }

    /**
     * Sets the acceleration vector of an object
     * @param acceleration Current acceleration vector
     */
    public void setOldAcceleration(Vector3d acceleration) { this.acceleration = acceleration; }
    public void setOldAcceleration(double[] acceleration) {
        this.oldAcceleration = new Vector3d(acceleration[0], acceleration[1], acceleration[2]);
    }

    /**
     * Gets the acceleration into a double[3]
     * @return double[3]
     */
    public double[] getOldAcceleration() {
        double[] a = new double[3];
        this.oldAcceleration.get(a);
        return a;
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
    public Vector3d getDistance(Object secondObject) {
        Vector3d distance = new Vector3d(0,0,0); // Empty
        distance.sub(this.position, secondObject.position);
        return distance;
    }

    /**
     * Updates the position based on dt
     * @param dt The difference in time
     */
    public void updatePosition(double dt) {
        // Write the vectors to double[3]
        double[] r = this.getPosition();
        double[] v = this.getSpeed();
        double[] a = this.getAcceleration();

        for (int i = 0; i != 3; i++){
            double dt2 = dt * dt;
            r[i] += v[i] * dt + 0.5 * a[i] * dt2;
        }

        // Write the doubles into the vectors to save them
        setPosition(r);
        setSpeed(v);
        setAcceleration(a);
    }

    /**
     * Updates the speed based on dt
     * @param dt The difference in speed
     */
    public void updateSpeed(double dt) {
        // Write the vectors to double[3]
        double[] v = this.getSpeed();
        double[] a = this.getAcceleration();
        double[] aold = this.getOldAcceleration();

        for (int i = 0; i != 3; i++){
            v[i] += 0.5 * dt *(a[i] + aold[i]);
        }

        setSpeed(v);
        setAcceleration(a);
        setOldAcceleration(aold);
    }

    /**
     * Updates the acceleration based on dt
     */
    public void updateAcceleration() {
        this.oldAcceleration = this.acceleration;
    }

    /**
     * Enacts a certain force on the object
     * @param force The force in N.
     */
    public void enactForceOnObject(Vector3d force) {
        double factor = 1/this.mass;
        Vector3d acceleration = force;
        acceleration.scale(factor);
        this.acceleration = acceleration;
    }
}
