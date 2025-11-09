import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;

public class PushNotification implements Notification {
    private String to;
    private String message;

    public PushNotification(String to, String message) {
        this.to = to;
        this.message = message;
    }

    @Override
    public void send() {
        FirebaseMessaging.getInstance().send(
                new FirebaseMessaging.Message.Builder()
                        .put("title", "Notification")
                        .put("body", message)
                        .put("to", to)
                        .build()
        );
        System.out.println("Push notification sent to " + to);
    }
}