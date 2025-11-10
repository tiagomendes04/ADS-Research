```java
import java.util.*;
import java.time.*;

class Assignment {
    private String title;
    private LocalDate deadline;

    public Assignment(String title, LocalDate deadline) {
        this.title = title;
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "title='" + title + '\'' +
                ", deadline=" + deadline +
                '}';
    }
}

class Student {
    private String name;
    private Map<Assignment, LocalDate> submissions;

    public Student(String name) {
        this.name = name;
        this.submissions = new HashMap<>();
    }

    public void submitAssignment(Assignment assignment, LocalDate submissionDate) {
        submissions.put(assignment, submissionDate);
    }

    public boolean isAssignmentSubmittedOnTime(Assignment assignment) {
        LocalDate submissionDate = submissions.get(assignment);
        if (submissionDate == null) return false;
        return !submissionDate.isAfter(assignment.getDeadline());
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", submissions=" + submissions +
                '}';
    }
}

class SubmissionSystem {
    private List<Assignment> assignments;
    private List<Student> students;

    public SubmissionSystem() {
        this.assignments = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void printAllAssignments() {
        assignments.forEach(System.out::println);
    }

    public void printAllStudents() {
        students.forEach(System.out::println);
    }

    public void checkSubmissionStatus(Student student, Assignment assignment) {
        if (student.isAssignmentSubmittedOnTime(assignment)) {
            System.out.println(student.getName() + " submitted '" + assignment.getTitle() + "' on time.");
        } else {
            System.out.println(student.getName() + " submitted '" + assignment.getTitle() + "' late.");
        }
    }

    public static void main(String[] args) {
        SubmissionSystem system = new SubmissionSystem();

        Assignment assignment1 = new Assignment("Math Homework", LocalDate.of(2023, 10, 15));
        Assignment assignment2 = new Assignment("Science Project", LocalDate.of(2023, 10, 20));

        system.addAssignment(assignment1);
        system.addAssignment(assignment2);

        Student student1 = new Student("Alice");
        Student student2 = new Student("Bob");

        student1.submitAssignment(assignment1, LocalDate.of(2023, 10, 14));
        student1.submitAssignment(assignment2, LocalDate.of(2023, 10, 21));

        student2.submitAssignment(assignment1, LocalDate.of(2023, 10, 15));
        student2.submitAssignment(assignment2, LocalDate.of(2023, 10, 19));

        system.addStudent(student1);
        system.addStudent(student2);

        system.printAllAssignments();
        System.out.println();
        system.printAllStudents();
        System.out.println();

        system.checkSubmissionStatus(student1, assignment1);
        system.checkSubmissionStatus(student1, assignment2);
        system.checkSubmissionStatus(student2, assignment1);
        system.checkSubmissionStatus(student2, assignment2);
    }
}
```