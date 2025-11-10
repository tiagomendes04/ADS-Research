```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class TaskManager {
    List<Task> tasks;

    TaskManager() {
        tasks = new ArrayList<>();
    }

    void addTask(String description, String deadline, String priority) {
        Task task = new Task(description, deadline, priority);
        tasks.add(task);
    }

    void viewAllTasks() {
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    void viewTasksByPriority(String priority) {
        List<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getPriority().equals(priority)) {
                matchingTasks.add(task);
            }
        }
        if (matchingTasks.isEmpty()) {
            System.out.println("No tasks found with priority: " + priority);
        } else {
            for (Task task : matchingTasks) {
                System.out.println(task);
            }
        }
    }

    void markTaskAsCompleted(String taskName) {
        for (Task task : tasks) {
            if (task.getName().equals(taskName)) {
                task.setCompleted(true);
                return;
            }
        }
        System.out.println("Task not found.");
    }
}

class Task {
    String name;
    String description;
    String deadline;
    String priority;
    boolean completed;

    public Task(String description, String deadline, String priority) {
        this.name = description;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.completed = false;
    }

    public String getName() {
        return name;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
```