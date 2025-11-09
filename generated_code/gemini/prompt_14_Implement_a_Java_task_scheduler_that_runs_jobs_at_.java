```java
import java.util.concurrent.*;
import java.util.function.Consumer;

public class TaskScheduler {

    private final ScheduledExecutorService scheduler;

    public TaskScheduler(int poolSize) {
        this.scheduler = Executors.newScheduledThreadPool(poolSize);
    }

    public void scheduleAtFixedRate(Runnable task, long initialDelay, long period, TimeUnit unit) {
        scheduler.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    public void scheduleWithFixedDelay(Runnable task, long initialDelay, long delay, TimeUnit unit) {
        scheduler.scheduleWithFixedDelay(task, initialDelay, delay, unit);
    }

    public ScheduledFuture<?> schedule(Runnable task, long delay, TimeUnit unit) {
        return scheduler.schedule(task, delay, unit);
    }

    public <T> void scheduleWithTrigger(
            Runnable task,
            Trigger trigger,
            Consumer<ScheduledFuture<?>> completionHandler
    ) {
        scheduleRecursive(task, trigger, completionHandler);
    }

    private void scheduleRecursive(
            Runnable task,
            Trigger trigger,
            Consumer<ScheduledFuture<?>> completionHandler
    ) {
        long delay = trigger.nextDelay();

        ScheduledFuture<?> future = scheduler.schedule(() -> {
            try {
                task.run();
            } finally {
                scheduleRecursive(task, trigger, completionHandler);
            }
        }, delay, trigger.getTimeUnit());

        completionHandler.accept(future);
    }

    public void shutdown() {
        scheduler.shutdown();
    }

    public void shutdownNow() {
        scheduler.shutdownNow();
    }

    public boolean isShutdown() {
        return scheduler.isShutdown();
    }

    public boolean isTerminated() {
        return scheduler.isTerminated();
    }

    public static void main(String[] args) throws InterruptedException {
        TaskScheduler scheduler = new TaskScheduler(2);

        // Example 1: Schedule a task to run every 2 seconds
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Fixed rate task: " + System.currentTimeMillis());
        }, 0, 2, TimeUnit.SECONDS);

        // Example 2: Schedule a task to run with a 3-second delay after each execution
        scheduler.scheduleWithFixedDelay(() -> {
            System.out.println("Fixed delay task: " + System.currentTimeMillis());
        }, 1, 3, TimeUnit.SECONDS);

        // Example 3: Schedule a task to run once after a 5-second delay
        scheduler.schedule(() -> {
            System.out.println("One-time task: " + System.currentTimeMillis());
        }, 5, TimeUnit.SECONDS);

        // Example 4: Schedule a task using a custom trigger (random delay between 1 and 5 seconds)
        Trigger randomDelayTrigger = () -> (long) (Math.random() * 4 + 1); // Delay between 1 and 5 seconds

        scheduler.scheduleWithTrigger(
            () -> System.out.println("Triggered task: " + System.currentTimeMillis()),
            randomDelayTrigger,
            future -> System.out.println("Scheduled next run.")
        );

        Thread.sleep(15000); // Let the tasks run for 15 seconds

        scheduler.shutdown();
        scheduler.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("Scheduler shutdown.");
    }
}

interface Trigger {
    long nextDelay();
    default TimeUnit getTimeUnit() {
        return TimeUnit.SECONDS;
    }
}
```