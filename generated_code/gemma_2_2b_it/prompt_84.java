```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SimpleCRM {

    static Map<String, Contact> contacts = new HashMap<>();
    static List<Task> tasks = new ArrayList<>();
    static Map<String, String> notes = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("CRM Menu:");
            System.out.println("1. Add Contact");
            System.out.println("2. Add Task");
            System.out.println("3. View Contacts");
            System.out.println("4. View Tasks");
            System.out.println("5. View Notes");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addContact(scanner);
                    break;
                case 2:
                    addTask(scanner);
                    break;
                case 3:
                    viewContacts();
                    break;
                case 4:
                    viewTasks();
                    break;
                case 5:
                    viewNotes();
                    break;
                case 6:
                    System.out.println("Exiting CRM...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void addContact(Scanner scanner) {
        System.out.println("Enter contact details:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine();

        Contact contact = new Contact(name, email, phoneNumber);
        contacts.put(name, contact);
        System.out.println("Contact added successfully.");
    }

    static void addTask(Scanner scanner) {
        System.out.println("Enter task details:");
        System.out.print("Task Name: ");
        String taskName = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Deadline: ");
        String deadline = scanner.nextLine();

        Task task = new Task(taskName, description, deadline);
        tasks.add(task);
        System.out.println("Task added successfully.");
    }

    static void viewContacts() {
        System.out.println("Contacts:");
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            for (Contact contact : contacts.values()) {
                System.out.println("Name: " + contact.getName());
                System.out.println("Email: " + contact.getEmail());
                System.out.println("Phone Number: " + contact.getPhoneNumber());
            }
        }
    }

    static void viewTasks() {
        System.out.println("Tasks:");
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            for (Task task : tasks) {
                System.out.println("Name: " + task.getTaskName());
                System.out.println("Description: " + task.getDescription());
                System.out.println("Deadline: " + task.getDeadline());
            }
        }
    }

    static void viewNotes() {
        System.out.println("Notes:");
        if (notes.isEmpty()) {
            System.out.println("No notes found.");
        } else {
            for (String key : notes.keySet()) {
                System.out.println("Key: " + key);
                System.out.println("Value: " + notes.get(key));
            }
        }
    }
}

class Contact {
    private String name;
    private String email;
    private String phoneNumber;

    public Contact(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}

class Task {
    private String taskName;
    private String description;
    private String deadline;

    public Task(String taskName, String description, String deadline) {
        this.taskName = taskName;
        this.description = description;
        this.deadline = deadline;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public String getDeadline() {
        return deadline;
    }
}
```