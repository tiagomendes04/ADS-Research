```java
import java.util.*;
import java.util.concurrent.*;

public class PubSubSystem {
    private final Map<String, List<Consumer<String>>> subscriptions = new ConcurrentHashMap<>();

    public void subscribe(String topic, Consumer<String> listener) {
        subscriptions.computeIfAbsent(topic, k -> new CopyOnWriteArrayList<>()).add(listener);
    }

    public void unsubscribe(String topic, Consumer<String> listener) {
        if (subscriptions.containsKey(topic)) {
            List<Consumer<String>> listeners = subscriptions.get(topic);
            listeners.remove(listener);
            if (listeners.isEmpty()) {
                subscriptions.remove(topic);
            }
        }
    }

    public void publish(String topic, String message) {
        List<Consumer<String>> listeners = subscriptions.get(topic);
        if (listeners != null) {
            for (Consumer<String> listener : listeners) {
                listener.accept(message);
            }
        }
    }

    public static void main(String[] args) {
        PubSubSystem pubSub = new PubSubSystem();
        Consumer<String> listener1 = message -> System.out.println("Listener 1 received: " + message);
        Consumer<String> listener2 = message -> System.out.println("Listener 2 received: " + message);

        pubSub.subscribe("news", listener1);
        pubSub.subscribe("news", listener2);
        pubSub.publish("news", "Breaking News!");

        pubSub.unsubscribe("news", listener1);
        pubSub.publish("news", "Another Update!");
    }
}
```