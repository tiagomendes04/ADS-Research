```java
package com.example.pubsub;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PubSub {
    private final Map<String, Subscriber> subscribers = new HashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public void registerSubscriber(String topic, Subscriber subscriber) {
        subscribers.put(topic, subscriber);
    }

    public void publish(String topic, String message) {
        subscribers.get(topic).onMessage(message);
    }

    public void start() {
        scheduler.scheduleWithFixedDelay(() -> {
            for (Subscriber subscriber : subscribers.values()) {
                subscriber.onMessage();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void stop() {
        scheduler.shutdown();
    }
}
```
 
```java
package com.example.pubsub;

public interface Subscriber {
    void onMessage(String message);
    void onMessage();
}
```
 
```java
package com.example.pubsub;

public class BasicSubscriber implements Subscriber {
    @Override
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
    }
    @Override
    public void onMessage() {
        //TODO implement
    }
}
``` 
 
```java
package com.example.pubsub;

public class SimpleSubscriber implements Subscriber {
    @Override
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
    }
    @Override
    public void onMessage() {
        //TODO implement
    }
}
```