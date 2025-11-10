```java
// SimpleLogger.java
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/** Logging levels */
enum LogLevel {
    TRACE, DEBUG, INFO, WARN, ERROR
}

/** Logger interface */
interface Logger {
    void log(LogLevel level, String message);
    default void trace(String msg) { log(LogLevel.TRACE, msg); }
    default void debug(String msg) { log(LogLevel.DEBUG, msg); }
    default void info(String msg)  { log(LogLevel.INFO,  msg); }
    default void warn(String msg)  { log(LogLevel.WARN,  msg); }
    default void error(String msg) { log(LogLevel.ERROR, msg); }
}

/** Console logger implementation */
class ConsoleLogger implements Logger {
    private final PrintStream out;

    ConsoleLogger(PrintStream out) {
        this.out = Objects.requireNonNull(out);
    }

    @Override
    public void log(LogLevel level, String message) {
        out.println(format(level, message));
    }

    private String format(LogLevel level, String message) {
        return String.format("%s [%s] %s",
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                level,
                message);
    }
}

/** File logger implementation */
class FileLogger implements Logger, Closeable {
    private final BufferedWriter writer;
    private final Object lock = new Object();

    FileLogger(String filePath, boolean append) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(filePath, append));
        // Ensure writer is closed on JVM shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try { close(); } catch (IOException ignored) {}
        }));
    }

    @Override
    public void log(LogLevel level, String message) {
        String line = format(level, message);
        synchronized (lock) {
            try {
                writer.write(line);
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                // Fallback to console if file writing fails
                System.err.println("Failed to write log to file: " + e.getMessage());
                System.err.println(line);
            }
        }
    }

    private String format(LogLevel level, String message) {
        return String.format("%s [%s] %s",
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                level,
                message);
    }

    @Override
    public void close() throws IOException {
        synchronized (lock) {
            writer.close();
        }
    }
}

/** Simple configuration holder */
class LoggerConfig {
    enum Destination { CONSOLE, FILE }

    private final Destination destination;
    private final String filePath;   // used only when destination == FILE
    private final boolean append;    // file append mode

    private LoggerConfig(Destination destination, String filePath, boolean append) {
        this.destination = destination;
        this.filePath = filePath;
        this.append = append;
    }

    static LoggerConfig toConsole() {
        return new LoggerConfig(Destination.CONSOLE, null, false);
    }

    static LoggerConfig toFile(String filePath, boolean append) {
        return new LoggerConfig(Destination.FILE, filePath, append);
    }

    Destination getDestination() { return destination; }
    String getFilePath() { return filePath; }
    boolean isAppend() { return append; }
}

/** Factory to create a logger based on configuration */
class LoggerFactory {
    static Logger create(LoggerConfig config) {
        switch (config.getDestination()) {
            case CONSOLE:
                return new ConsoleLogger(System.out);
            case FILE:
                try {
                    return new FileLogger(config.getFilePath(), config.isAppend());
                } catch (IOException e) {
                    // Fallback to console if file cannot be opened
                    System.err.println("Failed to open log file, falling back to console: " + e.getMessage());
                    return new ConsoleLogger(System.err);
                }
            default:
                throw new IllegalArgumentException("Unsupported destination");
        }
    }
}

/** Example usage */
public class SimpleLoggerDemo {
    public static void main(String[] args) {
        // Choose destination via simple argument: "console" or "file"
        Logger logger;
        if (args.length > 0 && "file".equalsIgnoreCase(args[0])) {
            LoggerConfig cfg = LoggerConfig.toFile("application.log", true);
            logger = LoggerFactory.create(cfg);
        } else {
            logger = LoggerFactory.create(LoggerConfig.toConsole());
        }

        logger.info("Application started");
        logger.debug("Debugging details");
        logger.warn("A warning message");
        logger.error("An error occurred");
        logger.info("Application finished");
    }
}
```