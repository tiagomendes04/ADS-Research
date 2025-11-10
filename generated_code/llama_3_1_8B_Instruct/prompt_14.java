```java
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskScheduler {

    private final ScheduledExecutorService scheduler;
    private final List<Job> jobs = new ArrayList<>();

    public TaskScheduler(int numThreads) {
        this.scheduler = Executors.newScheduledThreadPool(numThreads);
    }

    public void schedule(Job job) {
        jobs.add(job);
        scheduler.scheduleAtFixedRate(job::run, 0, job.getInterval(), TimeUnit.SECONDS);
    }

    public void schedule(Trigger trigger, Runnable job) {
        jobs.add(new TriggerJob(trigger, job));
        scheduler.scheduleAtFixedRate(job::run, 0, trigger.getInterval(), TimeUnit.SECONDS);
    }

    public void cancelJob(Job job) {
        jobs.remove(job);
        scheduler.remove(job.getTask());
    }

    public void shutdown() {
        scheduler.shutdownNow();
    }

    public static class Job {
        private final Runnable task;
        private final long interval;

        public Job(Runnable task, long interval) {
            this.task = task;
            this.interval = interval;
        }

        public Runnable getTask() {
            return task;
        }

        public long getInterval() {
            return interval;
        }
    }

    public static class Trigger {
        private final Instant startTime;
        private final Duration interval;

        public Trigger(Instant startTime, Duration interval) {
            this.startTime = startTime;
            this.interval = interval;
        }

        public Instant getStartTime() {
            return startTime;
        }

        public Duration getInterval() {
            return interval;
        }
    }

    private static class TriggerJob implements Runnable {
        private final Trigger trigger;
        private final Runnable job;

        public TriggerJob(Trigger trigger, Runnable job) {
            this.trigger = trigger;
            this.job = job;
        }

        @Override
        public void run() {
            if (ZonedDateTime.ofInstant(trigger.getStartTime(), ZoneId.systemDefault()).isBefore(ZonedDateTime.now())) {
                job.run();
                trigger.setStartTime(trigger.getStartTime().plus(trigger.getInterval()));
            }
        }
    }
}
```

```java
public class Trigger {
    private Instant startTime;
    private Duration interval;

    public Trigger(Instant startTime, Duration interval) {
        this.startTime = startTime;
        this.interval = interval;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Duration getInterval() {
        return interval;
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler(1);

        Runnable job = () -> System.out.println("Job executed");

        scheduler.schedule(new TaskScheduler.Job(job, 5));

        Trigger trigger = new Trigger(Instant.now().plus(Duration.ofSeconds(5)), Duration.ofSeconds(5));
        scheduler.schedule(trigger, job);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        scheduler.shutdown();
    }
}
```