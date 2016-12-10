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
import java.util.*;

public class AOPDataWriter extends DataWriter {
    public AOPDataWriter() throws WritingException {
        super("arguments");
        try {

            /**
             * Write the lines with information about the columns
             */

            this.writer.write("OBJECT" + DELIMITER + "ROUND" + DELIMITER + "ARGUMENT (RAD)" + NEW_LINE);
            this.counter++;
        } catch (Exception e) {
            e.printStackTrace();
            throw new WritingException("An error occurred while writing to the file!");
        }
    }

    public void write(String object, TreeMap<Integer, Double> arguments) throws WritingException {
        try {
            for (Map.Entry<Integer, Double> entry : arguments.entrySet()) {
                Integer key = entry.getKey();
                Double value = entry.getValue();
                this.writer.append(object + DELIMITER + key + DELIMITER + decimalFormatter(value) + NEW_LINE);
                this.counter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new WritingException("An error occurred while writing to the file!");
        }
    }
}