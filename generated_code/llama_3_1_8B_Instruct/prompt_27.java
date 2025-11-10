```java
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventLogger {
    private List<Event> events;
    private DateTimeFormatter formatter;

    public EventLogger() {
        this.events = new ArrayList<>();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    public void logEvent(String message) {
        Event event = new Event(LocalDateTime.now().format(formatter), message);
        events.add(event);
        System.out.println(event.toString());
    }

    public void logEvent(String message, Level level) {
        Event event = new Event(LocalDateTime.now().format(formatter), message, level);
        events.add(event);
        System.out.println(event.toString());
    }

    public void logError(String message) {
        logEvent(message, Level.ERROR);
    }

    public void logWarning(String message) {
        logEvent(message, Level.WARNING);
    }

    public void logInfo(String message) {
        logEvent(message, Level.INFO);
    }

    public void logDebug(String message) {
        logEvent(message, Level.DEBUG);
    }

    public void applyFilter(Filter filter) {
        events.removeIf(filter::test);
    }

    public List<Event> getEvents() {
        return new ArrayList<>(events);
    }

    public interface Filter {
        boolean test(Event event);
    }

    public static class Event {
        private String timestamp;
        private String message;
        private Level level;

        public Event(String timestamp, String message) {
            this.timestamp = timestamp;
            this.message = message;
        }

        public Event(String timestamp, String message, Level level) {
            this.timestamp = timestamp;
            this.message = message;
            this.level = level;
        }

        @Override
        public String toString() {
            if (level != null) {
                return String.format("%s %s: %s", timestamp, level.name(), message);
            } else {
                return String.format("%s: %s", timestamp, message);
            }
        }
    }

    public enum Level {
        ERROR,
        WARNING,
        INFO,
        DEBUG
    }
}
```