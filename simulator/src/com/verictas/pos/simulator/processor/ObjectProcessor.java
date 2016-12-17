package com.verictas.pos.simulator.processor;

import com.verictas.pos.simulator.Node;
import com.verictas.pos.simulator.Object;
import com.verictas.pos.simulator.Simulator;
import com.verictas.pos.simulator.SimulatorConfig;

import javax.vecmath.Vector3d;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ObjectProcessor {
    public Node aphelion;
    public Node perihelion;
    public double aphelionDistance = -1;
    public double perihelionDistance = -1;

    private Object thisObject;
    private Object referenceObject;

    private Vector3d startingPosition;
    private double lastStartDistance = -1;
    private double beforeLastStartDistance = -1;

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
            this.aphelion = new Node(this.thisObject.position);
            this.aphelion.setRound(Simulator.round);
            this.aphelionDistance = sunDistance;
        }

        if (sunDistance < perihelionDistance) {
            this.perihelion = new Node(this.thisObject.position);
            this.perihelion.setRound(Simulator.round);
            this.perihelionDistance = sunDistance;
        }
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
                if (SimulatorConfig.logConsole) {
                    System.out.println("INFO:: Object " + this.thisObject.name + " has made a full rotation last round.");
                }
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
        aphelion = new Node();
        perihelion = new Node();
    }
}
