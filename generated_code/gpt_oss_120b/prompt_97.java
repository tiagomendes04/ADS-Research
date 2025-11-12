```java
// File: src/main/java/com/example/assignment/model/Assignment.java
package com.example.assignment.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Assignment {
    private final String id;
    private final String title;
    private final String description;
    private final LocalDateTime dueDate;

    public Assignment(String id, String title, String description, LocalDateTime dueDate) {
        this.id = Objects.requireNonNull(id);
        this.title = Objects.requireNonNull(title);
        this.description = Objects.requireNonNull(description);
        this.dueDate = Objects.requireNonNull(dueDate);
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getDueDate() { return dueDate; }

    @Override
    public String toString() {
        return String.format("Assignment{id='%s', title='%s', due=%s}", id, title, dueDate);
    }
}
```

```java
// File: src/main/java/com/example/assignment/model/Student.java
package com.example.assignment.model;

import java.util.Objects;

public class Student {
    private final String id;
    private final String name;

    public Student(String id, String name) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
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
// File: src/main/java/com/example/assignment/model/Submission.java
package com.example.assignment.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Submission {
    private final Assignment assignment;
    private final Student student;
    private final LocalDateTime submitTime;
    private final String content;
    private final boolean late;

    public Submission(Assignment assignment, Student student, LocalDateTime submitTime, String content) {
        this.assignment = Objects.requireNonNull(assignment);
        this.student = Objects.requireNonNull(student);
        this.submitTime = Objects.requireNonNull(submitTime);
        this.content = Objects.requireNonNull(content);
        this.late = submitTime.isAfter(assignment.getDueDate());
    }

    public Assignment getAssignment() { return assignment; }
    public Student getStudent() { return student; }
    public LocalDateTime getSubmitTime() { return submitTime; }
    public String getContent() { return content; }
    public boolean isLate() { return late; }

    @Override
    public String toString() {
        return String.format("Submission{assignmentId='%s', studentId='%s', submitted=%s, late=%s}",
                assignment.getId(), student.getId(), submitTime, late);
    }
}
```

```java
// File: src/main/java/com/example/assignment/service/AssignmentService.java
package com.example.assignment.service;

import com.example.assignment.model.Assignment;
import com.example.assignment.model.Student;
import com.example.assignment.model.Submission;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AssignmentService {
    private final Map<String, Assignment> assignments = new ConcurrentHashMap<>();
    private final Map<String, Student> students = new ConcurrentHashMap<>();
    private final List<Submission> submissions = Collections.synchronizedList(new ArrayList<>());

    // Assignment management
    public void addAssignment(Assignment assignment) {
        assignments.put(assignment.getId(), assignment);
    }

    public Assignment getAssignment(String id) {
        return assignments.get(id);
    }

    public Collection<Assignment> listAssignments() {
        return assignments.values();
    }

    // Student management
    public void addStudent(Student student) {
        students.put(student.getId(), student);
    }

    public Student getStudent(String id) {
        return students.get(id);
    }

    public Collection<Student> listStudents() {
        return students.values();
    }

    // Submission handling
    public Submission submit(String assignmentId, String studentId, String content) {
        Assignment assignment = getAssignment(assignmentId);
        Student student = getStudent(studentId);
        if (assignment == null) throw new IllegalArgumentException("Assignment not found");
        if (student == null) throw new IllegalArgumentException("Student not found");
        Submission sub = new Submission(assignment, student, LocalDateTime.now(), content);
        submissions.add(sub);
        return sub;
    }

    public List<Submission> listSubmissions() {
        return new ArrayList<>(submissions);
    }

    public List<Submission> listSubmissionsByStudent(String studentId) {
        List<Submission> result = new ArrayList<>();
        for (Submission s : submissions) {
            if (s.getStudent().getId().equals(studentId)) {
                result.add(s);
            }
        }
        return result;
    }

    public List<Submission> listSubmissionsByAssignment(String assignmentId) {
        List<Submission> result = new ArrayList<>();
        for (Submission s : submissions) {
            if (s.getAssignment().getId().equals(assignmentId)) {
                result.add(s);
            }
        }
        return result;
    }
}
```

```java
// File: src/main/java/com/example/assignment/Main.java
package com.example.assignment;

import com.example.assignment.model.Assignment;
import com.example.assignment.model.Student;
import com.example.assignment.model.Submission;
import com.example.assignment.service.AssignmentService;

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
                case "1" -> listAssignments();
                case "2" -> createAssignment();
                case "3" -> listStudents();
                case "4" -> createStudent();
                case "5" -> submitAssignment();
                case "6" -> listSubmissions();
                case "0" -> {
                    System.out.println("Exiting.");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void seedData() {
        service.addStudent(new Student("s001", "Alice"));
        service.addStudent(new Student("s002", "Bob"));
        service.addAssignment(new Assignment("a001", "Essay", "Write an essay on AI.", LocalDateTime.now().plusDays(2)));
    }

    private static void printMenu() {
        System.out.println("\n--- Assignment Submission System ---");
        System.out.println("1. List Assignments");
        System.out.println("2. Create Assignment");
        System.out.println("3. List Students");
        System.out.println("4. Register Student");
        System.out.println("5. Submit Assignment");
        System.out.println("6. List Submissions");
        System.out.println("0. Exit");
        System.out.print("Select: ");
    }

    private static void listAssignments() {
        System.out.println("\nAssignments:");
        for (Assignment a : service.listAssignments()) {
            System.out.printf("ID: %s | Title: %s | Due: %s%n",
                    a.getId(), a.getTitle(), a.getDueDate().format(dtf));
        }
    }

    private static void createAssignment() {
        System.out.print("Assignment ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Description: ");
        String desc = scanner.nextLine().trim();
        System.out.print("Due date (yyyy-MM-dd HH:mm): ");
        String dueStr = scanner.nextLine().trim();
        LocalDateTime due = LocalDateTime.parse(dueStr, dtf);
        service.addAssignment(new Assignment(id, title, desc, due));
        System.out.println("Assignment added.");
    }

    private static void listStudents() {
        System.out.println("\nStudents:");
        for (Student s : service.listStudents()) {
            System.out.printf("ID: %s | Name: %s%n", s.getId(), s.getName());
        }
    }

    private static void createStudent() {
        System.out.print("Student ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        service.addStudent(new Student(id, name));
        System.out.println("Student registered.");
    }

    private static void submitAssignment() {
        System.out.print("Student ID: ");
        String sid = scanner.nextLine().trim();
        System.out.print("Assignment ID: ");
        String aid = scanner.nextLine().trim();
        System.out.print("Content (single line): ");
        String content = scanner.nextLine().trim();
        try {
            Submission sub = service.submit(aid, sid, content);
            System.out.printf("Submitted. Late: %s%n", sub.isLate() ? "YES" : "NO");
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void listSubmissions() {
        System.out.println("\nSubmissions:");
        for (Submission s : service.listSubmissions()) {
            System.out.printf("Student: %s | Assignment: %s | Time: %s | Late: %s%n",
                    s.getStudent().getId(),
                    s.getAssignment().getId(),
                    s.getSubmitTime().format(dtf),
                    s.isLate() ? "YES" : "NO");
        }
    }
}
```