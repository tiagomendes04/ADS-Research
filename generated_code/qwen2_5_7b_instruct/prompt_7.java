```java
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Scanner;

public class NotificationSystem {

    public static void sendEmail(String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("your-email@gmail.com", "your-password");
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("your-email@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendSMS(String phoneNumber, String message) {
        // Placeholder for SMS sending logic
        System.out.println("SMS sent to: " + phoneNumber + " with message: " + message);
    }

    public static void sendPushAlert(String token, String message) {
        // Placeholder for push alert sending logic
        System.out.println("Push alert sent to: " + token + " with message: " + message);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose notification type (email/sms/push): ");
        String notificationType = scanner.nextLine();

        System.out.println("Enter user details (email/phone/token) and message: ");
        String userDetail = scanner.nextLine();
        String message = scanner.nextLine();

        switch (notificationType.toLowerCase()) {
            case "email":
                sendEmail(userDetail, "Test Notification", message);
                break;
            case "sms":
                sendSMS(userDetail, message);
                break;
            case "push":
                sendPushAlert(userDetail, message);
                break;
            default:
                System.out.println("Invalid notification type");
        }
    }
}
```