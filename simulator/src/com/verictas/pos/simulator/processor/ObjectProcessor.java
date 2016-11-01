package com.verictas.pos.simulator.processor;

import com.verictas.pos.simulator.Object;
import com.verictas.pos.simulator.SimulatorConfig;
import com.verictas.pos.simulator.mathUtils.AU;

import javax.vecmath.Vector3d;

public class ObjectProcessor {
    public Vector3d aphelion;
    public Vector3d perihelion;
    public double aphelionDistance = -1;
    public double perihelionDistance = -1;

    public Object thisObject;
    public Object referenceObject;

    public Vector3d startingPosition;
    public double lastStartDistance = -1;
    public double beforeLastStartDistance = -1;

    public Vector3d ascendingNode;
    public Vector3d descendingNode;

    public double zAxisDistance = -1;
    public double pastzAxisDistance = -1;
    public Vector3d lastPos;

    public void setStartingPosition(Vector3d position) {
        this.startingPosition = position;
    }

    public void setObjectData(Object object) {
        this.thisObject = object;
    }

    public void setReferenceObjectData(Object object) {
        this.referenceObject = object;
    }

    /**
     * Processes the aphelion & perihelion
     */
    public void processAphelionAndPerihelion() {
        double sunDistance = this.thisObject.getDistance(this.referenceObject).length();

        /**
         * Set the defaults
         */

        if (this.aphelionDistance == -1) {
            this.aphelionDistance = sunDistance;
        }

        if (this.perihelionDistance == -1) {
            this.perihelionDistance = sunDistance;
        }

        /**
         * Check if the aphelion or perihelion should be changed
         */

        if (sunDistance > aphelionDistance) {
            this.aphelion = this.thisObject.position;
            this.aphelionDistance = sunDistance;
        }

        if (sunDistance < perihelionDistance) {
            this.perihelion = this.thisObject.position;
            this.perihelionDistance = sunDistance;
        }
    }

    /**
     * Get the ascending node
     */

    public void processNodes() {
        double zAxisDistance = Math.abs(this.thisObject.position.getZ() - SimulatorConfig.z);

        if (this.pastzAxisDistance == -1) {
            this.pastzAxisDistance = zAxisDistance;
        }

        if (this.pastzAxisDistance != -1 && this.zAxisDistance == -1) {
            this.zAxisDistance = zAxisDistance;
        }

        if (this.zAxisDistance != -1 && this.pastzAxisDistance != 1) {
            if ((this.pastzAxisDistance > this.zAxisDistance && zAxisDistance > this.zAxisDistance) && (this.zAxisDistance < SimulatorConfig.zThreshold)) {
                if (SimulatorConfig.outputUnit.equals("AU")) {
                    System.out.println("INFO:: Found a node within the threshold at " + AU.convertFromMeter(this.lastPos) + " (in AU) for object " + this.thisObject.name + "!");
                } else {
                    System.out.println("INFO:: Found a node within the threshold at " + this.lastPos + " (in m) for object " + this.thisObject.name + "!");
                }

                if ((this.lastPos.getZ() - this.thisObject.position.getZ()) < 0) {
                    if (SimulatorConfig.z < 0) {
                        // The reference plane is in negative z, so you have gone up!
                        this.ascendingNode = this.lastPos;
                        System.out.println("INFO:: Detected node as: ASCENDING NODE!");
                    } else {
                        // The reference plane is in positive z, so you have gone down!
                        this.descendingNode = this.lastPos;
                        System.out.println("INFO:: Detected node as: DESCENDING NODE!");
                    }
                } else {
                    if (SimulatorConfig.z < 0) {
                        // The reference plane is in negative z, so you have gone down!
                        this.descendingNode = this.lastPos;
                        System.out.println("INFO:: Detected node as: DESCENDING NODE!");
                    } else {
                        // The reference plane is in positive z, so you have gone up!
                        this.ascendingNode = this.lastPos;
                        System.out.println("INFO:: Detected node as: ASCENDING NODE!");
                    }
                }
            }

            this.pastzAxisDistance = this.zAxisDistance;
            this.zAxisDistance = zAxisDistance;
        }

        this.lastPos = this.thisObject.position;
    }

    /**
     * Processes the round check
     */
    public boolean processRoundCheck() {
        double startDistance = this.thisObject.getDistance(this.startingPosition).length();
        boolean fullRotation = false;

        /**
         * Check if all are set and shuffle!
         */

        if (beforeLastStartDistance != -1 && lastStartDistance != -1) {
            // Ready to go!
            if (beforeLastStartDistance > lastStartDistance && startDistance > lastStartDistance) {
                // Last point was the closest to the starting position overall!
                fullRotation = true;
                System.out.println("INFO:: Object " + this.thisObject.name + " has made a full rotation last round.");
            }

            beforeLastStartDistance = lastStartDistance;
            lastStartDistance = startDistance;
        }

        /**
         * Check if 1st distance is set and 2nd isn't set
         */

        if (beforeLastStartDistance != -1 && lastStartDistance == -1) {
            lastStartDistance = startDistance;
        }

        /**
         * Check if the 1st distance isn't set
         */
        if (beforeLastStartDistance == -1) {
            beforeLastStartDistance = startDistance;
        }

        if(fullRotation) {
            return true;
        } else {
            return false;
        }
    }

    public void reset() {
        aphelionDistance = -1;
        perihelionDistance = -1;
        lastStartDistance = -1;
        beforeLastStartDistance = -1;
        aphelion = null;
        perihelion = null;
        ascendingNode = null;
        descendingNode = null;
        zAxisDistance = -1;
        lastPos = null;
    }
}
