```java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Task {
    int id;
    String title;
    String description;
    String deadline;
    String priority;
    List<Task> subtasks = new ArrayList<>();

    public Task(int id, String title, String description, String deadline, String priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
    }
}

class TaskManager {
    private List<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void updateTask(Task task) {
        for (Task existingTask : tasks) {
            if (existingTask.id == task.id) {
                existingTask.title = task.title;
                existingTask.description = task.description;
                existingTask.deadline = task.deadline;
                existingTask.priority = task.priority;
                return;
            }
        }
    }

    public void deleteTask(int id) {
        tasks.removeIf(task -> task.id == id);
    }

    public void printTasks() {
        for (Task task : tasks) {
            System.out.println(task.id + " - " + task.title + " - " + task.deadline + " - " + task.priority + " - " + task.description);
            for (Task subtask : task.subtasks) {
                System.out.println("  - " + subtask.id + " - " + subtask.title + " - " + subtask.deadline + " - " + subtask.priority + " - " + subtask.description);
            }
        }
    }

    public void printSubtasks(int taskId) {
        Task task = getTaskById(taskId);
        if (task != null) {
            System.out.println(task.title + " - " + task.deadline + " - " + task.priority + " - " + task.description);
            for (Task subtask : task.subtasks) {
                System.out.println("  - " + subtask.id + " - " + subtask.title + " - " + subtask.deadline + " - " + subtask.priority + " - " + subtask.description);
            }
        } else {
            System.out.println("Task not found.");
        }
    }

    private Task getTaskById(int id) {
        for (Task task : tasks) {
            if (task.id == id) {
                return task;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        taskManager.addTask(new Task(1, "Task 1", "Description 1", "2024-01-01", "High"));
        taskManager.addTask(new Task(2, "Task 2", "Description 2", "2024-01-15", "Low"));
        taskManager.addTask(new Task(3, "Task 3", "Description 3", "2024-01-20", "Medium"));

        taskManager.printTasks();
        System.out.println();

        taskManager.printSubtasks(1);

        taskManager.updateTask(new Task(1, "Updated Task 1", "Updated Description 1", "2024-01-01", "High"));
        taskManager.printSubtasks(1);
    }
}
```