```java
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleLogger {
    private static final Logger logger = Logger.getLogger(SimpleLogger.class.getName());
    private static boolean logToFile = false;
    private static String logFilePath = "app.log";

    public static void configure(boolean logToFile, String logFilePath) {
        SimpleLogger.logToFile = logToFile;
        SimpleLogger.logFilePath = logFilePath;
    }

    public static void log(String message) {
        log(Level.INFO, message);
    }

    public static void log(Level level, String message) {
        String logMessage = String.format("[%s] %s", level.getName(), message);

        if (logToFile) {
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
                writer.println(logMessage);
            } catch (IOException e) {
                System.err.println("Failed to write to log file: " + e.getMessage());
            }
        } else {
            System.out.println(logMessage);
        }
    }

    public static void log(Level level, String message, Throwable throwable) {
        String logMessage = String.format("[%s] %s", level.getName(), message);
        if (logToFile) {
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
                writer.println(logMessage);
                throwable.printStackTrace(writer);
            } catch (IOException e) {
                System.err.println("Failed to write to log file: " + e.getMessage());
            }
        } else {
            System.out.println(logMessage);
            throwable.printStackTrace();
        }
    }
}
```