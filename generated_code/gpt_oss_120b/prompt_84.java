```java
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class CRMApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ContactService contactService = new ContactService();
        TaskService taskService = new TaskService();
        NoteService noteService = new NoteService();

        while (true) {
            System.out.println("\n--- Simple CRM ---");
            System.out.println("1. Manage Contacts");
            System.out.println("2. Manage Tasks");
            System.out.println("3. Manage Notes");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    manageContacts(scanner, contactService);
                    break;
                case "2":
                    manageTasks(scanner, taskService, contactService);
                    break;
                case "3":
                    manageNotes(scanner, noteService, contactService);
                    break;
                case "0":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void manageContacts(Scanner scanner, ContactService service) {
        while (true) {
            System.out.println("\n--- Contacts ---");
            System.out.println("1. List");
            System.out.println("2. Add");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            String c = scanner.nextLine();
            switch (c) {
                case "1":
                    service.list().forEach(System.out::println);
                    break;
                case "2":
                    System.out.print("Name: "); String name = scanner.nextLine();
                    System.out.print("Email: "); String email = scanner.nextLine();
                    System.out.print("Phone: "); String phone = scanner.nextLine();
                    service.add(new Contact(name, email, phone));
                    break;
                case "3":
                    System.out.print("ID to update: "); long uid = Long.parseLong(scanner.nextLine());
                    Contact existing = service.get(uid);
                    if (existing == null) { System.out.println("Not found."); break; }
                    System.out.print("New name (" + existing.getName() + "): "); String nName = scanner.nextLine();
                    System.out.print("New email (" + existing.getEmail() + "): "); String nEmail = scanner.nextLine();
                    System.out.print("New phone (" + existing.getPhone() + "): "); String nPhone = scanner.nextLine();
                    existing.setName(nName.isEmpty() ? existing.getName() : nName);
                    existing.setEmail(nEmail.isEmpty() ? existing.getEmail() : nEmail);
                    existing.setPhone(nPhone.isEmpty() ? existing.getPhone() : nPhone);
                    service.update(existing);
                    break;
                case "4":
                    System.out.print("ID to delete: "); long did = Long.parseLong(scanner.nextLine());
                    service.delete(did);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid.");
            }
        }
    }

    private static void manageTasks(Scanner scanner, TaskService taskService, ContactService contactService) {
        while (true) {
            System.out.println("\n--- Tasks ---");
            System.out.println("1. List");
            System.out.println("2. Add");
            System.out.println("3. Mark Completed");
            System.out.println("4. Delete");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            String c = scanner.nextLine();
            switch (c) {
                case "1":
                    taskService.list().forEach(System.out::println);
                    break;
                case "2":
                    System.out.print("Title: "); String title = scanner.nextLine();
                    System.out.print("Due date (YYYY-MM-DD): "); LocalDate due = LocalDate.parse(scanner.nextLine());
                    System.out.print("Contact ID (optional, blank for none): "); String cidStr = scanner.nextLine();
                    Long cid = cidStr.isBlank() ? null : Long.parseLong(cidStr);
                    taskService.add(new Task(title, due, cid));
                    break;
                case "3":
                    System.out.print("Task ID to complete: "); long tid = Long.parseLong(scanner.nextLine());
                    Task t = taskService.get(tid);
                    if (t != null) {
                        t.setCompleted(true);
                        taskService.update(t);
                    } else {
                        System.out.println("Not found.");
                    }
                    break;
                case "4":
                    System.out.print("Task ID to delete: "); long delId = Long.parseLong(scanner.nextLine());
                    taskService.delete(delId);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid.");
            }
        }
    }

    private static void manageNotes(Scanner scanner, NoteService noteService, ContactService contactService) {
        while (true) {
            System.out.println("\n--- Notes ---");
            System.out.println("1. List");
            System.out.println("2. Add");
            System.out.println("3. Delete");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            String c = scanner.nextLine();
            switch (c) {
                case "1":
                    noteService.list().forEach(System.out::println);
                    break;
                case "2":
                    System.out.print("Content: "); String content = scanner.nextLine();
                    System.out.print("Contact ID (optional, blank for none): "); String cidStr = scanner.nextLine();
                    Long cid = cidStr.isBlank() ? null : Long.parseLong(cidStr);
                    noteService.add(new Note(content, cid));
                    break;
                case "3":
                    System.out.print("Note ID to delete: "); long nid = Long.parseLong(scanner.nextLine());
                    noteService.delete(nid);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid.");
            }
        }
    }
}

/* ---------- Models ---------- */
class Contact {
    private static long COUNTER = 1;
    private final long id;
    private String name;
    private String email;
    private String phone;

    public Contact(String name, String email, String phone) {
        this.id = COUNTER++;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return String.format("Contact{id=%d, name='%s', email='%s', phone='%s'}", id, name, email, phone);
    }
}

class Task {
    private static long COUNTER = 1;
    private final long id;
    private String title;
    private LocalDate dueDate;
    private boolean completed;
    private Long contactId; // optional

    public Task(String title, LocalDate dueDate, Long contactId) {
        this.id = COUNTER++;
        this.title = title;
        this.dueDate = dueDate;
        this.completed = false;
        this.contactId = contactId;
    }

    public long getId() { return id; }
    public String getTitle() { return title; }
    public LocalDate getDueDate() { return dueDate; }
    public boolean isCompleted() { return completed; }
    public Long getContactId() { return contactId; }
    public void setTitle(String title) { this.title = title; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public void setContactId(Long contactId) { this.contactId = contactId; }

    @Override
    public String toString() {
        return String.format("Task{id=%d, title='%s', due=%s, completed=%s, contactId=%s}",
                id, title, dueDate, completed, contactId);
    }
}

class Note {
    private static long COUNTER = 1;
    private final long id;
    private String content;
    private LocalDateTime timestamp;
    private Long contactId; // optional

    public Note(String content, Long contactId) {
        this.id = COUNTER++;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.contactId = contactId;
    }

    public long getId() { return id; }
    public String getContent() { return content; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Long getContactId() { return contactId; }
    public void setContent(String content) { this.content = content; }
    public void setContactId(Long contactId) { this.contactId = contactId; }

    @Override
    public String toString() {
        return String.format("Note{id=%d, content='%s', time=%s, contactId=%s}",
                id, content, timestamp, contactId);
    }
}

/* ---------- Services ---------- */
class ContactService {
    private final Map<Long, Contact> contacts = new LinkedHashMap<>();

    public List<Contact> list() {
        return new ArrayList<>(contacts.values());
    }

    public void add(Contact c) {
        contacts.put(c.getId(), c);
    }

    public Contact get(long id) {
        return contacts.get(id);
    }

    public void update(Contact c) {
        contacts.put(c.getId(), c);
    }

    public void delete(long id) {
        contacts.remove(id);
    }
}

class TaskService {
    private final Map<Long, Task> tasks = new LinkedHashMap<>();

    public List<Task> list() {
        return new ArrayList<>(tasks.values());
    }

    public void add(Task t) {
        tasks.put(t.getId(), t);
    }

    public Task get(long id) {
        return tasks.get(id);
    }

    public void update(Task t) {
        tasks.put(t.getId(), t);
    }

    public void delete(long id) {
        tasks.remove(id);
    }
}

class NoteService {
    private final Map<Long, Note> notes = new LinkedHashMap<>();

    public List<Note> list() {
        return new ArrayList<>(notes.values());
    }

    public void add(Note n) {
        notes.put(n.getId(), n);
    }

    public Note get(long id) {
        return notes.get(id);
    }

    public void delete(long id) {
        notes.remove(id);
    }
}
```