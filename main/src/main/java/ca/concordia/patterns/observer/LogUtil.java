package ca.concordia.patterns.observer;

import java.io.File;

public class LogUtil {

    //Log file name
    public static final String LOG_FILE_NAME = "risk-log.txt";

    private static LogEntryBuffer d_LogEntryBuffer;
    private static LogWriter d_LogWriter;

    /**
     * This method is used to catch the exception during the start of the program
     * if there is already existing log file then exception is handled by catch
     *
     * @param p_Msg this is the parameter passed which contains the message
     */
    public static void log(String p_Msg) {
        try {
            System.out.println(p_Msg);
            if (d_LogEntryBuffer == null) {
                d_LogEntryBuffer = new LogEntryBuffer();

                if (d_LogWriter == null) {
                    d_LogWriter = new LogWriter();
                }

                d_LogEntryBuffer.attach(d_LogWriter);
            }

            d_LogEntryBuffer.setUpdate(p_Msg);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void detach() {
        if (d_LogEntryBuffer != null && d_LogWriter != null) {
            d_LogEntryBuffer.detach(d_LogWriter);
        }
    }

    /**
     * this method is used to clear the
     * existing file containing logs of previous moves
     */
    public static void clearOldLogFiles() {
        try {
            File l_LogFile = new File(LOG_FILE_NAME);
            if (l_LogFile.exists()) {
                l_LogFile.delete();
            }

            // also delete every file starting with that name
            String currentDirectory = System.getProperty("user.dir");
            File directoryPath = new File(currentDirectory);
            //List of all files and directories
            String contents[] = directoryPath.list();
            System.out.println("List of files and directories in the specified directory:");
            for (int i = 0; i < contents.length; i++) {
                System.out.println(contents[i]);
                try {
                    if (contents[i].startsWith(LOG_FILE_NAME)) {
                        new File(contents[i]).delete();
                    }
                } catch (Exception io) {
                }
            }
        } catch (Exception e) {

        }


    }
}

