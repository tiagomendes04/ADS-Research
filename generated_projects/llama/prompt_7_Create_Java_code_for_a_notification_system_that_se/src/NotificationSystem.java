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