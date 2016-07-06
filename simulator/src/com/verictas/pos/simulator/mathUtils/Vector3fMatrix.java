package com.verictas.pos.simulator.mathUtils;

import javax.vecmath.GMatrix;
import javax.vecmath.Vector3f;

public class Vector3fMatrix extends GMatrix {
    /**
     * Creates a new matrix with some helper functions for use with Vector3f. The created matrix will be empty.
     * @param n The number of rows.
     * @param m The number of columns.
     */
    public Vector3fMatrix(int n, int m) {
        // Change the size to suit Vector3f
        super(n, m * 3);
        this.setZero();
    }

    /**
     * Set the size of the matrix in the amount of vectors (e.g. a 1 x 3 vector matrix gets converted to a 1 x 9 storage matrix).
     * @param n The amount of rows
     * @param m The amount of columns expressed in vectors (1 vector = 3 values).
     */
    public void setSizeInVectors(int n, int m) {
        this.setSize(n, m * 3);
    }

    /**
     * Provides a function for putting the matrix into String form for visualisation.
     * @return String
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer(this.getNumRow() * this.getNumCol() * 8);

        for(int n = 0; n < this.getNumRow(); ++n) {
            for(int m = 0; m < this.getNumCol(); ++m) {
                if ((m + 1) == 1 || m % 3 == 0) {
                    // If m is 1 or a multiple of 4, begin the bracket.
                    buffer.append("(").append(this.getElement(n, m)).append(", ");
                } else if ((m + 1) % 3 == 0) {
                    // If m is a multiple of 3, close the bracket
                    buffer.append(this.getElement(n, m)).append(")\t\t");
                } else {
                    buffer.append(this.getElement(n, m)).append(", ");
                }
            }

            buffer.append("\n");
        }

        return buffer.toString();
    }

    /**
     * Provides a translator from the vector positions (e.g. the second vector starts at position 1) to the matrix positions (the second vector starts at position 3).
     * @param n The vector positions row
     * @param m The vector positions column
     * @return void
     */
    private int[] translatePosition(int n, int m) {
        return new int[]{n, m * 3};
    }

    /**
     * Provides a way to set a vector into a certain position in the matrix
     * @param settable The vector you want to put in the matrix
     * @param n The row to insert into
     * @param m The column to insert into
     */
    public void setPosition(Vector3f settable, int n, int m) {
        int[] position = translatePosition(n, m);
        n = position[0];
        m = position[1];

        this.setElement(n, m, settable.x);
        this.setElement(n, m + 1, settable.y);
        this.setElement(n, m + 2, settable.z);
    }

    /**
     * Provides a way to get a vector from a certain position in the matrix
     * @param n The row to get from
     * @param m The column to get from
     * @return Vector3f The vector in that position
     */
    public Vector3f getPosition(int n, int m) {
        int[] position = translatePosition(n, m);
        n = position[0];
        m = position[1];

        float x = (float) this.getElement(n, m);
        float y = (float) this.getElement(n, m + 1);
        float z = (float) this.getElement(n, m + 2);
        return new Vector3f(x, y, z);
    }
}