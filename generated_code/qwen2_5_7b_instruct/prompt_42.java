```java
import java.util.Timer;
import java.util.TimerTask;

public class RepeatedTaskScheduler {

    private Timer timer;

    public RepeatedTaskScheduler() {
        timer = new Timer();
    }

    public void scheduleTask(Runnable task, long initialDelay, long period) {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, initialDelay, period);
    }

    public void cancelTask() {
        timer.cancel();
    }

    public static void main(String[] args) {
        RepeatedTaskScheduler scheduler = new RepeatedTaskScheduler();
        scheduler.scheduleTask(() -> System.out.println("Task executed"), 1000, 2000);
    }
}
```