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

    public Node ascendingNode;
    public Node descendingNode;

    public Node absoluteMax;
    public Node absoluteMin;

    private Node carryOverNode;
    private int carryOverBit;

    public double referenceZ;

    private HashMap<Integer, Vector3d[]> history = new HashMap<>();

    private double lastMaxRound = -1;
    private double lastMinRound = -1;

    private boolean skipNodes = false;

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

    public void processHistory() {
        this.history.put(Simulator.round, new Vector3d[] {this.thisObject.position, this.thisObject.speed});
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
     * Get the absolute maximum and minimum positions (max z and min z)
     */

    public void calculateTops() {
        if (this.absoluteMax == null || this.absoluteMax.empty()) {
            this.absoluteMax = new Node(this.thisObject.position);
            this.absoluteMax.setRound(Simulator.round);
        }

        if (this.absoluteMin == null || this.absoluteMin.empty()) {
            this.absoluteMin = new Node(this.thisObject.position);
            this.absoluteMin.setRound(Simulator.round);
        }

        if (this.thisObject.position.getZ() > this.absoluteMax.getZ()) {
            this.absoluteMax = new Node(this.thisObject.position);
            this.absoluteMax.setRound(Simulator.round);
        }

        if (this.thisObject.position.getZ() < this.absoluteMin.getZ()) {
            this.absoluteMin = new Node(this.thisObject.position);
            this.absoluteMin.setRound(Simulator.round);
        }
    }
    /**
     * Process the nodes
     */

    public void processNodes() {
        /**
         * Determine how the starting positions are
         */

        /**
         * Carry out carry over checking
         */

        if (this.carryOverNode != null) {
            // There is a node present in memory from last round. We should check what the carryOverBit is, to see if it's a maximum or a minimum
            if (this.carryOverBit == 1) {
                // Last rounds node is a maximum, we're searching for a descending node
                Node result = this.findNode(this.absoluteMin, this.carryOverNode);

                if (!result.empty()) {
                    if (SimulatorConfig.logConsole) {
                        System.out.println("INFO:: Found descending node in round " + result.round + "\n");
                    }
                    this.descendingNode = result;
                }
            } else {
                // Last rounds node is a minimum, we're searching for an ascending node
                Node result = this.findNode(this.carryOverNode, this.absoluteMax);

                if (!result.empty()) {
                    if (SimulatorConfig.logConsole) {
                        System.out.println("INFO:: Found ascending node in round " + result.round + "\n");
                    }
                    this.ascendingNode = result;
                }
            }

            // Cleaning up
            this.carryOverNode = null;
            this.carryOverBit = -1;
        }

        /**
         * Carry out the normal checking
         */

        double minRound = this.absoluteMin.round;
        double maxRound = this.absoluteMax.round;

        if (minRound < maxRound) {
            // The minimum came before the maximum node, we're expecting to find the ascending node between the two
            // The maximum node should remain in memory to find the descending node next round

            Node result = this.findNode(this.absoluteMin, this.absoluteMax);

            if (!result.empty()) {
                if (SimulatorConfig.logConsole) {
                    System.out.println("INFO:: Found ascending node in round " + result.round + "\n");
                }
                this.ascendingNode = result;
            }

            this.carryOverNode = this.absoluteMax;
            this.carryOverBit = 1;
            this.cleanHistory(this.absoluteMax.round);
        } else {
            // The maximum came before the minimum node, we're expecting to find the descending node between the two
            // The minimum node should remain in memory to find the ascending node next round

            Node result = this.findNode(this.absoluteMin, this.absoluteMax);

            if (!result.empty()) {
                if (SimulatorConfig.logConsole) {
                    System.out.println("INFO:: Found descending node in round " + result.round + "\n");
                }
                this.descendingNode = result;
            }

            this.carryOverNode = this.absoluteMin;
            this.carryOverBit = 0;
            this.cleanHistory(this.absoluteMin.round);
        }

    }

    private Node findNode(Node min, Node max) {
        this.referenceZ = (min.getZ() + max.getZ()) / 2;

        if (SimulatorConfig.logConsole) {
            System.out.println("INFO:: Called node finder with min: " + min + " (round " + min.round + ") and max: " + max + " (round " + max.round + ") and a reference height of " + referenceZ);
        }

        Node returnNode = new Node();

        if (lastMaxRound == -1 || lastMaxRound == -1) {
            lastMinRound = min.round;
            lastMaxRound = max.round;
        } else {
            // You should compare these values to check.
            if (lastMaxRound < min.round && max.round < min.round && min.round == lastMinRound) {
                // max2 > max1 > (min1 = min2)
                System.out.println("WARNING:: This round's values for the nodes shouldn't be trusted. They are calculated incorrectly.");
                this.skipNodes = true;
            }

            if (lastMinRound < max.round && min.round < max.round && max.round == lastMaxRound) {
                // (max1 = max2) > min1 > min2
                System.out.println("WARNING:: This round's values for the nodes shouldn't be trusted. They are calculated incorrectly.");
                this.skipNodes = true;
            }
        }

        for (Map.Entry<Integer, Vector3d[]> entry : this.history.entrySet()) {
            Integer round = entry.getKey();
            Vector3d[] vectorArray = entry.getValue();

            boolean roundCheck;

            if (min.round < max.round) {
                roundCheck = min.round < round && round < max.round;
            } else {
                roundCheck = max.round < round && round < min.round;
            }

            if ((this.history.get(round + 1) != null) && roundCheck) {
                // There is a next key and this key is within logical bounds

                if (vectorArray[0].getZ() < referenceZ && this.history.get(round + 1)[0].getZ() > referenceZ) {
                    returnNode = new Node(vectorArray[0]);
                    returnNode.setRound(round);
                } else if (vectorArray[0].getZ() > referenceZ && this.history.get(round + 1)[0].getZ() < referenceZ) {
                    returnNode = new Node(vectorArray[0]);
                    returnNode.setRound(round);
                }
            }
        }

        if (!returnNode.empty()) {
            return returnNode;
        } else {
            return new Node();
        }
    }

    public boolean checkNodes() {
        return !this.skipNodes;
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
        ascendingNode = new Node();
        descendingNode = new Node();
        absoluteMax = new Node();
        absoluteMin = new Node();
        referenceZ = -1;
        lastMaxRound = -1;
        lastMinRound = -1;
        skipNodes = false;
    }

    /**
     * Clears all entries from history before the given key
     * @param key
     */
    public void cleanHistory(int key) {
        for(Iterator<Map.Entry<Integer, Vector3d[]>> it = this.history.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Integer, Vector3d[]> entry = it.next();
            if(entry.getKey() < key) {
                it.remove();
            }
        }
    }
}
