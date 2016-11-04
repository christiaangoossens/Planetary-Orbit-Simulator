package com.verictas.pos.simulator;

import javax.vecmath.Vector3d;

/**
 * Storage object for storing nodes on the graph
 */
public class Node extends Vector3d {
    public int round;

    /**
     * Constructor for casting
     * @param vector
     */
    public Node(Vector3d vector) {
        this.set(vector);
    }

    /**
     * Constructor for empty creation
     */
    public Node() {
        this.set(new Vector3d(0,0,0));
    }

    /**
     * Sets the stored round associated with this node
     * (It will most likely be the round when this node is reached)
     * @param round
     */
    public void setRound(int round) {
        this.round = round;
    }

    public boolean empty() {
        if (this.getX() == 0 && this.getY() == 0 && this.getZ() == 0) {
            return true;
        }
        return false;
    }
}
