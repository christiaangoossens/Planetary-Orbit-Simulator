package com.verictas.pos.simulator.mathUtils;

import javax.vecmath.Vector3d;

public  class AU {
    /**
     * Helper class for working with astronomical units
     */
    public static Vector3d convertToMeter(Vector3d input) {
        Vector3d output = new Vector3d(input);

        // Convert AU to m by NASA
        output.scale(149597870.700); // Number to large when multiplied with 1000
        output.scale(1000);

        return output;
    }

    public static Vector3d convertToMetersPerSecond(Vector3d input) {
        Vector3d output = new Vector3d(input);

        // 1 AU/day to M/s
        output.scale(1731456.84);

        return output;
    }

    public static Vector3d convertFromMeter(Vector3d input) {
        Vector3d output = new Vector3d(input);

        // Convert m to AU by NASA
        output.scale(6.6845871E-12);

        return output;
    }

    public static Vector3d convertFromMetersPerSecond(Vector3d input) {
        Vector3d output = new Vector3d(input);

        // Convert seconds to days by NASA
        output.scale(5.77548327E-7);

        return output;
    }
}
