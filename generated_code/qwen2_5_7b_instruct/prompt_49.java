```java
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailNewsletterSystem {

    private static final String SMTP_HOST = "smtp.example.com";
    private static final String SMTP_PORT = "587";
    private static final String EMAIL = "newsletter@example.com";
    private static final String PASSWORD = "yourpassword";
    private static final String FROM_EMAIL = "Newsletter <newsletter@example.com>";
    private static final String SUBJECT = "Your Newsletter";
    private static final String MESSAGE = "Here is your newsletter content.";

    private static final List<Subscription> subscriptions = new ArrayList<>();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    static {
        scheduler.scheduleAtFixedRate(() -> sendNewsletter(), 0, 1, TimeUnit.DAYS);
    }

    public static void main(String[] args) {
        // Example subscription
        subscriptions.add(new Subscription("user@example.com", new Date()));
    }

    public static void addSubscription(String email) {
        subscriptions.add(new Subscription(email, new Date()));
    }

    private static void sendNewsletter() {
        for (Subscription subscription : subscriptions) {
            sendEmail(subscription.getEmail());
        }
    }

    private static void sendEmail(String toEmail) {
        try {
            Session session = Session.getDefaultInstance(null, null);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(SUBJECT);
            message.setText(MESSAGE);

            Transport.send(message);
            System.out.println("Email sent to: " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    static class Subscription {
        private String email;
        private Date subscriptionDate;

        public Subscription(String email, Date subscriptionDate) {
            this.email = email;
            this.subscriptionDate = subscriptionDate;
        }

        public String getEmail() {
            return email;
        }
    }
}
```