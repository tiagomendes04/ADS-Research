```java
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {
    private final ScheduledExecutorService scheduler;
    private final List<RecurringTask> tasks;

    public Scheduler() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        tasks = new ArrayList<>();
    }

    public void schedule(Runnable task, long initialDelay, long period, TimeUnit unit) {
        tasks.add(new RecurringTask(task, initialDelay, period, unit));
        scheduler.scheduleAtFixedRate(() -> {
            for (RecurringTask recurringTask : tasks) {
                if (isTimeToRun(recurringTask, LocalDateTime.now())) {
                    recurringTask.getTask().run();
                }
            }
        }, initialDelay, period, unit);
    }

    private boolean isTimeToRun(RecurringTask recurringTask, LocalDateTime now) {
        return now.getHour() == recurringTask.getHour() &&
               now.getMinute() == recurringTask.getMinute() &&
               now.getSecond() == recurringTask.getSecond();
    }

    private class RecurringTask {
        private final Runnable task;
        private final long initialDelay;
        private final long period;
        private final TimeUnit unit;

        public RecurringTask(Runnable task, long initialDelay, long period, TimeUnit unit) {
            this.task = task;
            this.initialDelay = initialDelay;
            this.period = period;
            this.unit = unit;
        }

        public Runnable getTask() {
            return task;
        }

        public long getInitialDelay() {
            return initialDelay;
        }

        public long getPeriod() {
            return period;
        }

        public TimeUnit getUnit() {
            return unit;
        }
    }
}
```

```java
public class ExampleUsage {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();

        Runnable task = () -> System.out.println("Task executed");

        scheduler.schedule(task, 0, 10, TimeUnit.SECONDS);
    }
}
```

```java
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConfigurableScheduler {
    private final ScheduledExecutorService scheduler;
    private final List<RecurringTask> tasks;

    public ConfigurableScheduler() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        tasks = new ArrayList<>();
    }

    public void schedule(Runnable task, long initialDelay, long period, TimeUnit unit) {
        tasks.add(new RecurringTask(task, initialDelay, period, unit));
        scheduler.scheduleAtFixedRate(() -> {
            for (RecurringTask recurringTask : tasks) {
                if (isTimeToRun(recurringTask, LocalDateTime.now())) {
                    recurringTask.getTask().run();
                }
            }
        }, initialDelay, period, unit);
    }

    private boolean isTimeToRun(RecurringTask recurringTask, LocalDateTime now) {
        return now.getHour() == recurringTask.getHour() &&
               now.getMinute() == recurringTask.getMinute() &&
               now.getSecond() == recurringTask.getSecond();
    }

    private class RecurringTask {
        private final Runnable task;
        private final long initialDelay;
        private final long period;
        private final TimeUnit unit;

        public RecurringTask(Runnable task, long initialDelay, long period, TimeUnit unit) {
            this.task = task;
            this.initialDelay = initialDelay;
            this.period = period;
            this.unit = unit;
        }

        public Runnable getTask() {
            return task;
        }

        public long getInitialDelay() {
            return initialDelay;
        }

        public long getPeriod() {
            return period;
        }

        public TimeUnit getUnit() {
            return unit;
        }
    }
}
```

```java
public class ExampleUsageWithConfig {
    public static void main(String[] args) {
        ConfigurableScheduler scheduler = new ConfigurableScheduler();

        Runnable task = () -> System.out.println("Task executed");

        scheduler.schedule(task, 0, 10, TimeUnit.SECONDS);
    }
}
```

```java
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AdvancedScheduler {
    private final ScheduledExecutorService scheduler;
    private final List<RecurringTask> tasks;

    public AdvancedScheduler() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        tasks = new ArrayList<>();
    }

    public void schedule(Runnable task, Duration initialDelay, Duration period) {
        tasks.add(new RecurringTask(task, initialDelay, period));
        scheduler.scheduleAtFixedRate(() -> {
            for (RecurringTask recurringTask : tasks) {
                if (isTimeToRun(recurringTask, LocalDateTime.now())) {
                    recurringTask.getTask().run();
                }
            }
        }, initialDelay.toMillis(), period.toMillis(), TimeUnit.MILLISECONDS);
    }

    private boolean isTimeToRun(RecurringTask recurringTask, LocalDateTime now) {
        return now.getHour() == recurringTask.getHour() &&
               now.getMinute() == recurringTask.getMinute() &&
               now.getSecond() == recurringTask.getSecond();
    }

    private class RecurringTask {
        private final Runnable task;
        private final Duration initialDelay;
        private final Duration period;

        public RecurringTask(Runnable task, Duration initialDelay, Duration period) {
            this.task = task;
            this.initialDelay = initialDelay;
            this.period = period;
        }

        public Runnable getTask() {
            return task;
        }

        public Duration getInitialDelay() {
            return initialDelay;
        }

        public Duration getPeriod() {
            return period;
        }
    }
}
```

```java
public class ExampleUsageWithAdvanced {
    public static void main(String[] args) {
        AdvancedScheduler scheduler = new AdvancedScheduler();

        Runnable task = () -> System.out.println("Task executed");

        scheduler.schedule(task, Duration.ofSeconds(0), Duration.ofSeconds(10));
    }
}
```