```java
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Task {
    private String title;
    private Date deadline;
    private int priority;
    private List<Task> subtasks;

    public Task(String title, Date deadline, int priority) {
        this.title = title;
        this.deadline = deadline;
        this.priority = priority;
        this.subtasks = new ArrayList<>();
    }

    public void addSubtask(Task subtask) {
        subtasks.add(subtask);
    }

    public void display() {
        System.out.println("Title: " + title);
        System.out.println("Deadline: " + deadline);
        System.out.println("Priority: " + priority);
        System.out.println("Subtasks:");
        for (Task subtask : subtasks) {
            subtask.display();
        }
    }
}

class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void displayAllTasks() {
        for (Task task : tasks) {
            task.display();
        }
    }

    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Task");
            System.out.println("2. Display All Tasks");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Deadline (YYYY-MM-DD): ");
                    String deadlineStr = scanner.nextLine();
                    Date deadline = parseDate(deadlineStr);
                    System.out.print("Priority (1-5): ");
                    int priority = scanner.nextInt();
                    scanner.nextLine();

                    Task task = new Task(title, deadline, priority);
                    System.out.println("Add subtasks? (y/n): ");
                    String subtaskChoice = scanner.nextLine();
                    if (subtaskChoice.equalsIgnoreCase("y")) {
                        while (true) {
                            System.out.print("Subtask Title: ");
                            String subtaskTitle = scanner.nextLine();
                            System.out.print("Subtask Deadline (YYYY-MM-DD): ");
                            String subtaskDeadlineStr = scanner.nextLine();
                            Date subtaskDeadline = parseDate(subtaskDeadlineStr);
                            System.out.print("Subtask Priority (1-5): ");
                            int subtaskPriority = scanner.nextInt();
                            scanner.nextLine();

                            Task subtask = new Task(subtaskTitle, subtaskDeadline, subtaskPriority);
                            task.addSubtask(subtask);
                            System.out.println("Add another subtask? (y/n): ");
                            String anotherSubtask = scanner.nextLine();
                            if (!anotherSubtask.equalsIgnoreCase("y")) {
                                break;
                            }
                        }
                    }

                    manager.addTask(task);
                    break;
                case 2:
                    manager.displayAllTasks();
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private static Date parseDate(String dateStr) {
        try {
            return new Date(dateStr.split("-")[0], dateStr.split("-")[1], dateStr.split("-")[2]);
        } catch (Exception e) {
            System.out.println("Invalid date format");
            return null;
        }
    }
}
```