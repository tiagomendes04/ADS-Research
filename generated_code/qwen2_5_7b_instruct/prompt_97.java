```java
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class Assignment {
    private String title;
    private Date deadline;

    public Assignment(String title, Date deadline) {
        this.title = title;
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public Date getDeadline() {
        return deadline;
    }
}

class Student {
    private String name;
    private Map<String, Assignment> assignments;

    public Student(String name) {
        this.name = name;
        this.assignments = new HashMap<>();
    }

    public void submitAssignment(Assignment assignment) {
        assignments.put(assignment.getTitle(), assignment);
    }

    public void displayAssignments() {
        for (String title : assignments.keySet()) {
            System.out.println("Assignment: " + title + ", Deadline: " + assignments.get(title).getDeadline());
        }
    }
}

public class AssignmentSubmissionSystem {
    public static void main(String[] args) {
        Student student = new Student("Alice");

        Assignment assignment1 = new Assignment("Math Homework", new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 3));
        Assignment assignment2 = new Assignment("Science Project", new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 5));

        student.submitAssignment(assignment1);
        student.submitAssignment(assignment2);

        student.displayAssignments();
    }
}
```