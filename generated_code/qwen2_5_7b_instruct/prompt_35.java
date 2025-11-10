```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PubSubManager {

    private final Map<String, List<Subscriber>> subscriptions = new HashMap<>();

    public void subscribe(String topic, Subscriber subscriber) {
        subscriptions.computeIfAbsent(topic, k -> new ArrayList<>()).add(subscriber);
    }

    public void unsubscribe(String topic, Subscriber subscriber) {
        List<Subscriber> subscribers = subscriptions.get(topic);
        if (subscribers != null) {
            subscribers.remove(subscriber);
        }
    }

    public void publish(String topic, Object event) {
        List<Subscriber> subscribers = subscriptions.get(topic);
        if (subscribers != null) {
            for (Subscriber subscriber : subscribers) {
                subscriber.onEvent(event);
            }
        }
    }

    public interface Subscriber {
        void onEvent(Object event);
    }
}
```