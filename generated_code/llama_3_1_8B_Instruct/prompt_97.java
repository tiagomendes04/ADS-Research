```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Assignment {
    private String name;
    private String description;
    private LocalDate deadline;
    private List<StudentAssignment> studentAssignments;

    public Assignment(String name, String description, LocalDate deadline) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.studentAssignments = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public List<StudentAssignment> getStudentAssignments() {
        return studentAssignments;
    }

    public void addStudentAssignment(StudentAssignment studentAssignment) {
        this.studentAssignments.add(studentAssignment);
    }
}

class StudentAssignment {
    private String studentName;
    private String assignmentName;
    private String submissionDate;

    public StudentAssignment(String studentName, String assignmentName, String submissionDate) {
        this.studentName = studentName;
        this.assignmentName = assignmentName;
        this.submissionDate = submissionDate;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }
}

public class StudentAssignmentSubmissionSystem {
    private List<Assignment> assignments;
    private Scanner scanner;

    public StudentAssignmentSubmissionSystem() {
        this.assignments = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void addAssignment() {
        System.out.println("Enter assignment name:");
        String name = scanner.nextLine();
        System.out.println("Enter assignment description:");
        String description = scanner.nextLine();
        System.out.println("Enter deadline (yyyy-MM-dd):");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate deadline = LocalDate.parse(scanner.nextLine(), formatter);
        Assignment assignment = new Assignment(name, description, deadline);
        assignments.add(assignment);
    }

    public void viewAssignments() {
        for (int i = 0; i < assignments.size(); i++) {
            System.out.println((i + 1) + ". " + assignments.get(i).getName());
        }
    }

    public void submitAssignment() {
        System.out.println("Enter assignment number:");
        int assignmentNumber = Integer.parseInt(scanner.nextLine()) - 1;
        if (assignmentNumber >= 0 && assignmentNumber < assignments.size()) {
            Assignment assignment = assignments.get(assignmentNumber);
            System.out.println("Enter student name:");
            String studentName = scanner.nextLine();
            System.out.println("Enter submission date (yyyy-MM-dd):");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate submissionDate = LocalDate.parse(scanner.nextLine(), formatter);
            StudentAssignment studentAssignment = new StudentAssignment(studentName, assignment.getName(), submissionDate.toString());
            assignment.addStudentAssignment(studentAssignment);
            System.out.println("Assignment submitted successfully!");
        } else {
            System.out.println("Invalid assignment number!");
        }
    }

    public void viewSubmissionStatus() {
        System.out.println("Enter assignment number:");
        int assignmentNumber = Integer.parseInt(scanner.nextLine()) - 1;
        if (assignmentNumber >= 0 && assignmentNumber < assignments.size()) {
            Assignment assignment = assignments.get(assignmentNumber);
            System.out.println("Submission Status for " + assignment.getName() + ":");
            for (StudentAssignment studentAssignment : assignment.getStudentAssignments()) {
                System.out.println("Student Name: " + studentAssignment.getStudentName() + ", Submission Date: " + studentAssignment.getSubmissionDate());
            }
        } else {
            System.out.println("Invalid assignment number!");
        }
    }

    public void run() {
        while (true) {
            System.out.println("1. Add Assignment");
            System.out.println("2. View Assignments");
            System.out.println("3. Submit Assignment");
            System.out.println("4. View Submission Status");
            System.out.println("5. Exit");
            System.out.println("Enter your choice:");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    addAssignment();
                    break;
                case 2:
                    viewAssignments();
                    break;
                case 3:
                    submitAssignment();
                    break;
                case 4:
                    viewSubmissionStatus();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    public static void main(String[] args) {
        StudentAssignmentSubmissionSystem system = new StudentAssignmentSubmissionSystem();
        system.run();
    }
}
```