```java
import java.util.*;
import java.util.concurrent.*;

class ExamProctor {
    private final Map<String, Student> students;
    private final ExecutorService executorService;
    private final List<Report> reports;

    public ExamProctor() {
        students = new ConcurrentHashMap<>();
        executorService = Executors.newFixedThreadPool(10);
        reports = Collections.synchronizedList(new ArrayList<>());
    }

    public void addStudent(String studentId, String name) {
        students.put(studentId, new Student(studentId, name));
    }

    public void startMonitoring(String studentId) {
        if (students.containsKey(studentId)) {
            executorService.submit(() -> monitorStudent(studentId));
        }
    }

    private void monitorStudent(String studentId) {
        Student student = students.get(studentId);
        Random random = new Random();
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(random.nextInt(5000) + 5000); // Simulate monitoring interval
                int suspiciousActivityLevel = random.nextInt(101); // Simulate suspicious activity level
                if (suspiciousActivityLevel > 50) {
                    Report report = new Report(studentId, student.getName(), suspiciousActivityLevel);
                    reports.add(report);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public List<Report> getReports() {
        return new ArrayList<>(reports);
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    static class Student {
        private final String id;
        private final String name;

        public Student(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    static class Report {
        private final String studentId;
        private final String studentName;
        private final int suspiciousActivityLevel;

        public Report(String studentId, String studentName, int suspiciousActivityLevel) {
            this.studentId = studentId;
            this.studentName = studentName;
            this.suspiciousActivityLevel = suspiciousActivityLevel;
        }

        public String getStudentId() {
            return studentId;
        }

        public String getStudentName() {
            return studentName;
        }

        public int getSuspiciousActivityLevel() {
            return suspiciousActivityLevel;
        }

        @Override
        public String toString() {
            return "Report{" +
                    "studentId='" + studentId + '\'' +
                    ", studentName='" + studentName + '\'' +
                    ", suspiciousActivityLevel=" + suspiciousActivityLevel +
                    '}';
        }
    }

    public static void main(String[] args) {
        ExamProctor proctor = new ExamProctor();
        proctor.addStudent("S001", "Alice");
        proctor.addStudent("S002", "Bob");

        proctor.startMonitoring("S001");
        proctor.startMonitoring("S002");

        try {
            Thread.sleep(30000); // Monitor for 30 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Report> reports = proctor.getReports();
        for (Report report : reports) {
            System.out.println(report);
        }

        proctor.shutdown();
    }
}
```