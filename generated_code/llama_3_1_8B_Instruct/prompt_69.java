```java
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Task {
    private String name;
    private LocalDate deadline;
    private int priority;
    private ArrayList<Subtask> subtasks;

    public Task(String name, LocalDate deadline, int priority) {
        this.name = name;
        this.deadline = deadline;
        this.priority = priority;
        this.subtasks = new ArrayList<>();
    }

    public void addSubtask(Subtask subtask) {
        this.subtasks.add(subtask);
    }

    public void removeSubtask(Subtask subtask) {
        this.subtasks.remove(subtask);
    }

    public String getName() {
        return this.name;
    }

    public LocalDate getDeadline() {
        return this.deadline;
    }

    public int getPriority() {
        return this.priority;
    }

    public ArrayList<Subtask> getSubtasks() {
        return this.subtasks;
    }
}

class Subtask {
    private String name;
    private boolean completed;

    public Subtask(String name) {
        this.name = name;
        this.completed = false;
    }

    public void complete() {
        this.completed = true;
    }

    public void incomplete() {
        this.completed = false;
    }

    public String getName() {
        return this.name;
    }

    public boolean isCompleted() {
        return this.completed;
    }
}

class TaskManager {
    private ArrayList<Task> tasks;

    public TaskManager() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public void printTasks() {
        for (Task task : this.tasks) {
            System.out.println("Task: " + task.getName());
            System.out.println("Deadline: " + task.getDeadline());
            System.out.println("Priority: " + task.getPriority());
            System.out.println("Subtasks:");
            for (Subtask subtask : task.getSubtasks()) {
                System.out.println("- " + subtask.getName() + " (" + subtask.isCompleted() + ")");
            }
            System.out.println();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Task 1", LocalDate.of(2024, 7, 30), 1);
        Task task2 = new Task("Task 2", LocalDate.of(2024, 8, 1), 2);

        Subtask subtask1 = new Subtask("Subtask 1");
        Subtask subtask2 = new Subtask("Subtask 2");
        Subtask subtask3 = new Subtask("Subtask 3");

        task1.addSubtask(subtask1);
        task1.addSubtask(subtask2);
        task2.addSubtask(subtask3);

        subtask1.complete();
        subtask3.complete();

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        taskManager.printTasks();
    }
}
```