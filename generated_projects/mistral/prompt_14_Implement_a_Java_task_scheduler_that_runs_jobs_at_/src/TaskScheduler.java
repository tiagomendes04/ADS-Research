import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TaskScheduler {
    private final ScheduledExecutorService scheduler;
    private final AtomicBoolean isRunning;
    private final Lock lock;

    public TaskScheduler() {
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.isRunning = new AtomicBoolean(false);
        this.lock = new ReentrantLock();
    }

    public void scheduleAtFixedRate(Runnable task, long initialDelay, long period, TimeUnit unit) {
        lock.lock();
        try {
            if (isRunning.compareAndSet(false, true)) {
                scheduler.scheduleAtFixedRate(task, initialDelay, period, unit);
            }
        } finally {
            lock.unlock();
        }
    }

    public void scheduleWithFixedDelay(Runnable task, long initialDelay, long delay, TimeUnit unit) {
        lock.lock();
        try {
            if (isRunning.compareAndSet(false, true)) {
                scheduler.scheduleWithFixedDelay(task, initialDelay, delay, unit);
            }
        } finally {
            lock.unlock();
        }
    }

    public void schedule(Runnable task, long delay, TimeUnit unit) {
        lock.lock();
        try {
            if (isRunning.compareAndSet(false, true)) {
                scheduler.schedule(task, delay, unit);
            }
        } finally {
            lock.unlock();
        }
    }

    public void shutdown() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler();

        // Example usage
        scheduler.scheduleAtFixedRate(() -> System.out.println("Fixed rate task"), 1, 2, TimeUnit.SECONDS);
        scheduler.scheduleWithFixedDelay(() -> System.out.println("Fixed delay task"), 1, 2, TimeUnit.SECONDS);
        scheduler.schedule(() -> System.out.println("One-time task"), 5, TimeUnit.SECONDS);

        // Shutdown after 20 seconds
        scheduler.schedule(() -> scheduler.shutdown(), 20, TimeUnit.SECONDS);
    }
}