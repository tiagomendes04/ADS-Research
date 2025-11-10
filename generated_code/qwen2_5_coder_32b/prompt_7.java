```java
import java.util.List;

interface NotificationService {
    void sendNotification(String message, List<String> recipients);
}

class EmailNotificationService implements NotificationService {
    @Override
    public void sendNotification(String message, List<String> recipients) {
        for (String email : recipients) {
            System.out.println("Sending email to: " + email + " with message: " + message);
        }
    }
}

class SMSNotificationService implements NotificationService {
    @Override
    public void sendNotification(String message, List<String> recipients) {
        for (String phoneNumber : recipients) {
            System.out.println("Sending SMS to: " + phoneNumber + " with message: " + message);
        }
    }
}

class PushNotificationService implements NotificationService {
    @Override
    public void sendNotification(String message, List<String> recipients) {
        for (String deviceId : recipients) {
            System.out.println("Sending push notification to device ID: " + deviceId + " with message: " + message);
        }
    }
}

class NotificationManager {
    private final NotificationService emailService;
    private final NotificationService smsService;
    private final NotificationService pushService;

    public NotificationManager() {
        this.emailService = new EmailNotificationService();
        this.smsService = new SMSNotificationService();
        this.pushService = new PushNotificationService();
    }

    public void notifyUsers(String message, List<String> emailRecipients, List<String> smsRecipients, List<String> pushRecipients) {
        if (!emailRecipients.isEmpty()) {
            emailService.sendNotification(message, emailRecipients);
        }
        if (!smsRecipients.isEmpty()) {
            smsService.sendNotification(message, smsRecipients);
        }
        if (!pushRecipients.isEmpty()) {
            pushService.sendNotification(message, pushRecipients);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        NotificationManager manager = new NotificationManager();
        manager.notifyUsers("Hello, this is a test message!",
                List.of("user1@example.com", "user2@example.com"),
                List.of("+1234567890", "+0987654321"),
                List.of("deviceID1", "deviceID2"));
    }
}
```