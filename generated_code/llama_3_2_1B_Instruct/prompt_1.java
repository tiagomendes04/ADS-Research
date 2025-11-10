```java
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logger {
    private static final String CONSOLE_LOG_FILE = "log.txt";
    private static final String CONSOLE_LOG_LEVEL = "INFO";
    private static final Logger logger = Logger.getLogger(Logger.class.getName());

    public static void main(String[] args) {
        // Set logging level
        logger.setLevel(Level.ALL);

        // Create console handler
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO);

        // Set formatter
        Formatter formatter = new SimpleFormatter();
        consoleHandler.setFormatter(formatter);

        // Add handler to logger
        logger.addHandler(consoleHandler);

        // Write to console log file
        writeToConsoleLog();
    }

    private static void writeToConsoleLog() {
        // Log message
        logger.info("Console log message");
    }

    private static void writeToConsoleLog(String message) {
        // Log message
        logger.info(message);
    }
}
```