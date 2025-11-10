```java
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public interface Notification {
    void send(String recipient, String subject, String message) throws Exception;
}

public class EmailNotification implements Notification {
    private final String smtpHost = "smtp.example.com";
    private final String smtpPort = "587";
    private final String username = "your_email@example.com";
    private final String password = "your_password";

    @Override
    public void send(String recipient, String subject, String message) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        Message mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(username));
        mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        mimeMessage.setSubject(subject);
        mimeMessage.setText(message);

        Transport.send(mimeMessage);
    }
}

public class SmsNotification implements Notification {
    private final String accountSid = "your_twilio_account_sid";
    private final String authToken = "your_twilio_auth_token";
    private final String fromNumber = "+1234567890";

    @Override
    public void send(String recipient, String subject, String message) throws Exception {
        // Placeholder for Twilio SDK usage
        // Twilio.init(accountSid, authToken);
        // Message.creator(new PhoneNumber(recipient), new PhoneNumber(fromNumber), message).create();
        System.out.println("SMS sent to " + recipient + ": " + message);
    }
}

public class PushNotification implements Notification {
    private final String firebaseServerKey = "your_firebase_server_key";

    @Override
    public void send(String recipient, String subject, String message) throws Exception {
        // Placeholder for Firebase Cloud Messaging HTTP request
        // Use HttpURLConnection or a library like OkHttp to POST JSON payload
        System.out.println("Push notification sent to " + recipient + ": " + message);
    }
}

public class NotificationFactory {
    public static Notification getNotificationChannel(String channel) {
        switch (channel.toLowerCase()) {
            case "email":
                return new EmailNotification();
            case "sms":
                return new SmsNotification();
            case "push":
                return new PushNotification();
            default:
                throw new IllegalArgumentException("Unsupported channel: " + channel);
        }
    }
}

public class NotificationService {
    public void notifyUsers(List<User> users, String channel, String subject, String message) {
        Notification notifier = NotificationFactory.getNotificationChannel(channel);
        for (User user : users) {
            try {
                String recipient = user.getContactInfo(channel);
                notifier.send(recipient, subject, message);
            } catch (Exception e) {
                System.err.println("Failed to send " + channel + " to " + user.getId() + ": " + e.getMessage());
            }
        }
    }
}

class User {
    private final String id;
    private final String email;
    private final String phoneNumber;
    private final String pushToken;

    public User(String id, String email, String phoneNumber, String pushToken) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.pushToken = pushToken;
    }

    public String getId() {
        return id;
    }

    public String getContactInfo(String channel) {
        switch (channel.toLowerCase()) {
            case "email":
                return email;
            case "sms":
                return phoneNumber;
            case "push":
                return pushToken;
            default:
                throw new IllegalArgumentException("Unsupported channel: " + channel);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        List<User> users = List.of(
                new User("1", "alice@example.com", "+1555000111", "alicePushToken"),
                new User("2", "bob@example.com", "+1555000222", "bobPushToken")
        );

        NotificationService service = new NotificationService();

        service.notifyUsers(users, "email", "Welcome", "Hello, this is an email notification.");
        service.notifyUsers(users, "sms", "", "Hello, this is an SMS notification.");
        service.notifyUsers(users, "push", "", "Hello, this is a push notification.");
    }
}
```