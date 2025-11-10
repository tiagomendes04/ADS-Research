```java
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Random;

public class NotificationSystem {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String SMTP_AUTH = "true";
    private static final String SMTP_STARTTLS = "true";
    private static final String FROM_EMAIL = "your-email@gmail.com";
    private static final String PASSWORD = "your-password";

    public static void sendEmail(String toEmail, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", SMTP_AUTH);
        props.put("mail.smtp.starttls.enable", SMTP_STARTTLS);

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }

    public static void sendSMS(String phoneNumber, String message) {
        // Implement SMS sending functionality
        // For example, using Twilio API
        // This is a simplified example and may not work as-is
        System.out.println("Sending SMS to " + phoneNumber + ": " + message);
    }

    public static void sendPushNotification(String userId, String message) {
        // Implement push notification sending functionality
        // For example, using Firebase Cloud Messaging (FCM)
        // This is a simplified example and may not work as-is
        System.out.println("Sending push notification to user " + userId + ": " + message);
    }

    public static void main(String[] args) {
        // Example usage
        sendEmail("recipient@example.com", "Test Email", "Hello, this is a test email!");
        sendSMS("+1234567890", "Hello, this is a test SMS!");
        sendPushNotification("user123", "Hello, this is a test push notification!");
    }
}
```