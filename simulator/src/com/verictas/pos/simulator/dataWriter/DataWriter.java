package com.verictas.pos.simulator.dataWriter;

import com.verictas.pos.simulator.Object;
import com.verictas.pos.simulator.SimulatorConfig;
import com.verictas.pos.simulator.mathUtils.AU;

import javax.vecmath.Vector3d;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataWriter {
    private FileWriter writer = null;

    /**
     * Set global variables, such as the delimiter and the new line character
     */
    private static final String DELIMITER = "\t";
    private static final String NEW_LINE = "\n";

    private int counter = 0;

    /**
     * Constructor
     * @throws WritingException
     */
    public DataWriter() throws WritingException {
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
            this.writer.write("Object" + DELIMITER + "Position (m)" + DELIMITER + "Position (AU)" + DELIMITER+ "Distance from the sun (m)" + DELIMITER + "Speed (m/s)" + DELIMITER + "Speed (AU/day)" + DELIMITER + "Old Acceleration" + DELIMITER + "Acceleration" + DELIMITER + "Mass" + NEW_LINE);
            this.counter++;
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
        Vector3d oldAcceleration = object.oldAcceleration;
        Vector3d acceleration = object.acceleration;
        double mass = object.mass;

        if (this.writer == null) {
            throw new WritingException("The writer isn't defined yet");
        } else {
            try {
                if (this.counter % SimulatorConfig.skipLines == 0) {

                    // Calculate the distance to the sun
                    double sunDistance = object.getDistance(reference).length();

                    this.writer.append(id + DELIMITER + position.toString() + DELIMITER + AU.convertFromMeter(position).toString() + DELIMITER + String.valueOf(sunDistance) + DELIMITER + speed.toString() + DELIMITER + AU.convertFromMetersPerSecond(speed).toString() + DELIMITER + oldAcceleration.toString() + DELIMITER + acceleration.toString() + DELIMITER + String.valueOf(mass) + NEW_LINE);
                }
                this.counter++;
            } catch (Exception e) {
                e.printStackTrace();
                throw new WritingException("An error occurred while writing to the file!");
            }
        }
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
