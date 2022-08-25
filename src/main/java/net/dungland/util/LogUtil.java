/*
 * 
 */
package net.dungland.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import net.classic.remastered.client.main.Minecraft;

public final class LogUtil {
    private static final String LOG_FILE_NAME = "client.log";
    private static final String LOG_OLD_FILE_NAME = "client.old.log";
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static final Logger logger = Logger.getLogger(LogUtil.class.getName());

    static {
        logger.setLevel(Level.ALL);
        CustomFormatter formatter = new CustomFormatter();
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);
        logger.addHandler(consoleHandler);
        File directory = Minecraft.getMinecraftDirectory();
        File logFile = new File(directory, LOG_FILE_NAME);
        File logOldFile = new File(directory, LOG_OLD_FILE_NAME);
        if (logFile.exists()) {
            if (logOldFile.exists()) {
                logOldFile.delete();
            }
            logFile.renameTo(logOldFile);
        }
        try {
            FileHandler fileHandler = new FileHandler(logFile.getAbsolutePath());
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
        }
        catch (IOException | SecurityException ex) {
            System.err.println("Error creating log file! " + ex);
        }
        logger.log(Level.INFO, "Log starts on {0}", DATE_FORMAT.format(new Date()));
    }

    private LogUtil() {
    }

    public static void logInfo(String message) {
        logger.log(Level.INFO, message);
    }

    public static void logInfo(String message, Throwable exception) {
        logger.log(Level.INFO, message, exception);
    }

    public static void logWarning(String message) {
        logger.log(Level.WARNING, message);
    }

    public static void logWarning(String message, Throwable exception) {
        logger.log(Level.WARNING, message, exception);
    }

    public static void logError(String message) {
        logger.log(Level.SEVERE, message);
    }

    public static void logError(String message, Throwable exception) {
        logger.log(Level.SEVERE, message, exception);
    }

    static final class CustomFormatter
    extends Formatter {
        CustomFormatter() {
        }

        @Override
        public String format(LogRecord record) {
            StringBuilder sb = new StringBuilder();
            Date eventDate = new Date(record.getMillis());
            sb.append(TIME_FORMAT.format(eventDate)).append(" [").append(record.getLevel().getName()).append("] ").append(this.formatMessage(record)).append(LINE_SEPARATOR);
            Throwable exception = record.getThrown();
            if (exception != null) {
                try {
                    StringWriter sw = new StringWriter();
                    Throwable throwable = null;
                    Object var7_9 = null;
                    try (PrintWriter pw = new PrintWriter(sw);){
                        exception.printStackTrace(pw);
                    }
                    catch (Throwable throwable2) {
                        if (throwable == null) {
                            throwable = throwable2;
                        } else if (throwable != throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                        try {
							throw throwable;
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }
                    sb.append(sw.toString());
                }
                catch (Exception exception2) {
                    // empty catch block
                }
            }
            return sb.toString();
        }
    }
}

