```java
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskScheduler {

    private ScheduledExecutorService scheduler;

    public TaskScheduler() {
        scheduler = Executors.newScheduledThreadPool(1);
    }

    public void scheduleJob(Runnable job, long initialDelay, long period) {
        scheduler.scheduleAtFixedRate(job, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    public void scheduleJob(Runnable job, long delay) {
        scheduler.schedule(job, delay, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}
```