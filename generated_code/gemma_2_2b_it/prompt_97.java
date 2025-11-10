```java
import java.util.ArrayList;
import java.util.Scanner;

public class AssignmentSubmissionSystem {

    static ArrayList<Assignment> assignments = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Submit Assignment");
            System.out.println("2. View Assignments");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    submitAssignment(scanner);
                    break;
                case 2:
                    viewAssignments();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void submitAssignment(Scanner scanner) {
        System.out.println("\nSubmit Assignment:");
        System.out.print("Enter assignment name: ");
        String name = scanner.nextLine();
        System.out.print("Enter due date (YYYY-MM-DD): ");
        String deadline = scanner.nextLine();

        Assignment assignment = new Assignment(name, deadline);
        assignments.add(assignment);
        System.out.println("Assignment submitted successfully.");
    }

    static void viewAssignments() {
        if (assignments.isEmpty()) {
            System.out.println("No assignments yet.");
            return;
        }

        System.out.println("\nAssignments:");
        for (Assignment assignment : assignments) {
            System.out.println(assignment);
        }
    }
}

class Assignment {
    private String name;
    private String deadline;

    public Assignment(String name, String deadline) {
        this.name = name;
        this.deadline = deadline;
    }

    public String getName() {
        return name;
    }

    public String getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        return "Assignment Name: " + name + ", Deadline: " + deadline;
    }
}
```