```java
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
```

```java
public class Task {
    private String id;
    private String name;
    private String description;
    private String dueDate;
    private String status;

    public Task(String id, String name, String description, String dueDate, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }
}
```

```java
public class Note {
    private String id;
    private String name;
    private String content;
    private String date;

    public Note(String id, String name, String content, String date) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class ContactManager {
    private List<Contact> contacts;

    public ContactManager() {
        this.contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void removeContact(String id) {
        contacts.removeIf(contact -> contact.getId().equals(id));
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void saveContacts() {
        // Implementation to save contacts to a database or file
    }
}
```