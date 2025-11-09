```java
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.json.JSONObject;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NotificationSystem {
    private static final String TWILIO_ACCOUNT_SID = "your_account_sid";
    private static final String TWILIO_AUTH_TOKEN = "your_auth_token";
    private static final String TWILIO_PHONE_NUMBER = "+1234567890";

    public static void sendEmail(String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.example.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("your_email@example.com", "your_password");
            }
        });

        try {
            Message emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress("your_email@example.com"));
            emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            emailMessage.setSubject(subject);
            emailMessage.setText(body);

            Transport.send(emailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendSMS(String to, String message) {
        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);

        Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(TWILIO_PHONE_NUMBER),
                message
        ).create();
    }

    public static void sendPushNotification(String deviceToken, String title, String body) {
        try {
            String apiKey = "your_firebase_api_key";
            String url = "https://fcm.googleapis.com/fcm/send";

            JSONObject json = new JSONObject();
            json.put("to", deviceToken);
            JSONObject info = new JSONObject();
            info.put("title", title);
            info.put("body", body);
            json.put("notification", info);

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "key=" + apiKey);
            con.setRequestProperty("Content-Type", "application/json");

            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(json.toString().getBytes("UTF-8"));
            os.flush();
            os.close();

            int responseCode = con.getResponseCode();
            System.out.println("Response Code: " + responseCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Example usage
        sendEmail("user@example.com", "Test Email", "This is a test email");
        sendSMS("+1987654321", "This is a test SMS");
        sendPushNotification("device_token_here", "Test Push", "This is a test push notification");
    }
}
```