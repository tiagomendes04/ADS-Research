```java
package com.exam.proctoring;

import java.io.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

// -------------------- Core Entities --------------------
class ExamSession {
    private final String examId;
    private final String studentId;
    private final LocalDateTime startTime;
    private final Duration duration;
    private final MonitoringService monitoringService;
    private final ReportGenerator reportGenerator;
    private final AtomicBoolean active = new AtomicBoolean(false);

    public ExamSession(String examId, String studentId, Duration duration,
                       MonitoringService monitoringService,
                       ReportGenerator reportGenerator) {
        this.examId = examId;
        this.studentId = studentId;
        this.startTime = LocalDateTime.now();
        this.duration = duration;
        this.monitoringService = monitoringService;
        this.reportGenerator = reportGenerator;
    }

    public void start() {
        if (active.compareAndSet(false, true)) {
            monitoringService.start(this);
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.schedule(this::stop, duration.toMillis(), TimeUnit.MILLISECONDS);
        }
    }

    public void stop() {
        if (active.compareAndSet(true, false)) {
            monitoringService.stop(this);
            Report report = reportGenerator.generate(this, monitoringService.getEvents(this));
            report.save("reports/" + examId + "_" + studentId + ".json");
        }
    }

    public String getExamId() { return examId; }
    public String getStudentId() { return studentId; }
    public LocalDateTime getStartTime() { return startTime; }
    public Duration getDuration() { return duration; }
    public boolean isActive() { return active.get(); }
}

// -------------------- Monitoring Service --------------------
interface MonitoringService {
    void start(ExamSession session);
    void stop(ExamSession session);
    List<MonitoringEvent> getEvents(ExamSession session);
}

class DefaultMonitoringService implements MonitoringService {
    private final Map<String, List<MonitoringEvent>> sessionEvents = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public void start(ExamSession session) {
        sessionEvents.put(session.getExamId(), Collections.synchronizedList(new ArrayList<>()));
        executor.submit(() -> captureVideo(session));
        executor.submit(() -> captureAudio(session));
        executor.submit(() -> captureScreen(session));
        executor.submit(() -> detectAnomalies(session));
    }

    @Override
    public void stop(ExamSession session) {
        // In real implementation we would gracefully stop capture threads.
        // Here we just rely on thread interruption.
        executor.shutdownNow();
    }

    @Override
    public List<MonitoringEvent> getEvents(ExamSession session) {
        return sessionEvents.getOrDefault(session.getExamId(), Collections.emptyList());
    }

    // -------------------- Mock Capture Implementations --------------------
    private void captureVideo(ExamSession session) {
        while (!Thread.currentThread().isInterrupted()) {
            recordEvent(session, MonitoringEvent.Type.VIDEO_FRAME, "video_frame_placeholder");
            sleep(200);
        }
    }

    private void captureAudio(ExamSession session) {
        while (!Thread.currentThread().isInterrupted()) {
            recordEvent(session, MonitoringEvent.Type.AUDIO_SAMPLE, "audio_sample_placeholder");
            sleep(300);
        }
    }

    private void captureScreen(ExamSession session) {
        while (!Thread.currentThread().isInterrupted()) {
            recordEvent(session, MonitoringEvent.Type.SCREENSHOT, "screenshot_placeholder");
            sleep(500);
        }
    }

    private void detectAnomalies(ExamSession session) {
        Random rnd = new Random();
        while (!Thread.currentThread().isInterrupted()) {
            if (rnd.nextDouble() < 0.02) { // 2% chance of anomaly per check
                recordEvent(session, MonitoringEvent.Type.ANOMALY, "Potential cheating detected");
            }
            sleep(1000);
        }
    }

    private void recordEvent(ExamSession session, MonitoringEvent.Type type, String payload) {
        MonitoringEvent event = new MonitoringEvent(
                session.getExamId(),
                session.getStudentId(),
                LocalDateTime.now(),
                type,
                payload
        );
        sessionEvents.computeIfAbsent(session.getExamId(), k -> Collections.synchronizedList(new ArrayList<>()))
                     .add(event);
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}

// -------------------- Monitoring Event --------------------
class MonitoringEvent {
    enum Type { VIDEO_FRAME, AUDIO_SAMPLE, SCREENSHOT, ANOMALY }

    private final String examId;
    private final String studentId;
    private final LocalDateTime timestamp;
    private final Type type;
    private final String payload; // could be a file path, base64 data, or description

    public MonitoringEvent(String examId, String studentId, LocalDateTime timestamp,
                           Type type, String payload) {
        this.examId = examId;
        this.studentId = studentId;
        this.timestamp = timestamp;
        this.type = type;
        this.payload = payload;
    }

    // Getters
    public String getExamId() { return examId; }
    public String getStudentId() { return studentId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Type getType() { return type; }
    public String getPayload() { return payload; }
}

// -------------------- Reporting --------------------
class Report {
    private final String examId;
    private final String studentId;
    private final LocalDateTime generatedAt;
    private final List<MonitoringEvent> events;

    public Report(String examId, String studentId, LocalDateTime generatedAt, List<MonitoringEvent> events) {
        this.examId = examId;
        this.studentId = studentId;
        this.generatedAt = generatedAt;
        this.events = new ArrayList<>(events);
    }

    public void save(String filePath) {
        try (Writer writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"examId\": \"").append(examId).append("\",\n");
        sb.append("  \"studentId\": \"").append(studentId).append("\",\n");
        sb.append("  \"generatedAt\": \"").append(generatedAt).append("\",\n");
        sb.append("  \"events\": [\n");
        for (int i = 0; i < events.size(); i++) {
            MonitoringEvent ev = events.get(i);
            sb.append("    {\n");
            sb.append("      \"timestamp\": \"").append(ev.getTimestamp()).append("\",\n");
            sb.append("      \"type\": \"").append(ev.getType()).append("\",\n");
            sb.append("      \"payload\": \"").append(ev.getPayload()).append("\"\n");
            sb.append("    }");
            if (i < events.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("  ]\n");
        sb.append("}");
        return sb.toString();
    }
}

class ReportGenerator {
    public Report generate(ExamSession session, List<MonitoringEvent> events) {
        return new Report(
                session.getExamId(),
                session.getStudentId(),
                LocalDateTime.now(),
                events
        );
    }
}

// -------------------- Example Usage --------------------
public class ProctoringApp {
    public static void main(String[] args) throws Exception {
        MonitoringService monitoringService = new DefaultMonitoringService();
        ReportGenerator reportGenerator = new ReportGenerator();

        ExamSession session = new ExamSession(
                "EXAM-2025-MATH101",
                "student-12345",
                Duration.ofMinutes(30),
                monitoringService,
                reportGenerator
        );

        System.out.println("Exam started for student " + session.getStudentId());
        session.start();

        // Simulate external stop (e.g., admin abort) after 10 minutes for demo
        ScheduledExecutorService adminScheduler = Executors.newSingleThreadScheduledExecutor();
        adminScheduler.schedule(() -> {
            System.out.println("Admin terminating exam early.");
            session.stop();
        }, 10, TimeUnit.MINUTES);

        // Keep main thread alive until session ends
        while (session.isActive()) {
            Thread.sleep(1000);
        }

        System.out.println("Exam finished. Report generated.");
        adminScheduler.shutdownNow();
    }
}
```