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