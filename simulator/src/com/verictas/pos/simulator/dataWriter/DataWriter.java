package com.verictas.pos.simulator.dataWriter;

import com.verictas.pos.simulator.Main;
import com.verictas.pos.simulator.SimulatorConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.*;
import java.util.Date;

public class DataWriter {
    protected FileWriter writer = null;

    /**
     * Set global variables, such as the delimiter and the new line character
     */
    protected static final String DELIMITER = "\t";
    protected static final String NEW_LINE = "\n";

    protected int counter = 0;

    /**
     * Decimal formatter
     */

    public DecimalFormat formatter = new DecimalFormat();

    /**
     * Constructor
     * @throws WritingException
     */
    public DataWriter(String filenameAppendix) throws WritingException {

        /**
         * Prepare the locale
         */

        try {
            /**
             * Define the save path
             */
            String directory =  System.getProperty("user.home") + File.separator + "simulatorExports";
            File directoryPath = new File(directory);

            String path = directory + File.separator + "v" + Main.version + "-" + getCurrentTimeStamp() + "-" + filenameAppendix + ".txt";
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

    protected String decimalFormatter(double input) {
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
