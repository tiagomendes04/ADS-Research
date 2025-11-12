```java
import java.util.*;
import java.util.concurrent.*;

class NewsletterSystem {
    private final Set<String> subscribers = new HashSet<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void subscribe(String email) {
        subscribers.add(email);
    }

    public void unsubscribe(String email) {
        subscribers.remove(email);
    }

    public void scheduleNewsletter(Runnable task, long initialDelay, long period, TimeUnit timeUnit) {
        scheduler.scheduleAtFixedRate(task, initialDelay, period, timeUnit);
    }

    public void sendNewsletter(String subject, String content) {
        for (String subscriber : subscribers) {
            System.out.println("Sending to: " + subscriber);
            System.out.println("Subject: " + subject);
            System.out.println("Content: " + content);
        }
    }

    public void shutdown() {
        scheduler.shutdown();
    }

    public static void main(String[] args) {
        NewsletterSystem system = new NewsletterSystem();
        system.subscribe("example1@example.com");
        system.subscribe("example2@example.com");

        Runnable newsletterTask = () -> {
            String subject = "Weekly Newsletter";
            String content = "Hello! Here is your weekly update.";
            system.sendNewsletter(subject, content);
        };

        system.scheduleNewsletter(newsletterTask, 0, 7, TimeUnit.DAYS);
    }
}
```