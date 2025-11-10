```java
package com.example.scheduler;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Recurring task scheduler with configurable intervals.
 */
public class ConfigurableScheduler implements AutoCloseable {

    private final ScheduledExecutorService executor;
    private final ConcurrentMap<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    public ConfigurableScheduler(int poolSize) {
        this.executor = Executors.newScheduledThreadPool(poolSize, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            t.setName("ConfigurableScheduler-" + t.getId());
            return t;
        });
    }

    public ConfigurableScheduler() {
        this(Runtime.getRuntime().availableProcessors());
    }

    /**
     * Schedules a recurring task.
     *
     * @param id            unique identifier for the task
     * @param task          the runnable to execute
     * @param initialDelay  delay before first execution
     * @param interval      period between successive executions
     * @param timeUnit      unit of the interval
     * @return true if the task was scheduled, false if a task with the same id already exists
     */
    public boolean scheduleAtFixedRate(
            String id,
            Runnable task,
            Duration initialDelay,
            Duration interval,
            TimeUnit timeUnit) {

        Objects.requireNonNull(id);
        Objects.requireNonNull(task);
        Objects.requireNonNull(initialDelay);
        Objects.requireNonNull(interval);
        Objects.requireNonNull(timeUnit);

        if (scheduledTasks.containsKey(id)) {
            return false;
        }

        ScheduledFuture<?> future = executor.scheduleAtFixedRate(
                wrap(task, id),
                initialDelay.toMillis(),
                interval.toMillis(),
                timeUnit);

        scheduledTasks.put(id, future);
        return true;
    }

    /**
     * Cancels a scheduled task.
     *
     * @param id          task identifier
     * @param mayInterruptIfRunning true to interrupt if running
     * @return true if the task was cancelled, false if no such task exists
     */
    public boolean cancel(String id, boolean mayInterruptIfRunning) {
        ScheduledFuture<?> future = scheduledTasks.remove(id);
        if (future != null) {
            return future.cancel(mayInterruptIfRunning);
        }
        return false;
    }

    /**
     * Updates the interval of an existing task.
     *
     * @param id          task identifier
     * @param newInterval new period between executions
     * @param timeUnit    unit of the interval
     * @return true if the interval was updated, false otherwise
     */
    public boolean reschedule(String id, Duration newInterval, TimeUnit timeUnit) {
        Objects.requireNonNull(newInterval);
        Objects.requireNonNull(timeUnit);
        ScheduledFuture<?> oldFuture = scheduledTasks.get(id);
        if (oldFuture == null) return false;

        // Cancel old task but keep the runnable
        Runnable task = ((WrappedRunnable) ((ScheduledThreadPoolExecutor) executor)
                .getQueue()
                .stream()
                .filter(r -> r instanceof ScheduledFutureTask)
                .map(r -> ((ScheduledFutureTask<?>) r).getRunnable())
                .filter(r -> r instanceof WrappedRunnable && ((WrappedRunnable) r).id.equals(id))
                .findFirst()
                .orElse(null));

        if (task == null) return false;

        cancel(id, true);
        return scheduleAtFixedRate(id, ((WrappedRunnable) task).delegate, Duration.ZERO, newInterval, timeUnit);
    }

    private Runnable wrap(Runnable task, String id) {
        return new WrappedRunnable(task, id);
    }

    private static final class WrappedRunnable implements Runnable {
        private final Runnable delegate;
        private final String id;
        private final AtomicBoolean running = new AtomicBoolean(false);

        WrappedRunnable(Runnable delegate, String id) {
            this.delegate = delegate;
            this.id = id;
        }

        @Override
        public void run() {
            if (running.compareAndSet(false, true)) {
                try {
                    delegate.run();
                } finally {
                    running.set(false);
                }
            }
        }
    }

    @Override
    public void close() {
        executor.shutdownNow();
    }

    // ------------------------------------------------------------
    // Example usage
    // ------------------------------------------------------------
    public static void main(String[] args) throws InterruptedException {
        try (ConfigurableScheduler scheduler = new ConfigurableScheduler(2)) {

            scheduler.scheduleAtFixedRate(
                    "task1",
                    () -> System.out.println("Task1 executed at " + System.currentTimeMillis()),
                    Duration.ofSeconds(1),
                    Duration.ofSeconds(5),
                    TimeUnit.MILLISECONDS);

            scheduler.scheduleAtFixedRate(
                    "task2",
                    () -> System.out.println("Task2 executed at " + System.currentTimeMillis()),
                    Duration.ofSeconds(2),
                    Duration.ofSeconds(3),
                    TimeUnit.MILLISECONDS);

            Thread.sleep(15000);

            scheduler.cancel("task1", true);
            System.out.println("Cancelled task1");

            Thread.sleep(10000);
        }
    }
}
```