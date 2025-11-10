```java
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.function.Supplier;

/* -------------------- Job -------------------- */
@FunctionalInterface
public interface Job extends Runnable {}

/* -------------------- Trigger -------------------- */
@FunctionalInterface
public interface Trigger {
    /**
     * @return the next execution instant, or {@code null} if no further executions are required.
     */
    Instant nextExecution();
}

/* -------------------- Scheduler -------------------- */
public class Scheduler {

    private final ScheduledExecutorService executor;
    private final ExecutorService triggerExecutor;
    private final ConcurrentMap<Job, ScheduledFuture<?>> fixedRateTasks = new ConcurrentHashMap<>();
    private final ConcurrentMap<Job, Future<?>> triggerTasks = new ConcurrentHashMap<>();

    public Scheduler(int poolSize) {
        this.executor = Executors.newScheduledThreadPool(poolSize);
        this.triggerExecutor = Executors.newCachedThreadPool();
    }

    /* ---- Fixed‑rate / fixed‑delay scheduling ---- */
    public ScheduledFuture<?> scheduleAtFixedRate(Job job,
                                                  long initialDelay,
                                                  long period,
                                                  TimeUnit unit) {
        Objects.requireNonNull(job);
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(job, initialDelay, period, unit);
        fixedRateTasks.put(job, future);
        return future;
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Job job,
                                                     long initialDelay,
                                                     long delay,
                                                     TimeUnit unit) {
        Objects.requireNonNull(job);
        ScheduledFuture<?> future = executor.scheduleWithDelay(job, initialDelay, delay, unit);
        fixedRateTasks.put(job, future);
        return future;
    }

    /* ---- Trigger‑based scheduling ---- */
    public void schedule(Job job, Trigger trigger) {
        Objects.requireNonNull(job);
        Objects.requireNonNull(trigger);
        Future<?> future = triggerExecutor.submit(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Instant next = trigger.nextExecution();
                    if (next == null) break;
                    long waitMillis = Duration.between(Instant.now(), next).toMillis();
                    if (waitMillis > 0) Thread.sleep(waitMillis);
                    job.run();
                }
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        });
        triggerTasks.put(job, future);
    }

    /* ---- Cancellation & shutdown ---- */
    public boolean cancel(Job job, boolean mayInterruptIfRunning) {
        ScheduledFuture<?> f1 = fixedRateTasks.remove(job);
        if (f1 != null && !f1.isDone()) return f1.cancel(mayInterruptIfRunning);
        Future<?> f2 = triggerTasks.remove(job);
        if (f2 != null && !f2.isDone()) return f2.cancel(mayInterruptIfRunning);
        return false;
    }

    public void shutdown() {
        fixedRateTasks.values().forEach(f -> f.cancel(true));
        triggerTasks.values().forEach(f -> f.cancel(true));
        executor.shutdownNow();
        triggerExecutor.shutdownNow();
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        long deadline = System.nanoTime() + unit.toNanos(timeout);
        if (!executor.awaitTermination(deadline - System.nanoTime(), TimeUnit.NANOSECONDS)) return false;
        return triggerExecutor.awaitTermination(deadline - System.nanoTime(), TimeUnit.NANOSECONDS);
    }
}

/* -------------------- Example Triggers -------------------- */

/* Fixed‑delay trigger – executes every `delay` seconds */
class FixedDelayTrigger implements Trigger {
    private final Duration delay;
    private Instant next;

    public FixedDelayTrigger(Duration delay) {
        this.delay = delay;
        this.next = Instant.now().plus(delay);
    }

    @Override
    public synchronized Instant nextExecution() {
        Instant cur = next;
        next = cur.plus(delay);
        return cur;
    }
}

/* One‑shot trigger – executes once at a specific time */
class OneShotTrigger implements Trigger {
    private final Instant executionTime;
    private boolean fired = false;

    public OneShotTrigger(Instant executionTime) {
        this.executionTime = executionTime;
    }

    @Override
    public synchronized Instant nextExecution() {
        if (fired) return null;
        fired = true;
        return executionTime;
    }
}

/* -------------------- Usage Example -------------------- */
class Demo {
    public static void main(String[] args) throws Exception {
        Scheduler scheduler = new Scheduler(4);

        Job helloJob = () -> System.out.println("Hello at " + Instant.now());

        // Fixed‑rate every 2 seconds, start after 1 second
        scheduler.scheduleAtFixedRate(helloJob, 1, 2, TimeUnit.SECONDS);

        // Trigger‑based: every 3 seconds using FixedDelayTrigger
        scheduler.schedule(() -> System.out.println("Triggered at " + Instant.now()),
                new FixedDelayTrigger(Duration.ofSeconds(3)));

        // One‑shot trigger 5 seconds from now
        scheduler.schedule(() -> System.out.println("One‑shot at " + Instant.now()),
                new OneShotTrigger(Instant.now().plusSeconds(5)));

        // Let the demo run for 15 seconds
        Thread.sleep(15000);
        scheduler.shutdown();
        scheduler.awaitTermination(5, TimeUnit.SECONDS);
    }
}
```