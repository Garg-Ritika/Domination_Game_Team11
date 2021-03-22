package ca.concordia.patterns.observer;

import java.io.File;

public class LogUtil {

    //Log file name
    public static final String LOG_FILE_NAME = "risk-log.txt";

    private static LogEntryBuffer d_LogEntryBuffer;
    private static LogWriter d_LogWriter;

    /**
     * this method is used to catch the exception during the start of the program
     * if there is already existing log file then exception is handled by catch
     * @param p_Msg
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
        } catch (Exception e) {

        }
    }
}

