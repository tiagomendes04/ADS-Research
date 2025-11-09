```java
// LogLevel.java
public enum LogLevel {
    INFO,
    WARN,
    ERROR
}
```

```java
// LogDestination.java
public enum LogDestination {
    CONSOLE,
    FILE
}
```

```java
// Logger.java
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static Logger instance;
    private LogLevel minLogLevel = LogLevel.INFO;
    private LogDestination destination = LogDestination.CONSOLE;
    private String logFilePath = "application.log";
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Logger() {
        // Private constructor for Singleton pattern
    }

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void setMinLogLevel(LogLevel level) {
        this.minLogLevel = level;
    }

    public void setDestination(LogDestination destination) {
        this.destination = destination;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public void log(LogLevel level, String message) {
        if (level.ordinal() < minLogLevel.ordinal()) {
            return; // Don't log if level is below the minimum configured level
        }

        String formattedMessage = String.format("[%s] [%s] %s",
                LocalDateTime.now().format(dateTimeFormatter),
                level,
                message);

        switch (destination) {
            case CONSOLE:
                System.out.println(formattedMessage);
                break;
            case FILE:
                writeToFile(formattedMessage);
                break;
        }
    }

    private void writeToFile(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) { // true for append mode
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            // Fallback: log to console if file writing fails
            System.err.println("Error writing to log file '" + logFilePath + "': " + e.getMessage());
            System.err.println("Original message intended for file: " + message);
        }
    }

    // Convenience methods
    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }
}
```

```java
// Main.java
public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();

        // --- Console Logging Example ---
        System.out.println("--- Starting Console Logging Test ---");
        logger.setMinLogLevel(LogLevel.INFO); // Set minimum level to INFO
        logger.setDestination(LogDestination.CONSOLE); // Set destination to console

        logger.info("This is an INFO message to console.");
        logger.warn("This is a WARNING message to console.");
        logger.error("This is an ERROR message to console.");
        
        System.out.println("--- Changing Console Log Level ---");
        logger.setMinLogLevel(LogLevel.WARN); // Change minimum level to WARN
        logger.info("This INFO message should NOT be shown (min level is WARN).");
        logger.warn("This WARNING message SHOULD be shown.");
        logger.error("This ERROR message SHOULD be shown.");


        // --- File Logging Example ---
        System.out.println("\n--- Starting File Logging Test ---");
        logger.setMinLogLevel(LogLevel.INFO); // Reset minimum level for file logging
        logger.setDestination(LogDestination.FILE); // Set destination to file
        logger.setLogFilePath("my_application.log"); // Specify a custom log file name

        logger.info("This is an INFO message to file.");
        logger.warn("This is a WARNING message to file.");
        logger.error("This is an ERROR message to file.");

        System.out.println("--- Changing File Log Level ---");
        logger.setMinLogLevel(LogLevel.ERROR); // Change minimum level to ERROR
        logger.info("This INFO message should NOT be shown in file (min level is ERROR).");
        logger.warn("This WARNING message should NOT be shown in file (min level is ERROR).");
        logger.error("This ERROR message SHOULD be shown in file.");

        System.out.println("\nFile logging complete. Please check 'my_application.log' for entries.");
    }
}
```