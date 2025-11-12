```java
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class LibraryOverdueNotificationSystem {
    private List<Book> books;
    private Properties emailProperties;
    private Session emailSession;

    public LibraryOverdueNotificationSystem() {
        books = new ArrayList<>();
        setupEmail();
    }

    private void setupEmail() {
        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.host", "smtp.example.com");
        emailProperties.put("mail.smtp.port", "587");
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("username@example.com", "password");
            }
        };

        emailSession = Session.getInstance(emailProperties, auth);
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void sendNotifications() {
        Date currentDate = new Date();
        for (Book book : books) {
            if (book.isOverdue(currentDate)) {
                sendEmail(book.getBorrowerEmail(), "Overdue Notice: " + book.getTitle(), "Your book " + book.getTitle() + " is overdue. Please return it as soon as possible.");
            }
        }
    }

    private void sendEmail(String toEmail, String subject, String body) {
        try {
            MimeMessage message = new MimeMessage(emailSession);
            message.setFrom(new InternetAddress("library@example.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully to " + toEmail);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LibraryOverdueNotificationSystem system = new LibraryOverdueNotificationSystem();
        system.addBook(new Book("1984", "George Orwell", "john.doe@example.com", new GregorianCalendar(2023, Calendar.OCTOBER, 1).getTime()));
        system.addBook(new Book("To Kill a Mockingbird", "Harper Lee", "jane.smith@example.com", new GregorianCalendar(2023, Calendar.SEPTEMBER, 15).getTime()));
        system.sendNotifications();
    }
}

class Book {
    private String title;
    private String author;
    private String borrowerEmail;
    private Date dueDate;

    public Book(String title, String author, String borrowerEmail, Date dueDate) {
        this.title = title;
        this.author = author;
        this.borrowerEmail = borrowerEmail;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public String getBorrowerEmail() {
        return borrowerEmail;
    }

    public boolean isOverdue(Date currentDate) {
        return currentDate.after(dueDate);
    }
}
```