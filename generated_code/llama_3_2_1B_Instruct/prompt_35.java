```java
import java.util.*;

public class PubSub {
    public static class Event {
        private String type;
        private Object data;

        public Event(String type, Object data) {
            this.type = type;
            this.data = data;
        }

        public String getType() {
            return type;
        }

        public Object getData() {
            return data;
        }
    }

    public static class Subscription {
        private String name;
        private List<Event> subscribers;
        private boolean active;

        public Subscription(String name) {
            this.name = name;
            this.subscribers = new ArrayList<>();
            this.active = true;
        }

        public void subscribe(Event event) {
            if (active) {
                subscribers.add(event);
            }
        }

        public void unsubscribe(Event event) {
            if (active) {
                subscribers.remove(event);
            }
        }

        public boolean isActive() {
            return active;
        }

        public List<Event> getSubscribers() {
            return new ArrayList<>(subscribers);
        }
    }

    public static class PubSubManager {
        private Map<String, Subscription> subscriptions;
        private Map<String, Event> events;

        public PubSubManager() {
            this.subscriptions = new HashMap<>();
            this.events = new HashMap<>();
        }

        public void subscribe(String name, Event event) {
            if (events.containsKey(name)) {
                throw new RuntimeException("Event with name '" + name + "' already exists.");
            }
            if (subscriptions.containsKey(name)) {
                throw new RuntimeException("Subscription with name '" + name + "' already exists.");
            }
            Subscription subscription = new Subscription(name);
            subscriptions.put(name, subscription);
            events.put(name, event);
        }

        public void unsubscribe(String name, Event event) {
            if (!subscriptions.containsKey(name)) {
                throw new RuntimeException("No subscription with name '" + name + "' exists.");
            }
            if (!subscriptions.get(name).isActive()) {
                throw new RuntimeException("No subscription with name '" + name + "' exists.");
            }
            subscriptions.get(name).unsubscribe(event);
        }

        public void publish(String name, Object data) {
            if (!subscriptions.containsKey(name)) {
                throw new RuntimeException("No subscription with name '" + name + "' exists.");
            }
            Event event = new Event(name, data);
            subscriptions.get(name).subscribe(event);
        }

        public List<Subscription> getSubscriptions(String name) {
            return subscriptions.get(name).getSubscribers();
        }

        public List<Event> getEvents(String name) {
            return events.get(name);
        }
    }
}
```