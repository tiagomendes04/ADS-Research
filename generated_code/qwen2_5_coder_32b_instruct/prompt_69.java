```java
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Task {
    private String name;
    private Date deadline;
    private int priority;
    private List<Task> subTasks;

    public Task(String name, Date deadline, int priority) {
        this.name = name;
        this.deadline = deadline;
        this.priority = priority;
        this.subTasks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<Task> getSubTasks() {
        return subTasks;
    }

    public void addSubTask(Task subTask) {
        this.subTasks.add(subTask);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", deadline=" + deadline +
                ", priority=" + priority +
                ", subTasks=" + subTasks +
                '}';
    }
}

public class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        
        Task mainTask = new Task("Project X", new Date(2023, 12, 15), 1);
        Task subTask1 = new Task("Design Phase", new Date(2023, 11, 30), 2);
        Task subTask2 = new Task("Development Phase", new Date(2024, 1, 15), 1);
        
        mainTask.addSubTask(subTask1);
        mainTask.addSubTask(subTask2);
        
        manager.addTask(mainTask);
        
        System.out.println(manager.getTasks());
    }
}
```