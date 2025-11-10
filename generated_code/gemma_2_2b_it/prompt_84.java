```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SimpleCRM {

    private static HashMap<Integer, Contact> contacts = new HashMap<>();
    private static HashMap<Integer, Task> tasks = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    addTask();
                    break;
                case 3:
                    getContactById();
                    break;
                case 4:
                    getTaskById();
                    break;
                case 5:
                    addNoteToContact();
                    break;
                case 6:
                    updateContact();
                    break;
                case 7:
                    updateTask();
                    break;
                case 8:
                    deleteContact();
                    break;
                case 9:
                    deleteTask();
                    break;
                case 10:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 10);
    }

    private static void printMenu() {
        System.out.println("\nSimple CRM System");
        System.out.println("1. Add Contact");
        System.out.println("2. Add Task");
        System.out.println("3. Get Contact by ID");
        System.out.println("4. Get Task by ID");
        System.out.println("5. Add Note to Contact");
        System.out.println("6. Update Contact");
        System.out.println("7. Update Task");
        System.out.println("8. Delete Contact");
        System.out.println("9. Delete Task");
        System.out.println("10. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addContact() {
        System.out.println("Enter Contact details:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        contacts.put(contacts.size() + 1, new Contact(name, email));
        System.out.println("Contact added successfully!");
    }

    private static void addTask() {
        System.out.println("Enter Task details:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Due Date: ");
        String dueDate = scanner.nextLine();
        tasks.put(tasks.size() + 1, new Task(name, description, dueDate));
        System.out.println("Task added successfully!");
    }

    private static void getContactById() {
        System.out.print("Enter Contact ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Contact contact = contacts.get(id);
        if (contact != null) {
            System.out.println("Name: " + contact.getName());
            System.out.println("Email: " + contact.getEmail());
        } else {
            System.out.println("Contact not found.");
        }
    }

    private static void getTaskById() {
        System.out.print("Enter Task ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Task task = tasks.get(id);
        if (task != null) {
            System.out.println("Name: " + task.getName());
            System.out.println("Description: " + task.getDescription());
            System.out.println("Due Date: " + task.getDueDate());
        } else {
            System.out.println("Task not found.");
        }
    }

    private static void addNoteToContact() {
        System.out.print("Enter Contact ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Contact contact = contacts.get(id);
        if (contact != null) {
            System.out.print("Enter Note: ");
            String note = scanner.nextLine();
            contact.addNote(note);
            System.out.println("Note added successfully!");
        } else {
            System.out.println("Contact not found.");
        }
    }

    private static void updateContact() {
        System.out.print("Enter Contact ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Contact contact = contacts.get(id);
        if (contact != null) {
            System.out.print("Enter new name (leave blank to keep current): ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                contact.setName(name);
            }
            System.out.print("Enter new email (leave blank to keep current): ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) {
                contact.setEmail(email);
            }
            System.out.println("Contact updated successfully!");
        } else {
            System.out.println("Contact not found.");
        }
    }

    private static void updateTask() {
        System.out.print("Enter Task ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Task task = tasks.get(id);
        if (task != null) {
            System.out.print("Enter new name (leave blank to keep current): ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                task.setName(name);
            }
            System.out.print("Enter new description (leave blank to keep current): ");
            String description = scanner.nextLine();
            if (!description.isEmpty()) {
                task.setDescription(description);
            }
            System.out.print("Enter new Due Date (leave blank to keep current): ");
            String dueDate = scanner.nextLine();
            if (!dueDate.isEmpty()) {
                task.setDueDate(dueDate);
            }
            System.out.println("Task updated successfully!");
        } else {
            System.out.println("Task not found.");
        }
    }

    private static void deleteContact() {
        System.out.print("Enter Contact ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Contact contact = contacts.get(id);
        if (contact != null) {
            contacts.remove(id);
            System.out.println("Contact deleted successfully!");
        } else {
            System.out.println("Contact not found.");
        }
    }

    private static void deleteTask() {
        System.out.print("Enter Task ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Task task = tasks.get(id);
        if (task != null) {
            tasks.remove(id);
            System.out.println("Task deleted successfully!");
        } else {
            System.out.println("Task not found.");
        }
    }
}

class Contact {
    private String name;
    private String email;
    private ArrayList<String> notes = new ArrayList<>();

    public Contact(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addNote(String note) {
        notes.add(note);
    }

    public ArrayList<String> getNotes() {
        return notes;
    }
}

class Task {
    private String name;
    private String description;
    private String dueDate;

    public Task(String name, String description, String dueDate) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String