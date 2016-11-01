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

public class DataWriter {
    private FileWriter writer = null;

    /**
     * Set global variables, such as the delimiter and the new line character
     */
    private static final String DELIMITER = "\t";
    private static final String NEW_LINE = "\n";

    private int counter = 0;

    /**
     * Decimal formatter
     */

    public DecimalFormat formatter = new DecimalFormat();

    /**
     * Constructor
     * @throws WritingException
     */
    public DataWriter() throws WritingException {

        /**
         * Prepare the locale
         */

        try {
            /**
             * Define the save path
             */
            String directory =  System.getProperty("user.home") + File.separator + "simulatorExports";
            File directoryPath = new File(directory);

            String path = directory + File.separator + getCurrentTimeStamp() + ".txt";
            System.out.println("WRITING DATA TO: " + path);

            /**
             * Check if the saving directory exists to prevent IOException
             */
            if (!directoryPath.exists()) {
                directoryPath.mkdirs();
            }

            /**
             * Open a file to write to and write the header
             */
            this.writer = new FileWriter(path);

            /**
             * Write the lines with information about the columns
             */

            if (SimulatorConfig.outputUnit.equals("AU")) {
                this.writer.write("Object" + DELIMITER + "X (AU)" + DELIMITER + "Y (AU)" + DELIMITER + "Z (AU)" + DELIMITER + "VX (AU/day)" + DELIMITER + "VY (AU/day)" + DELIMITER + "VZ (AU/day)" + NEW_LINE);
            } else {
                this.writer.write("Object" + DELIMITER + "X (m)" + DELIMITER + "Y (m)" + DELIMITER + "Z (m)" + DELIMITER + "VX (m/s)" + DELIMITER + "VY (m/s)" + DELIMITER + "VZ (m/s)" + NEW_LINE);
            }

            this.counter++;

            /**
             * Configure the decimal formatter
             */
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            if (SimulatorConfig.outputNumbers == 0) {
                symbols.setDecimalSeparator(',');
                symbols.setGroupingSeparator('.');
            } else {
                symbols.setDecimalSeparator('.');
                symbols.setGroupingSeparator(',');
            }
            this.formatter.setDecimalFormatSymbols(symbols);
            this.formatter.setMinimumFractionDigits(0);
            this.formatter.setMaximumFractionDigits(25);
        } catch(IOException e) {
            throw new WritingException("The destination file couldn't be created.");
        } catch(Exception e) {
            throw new WritingException("Some unknown error occurred while writing to the file!");
        }
    }

    /**
     * Writes a string to the file
     * @param string
     * @throws WritingException
     */
    public void write(String string) throws WritingException {
        if (this.writer == null) {
            throw new WritingException("The writer isn't defined yet");
        } else {
            try {
                if (this.counter % SimulatorConfig.skipLines == 0) {
                    this.writer.append(string);
                }
                this.counter++;
            } catch (Exception e) {
                e.printStackTrace();
                throw new WritingException("An error occurred while writing to the file!");
            }
        }
    }

    /**
     *
     * @param object The object you want to write data about
     * @param reference The system's star
     * @throws WritingException
     */
    public void write(Object object, Object reference) throws WritingException {
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

    private String decimalFormatter(double input) {
        return this.formatter.format(input);
    }

    /**
     * Saves the file to disk
     * @throws WritingException
     */
    public void save() throws WritingException {
        if (this.writer == null) {
            throw new WritingException("The writer isn't defined yet");
        } else {
            try {
                this.writer.flush();
                this.writer.close();
            } catch (IOException e) {
                throw new WritingException("Whoop! Save error!");
            }
        }
    }

    /**
     * Gets the current line count
     * @return int
     */
    public int getLines() {
        return this.counter;
    }

    /**
     * Gets the current filestamp for file naming
     * @return String
     */
    private String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    }
}
