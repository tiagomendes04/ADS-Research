import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class EventLogger {

    private static final String DEFAULT_LOG_FILE = "application.log";
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private String logFile;
    private List<Predicate<LogEvent>> filters;
    private boolean enabled;

    public EventLogger() {
        this(DEFAULT_LOG_FILE);
    }

    public EventLogger(String logFile) {
        this.logFile = logFile;
        this.filters = new ArrayList<>();
        this.enabled = true;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void addFilter(Predicate<LogEvent> filter) {
        filters.add(filter);
    }

    public void removeFilter(Predicate<LogEvent> filter) {
        filters.remove(filter);
    }

    public void clearFilters() {
        filters.clear();
    }

    public void logEvent(LogEvent event) {
        if (!enabled) {
            return;
        }

        for (Predicate<LogEvent> filter : filters) {
            if (!filter.test(event)) {
                return; // Skip logging if any filter rejects the event
            }
        }

        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String logEntry = String.format("%s [%s] %s: %s%n", timestamp, event.getEventType(), event.getSource(), event.getMessage());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            writer.write(logEntry);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    // Overloaded methods for convenience
    public void logEvent(String eventType, String source, String message) {
        logEvent(new LogEvent(eventType, source, message));
    }

    public void logInfo(String source, String message) {
        logEvent("INFO", source, message);
    }

    public void logWarning(String source, String message) {
        logEvent("WARNING", source, message);
    }

    public void logError(String source, String message) {
        logEvent("ERROR", source, message);
    }

    public static class LogEvent {
        private final String eventType;
        private final String source;
        private final String message;

        public LogEvent(String eventType, String source, String message) {
            this.eventType = eventType;
            this.source = source;
            this.message = message;
        }

        public String getEventType() {
            return eventType;
        }

        public String getSource() {
            return source;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "LogEvent{" +
                    "eventType='" + eventType + '\'' +
                    ", source='" + source + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        EventLogger logger = new EventLogger("test.log");

        // Example Filter: Log only events from "Button1"
        logger.addFilter(event -> event.getSource().equals("Button1"));

        logger.logInfo("Button1", "Button was clicked");
        logger.logWarning("TextField1", "Input is invalid");
        logger.logError("Database", "Connection failed");
        logger.logInfo("Button1", "Action Completed");

        logger.clearFilters(); // Remove the filter.

        logger.logInfo("Button1", "Another button click"); // This will be logged
        logger.logInfo("TextField1", "Input OK");  // This will also be logged

        logger.setEnabled(false); // Disable logging
        logger.logInfo("Button2", "This won't be logged");

        logger.setEnabled(true);
        logger.logInfo("Button2", "Logging enabled again");

        //Example filter using lambda
        logger.addFilter(event -> event.getEventType().equals("ERROR"));
        logger.logError("Button2", "This should be logged despite other logging");
        logger.logInfo("Button2", "This should NOT be logged");

        // Example usage with LogEvent object
        LogEvent customEvent = new LogEvent("CUSTOM", "System", "Application started");
        logger.logEvent(customEvent);
    }
}