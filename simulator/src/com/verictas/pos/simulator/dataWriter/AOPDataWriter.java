package com.verictas.pos.simulator.dataWriter;

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