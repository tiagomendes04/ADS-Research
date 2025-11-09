**TaskScheduler.java**
```java
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskScheduler {
    private final ScheduledExecutorService scheduler;

    public TaskScheduler() {
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

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
}
```

**Trigger.java**
```java
public interface Trigger {
    boolean isTriggered(long time);
}
```

**IntervalTrigger.java**
```java
public class IntervalTrigger implements Trigger {
    private final long interval;

    public IntervalTrigger(long interval) {
        this.interval = interval;
    }

    @Override
    public boolean isTriggered(long time) {
        return time % interval == 0;
    }
}
```

**CustomTrigger.java**
```java
public class CustomTrigger implements Trigger {
    private final long[] triggerTimes;

    public CustomTrigger(long[] triggerTimes) {
        this.triggerTimes = triggerTimes;
    }

    @Override
    public boolean isTriggered(long time) {
        for (long triggerTime : triggerTimes) {
            if (time == triggerTime) {
                return true;
            }
        }
        return false;
    }
}
```

**Job.java**
```java
public interface Job {
    void execute();
}
```

**ExampleUsage.java**
```java
public class ExampleUsage {
    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler();

        // Schedule a job to run every 5 seconds
        scheduler.scheduleAtFixedRate(new Job() {
            @Override
            public void execute() {
                System.out.println("Job executed at fixed rate");
            }
        }, 0, 5, TimeUnit.SECONDS);

        // Schedule a job to run every 10 seconds with a delay of 2 seconds
        scheduler.scheduleWithFixedDelay(new Job() {
            @Override
            public void execute() {
                System.out.println("Job executed with fixed delay");
            }
        }, 0, 2, TimeUnit.SECONDS);

        // Schedule a job to run at a custom trigger time
        scheduler.schedule(new Job() {
            @Override
            public void execute() {
                System.out.println("Job executed at custom trigger time");
            }
        }, 10000, TimeUnit.MILLISECONDS);

        // Shutdown the scheduler
        scheduler.shutdown();
    }
}
```

**TriggerExample.java**
```java
public class TriggerExample {
    public static void main(String[] args) {
        Trigger trigger = new IntervalTrigger(5);

        for (int i = 0; i < 10; i++) {
            if (trigger.isTriggered(i)) {
                System.out.println("Triggered at " + i);
            }
        }
    }
}
```

**CustomTriggerExample.java**
```java
public class CustomTriggerExample {
    public static void main(String[] args) {
        Trigger trigger = new CustomTrigger(new long[] { 5, 10, 15 });

        for (int i = 0; i < 20; i++) {
            if (trigger.isTriggered(i)) {
                System.out.println("Triggered at " + i);
            }
        }
    }
}
```