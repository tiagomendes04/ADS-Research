```java
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskScheduler {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void scheduleAtFixedRate(Runnable task, long initialDelay, long period, TimeUnit unit) {
        scheduler.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    public void scheduleWithFixedDelay(Runnable task, long initialDelay, long delay, TimeUnit unit) {
        scheduler.scheduleWithFixedDelay(task, initialDelay, delay, unit);
    }

    public void schedule(Runnable task, long delay, TimeUnit unit) {
        scheduler.schedule(task, delay, unit);
    }

    public void shutdown() {
        scheduler.shutdown();
    }

    public static void main(String[] args) {
        TaskScheduler taskScheduler = new TaskScheduler();
        Runnable task = () -> System.out.println("Task executed at " + System.currentTimeMillis());

        taskScheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
    }
}
```