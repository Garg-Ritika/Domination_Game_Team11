package ca.concordia.patterns.observer;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This class implements observer interface.
 * It writes the contents of LogEntryBuffer to a log file when it is changed
 */
public class LogWriter implements Observer {

    private static Logger LOGGER = null;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        LOGGER = Logger.getLogger(LogWriter.class.getName());
    }


    // 10MB file size
    public final int FILE_SIZE = 10 * 1024 * 1024;

    /**
     * This is te update method of observer class
     *
     * @param p_ObservableState: Object that is passed by the subject (observable).
     */
    @Override
    public void update(Observable p_ObservableState) {
        String l_LogString = ((LogEntryBuffer) p_ObservableState).getUpdate();
        log(l_LogString);
    }

    /**
     * This is the log method which helps user to clearly see all the actions that happened during game by looking at this file
     *
     * @param p_Message String message passed as an arguement to log method
     */
    public void log(String p_Message) {
        try {

            FileHandler l_FH = new FileHandler(LogUtil.LOG_FILE_NAME, FILE_SIZE, 1, true);

            LOGGER.addHandler(l_FH);

            // to avoid printing the log lines in console but only in log file.
            LOGGER.setUseParentHandlers(false);

            // configure simple format
            SimpleFormatter l_Sf = new SimpleFormatter();
            l_FH.setFormatter(l_Sf);

            // everything is info log be default
            LOGGER.info(p_Message);

            l_FH.close();


        } catch (IOException io) {
            io.printStackTrace();
        }
    }


    /**
     * This is the log method which helps user to clearly see all the actions that happened during game by looking at this file
     *
     * @param p_Message String message passed as an arguement to log method
     */
    public void logError(String p_Message) {
        try {


            FileHandler l_FH = new FileHandler(LogUtil.LOG_FILE_NAME, FILE_SIZE, 1, true);

            LOGGER.addHandler(l_FH);

            // to avoid printing the log lines in console but only in log file.
            LOGGER.setUseParentHandlers(false);


            // configure simple format
            SimpleFormatter l_Sf = new SimpleFormatter();
            l_FH.setFormatter(l_Sf);

            // everything is info log be default
            LOGGER.info(p_Message);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
