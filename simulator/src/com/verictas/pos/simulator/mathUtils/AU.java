package com.verictas.pos.simulator.mathUtils;

import javax.vecmath.Vector3d;

public class AU {
    /**
     * Helper class for working with astronomical units
     */

    /**
     * Converts AU to meters
     * @param input Vector3d with values in AU
     * @return Vector3d with values in meter
     */
    public static Vector3d convertToMeter(Vector3d input) {
        Vector3d output = new Vector3d(input);

        // Convert AU to m by NASA
        output.scale(149597870.700); // Number to large when multiplied with 1000
        output.scale(1000);

        return output;
    }

    /**
     * Converts AU/day to m/s
     * @param input Vector3d with values in AU/day
     * @return Vector3d with values in m/s
     */
    public static Vector3d convertToMetersPerSecond(Vector3d input) {
        Vector3d output = new Vector3d(input);

        // 1 AU/day to M/s
        output.scale(1731456.84);

        return output;
    }

    /**
     * Converts meters to AU for data collection
     * @param input Vector3d with values in meters
     * @return Vector3d with values in AU
     */
    public static Vector3d convertFromMeter(Vector3d input) {
        Vector3d output = new Vector3d(input);

        // Convert m to AU by NASA
        output.scale(6.6845871E-12);

        return output;
    }

    public static double convertFromMeter(double input) {
        return input * 6.6845871E-12;
    }

    /**
     * Converts m/s to AU/day for data collection
     * @param input Vector3d with values in m/s
     * @return Vector3d with values in AU/day
     */
    public static Vector3d convertFromMetersPerSecond(Vector3d input) {
        Vector3d output = new Vector3d(input);

        // Convert seconds to days by NASA
        output.scale(5.77548327E-7);

        return output;
    }
}
