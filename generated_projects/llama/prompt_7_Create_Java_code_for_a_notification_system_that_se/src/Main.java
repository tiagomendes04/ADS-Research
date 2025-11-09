public class Main {
    public static void main(String[] args) {
        NotificationSystem notificationSystem = new NotificationSystem();
        notificationSystem.sendEmail("recipient@example.com", "Test Email", "This is a test email.");
        notificationSystem.sendSMS("+1234567890", "This is a test SMS.");
        notificationSystem.sendPushAlert("recipient-device-id", "This is a test push notification.");
        notificationSystem.sendAllNotifications();
    }
}