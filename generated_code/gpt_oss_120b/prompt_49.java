```java
// Email.java
public class Email {
    private final String subject;
    private final String body;
    private final String from;

    public Email(String from, String subject, String body) {
        this.from = from;
        this.subject = subject;
        this.body = body;
    }

    public String getSubject() { return subject; }
    public String getBody() { return body; }
    public String getFrom() { return from; }
}
```

```java
// Subscriber.java
public class Subscriber {
    private final String email;
    private final String name;

    public Subscriber(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() { return email; }
    public String getName() { return name; }
}
```

```java
// SubscriptionManager.java
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SubscriptionManager {
    private final Set<Subscriber> subscribers = Collections.synchronizedSet(new HashSet<>());

    public boolean subscribe(Subscriber subscriber) {
        return subscribers.add(subscriber);
    }

    public boolean unsubscribe(String email) {
        return subscribers.removeIf(s -> s.getEmail().equalsIgnoreCase(email));
    }

    public Set<Subscriber> getAllSubscribers() {
        return Collections.unmodifiableSet(subscribers);
    }
}
```

```java
// EmailSender.java
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {
    private final Session session;
    private final String fromAddress;

    public EmailSender(String host, int port, final String username, final String password, String fromAddress) {
        this.fromAddress = fromAddress;
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", String.valueOf(port));

        session = Session.getInstance(props, new Authenticator() {
            @Override protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void send(Subscriber subscriber, Email email) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromAddress));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(subscriber.getEmail()));
        message.setSubject(email.getSubject());
        message.setText(email.getBody());
        Transport.send(message);
    }
}
```

```java
// NewsletterScheduler.java
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.*;

public class NewsletterScheduler {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final SubscriptionManager subscriptionManager;
    private final EmailSender emailSender;
    private final Email newsletterTemplate;

    public NewsletterScheduler(SubscriptionManager subscriptionManager,
                               EmailSender emailSender,
                               Email newsletterTemplate) {
        this.subscriptionManager = subscriptionManager;
        this.emailSender = emailSender;
        this.newsletterTemplate = newsletterTemplate;
    }

    public void scheduleAtFixedRate(Duration initialDelay, Duration period) {
        scheduler.scheduleAtFixedRate(this::dispatchNewsletter,
                initialDelay.toMillis(),
                period.toMillis(),
                TimeUnit.MILLISECONDS);
    }

    public void scheduleAt(LocalDateTime dateTime) {
        long delay = Duration.between(LocalDateTime.now(), dateTime).toMillis();
        if (delay < 0) delay = 0;
        scheduler.schedule(this::dispatchNewsletter, delay, TimeUnit.MILLISECONDS);
    }

    private void dispatchNewsletter() {
        for (Subscriber subscriber : subscriptionManager.getAllSubscribers()) {
            try {
                emailSender.send(subscriber, newsletterTemplate);
            } catch (Exception e) {
                System.err.println("Failed to send to " + subscriber.getEmail() + ": " + e.getMessage());
            }
        }
    }

    public void shutdown() {
        scheduler.shutdownNow();
    }
}
```

```java
// Main.java
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // Configuration (replace with real credentials)
        String smtpHost = "smtp.example.com";
        int smtpPort = 587;
        String smtpUser = "user@example.com";
        String smtpPass = "password";
        String fromAddress = "newsletter@example.com";

        SubscriptionManager subscriptionManager = new SubscriptionManager();

        // Sample subscriptions
        subscriptionManager.subscribe(new Subscriber("alice@example.com", "Alice"));
        subscriptionManager.subscribe(new Subscriber("bob@example.com", "Bob"));

        EmailSender emailSender = new EmailSender(smtpHost, smtpPort, smtpUser, smtpPass, fromAddress);

        Email newsletter = new Email(
                fromAddress,
                "Weekly Newsletter - " + LocalDateTime.now().toLocalDate(),
                "Hello,\n\nThis is our weekly newsletter.\n\nBest regards,\nTeam"
        );

        NewsletterScheduler scheduler = new NewsletterScheduler(subscriptionManager, emailSender, newsletter);

        // Schedule every Monday at 09:00
        LocalDateTime nextRun = LocalDateTime.now()
                .with(java.time.DayOfWeek.MONDAY)
                .withHour(9).withMinute(0).withSecond(0).withNano(0);
        if (nextRun.isBefore(LocalDateTime.now())) {
            nextRun = nextRun.plusWeeks(1);
        }
        scheduler.scheduleAt(nextRun);

        // Or schedule a fixed rate (e.g., every 7 days after first run)
        // scheduler.scheduleAtFixedRate(Duration.ofSeconds(10), Duration.ofDays(7));

        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(scheduler::shutdown));
    }
}
```