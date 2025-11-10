```java
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

enum Priority {
    LOW, MEDIUM, HIGH
}

class SubTask {
    private final String title;
    private boolean completed = false;

    SubTask(String title) {
        this.title = title;
    }

    String getTitle() {
        return title;
    }

    boolean isCompleted() {
        return completed;
    }

    void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return (completed ? "[x] " : "[ ] ") + title;
    }
}

class Task {
    private final UUID id = UUID.randomUUID();
    private final String title;
    private final LocalDateTime deadline;
    private final Priority priority;
    private final List<SubTask> subTasks = new ArrayList<>();
    private boolean completed = false;

    Task(String title, LocalDateTime deadline, Priority priority) {
        this.title = title;
        this.deadline = deadline;
        this.priority = priority;
    }

    UUID getId() {
        return id;
    }

    String getTitle() {
        return title;
    }

    LocalDateTime getDeadline() {
        return deadline;
    }

    Priority getPriority() {
        return priority;
    }

    List<SubTask> getSubTasks() {
        return subTasks;
    }

    boolean isCompleted() {
        return completed;
    }

    void setCompleted(boolean completed) {
        this.completed = completed;
    }

    void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("%s %s (Due: %s) [Priority: %s]",
                completed ? "[x]" : "[ ]", title, deadline.format(fmt), priority);
    }
}

class TaskManager {
    private final Map<UUID, Task> tasks = new LinkedHashMap<>();

    void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    Optional<Task> getTask(UUID id) {
        return Optional.ofNullable(tasks.get(id));
    }

    List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    void removeTask(UUID id) {
        tasks.remove(id);
    }

    List<Task> getPendingTasks() {
        return tasks.values().stream()
                .filter(t -> !t.isCompleted())
                .toList();
    }

    List<Task> getOverdueTasks() {
        LocalDateTime now = LocalDateTime.now();
        return tasks.values().stream()
                .filter(t -> !t.isCompleted() && t.getDeadline().isBefore(now))
                .toList();
    }
}

public class TaskApp {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final TaskManager manager = new TaskManager();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            String choice = SCANNER.nextLine().trim();
            switch (choice) {
                case "1" -> createTask();
                case "2" -> listTasks();
                case "3" -> addSubTask();
                case "4" -> toggleTaskCompletion();
                case "5" -> toggleSubTaskCompletion();
                case "6" -> deleteTask();
                case "0" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Task Manager ---");
        System.out.println("1. Create Task");
        System.out.println("2. List Tasks");
        System.out.println("3. Add SubTask");
        System.out.println("4. Toggle Task Completion");
        System.out.println("5. Toggle SubTask Completion");
        System.out.println("6. Delete Task");
        System.out.println("0. Exit");
        System.out.print("Select: ");
    }

    private static void createTask() {
        System.out.print("Title: ");
        String title = SCANNER.nextLine();
        System.out.print("Deadline (yyyy-MM-dd HH:mm): ");
        String deadlineStr = SCANNER.nextLine();
        LocalDateTime deadline;
        try {
            deadline = LocalDateTime.parse(deadlineStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (Exception e) {
            System.out.println("Invalid date format.");
            return;
        }
        System.out.print("Priority (LOW/MEDIUM/HIGH): ");
        String prioStr = SCANNER.nextLine().toUpperCase();
        Priority priority;
        try {
            priority = Priority.valueOf(prioStr);
        } catch (Exception e) {
            System.out.println("Invalid priority.");
            return;
        }
        Task task = new Task(title, deadline, priority);
        manager.addTask(task);
        System.out.println("Task created with ID: " + task.getId());
    }

    private static void listTasks() {
        List<Task> tasks = manager.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks.");
            return;
        }
        for (Task t : tasks) {
            System.out.println("\nID: " + t.getId());
            System.out.println(t);
            if (!t.getSubTasks().isEmpty()) {
                System.out.println("  Subtasks:");
                for (int i = 0; i < t.getSubTasks().size(); i++) {
                    System.out.println("   " + (i + 1) + ". " + t.getSubTasks().get(i));
                }
            }
        }
    }

    private static void addSubTask() {
        Task task = selectTask();
        if (task == null) return;
        System.out.print("SubTask title: ");
        String title = SCANNER.nextLine();
        task.addSubTask(new SubTask(title));
        System.out.println("SubTask added.");
    }

    private static void toggleTaskCompletion() {
        Task task = selectTask();
        if (task == null) return;
        task.setCompleted(!task.isCompleted());
        System.out.println("Task marked as " + (task.isCompleted() ? "completed" : "incomplete") + ".");
    }

    private static void toggleSubTaskCompletion() {
        Task task = selectTask();
        if (task == null) return;
        List<SubTask> subs = task.getSubTasks();
        if (subs.isEmpty()) {
            System.out.println("No subtasks.");
            return;
        }
        for (int i = 0; i < subs.size(); i++) {
            System.out.println((i + 1) + ". " + subs.get(i));
        }
        System.out.print("Select subtask number: ");
        try {
            int idx = Integer.parseInt(SCANNER.nextLine()) - 1;
            if (idx < 0 || idx >= subs.size()) {
                System.out.println("Invalid number.");
                return;
            }
            SubTask sub = subs.get(idx);
            sub.setCompleted(!sub.isCompleted());
            System.out.println("SubTask marked as " + (sub.isCompleted() ? "completed" : "incomplete") + ".");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private static void deleteTask() {
        Task task = selectTask();
        if (task == null) return;
        manager.removeTask(task.getId());
        System.out.println("Task deleted.");
    }

    private static Task selectTask() {
        System.out.print("Enter Task ID: ");
        String idStr = SCANNER.nextLine();
        try {
            UUID id = UUID.fromString(idStr);
            return manager.getTask(id).orElse(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID.");
            return null;
        }
    }
}
```