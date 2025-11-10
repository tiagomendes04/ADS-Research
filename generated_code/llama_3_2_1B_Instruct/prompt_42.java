```java
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    private final int maxThreads;
    private final int interval;
    private final ExecutorService executor;

    public Scheduler(int maxThreads, int interval) {
        this.maxThreads = maxThreads;
        this.interval = interval;
        this.executor = Executors.newFixedThreadPool(maxThreads);
    }

    public void schedule(Runnable task) {
        task.run();
        executor.execute(() -> {
            while (true) {
                try {
                    LocalDateTime now = LocalDateTime.now();
                    Duration duration = Duration.between(now, now.plusSeconds(interval));
                    if (duration.getSeconds() >= interval) {
                        task.run();
                        duration = Duration.between(now, now.plusSeconds(interval));
                    }
                    Thread.sleep(duration.getSeconds());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    public void shutdown() {
        executor.shutdown();
    }
}
```

```java
public class Main {

    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler(5, 10);
        scheduler.schedule(() -> {
            System.out.println("Task executed at " + LocalDateTime.now());
        });
        scheduler.shutdown();
    }
}
```