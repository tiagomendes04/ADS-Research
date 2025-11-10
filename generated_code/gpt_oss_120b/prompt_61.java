```java
// src/main/java/com/proctoring/ProctoringApp.java
package com.proctoring;

import com.proctoring.monitor.CameraMonitor;
import com.proctoring.monitor.ScreenMonitor;
import com.proctoring.session.ExamSession;
import com.proctoring.report.ReportGenerator;

public class ProctoringApp {
    public static void main(String[] args) {
        ExamSession session = new ExamSession("exam-123", "student-456");
        CameraMonitor cam = new CameraMonitor(session);
        ScreenMonitor screen = new ScreenMonitor(session);

        cam.start();
        screen.start();

        // Simulate exam duration (e.g., 60 minutes)
        try {
            Thread.sleep(60 * 60 * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        cam.stop();
        screen.stop();

        ReportGenerator.generate(session);
    }
}
```

```java
// src/main/java/com/proctoring/session/ExamSession.java
package com.proctoring.session;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExamSession {
    private final String examId;
    private final String studentId;
    private final LocalDateTime startTime;
    private LocalDateTime endTime;
    private final List<ProctorEvent> events = Collections.synchronizedList(new ArrayList<>());

    public ExamSession(String examId, String studentId) {
        this.examId = examId;
        this.studentId = studentId;
        this.startTime = LocalDateTime.now();
    }

    public String getExamId() {
        return examId;
    }

    public String getStudentId() {
        return studentId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setEndTime(LocalDateTime end) {
        this.endTime = end;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void addEvent(ProctorEvent event) {
        events.add(event);
    }

    public List<ProctorEvent> getEvents() {
        return new ArrayList<>(events);
    }
}
```

```java
// src/main/java/com/proctoring/session/ProctorEvent.java
package com.proctoring.session;

import java.time.LocalDateTime;

public class ProctorEvent {
    public enum Type {
        FACE_NOT_DETECTED,
        MULTIPLE_FACES,
        SCREEN_CHANGE,
        IDLE_TIME,
        AUDIO_DETECTED,
        CUSTOM
    }

    private final Type type;
    private final LocalDateTime timestamp;
    private final String details;

    public ProctorEvent(Type type, String details) {
        this.type = type;
        this.timestamp = LocalDateTime.now();
        this.details = details;
    }

    public Type getType() {
        return type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getDetails() {
        return details;
    }
}
```

```java
// src/main/java/com/proctoring/monitor/Monitor.java
package com.proctoring.monitor;

import com.proctoring.session.ExamSession;

public abstract class Monitor implements Runnable {
    protected final ExamSession session;
    protected volatile boolean running = false;
    protected Thread thread;

    public Monitor(ExamSession session) {
        this.session = session;
    }

    public void start() {
        if (running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running = false;
        if (thread != null) {
            thread.interrupt();
        }
    }
}
```

```java
// src/main/java/com/proctoring/monitor/CameraMonitor.java
package com.proctoring.monitor;

import com.proctoring.session.ExamSession;
import com.proctoring.session.ProctorEvent;
import com.proctoring.session.ProctorEvent.Type;

public class CameraMonitor extends Monitor {

    public CameraMonitor(ExamSession session) {
        super(session);
    }

    @Override
    public void run() {
        // Placeholder: replace with actual webcam handling (e.g., OpenCV)
        while (running) {
            try {
                // Simulate face detection logic
                boolean faceDetected = simulateFaceDetection();
                if (!faceDetected) {
                    session.addEvent(new ProctorEvent(Type.FACE_NOT_DETECTED,
                            "No face detected in frame"));
                } else if (simulateMultipleFaces()) {
                    session.addEvent(new ProctorEvent(Type.MULTIPLE_FACES,
                            "Multiple faces detected"));
                }

                Thread.sleep(1000); // capture interval
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private boolean simulateFaceDetection() {
        // Randomly return true/false for demo purposes
        return Math.random() > 0.05;
    }

    private boolean simulateMultipleFaces() {
        return Math.random() < 0.02;
    }
}
```

```java
// src/main/java/com/proctoring/monitor/ScreenMonitor.java
package com.proctoring.monitor;

import com.proctoring.session.ExamSession;
import com.proctoring.session.ProctorEvent;
import com.proctoring.session.ProctorEvent.Type;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class ScreenMonitor extends Monitor {

    private final Set<String> allowedApplications = Set.of("ExamClient", "Browser");

    public ScreenMonitor(ExamSession session) {
        super(session);
    }

    @Override
    public void run() {
        // Placeholder: replace with actual screen capture & process detection
        while (running) {
            try {
                BufferedImage screenshot = captureScreen();
                // In a real implementation, analyze screenshot for prohibited content

                String activeApp = simulateActiveApplication();
                if (!allowedApplications.contains(activeApp)) {
                    session.addEvent(new ProctorEvent(Type.SCREEN_CHANGE,
                            "Prohibited application active: " + activeApp));
                }

                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private BufferedImage captureScreen() {
        try {
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            return new Robot().createScreenCapture(screenRect);
        } catch (AWTException e) {
            return null;
        }
    }

    private String simulateActiveApplication() {
        // Randomly return an application name
        double r = Math.random();
        if (r < 0.80) return "ExamClient";
        if (r < 0.90) return "Browser";
        return "Notepad";
    }
}
```

```java
// src/main/java/com/proctoring/report/ReportGenerator.java
package com.proctoring.report;

import com.proctoring.session.ExamSession;
import com.proctoring.session.ProctorEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportGenerator {

    public static void generate(ExamSession session) {
        String fileName = "report-" + session.getExamId() + "-" + session.getStudentId() + ".csv";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("Exam ID,Student ID,Start Time,End Time\n");
            writer.append(session.getExamId()).append(',')
                  .append(session.getStudentId()).append(',')
                  .append(session.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append(',')
                  .append(session.getEndTime() != null ?
                          session.getEndTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : "")
                  .append('\n');

            writer.append("\nEvent Type,Timestamp,Details\n");
            List<ProctorEvent> events = session.getEvents();
            for (ProctorEvent ev : events) {
                writer.append(ev.getType().name()).append(',')
                      .append(ev.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append(',')
                      .append(ev.getDetails().replaceAll(",", ";")).append('\n');
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

```xml
<!-- pom.xml -->
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.proctoring</groupId>
    <artifactId>online-exam-proctor</artifactId>
    <version>1.0.0</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>

    <dependencies>
        <!-- Add real webcam/screen capture libs here