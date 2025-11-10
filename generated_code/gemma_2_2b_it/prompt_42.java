```java
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RecurringTaskScheduler {

    private ScheduledExecutorService executor;

    public RecurringTaskScheduler(Runnable task, long initialDelay, long timeInterval) {
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(task, initialDelay, timeInterval, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        executor.shutdown();
    }
}
```