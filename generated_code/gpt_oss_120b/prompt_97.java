```java
// File: src/main/java/com/example/assignment/Assignment.java
package com.example.assignment;

import java.time.LocalDateTime;

public class Assignment {
    private final String id;
    private final String title;
    private final LocalDateTime deadline;

    public Assignment(String id, String title, LocalDateTime deadline) {
        this.id = id;
        this.title = title;
        this.deadline = deadline;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public LocalDateTime getDeadline() { return deadline; }

    @Override
    public String toString() {
        return String.format("Assignment{id='%s', title='%s', deadline=%s}", id, title, deadline);
    }
}
```

```java
// File: src/main/java/com/example/assignment/Student.java
package com.example.assignment;

public class Student {
    private final String id;
    private final String name;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return String.format("Student{id='%s', name='%s'}", id, name);
    }
}
```

```java
// File: src/main/java/com/example/assignment/Submission.java
package com.example.assignment;

import java.time.LocalDateTime;

public class Submission {
    private final String assignmentId;
    private final String studentId;
    private final LocalDateTime timestamp;
    private final String content;

    public Submission(String assignmentId, String studentId, LocalDateTime timestamp, String content) {
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.timestamp = timestamp;
        this.content = content;
    }

    public String getAssignmentId() { return assignmentId; }
    public String getStudentId() { return studentId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getContent() { return content; }

    @Override
    public String toString() {
        return String.format("Submission{assignmentId='%s', studentId='%s', timestamp=%s, content='%s'}",
                assignmentId, studentId, timestamp, content);
    }
}
```

```java
// File: src/main/java/com/example/assignment/AssignmentService.java
package com.example.assignment;

import java.time.LocalDateTime;
import java.util.*;

public class AssignmentService {
    private final Map<String, Assignment> assignments = new HashMap<>();
    private final Map<String, Student> students = new HashMap<>();
    private final List<Submission> submissions = new ArrayList<>();

    // Assignment management
    public void addAssignment(String id, String title, LocalDateTime deadline) {
        assignments.put(id, new Assignment(id, title, deadline));
    }

    public Assignment getAssignment(String id) {
        return assignments.get(id);
    }

    public Collection<Assignment> listAssignments() {
        return assignments.values();
    }

    // Student management
    public void addStudent(String id, String name) {
        students.put(id, new Student(id, name));
    }

    public Student getStudent(String id) {
        return students.get(id);
    }

    public Collection<Student> listStudents() {
        return students.values();
    }

    // Submission handling
    public boolean submit(String assignmentId, String studentId, String content) {
        Assignment a = assignments.get(assignmentId);
        Student s = students.get(studentId);
        if (a == null || s == null) return false;

        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(a.getDeadline())) {
            return false; // deadline passed
        }
        submissions.add(new Submission(assignmentId, studentId, now, content));
        return true;
    }

    public List<Submission> listSubmissions() {
        return Collections.unmodifiableList(submissions);
    }

    public List<Submission> getSubmissionsByStudent(String studentId) {
        List<Submission> result = new ArrayList<>();
        for (Submission sub : submissions) {
            if (sub.getStudentId().equals(studentId)) {
                result.add(sub);
            }
        }
        return result;
    }

    public List<Submission> getSubmissionsByAssignment(String assignmentId) {
        List<Submission> result = new ArrayList<>();
        for (Submission sub : submissions) {
            if (sub.getAssignmentId().equals(assignmentId)) {
                result.add(sub);
            }
        }
        return result;
    }
}
```

```java
// File: src/main/java/com/example/assignment/Main.java
package com.example.assignment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private static final AssignmentService service = new AssignmentService();
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        seedData();
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": listAssignments(); break;
                case "2": listStudents(); break;
                case "3": createAssignment(); break;
                case "4": createStudent(); break;
                case "5": submitAssignment(); break;
                case "6": listSubmissions(); break;
                case "0": System.out.println("Exiting."); return;
                default: System.out.println("Invalid option."); break;
            }
        }
    }

    private static void seedData() {
        service.addStudent("s1", "Alice");
        service.addStudent("s2", "Bob");
        service.addAssignment("a1", "Math Homework", LocalDateTime.now().plusDays(2));
        service.addAssignment("a2", "History Essay", LocalDateTime.now().plusHours(5));
    }

    private static void printMenu() {
        System.out.println("\n=== Assignment Submission System ===");
        System.out.println("1. List Assignments");
        System.out.println("2. List Students");
        System.out.println("3. Create Assignment");
        System.out.println("4. Create Student");
        System.out.println("5. Submit Assignment");
        System.out.println("6. List All Submissions");
        System.out.println("0. Exit");
        System.out.print("Select option: ");
    }

    private static void listAssignments() {
        System.out.println("\n--- Assignments ---");
        for (Assignment a : service.listAssignments()) {
            System.out.printf("ID: %s | Title: %s | Deadline: %s%n",
                    a.getId(), a.getTitle(), a.getDeadline().format(dtf));
        }
    }

    private static void listStudents() {
        System.out.println("\n--- Students ---");
        for (Student s : service.listStudents()) {
            System.out.printf("ID: %s | Name: %s%n", s.getId(), s.getName());
        }
    }

    private static void createAssignment() {
        System.out.print("Enter Assignment ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter Deadline (yyyy-MM-dd HH:mm): ");
        String deadlineStr = scanner.nextLine().trim();
        try {
            LocalDateTime deadline = LocalDateTime.parse(deadlineStr, dtf);
            service.addAssignment(id, title, deadline);
            System.out.println("Assignment added.");
        } catch (Exception e) {
            System.out.println("Invalid deadline format.");
        }
    }

    private static void createStudent() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        service.addStudent(id, name);
        System.out.println("Student added.");
    }

    private static void submitAssignment() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();
        System.out.print("Enter Assignment ID: ");
        String assignmentId = scanner.nextLine().trim();
        System.out.print("Enter Submission Content: ");
        String content = scanner.nextLine().trim();

        boolean success = service.submit(assignmentId, studentId, content);
        if (success) {
            System.out.println("Submission successful.");
        } else {
            System.out.println("Submission failed (invalid IDs or deadline passed).");
        }
    }

    private static void listSubmissions() {
        System.out.println("\n--- Submissions ---");
        for (Submission sub : service.listSubmissions()) {
            System.out.printf("Assignment: %s | Student: %s | Time: %s | Content: %s%n",
                    sub.getAssignmentId(),
                    sub.get