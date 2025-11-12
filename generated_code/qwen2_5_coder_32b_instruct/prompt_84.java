```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Contact {
    private String name;
    private String email;
    private String phone;

    public Contact(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Email: " + email + ", Phone: " + phone;
    }
}

class Task {
    private String description;
    private String dueDate;

    public Task(String description, String dueDate) {
        this.description = description;
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return "Task: " + description + ", Due Date: " + dueDate;
    }
}

class Note {
    private String content;

    public Note(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Note: " + content;
    }
}

class CRMSystem {
    private List<Contact> contacts;
    private List<Task> tasks;
    private List<Note> notes;
    private Scanner scanner;

    public CRMSystem() {
        contacts = new ArrayList<>();
        tasks = new ArrayList<>();
        notes = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void addContact() {
        System.out.print("Enter contact name: ");
        String name = scanner.nextLine();
        System.out.print("Enter contact email: ");
        String email = scanner.nextLine();
        System.out.print("Enter contact phone: ");
        String phone = scanner.nextLine();
        contacts.add(new Contact(name, email, phone));
        System.out.println("Contact added successfully.");
    }

    public void addTask() {
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();
        System.out.print("Enter task due date (MM/DD/YYYY): ");
        String dueDate = scanner.nextLine();
        tasks.add(new Task(description, dueDate));
        System.out.println("Task added successfully.");
    }

    public void addNote() {
        System.out.print("Enter note content: ");
        String content = scanner.nextLine();
        notes.add(new Note(content));
        System.out.println("Note added successfully.");
    }

    public void displayContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
        } else {
            System.out.println("Contacts:");
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        }
    }

    public void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            System.out.println("Tasks:");
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }

    public void displayNotes() {
        if (notes.isEmpty()) {
            System.out.println("No notes available.");
        } else {
            System.out.println("Notes:");
            for (Note note : notes) {
                System.out.println(note);
            }
        }
    }

    public void run() {
        while (true) {
            System.out.println("\nSimple CRM System");
            System.out.println("1. Add Contact");
            System.out.println("2. Add Task");
            System.out.println("3. Add Note");
            System.out.println("4. Display Contacts");
            System.out.println("5. Display Tasks");
            System.out.println("6. Display Notes");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    addTask();
                    break;
                case 3:
                    addNote();
                    break;
                case 4:
                    displayContacts();
                    break;
                case 5:
                    displayTasks();
                    break;
                case 6:
                    displayNotes();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        CRMSystem crmSystem = new CRMSystem();
        crmSystem.run();
    }
}
```