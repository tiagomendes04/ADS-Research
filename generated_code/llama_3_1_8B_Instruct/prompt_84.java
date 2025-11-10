```java
// Contact.java
public class Contact {
    private String id;
    private String name;
    private String email;
    private String phone;

    public Contact(String id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getId() {
        return id;
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
}

// Task.java
import java.time.LocalDate;

public class Task {
    private String id;
    private String contactId;
    private String description;
    private LocalDate dueDate;
    private boolean isCompleted;

    public Task(String id, String contactId, String description, LocalDate dueDate) {
        this.id = id;
        this.contactId = contactId;
        this.description = description;
        this.dueDate = dueDate;
        this.isCompleted = false;
    }

    public String getId() {
        return id;
    }

    public String getContactId() {
        return contactId;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}

// Note.java
import java.time.LocalDate;

public class Note {
    private String id;
    private String contactId;
    private String description;
    private LocalDate dateAdded;

    public Note(String id, String contactId, String description) {
        this.id = id;
        this.contactId = contactId;
        this.description = description;
        this.dateAdded = LocalDate.now();
    }

    public String getId() {
        return id;
    }

    public String getContactId() {
        return contactId;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }
}

// CRMSystem.java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CRMSystem {
    private Map<String, Contact> contacts;
    private Map<String, Task> tasks;
    private Map<String, Note> notes;

    public CRMSystem() {
        this.contacts = new HashMap<>();
        this.tasks = new HashMap<>();
        this.notes = new HashMap<>();
    }

    public void addContact(Contact contact) {
        contacts.put(contact.getId(), contact);
    }

    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void addNote(Note note) {
        notes.put(note.getId(), note);
    }

    public Contact getContact(String id) {
        return contacts.get(id);
    }

    public Task getTask(String id) {
        return tasks.get(id);
    }

    public Note getNote(String id) {
        return notes.get(id);
    }

    public List<Contact> getContacts() {
        return new ArrayList<>(contacts.values());
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public List<Note> getNotes() {
        return new ArrayList<>(notes.values());
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        CRMSystem crmSystem = new CRMSystem();

        Contact contact1 = new Contact("1", "John Doe", "johndoe@example.com", "123-456-7890");
        Contact contact2 = new Contact("2", "Jane Doe", "janedoe@example.com", "987-654-3210");

        crmSystem.addContact(contact1);
        crmSystem.addContact(contact2);

        Task task1 = new Task("1", "1", "Task 1", LocalDate.of(2024, 7, 26));
        Task task2 = new Task("2", "1", "Task 2", LocalDate.of(2024, 7, 27));

        crmSystem.addTask(task1);
        crmSystem.addTask(task2);

        Note note1 = new Note("1", "1", "Note 1");
        Note note2 = new Note("2", "2", "Note 2");

        crmSystem.addNote(note1);
        crmSystem.addNote(note2);

        System.out.println("Contacts:");
        for (Contact contact : crmSystem.getContacts()) {
            System.out.println(contact.getName());
        }

        System.out.println("\nTasks:");
        for (Task task : crmSystem.getTasks()) {
            System.out.println(task.getDescription() + " (Due: " + task.getDueDate() + ")");
        }

        System.out.println("\nNotes:");
        for (Note note : crmSystem.getNotes()) {
            System.out.println(note.getDescription() + " (Added: " + note.getDateAdded() + ")");
        }
    }
}
```