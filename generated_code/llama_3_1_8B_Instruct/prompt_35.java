```java
public abstract class Event {
    private final String id;
    private final String type;

    public Event(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public abstract void execute();
}
```

```java
import java.util.*;

public class EventBus {
    private final Map<String, List<EventHandler>> eventHandlers;
    private final Map<String, Event> events;

    public EventBus() {
        this.eventHandlers = new HashMap<>();
        this.events = new HashMap<>();
    }

    public void registerHandler(String eventType, EventHandler handler) {
        eventHandlers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handler);
    }

    public void unregisterHandler(String eventType, EventHandler handler) {
        eventHandlers.get(eventType).remove(handler);
    }

    public void publishEvent(Event event) {
        String eventType = event.getType();
        List<EventHandler> handlers = eventHandlers.get(eventType);
        if (handlers != null) {
            for (EventHandler handler : handlers) {
                handler.handle(event);
            }
        }
        events.put(event.getId(), event);
    }

    public void clearEvents() {
        events.clear();
    }

    public void clearEventHandlers() {
        eventHandlers.clear();
    }
}
```

```java
public interface EventHandler {
    void handle(Event event);
}
```

```java
public class ExampleUsage {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();

        eventBus.registerHandler("example.event.type", (event) -> {
            System.out.println("Received event: " + event.getType());
        });

        Event event = new ExampleEvent("example-event-id", "example.event.type");
        eventBus.publishEvent(event);
    }
}

class ExampleEvent extends Event {
    public ExampleEvent(String id, String type) {
        super(id, type);
    }

    @Override
    public void execute() {
        // Simulate some work
    }
}
```