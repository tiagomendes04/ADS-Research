```java
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskScheduler {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void scheduleTask(Runnable task, long initialDelay, long period, TimeUnit timeUnit) {
        scheduler.scheduleAtFixedRate(task, initialDelay, period, timeUnit);
    }

    public void shutdown() {
        scheduler.shutdown();
    }

    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler();
        Runnable task = () -> System.out.println("Task executed at: " + System.currentTimeMillis());
        scheduler.scheduleTask(task, 0, 5, TimeUnit.SECONDS);
        
        // To stop the scheduler after some time, you can uncomment the following lines:
        // try {
        //     Thread.sleep(30000);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        // scheduler.shutdown();
    }
}
```