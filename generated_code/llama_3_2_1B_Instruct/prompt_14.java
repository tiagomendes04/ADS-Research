```java
import java.util.*;
import java.util.concurrent.*;

public class Scheduler {
    public static void main(String[] args) {
        // Create a map to store jobs
        Map<String, Runnable> jobs = new HashMap<>();

        // Define job types
        Set<String> jobTypes = new HashSet<>(Arrays.asList("interval", "trigger"));

        // Schedule jobs at 1 second interval
        for (int i = 0; i < 10; i++) {
            jobs.put("job_" + i, () -> System.out.println("Job " + (i + 1)));
        }

        // Schedule jobs on triggers
        List<String> triggers = Arrays.asList("00:00", "23:59");
        for (String trigger : triggers) {
            jobs.put("job_trigger_" + trigger, () -> System.out.println("Job triggered at " + trigger));
        }

        // Run jobs
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        for (Map.Entry<String, Runnable> entry : jobs.entrySet()) {
            executor.scheduleAtFixedRate(entry.getValue(), 0, 1000, TimeUnit.MILLISECONDS);
        }
        executor.shutdown();
    }
}
```