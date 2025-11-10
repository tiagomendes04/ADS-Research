```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailNewsletter {

    private static Map<String, Subscription> subscriptions = new HashMap<>();
    private static Properties emailProps = new Properties();

    public static void main(String[] args) {
        emailProps.put("mail.smtp.host", "smtp.example.com");
        emailProps.put("mail.smtp.port", "587");
        emailProps.put("mail.smtp.auth", "true");
        emailProps.put("mail.smtp.starttls.enable", "true");

        // Add subscribers
        addSubscriber("john.doe@example.com", "John Doe");
        addSubscriber("jane.doe@example.com", "Jane Doe");

        // Schedule and send newsletter
        scheduleAndSendNewsletter("John Doe", "weekly", "example.html");
        scheduleAndSendNewsletter("Jane Doe", "weekly", "example.html");
    }

    public static void addSubscriber(String email, String name) {
        subscriptions.put(email, new Subscription(name, email));
    }

    public static void scheduleAndSendNewsletter(String recipientEmail, String schedule, String content) {
        Subscription subscription = subscriptions.get(recipientEmail);
        if (subscription != null) {
            try {
                // Create and send email
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                Session session = Session.getInstance(emailProps, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("sender@example.com", "password");
                    }
                });
                Transport.send(new MimeMessage(session, createEmailMessage(subscription, content)));
                System.out.println("Email sent successfully to: " + recipientEmail);
            } catch (Exception e) {
                System.err.println("Email sending error: " + e.getMessage());
            }
        }
    }

    private static MimeMessage createEmailMessage(Subscription subscription, String content) throws MessagingException {
        MimeMessage msg = new MimeMessage(Session.getInstance(emailProps, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("sender@example.com", "password");
            }
        }));
        msg.setFrom(new InternetAddress("sender@example.com"));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(subscription.getEmail()));
        msg.setSubject("Newsletter");
        msg.setContent(content, "text/html");
        return msg;
    }

}

class Subscription {
    private String name;
    private String email;

    public Subscription(String name, String email) {
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