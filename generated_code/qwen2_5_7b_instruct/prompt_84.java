```java
import java.util.*;

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
        return "Contact{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

class Task {
    private String description;
    private Date dueDate;

    public Task(String description, Date dueDate) {
        this.description = description;
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", dueDate=" + dueDate +
                '}';
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
        return "Note{" +
                "content='" + content + '\'' +
                '}';
    }
}

class CRMSystem {
    private List<Contact> contacts = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();
    private List<Note> notes = new ArrayList<>();

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public void displayContacts() {
        System.out.println("Contacts:");
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    public void displayTasks() {
        System.out.println("Tasks:");
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public void displayNotes() {
        System.out.println("Notes:");
        for (Note note : notes) {
            System.out.println(note);
        }
    }
}

public class SimpleCRMSys {
    public static void main(String[] args) {
        CRMSystem crm = new CRMSystem();

        crm.addContact(new Contact("John Doe", "john@example.com", "1234567890"));
        crm.addTask(new Task("Call John", new Date()));
        crm.addNote(new Note("John's next meeting is on Friday"));

        crm.displayContacts();
        crm.displayTasks();
        crm.displayNotes();
    }
}
```