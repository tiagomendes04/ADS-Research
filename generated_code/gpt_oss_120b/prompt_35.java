```java
// src/main/java/com/example/pubsub/Event.java
package com.example.pubsub;

public final class Event<T> {
    private final T payload;

    public Event(T payload) {
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }
}
```

```java
// src/main/java/com/example/pubsub/Subscriber.java
package com.example.pubsub;

@FunctionalInterface
public interface Subscriber<T> {
    void onEvent(Event<T> event);
}
```

```java
// src/main/java/com/example/pubsub/Subscription.java
package com.example.pubsub;

import java.util.Objects;
import java.util.UUID;

public final class Subscription<T> {
    private final UUID id;
    private final Class<T> eventType;
    private final Subscriber<? super T> subscriber;
    private final java.util.function.Predicate<? super T> filter;

    Subscription(Class<T> eventType,
                 Subscriber<? super T> subscriber,
                 java.util.function.Predicate<? super T> filter) {
        this.id = UUID.randomUUID();
        this.eventType = Objects.requireNonNull(eventType);
        this.subscriber = Objects.requireNonNull(subscriber);
        this.filter = filter != null ? filter : t -> true;
    }

    UUID getId() {
        return id;
    }

    Class<T> getEventType() {
        return eventType;
    }

    Subscriber<? super T> getSubscriber() {
        return subscriber;
    }

    boolean test(T payload) {
        return filter.test(payload);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscription)) return false;
        Subscription<?> that = (Subscription<?>) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
```

```java
// src/main/java/com/example/pubsub/EventBus.java
package com.example.pubsub;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public final class EventBus {
    private final ConcurrentMap<Class<?>, CopyOnWriteArraySet<Subscription<?>>> subscribers = new ConcurrentHashMap<>();
    private final Executor executor;

    public EventBus() {
        this(Executors.newCachedThreadPool());
    }

    public EventBus(Executor executor) {
        this.executor = Objects.requireNonNull(executor);
    }

    public <T> Subscription<T> subscribe(Class<T> eventType,
                                         Subscriber<? super T> subscriber) {
        return subscribe(eventType, subscriber, null);
    }

    public <T> Subscription<T> subscribe(Class<T> eventType,
                                         Subscriber<? super T> subscriber,
                                         java.util.function.Predicate<? super T> filter) {
        Subscription<T> sub = new Subscription<>(eventType, subscriber, filter);
        subscribers.computeIfAbsent(eventType, k -> new CopyOnWriteArraySet<>()).add(sub);
        return sub;
    }

    public void unsubscribe(Subscription<?> subscription) {
        Set<Subscription<?>> set = subscribers.get(subscription.getEventType());
        if (set != null) {
            set.remove(subscription);
            if (set.isEmpty()) {
                subscribers.remove(subscription.getEventType(), Collections.emptySet());
            }
        }
    }

    public <T> void publish(Event<T> event) {
        Class<?> type = event.getPayload().getClass();
        // Dispatch to exact type subscribers
        dispatch(event, type);
        // Dispatch to subscribers of super types / interfaces
        for (Class<?> iface : getAllSuperTypes(type)) {
            dispatch(event, iface);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> void dispatch(Event<T> event, Class<?> type) {
        Set<Subscription<?>> set = subscribers.get(type);
        if (set == null) return;
        for (Subscription<?> rawSub : set) {
            Subscription<T> sub = (Subscription<T>) rawSub;
            if (sub.test(event.getPayload())) {
                executor.execute(() -> sub.getSubscriber().onEvent(event));
            }
        }
    }

    private Set<Class<?>> getAllSuperTypes(Class<?> clazz) {
        Set<Class<?>> result = new LinkedHashSet<>();
        Queue<Class<?>> queue = new ArrayDeque<>();
        queue.add(clazz.getSuperclass());
        Collections.addAll(queue, clazz.getInterfaces());

        while (!queue.isEmpty()) {
            Class<?> cur = queue.poll();
            if (cur == null || cur == Object.class) continue;
            if (result.add(cur)) {
                queue.add(cur.getSuperclass());
                Collections.addAll(queue, cur.getInterfaces());
            }
        }
        return result;
    }

    public void shutdown() {
        if (executor instanceof ExecutorService) {
            ((ExecutorService) executor).shutdown();
        }
    }
}
```

```java
// src/main/java/com/example/pubsub/Topic.java
package com.example.pubsub;

import java.util.Objects;

/**
 * Simple immutable topic identifier. Useful when you want to publish
 * events under a logical name rather than by payload type.
 */
public final class Topic {
    private final String name;

    private Topic(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public static Topic of(String name) {
        return new Topic(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        return (this == o) || (o instanceof Topic && name.equals(((Topic) o).name));
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Topic{" + "name='" + name + '\'' + '}';
    }
}
```

```java
// src/main/java/com/example/pubsub/TopicEventBus.java
package com.example.pubsub;

import java.util.*;
import java.util.concurrent.*;

public final class TopicEventBus {
    private final ConcurrentMap<Topic, EventBus> topicBuses = new ConcurrentHashMap<>();
    private final Executor executor;

    public TopicEventBus() {
        this(Executors.newCachedThreadPool());
    }

    public TopicEventBus(Executor executor) {
        this.executor = Objects.requireNonNull(executor);
    }

    private EventBus busFor(Topic topic) {
        return topicBuses.computeIfAbsent(topic, t -> new EventBus(executor));
    }

    public <T> Subscription<T> subscribe(Topic topic,
                                         Class<T> eventType,
                                         Subscriber<? super T> subscriber) {
        return subscribe(topic, eventType, subscriber, null);
    }

    public <T> Subscription<T> subscribe(Topic topic,
                                         Class<T> eventType,
                                         Subscriber<? super T> subscriber,
                                         java.util.function.Predicate<? super T> filter) {
        return busFor(topic).subscribe(eventType, subscriber, filter);
    }

    public void unsubscribe(Topic topic, Subscription<?> subscription) {
        EventBus bus = topicBuses.get(topic);
        if (bus != null) {
            bus.unsubscribe(subscription);
        }
    }

    public <T> void publish(Topic topic, Event<T> event) {
        EventBus bus = topicBuses.get(topic);
        if (bus != null) {
            bus.publish(event);
        }
    }

    public void shutdown() {
        for (EventBus bus : topicBuses.values()) {
            bus.shutdown();
        }
        if (executor instanceof ExecutorService) {
            ((ExecutorService) executor).shutdown();
        }
    }
}
```