```java
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.google.cloud.messaging.Message;
import com.google.cloud.messaging.MessageSender;
import com.google.cloud.messaging.SendResponse;
import com.google.cloud.messaging.Channel;

@Service
public class NotificationService {

    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;

    @Autowired
    private MessageSender messageSender;

    public void sendEmail(String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", mailHost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailUsername, mailPassword);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(mailUsername));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText(body);
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendSMS(String phoneNumber, String message) {
        // Implementation for SMS sending
    }

    public void sendPushNotification(String token, String message) {
        // Implementation for Push notification sending
    }
}
``` 

```java
import com.google.cloud.messaging.Channel;
import com.google.cloud.messaging.Message;
import com.google.cloud.messaging.MessageSender;
import com.google.cloud.messaging.SendResponse;

public class MyMessageSender implements MessageSender {

    @Override
    public SendResponse sendMessage(Channel channel, Message message) {
        // Implementation for sending messages
    }

}
```