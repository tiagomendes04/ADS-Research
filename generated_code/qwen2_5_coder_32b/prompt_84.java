```java
import java.util.ArrayList;
import java.util.Date;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

class Task {
    private String title;
    private String description;
    private Date dueDate;
    private boolean completed;

    public Task(String title, String description, Date dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", completed=" + completed +
                '}';
    }
}

class Note {
    private String content;
    private Date createdAt;

    public Note(String content) {
        this.content = content;
        this.createdAt = new Date();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Note{" +
                "content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

class CRM {
    private List<Contact> contacts;
    private List<Task> tasks;
    private List<Note> notes;

    public CRM() {
        this.contacts = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.notes = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public void listContacts() {
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    public void listTasks() {
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public void listNotes() {
        for (Note note : notes) {
            System.out.println(note);
        }
    }
}

public class SimpleCRMApp {
    public static void main(String[] args) {
        CRM crm = new CRM();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("1. Add Contact");
            System.out.println("2. Add Task");
            System.out.println("3. Add Note");
            System.out.println("4. List Contacts");
            System.out.println("5. List Tasks");
            System.out.println("6. List Notes");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter contact name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter contact email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter contact phone: ");
                    String phone = scanner.nextLine();
                    crm.addContact(new Contact(name, email, phone));
                    break;
                case 2:
                    System.out.print("Enter task title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter task description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter task due date (yyyy-mm-dd): ");
                    String dueDateString = scanner.nextLine();
                    try {