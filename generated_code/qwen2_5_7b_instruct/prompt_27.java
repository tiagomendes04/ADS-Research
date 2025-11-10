```java
import java.util.logging.*;

public class EventLogger {

    private static final Logger logger = Logger.getLogger(EventLogger.class.getName());
    private static final Level INFO_LEVEL = Level.INFO;
    private static final Level ERROR_LEVEL = Level.SEVERE;

    public static void logEvent(String event, Level level) {
        logger.log(level, event);
    }

    public static void logInfoEvent(String event) {
        logEvent(event, INFO_LEVEL);
    }

    public static void logErrorEvent(String event) {
        logEvent(event, ERROR_LEVEL);
    }

    public static void setupLogger() {
        logger.setLevel(INFO_LEVEL);
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(INFO_LEVEL);
        logger.addHandler(consoleHandler);
    }
}
```