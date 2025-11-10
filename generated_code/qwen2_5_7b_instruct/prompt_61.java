```java
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExamProctoringSystem {

    private Map<String, StudentSession> sessions = new HashMap<>();
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

    public ExamProctoringSystem() {
        executor.scheduleAtFixedRate(this::monitorSessions, 0, 5, TimeUnit.SECONDS);
    }

    public void startSession(String studentId) {
        sessions.put(studentId, new StudentSession(studentId));
        System.out.println("Session started for student: " + studentId);
    }

    public void stopSession(String studentId) {
        sessions.remove(studentId);
        System.out.println("Session stopped for student: " + studentId);
    }

    private void monitorSessions() {
        for (Map.Entry<String, StudentSession> entry : sessions.entrySet()) {
            StudentSession session = entry.getValue();
            if (session.isInactive()) {
                stopSession(entry.getKey());
            }
        }
    }

    public static class StudentSession {
        private String studentId;
        private long lastActivityTime;
        private int inactivityThreshold = 30; // seconds

        public StudentSession(String studentId) {
            this.studentId = studentId;
        }

        public void registerActivity() {
            lastActivityTime = System.currentTimeMillis();
        }

        public boolean isInactive() {
            return (System.currentTimeMillis() - lastActivityTime) > (inactivityThreshold * 1000);
        }
    }

    public static void main(String[] args) {
        ExamProctoringSystem system = new ExamProctoringSystem();
        system.startSession("12345");
        system.startSession("67890");

        // Simulate activity
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        system.registerActivityFor("12345");

        // Simulate inactivity for 30 seconds
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Simulate activity again
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        system.registerActivityFor("67890");

        // Simulate inactivity for 30 seconds
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Stop all sessions
        system.stopSession("12345");
        system.stopSession("67890");
    }

    public void registerActivityFor(String studentId) {
        sessions.get(studentId).registerActivity();
    }
}
```