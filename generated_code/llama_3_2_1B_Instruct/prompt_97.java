```java
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class StudentAssignmentSystem {
    private Map<String, Assignment> assignments;
    private LocalTime deadline;
    private boolean isSubmitted;

    public StudentAssignmentSystem() {
        assignments = new HashMap<>();
        deadline = LocalTime.now();
        isSubmitted = false;
    }

    public void addAssignment(Assignment assignment) {
        assignments.put(assignment.getId(), assignment);
    }

    public void submitAssignment(String assignmentId) {
        if (isSubmitted) {
            System.out.println("Assignment already submitted.");
            return;
        }
        if (assignments.containsKey(assignmentId)) {
            Assignment assignment = assignments.get(assignmentId);
            if (assignment DeadlineExceeded()) {
                System.out.println("Assignment has exceeded the deadline.");
                return;
            }
            isSubmitted = true;
            System.out.println("Assignment submitted successfully.");
            System.out.println("Deadline: " + deadline.format(DateTimeFormatter.ofPattern("HH:mm")));
        } else {
            System.out.println("Assignment not found.");
        }
    }

    public void displayAssignments() {
        System.out.println("Assignments:");
        for (Assignment assignment : assignments.values()) {
            System.out.println(assignment);
        }
    }

    public void displaySubmissions() {
        System.out.println("Submissions:");
        for (Map.Entry<String, Assignment> entry : assignments.entrySet()) {
            if (entry.getValue().isSubmitted) {
                System.out.println(entry.getKey() + ": submitted");
            }
        }
    }

    public void displayDeadline() {
        System.out.println("Deadline: " + deadline.format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    public boolean DeadlineExceeded() {
        return LocalTime.now().isAfter(deadline);
    }
}

class Assignment {
    private int id;
    private String title;
    private String deadline;
    private boolean isSubmitted;

    public Assignment(int id, String title, String deadline) {
        this.id = id;
        this.title = title;
        this.deadline = DateTimeFormatter.ofPattern("HH:mm").format(LocalTime.parse(deadline, DateTimeFormatter.ofPattern("HH:mm")));
        isSubmitted = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDeadline() {
        return deadline;
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
    }

    public boolean DeadlineExceeded() {
        return LocalTime.now().isAfter(LocalTime.parse(deadline, DateTimeFormatter.ofPattern("HH:mm")));
    }
}
```
```java
public class Assignment {
    private int id;
    private String title;

    public Assignment(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
```