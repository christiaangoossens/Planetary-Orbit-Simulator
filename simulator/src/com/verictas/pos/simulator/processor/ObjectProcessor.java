package com.verictas.pos.simulator.processor;

import com.verictas.pos.simulator.Object;
import javax.vecmath.Vector3d;
import java.util.HashMap;
import java.util.Map;

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

    public Vector3d absoluteMax;
    public Vector3d absoluteMin;

    public double referenceZ;

    public HashMap<Integer, Vector3d[]> history = new HashMap<>();

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
     * Keep an history of the object position and speed (for logging and further processing)
     */

    public void processHistory(int round) {
        this.history.put(round, new Vector3d[] {this.thisObject.position, this.thisObject.speed});
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
     * Get the absolute maximum and minimum positions (max z and min z)
     */

    public void calculateTops() {
        if (this.absoluteMax == null) {
            this.absoluteMax = this.thisObject.position;
        }

        if (this.absoluteMin == null) {
            this.absoluteMin = this.thisObject.position;
        }

        if (this.thisObject.position.getZ() > this.absoluteMax.getZ()) {
            this.absoluteMax = this.thisObject.position;
        }

        if (this.thisObject.position.getZ() < this.absoluteMin.getZ()) {
            this.absoluteMin = this.thisObject.position;
        }
    }
    /**
     * Process the nodes
     */

    public void processNodes() {
        this.referenceZ = (this.absoluteMin.getZ() + this.absoluteMax.getZ()) / 2;

        // Loop through the entire history
        for (Map.Entry<Integer, Vector3d[]> entry : this.history.entrySet()) {
            Integer round = entry.getKey();
            Vector3d[] vectorArray = entry.getValue();

            if (this.history.get(round + 1) != null) {
                // There is a next key!
                if (vectorArray[0].getZ() < referenceZ && this.history.get(round + 1)[0].getZ() > referenceZ) {
                    // This point is below the reference height and the next is above. This point is the ascending node (with positive z)
                    if (referenceZ <= 0) {
                        // Descending
                        this.descendingNode = vectorArray[0];
                        System.out.println("INFO:: Found a descending node at round " + round + " at position: " + vectorArray[0]);
                    } else {
                        // Ascending
                        this.ascendingNode = vectorArray[0];
                        System.out.println("INFO:: Found a ascending node at round " + round + " at position: " + vectorArray[0]);
                    }
                } else if (vectorArray[0].getZ() > referenceZ && this.history.get(round + 1)[0].getZ() < referenceZ) {
                    // This point is above the reference height and the next is below. This point is the descending node (with positive z)
                    if (referenceZ <= 0) {
                        // Ascending
                        this.ascendingNode = vectorArray[0];
                        System.out.println("INFO:: Found a ascending node at round " + round + " at position: " + vectorArray[0]);
                    } else {
                        // Descending
                        this.descendingNode = vectorArray[0];
                        System.out.println("INFO:: Found a descending node at round " + round + " at position: " + vectorArray[0]);
                    }
                }
            }
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
        history = new HashMap<>();
        absoluteMax = null;
        absoluteMin = null;
        referenceZ = -1;
    }
}
