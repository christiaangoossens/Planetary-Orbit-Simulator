package com.verictas.pos.simulator.dataWriter;

import com.verictas.pos.simulator.mathUtils.AU;

import javax.vecmath.Vector3d;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataWriter {
    private FileWriter writer = null;

    // Delimiter used in text file (for import in Excel)
    private static final String DELIMITER = "\t";
    private static final String NEW_LINE = "\n";

    public DataWriter() throws WritingException {
        try {
            // Define the save path
            String directory =  System.getProperty("user.home") + File.separator + "simulatorExports";
            File directoryPath = new File(directory);

            String path = directory + File.separator + getCurrentTimeStamp() + ".txt";
            System.out.println("WRITING DATA TO: " + path);

            if (!directoryPath.exists()) {
                directoryPath.mkdirs();
            }

            this.writer = new FileWriter(path);
            this.writer.write("Object" + DELIMITER + "Position (m)" + DELIMITER + "Position (AU)" + DELIMITER + "Speed (m)" + DELIMITER + "Speed (AU)" + DELIMITER + "Old Acceleration" + DELIMITER + "Acceleration" + DELIMITER + "Mass" + NEW_LINE);
        } catch(IOException e) {
            throw new WritingException("Whoop! Creation error!");
        } catch(Exception e) {
            throw new WritingException("Whoop! Unknown creation error!");
        }
    }

    public void write(String string) throws WritingException {
        if (this.writer == null) {
            throw new WritingException("The writer isn't defined yet");
        } else {
            try {
                this.writer.append(string);
            } catch (Exception e) {
                throw new WritingException("Whoop! Write error!");
            }
        }
    }

    public void write(String id, Vector3d position, Vector3d speed, Vector3d oldAcceleration, Vector3d acceleration, double mass) throws WritingException {
        if (this.writer == null) {
            throw new WritingException("The writer isn't defined yet");
        } else {
            try {
                this.writer.append(id + DELIMITER + position.toString() + DELIMITER + AU.convertFromMeter(position).toString() + DELIMITER + speed.toString() + DELIMITER + AU.convertFromMetersPerSecond(speed).toString() + DELIMITER + oldAcceleration.toString() + DELIMITER + acceleration.toString() + DELIMITER + String.valueOf(mass) + NEW_LINE);
            } catch (Exception e) {
                e.printStackTrace();
                throw new WritingException("Whoop! Write error!");
            }
        }
    }

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

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    }
}
