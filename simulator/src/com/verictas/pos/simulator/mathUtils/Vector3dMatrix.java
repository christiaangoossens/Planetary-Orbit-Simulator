package com.verictas.pos.simulator.mathUtils;

import javax.vecmath.GMatrix;
import javax.vecmath.Vector3d;

public class Vector3dMatrix extends GMatrix {
    /**
     * Creates a new matrix with some helper functions for use with Vector3f. The created matrix will be empty.
     * @param n The number of rows.
     * @param m The number of columns.
     */
    public Vector3dMatrix(int n, int m) {
        // Change the size to suit Vector3d
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
    public void setPosition(Vector3d settable, int n, int m) {
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
     * @return Vector3d The vector in that position
     */
    public Vector3d getPosition(int n, int m) {
        int[] position = translatePosition(n, m);
        n = position[0];
        m = position[1];

        double x = this.getElement(n, m);
        double y = this.getElement(n, m + 1);
        double z = this.getElement(n, m + 2);
        return new Vector3d(x, y, z);
    }

    /**
     * Provides a way to calculate the result vector of a certain row
     * @param row The row to calculate the total of
     * @return Vector3d
     */
    public Vector3d getRowTotal(int row) {
        double[] rowTotal = new double[this.getNumCol()];
        this.getRow(row, rowTotal);

        // Create an empty vector to store the result
        Vector3d resultVector = new Vector3d(0,0,0);

        for(int i = 0; i < this.getNumCol(); i = i + 3) {
            // For every third entry (including 0).
            double x = this.getElement(row, i);
            double y = this.getElement(row, i + 1);
            double z = this.getElement(row, i + 2);
            resultVector.add(new Vector3d(x, y, z));
        }

        return resultVector;
    }

    /**
     * Provides a way to calculate the result vector of a certain column
     * @param column The column to calculate the total of
     * @return Vector3d
     */
    public Vector3d getColumnTotal(int column) {
        double[] columnTotal = new double[this.getNumRow()];

        // Translate the column number to the correct vector column
        int[] position = translatePosition(0, column);
        column = position[1];

        this.getColumn(column, columnTotal);

        // Create an empty vector to store the result
        Vector3d resultVector = new Vector3d(0,0,0);

        for(int i = 0; i < this.getNumRow(); i++) {
            // For every entry (including 0).
            double x = this.getElement(i, column);
            double y = this.getElement(i, column + 1);
            double z = this.getElement(i, column + 2);
            resultVector.add(new Vector3d(x, y, z));
        }

        return resultVector;
    }
}