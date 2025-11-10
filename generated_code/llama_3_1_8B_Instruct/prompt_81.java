```java
package com.library.model;

public class Library {
    private String name;
    private String email;

    public Library(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
```

```java
package com.library.model;

import java.time.LocalDate;

public class Book {
    private String title;
    private LocalDate borrowingDate;
    private LocalDate dueDate;

    public Book(String title, LocalDate borrowingDate, LocalDate dueDate) {
        this.title = title;
        this.borrowingDate = borrowingDate;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getBorrowingDate() {
        return borrowingDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isOverdue() {
        return LocalDate.now().isAfter(dueDate);
    }
}
```

```java
package com.library.service;

import com.library.model.Book;
import com.library.model.Library;
import com.library.model.MailSender;

import java.util.ArrayList;
import java.util.List;

public class LibraryService {
    private List<Library> libraries;
    private List<Book> books;

    public LibraryService() {
        libraries = new ArrayList<>();
        books = new ArrayList<>();
    }

    public void addLibrary(Library library) {
        libraries.add(library);
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void sendOverdueNotifications() {
        for (Library library : libraries) {
            for (Book book : books) {
                if (book.isOverdue() && book.getBorrowingDate().equals(library.getEmail())) {
                    MailSender.sendMail(library.getName(), book.getTitle() + " is overdue.");
                }
            }
        }
    }
}
```

```java
package com.library.model;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
    public static void sendMail(String recipient, String message) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("your-email@gmail.com", "your-password");
                    }
                });

        try {
            MimeMessage message1 = new MimeMessage(session);
            message1.setFrom(new InternetAddress("your-email@gmail.com"));
            message1.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message1.setSubject("Library Overdue Notification");
            message1.setText(message);
            Transport.send(message1);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
```

```java
package com.library.main;

import com.library.model.Book;
import com.library.model.Library;
import com.library.service.LibraryService;

public class Main {
    public static void main(String[] args) {
        Library library = new Library("John Doe", "john.doe@example.com");
        Book book = new Book("Book Title", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31));

        LibraryService service = new LibraryService();
        service.addLibrary(library);
        service.addBook(book);

        service.sendOverdueNotifications();
    }
}
```