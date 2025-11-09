**NotificationSystem.java**
```java
import java.util.ArrayList;
import java.util.List;

public class NotificationSystem {
    private List<Notification> notifications;

    public NotificationSystem() {
        this.notifications = new ArrayList<>();
    }

    public void sendEmail(String to, String subject, String body) {
        EmailNotification email = new EmailNotification(to, subject, body);
        notifications.add(email);
        email.send();
    }

    public void sendSMS(String to, String message) {
        SMSNotification sms = new SMSNotification(to, message);
        notifications.add(sms);
        sms.send();
    }

    public void sendPushAlert(String to, String message) {
        PushNotification push = new PushNotification(to, message);
        notifications.add(push);
        push.send();
    }

    public void sendAllNotifications() {
        for (Notification notification : notifications) {
            notification.send();
        }
    }
}
```

**EmailNotification.java**
```java
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailNotification implements Notification {
    private String to;
    private String subject;
    private String body;

    public EmailNotification(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    @Override
    public void send() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("your-email@gmail.com", "your-password");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("your-email@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            System.out.println("Email sent to " + to);
        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }
}
```

**SMSNotification.java**
```java
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

public class SMSNotification implements Notification {
    private String to;
    private String message;

    public SMSNotification(String to, String message) {
        this.to = to;
        this.message = message;
    }

    @Override
    public void send() {
        Account account = new Account("your-account_sid", "your-auth_token");
        MessageCreator creator = new MessageCreator(
                new PhoneNumber(to),
                new PhoneNumber("your-twilio-phone-number"),
                message
        );
        Message message = Message.creator(creator).create(account);
        System.out.println("SMS sent to " + to);
    }
}
```

**PushNotification.java**
```java
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;

public class PushNotification implements Notification {
    private String to;
    private String message;

    public PushNotification(String to, String message) {
        this.to = to;
        this.message = message;
    }

    @Override
    public void send() {
        FirebaseMessaging.getInstance().send(
                new FirebaseMessaging.Message.Builder()
                        .put("title", "Notification")
                        .put("body", message)
                        .put("to", to)
                        .build()
        );
        System.out.println("Push notification sent to " + to);
    }
}
```

**Notification.java**
```java
public interface Notification {
    void send();
}
```

**Main.java**
```java
public class Main {
    public static void main(String[] args) {
        NotificationSystem notificationSystem = new NotificationSystem();
        notificationSystem.sendEmail("recipient@example.com", "Test Email", "This is a test email.");
        notificationSystem.sendSMS("+1234567890", "This is a test SMS.");
        notificationSystem.sendPushAlert("recipient-device-id", "This is a test push notification.");
        notificationSystem.sendAllNotifications();
    }
}
```