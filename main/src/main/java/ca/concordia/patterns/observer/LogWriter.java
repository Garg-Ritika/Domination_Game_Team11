package ca.concordia.patterns.observer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class LogWriter implements Observer {

    // 10MB file size
    public static final int FILE_SIZE = 10 * 1024 * 1024;
    private static final String TAG = LogWriter.class.getSimpleName();
    private static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    public static void log(String classname, String message) {
        log(classname, message, "info");
    }

    public static void log(String classname, String message, String type) {

        try {
            // configure the logger
            Logger logger = Logger.getLogger(TAG);
            FileHandler fH = new FileHandler("risk.log", FILE_SIZE, 2, true);

            logger.addHandler(fH);

            // configure simple format
            SimpleFormatter sf = new SimpleFormatter();
            fH.setFormatter(sf);

            //prepend date into the log message
            String datetime = new SimpleDateFormat(PATTERN).format(
                    new Date(System.currentTimeMillis()));

            //log messages into file
            if ("error".equalsIgnoreCase(type)) {
                logger.severe(datetime + " " + classname + " " + message);

            } else if ("debug".equalsIgnoreCase(type)) {
                // debug is for internal use only
                logger.fine(datetime + " " + classname + " " + message);

            } else {
                // everything is info log be default
                logger.info(datetime + " " + classname + " " + message);
            }

            fH.close();

        } catch (IOException io) {
            io.printStackTrace();
        }
    }


    @Override
    public void update(Observable p_ObservableState) {

    }

}
