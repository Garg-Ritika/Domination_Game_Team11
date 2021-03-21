package ca.concordia.patterns.observer;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogWriter implements Observer {

    // 10MB file size
    public final int FILE_SIZE = 10 * 1024 * 1024;

    @Override
    public void update(Observable p_ObservableState) {
        String l_LogString = ((LogEntryBuffer) p_ObservableState).getUpdate();
        log(l_LogString);
    }

    public void log(String message) {
        try {
            Logger logger = Logger.getAnonymousLogger();
            FileHandler fH = new FileHandler(LogUtil.LOG_FILE_NAME, FILE_SIZE, 1, true);

            logger.addHandler(fH);

            // to avoid printing the log lines in console but only in log file.
            logger.setUseParentHandlers(false);

            // configure simple format
            SimpleFormatter sf = new SimpleFormatter();
            fH.setFormatter(sf);

            // everything is info log be default
            logger.info(message);

            fH.close();

        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
