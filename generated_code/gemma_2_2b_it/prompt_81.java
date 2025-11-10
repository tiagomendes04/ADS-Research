```java
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class LibraryOverdueNotifications {

    static class Book {
        String title;
        String borrowerName;
        String dueDate;

        Book(String title, String borrowerName, String dueDate) {
            this.title = title;
            this.borrowerName = borrowerName;
            this.dueDate = dueDate;
        }
    }

    static List<Book> overdueBooks = new ArrayList<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Properties props = System.getProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Setup email sending
        Session session = Session.getDefaultInstance(props);

        try {
            // Add sender email and receiver email addresses
            String senderEmail = "your_email@gmail.com";
            String receiverEmail = "recipient_email@gmail.com";

            // Send email
            sendOverdueNotifications(session, senderEmail, receiverEmail);

        } catch (MessagingException e) {
            System.err.println("Error sending emails: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void sendOverdueNotifications(Session session, String senderEmail, String receiverEmail) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));
        message.setSubject("Overdue Book Reminder");
        message.setText("Your book, " + overdueBooks.get(0).title + ", is overdue. Please return it by the due date: " + overdueBooks.get(0).dueDate + ".");

        Transport.send(message);
    }

}
```