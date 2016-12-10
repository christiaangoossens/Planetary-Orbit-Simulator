package com.verictas.pos.simulator.dataWriter;

import com.verictas.pos.simulator.Object;
import com.verictas.pos.simulator.Simulator;
import com.verictas.pos.simulator.SimulatorConfig;
import com.verictas.pos.simulator.mathUtils.AU;
import com.verictas.pos.simulator.processor.ObjectProcessor;

import javax.vecmath.Vector3d;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.*;
import java.util.Date;
import java.util.Locale;

public class PosDataWriter extends DataWriter {
    public PosDataWriter() throws WritingException {
        super("position");
        try {

            /**
             * Write the lines with information about the columns
             */

            if (SimulatorConfig.outputUnit.equals("AU")) {
                this.writer.write("Object" + DELIMITER + "X (AU)" + DELIMITER + "Y (AU)" + DELIMITER + "Z (AU)" + DELIMITER + "VX (AU/day)" + DELIMITER + "VY (AU/day)" + DELIMITER + "VZ (AU/day)" + NEW_LINE);
            } else {
                this.writer.write("Object" + DELIMITER + "X (m)" + DELIMITER + "Y (m)" + DELIMITER + "Z (m)" + DELIMITER + "VX (m/s)" + DELIMITER + "VY (m/s)" + DELIMITER + "VZ (m/s)" + NEW_LINE);
            }

            this.counter++;
        } catch (Exception e) {
            e.printStackTrace();
            throw new WritingException("An error occurred while writing to the file!");
        }
    }

    /**
     *
     * @param object The object you want to write data about
     * @throws WritingException
     */
    public void write(Object object) throws WritingException {
        String id = object.name;
        Vector3d position = object.position;
        Vector3d speed = object.speed;
        Vector3d AUposition = AU.convertFromMeter(position);
        Vector3d AUspeed = AU.convertFromMetersPerSecond(speed);

        if (this.writer == null) {
            throw new WritingException("The writer isn't defined yet");
        } else {
            try {
                if (this.counter % SimulatorConfig.skipLines == 0) {
                    if (SimulatorConfig.outputUnit.equals("AU")) {
                        this.writer.append(id + DELIMITER + decimalFormatter(AUposition.getX()) + DELIMITER + decimalFormatter(AUposition.getY()) + DELIMITER + decimalFormatter(AUposition.getZ()) + DELIMITER + decimalFormatter(AUspeed.getX()) + DELIMITER + decimalFormatter(AUspeed.getY()) + DELIMITER + decimalFormatter(AUspeed.getZ()) + NEW_LINE);
                    } else {
                        this.writer.append(id + DELIMITER + decimalFormatter(position.getX()) + DELIMITER + decimalFormatter(position.getY()) + DELIMITER + decimalFormatter(position.getZ()) + DELIMITER + decimalFormatter(speed.getX()) + DELIMITER + decimalFormatter(speed.getY()) + DELIMITER + decimalFormatter(speed.getZ()) + NEW_LINE);
                    }
                }
                this.counter++;
            } catch (Exception e) {
                e.printStackTrace();
                throw new WritingException("An error occurred while writing to the file!");
            }
        }
    }
}