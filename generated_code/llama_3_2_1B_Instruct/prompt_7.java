```java
import java.util.Properties;
import java.util.Scanner;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
    public static void main(String[] args) {
        String from = "sender@example.com";
        String to = "recipient@example.com";
        String subject = "Notification";
        String body = "This is a notification";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.example.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.user", "sender");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "password");
            }
        });

        try (MailSession mailSession = session) {
            try (Message message = new MimeMessage(mailSession)) {
                message.setFrom(InternetAddress.from(from));
                message.setRecipient(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);
                message.setText(body);

                System.out.println("Email sent successfully");
            }
        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }
}
```

```java
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SmsSender {
    public static void main(String[] args) {
        String phone = "+1234567890";
        String message = "Hello, this is a notification";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.example.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.user", "sender");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "password");
            }
        });

        try (MailSession mailSession = session) {
            try (Message message = new MimeMessage(mailSession)) {
                message.setFrom(InternetAddress.from(from));
                message.setRecipient(Message.RecipientType.TO, InternetAddress.parse(phone));
                message.setSubject("Notification");
                message.setText(message);
            }
        } catch (MessagingException e) {
            System.out.println("Error sending SMS: " + e.getMessage());
        }
    }
}
```

```java
import java.util.Properties;
import java.util.Scanner;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class PushSender {
    public static void main(String[] args) {
        String token = "YOUR_TOKEN";
        String deviceToken = "YOUR_DEVICE_TOKEN";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.example.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.user", "sender");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "password");
            }
        });

        try (MailSession mailSession = session) {
            try (Message message = new MimeMessage(mailSession)) {
                message.setFrom(InternetAddress.from(from));
                message.setRecipient(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject("Push Notification");
                message.setText("This is a push notification");

                // Add push notification logic here
                message.setBcc("bcc@example.com");
                message.setMimeHeader("Content-Type: text/plain; charset=UTF-8");
            }
        } catch (MessagingException e) {
            System.out.println("Error sending push alert: " + e.getMessage());
        }
    }
}
```