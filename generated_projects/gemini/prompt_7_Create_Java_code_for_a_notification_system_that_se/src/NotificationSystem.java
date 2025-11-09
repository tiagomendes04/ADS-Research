import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// 1. Notification Types Enum
enum NotificationType {
    EMAIL,
    SMS,
    PUSH
}

// 2. User Class
class User {
    private String userId;
    private String name;
    private String email;
    private String phoneNumber; // For SMS
    private String deviceToken; // For Push Notifications
    private Set<NotificationType> preferredNotificationTypes;

    public User(String userId, String name, String email, String phoneNumber, String deviceToken, Set<NotificationType> preferredNotificationTypes) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.deviceToken = deviceToken;
        this.preferredNotificationTypes = preferredNotificationTypes;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public Set<NotificationType> getPreferredNotificationTypes() {
        return preferredNotificationTypes;
    }

    @Override
    public String toString() {
        return "User{" +
               "userId='" + userId + '\'' +
               ", name='" + name + '\'' +
               ", preferredNotificationTypes=" + preferredNotificationTypes +
               '}';
    }
}

// 3. Notification Sender Interface
interface NotificationSender {
    NotificationType getType();
    void send(User user, String message);
}

// 4. Concrete Notification Sender Implementations

class EmailSender implements NotificationSender {
    @Override
    public NotificationType getType() {
        return NotificationType.EMAIL;
    }

    @Override
    public void send(User user, String message) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            System.out.println("ERROR: No email address for user " + user.getName() + " to send email.");
            return;
        }
        System.out.println("Sending Email to " + user.getName() + " (" + user.getEmail() + "): " + message);
        // In a real application, this would integrate with an email API (e.g., JavaMail, SendGrid)
    }
}

class SMSSender implements NotificationSender {
    @Override
    public NotificationType getType() {
        return NotificationType.SMS;
    }

    @Override
    public void send(User user, String message) {
        if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) {
            System.out.println("ERROR: No phone number for user " + user.getName() + " to send SMS.");
            return;
        }
        System.out.println("Sending SMS to " + user.getName() + " (" + user.getPhoneNumber() + "): " + message);
        // In a real application, this would integrate with an SMS API (e.g., Twilio, Nexmo)
    }
}

class PushSender implements NotificationSender {
    @Override
    public NotificationType getType() {
        return NotificationType.PUSH;
    }

    @Override
    public void send(User user, String message) {
        if (user.getDeviceToken() == null || user.getDeviceToken().isEmpty()) {
            System.out.println("ERROR: No device token for user " + user.getName() + " to send push notification.");
            return;
        }
        System.out.println("Sending Push Notification to " + user.getName() + " (Device: " + user.getDeviceToken() + "): " + message);
        // In a real application, this would integrate with a Push Notification Service (e.g., Firebase Cloud Messaging, Apple Push Notification Service)
    }
}

// 5. Notification Service
class NotificationService {
    private Map<NotificationType, NotificationSender> senders;

    public NotificationService() {
        this.senders = new HashMap<>();
    }

    public void registerSender(NotificationSender sender) {
        senders.put(sender.getType(), sender);
        System.out.println("Registered sender for " + sender.getType());
    }

    public void sendNotificationToUser(User user, String message) {
        System.out.println("\nAttempting to send notification to " + user.getName() + " (ID: " + user.getUserId() + ")");
        if (user.getPreferredNotificationTypes().isEmpty()) {
            System.out.println("User " + user.getName() + " has no preferred notification types set.");
            return;
        }

        for (NotificationType type : user.getPreferredNotificationTypes()) {
            NotificationSender sender = senders.get(type);
            if (sender != null) {
                sender.send(user, message);
            } else {
                System.out.println("No sender registered for notification type: " + type);
            }
        }
    }
}

// 6. Main Application Class
public class NotificationSystem {
    public static void main(String[] args) {
        // Initialize Notification Service
        NotificationService notificationService = new NotificationService();

        // Register available notification senders
        notificationService.registerSender(new EmailSender());
        notificationService.registerSender(new SMSSender());
        notificationService.registerSender(new PushSender());

        // Create Users with different preferences
        User user1 = new User("U001", "Alice", "alice@example.com", "123-456-7890", "device_token_alice",
                EnumSet.of(NotificationType.EMAIL, NotificationType.SMS));

        User user2 = new User("U002", "Bob", null, "098-765-4321", "device_token_bob",
                EnumSet.of(NotificationType.SMS, NotificationType.PUSH));

        User user3 = new User("U003", "Charlie", "charlie@example.com", null, null,
                EnumSet.of(NotificationType.EMAIL));

        User user4 = new User("U004", "David", "david@example.com", "555-123-4567", "device_token_david",
                EnumSet.allOf(NotificationType.class)); // All types

        User user5 = new User("U005", "Eve", null, null, null,
                EnumSet.noneOf(NotificationType.class)); // No preferences

        // Send notifications
        String message1 = "Your order #12345 has been shipped!";
        notificationService.sendNotificationToUser(user1, message1);

        String message2 = "New software update available for your device.";
        notificationService.sendNotificationToUser(user2, message2);

        String message3 = "Reminder: Your meeting starts in 15 minutes.";
        notificationService.sendNotificationToUser(user3, message3);

        String message4 = "Emergency Alert: Severe weather warning.";
        notificationService.sendNotificationToUser(user4, message4);

        String message5 = "Promotional offer just for you!";
        notificationService.sendNotificationToUser(user5, message5);
    }
}