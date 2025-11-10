```java
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmailClient {
    private Properties props;
    private Session session;
    private List<Email> inbox = new ArrayList<>();
    private String from;
    private String password;

    public EmailClient(String from, String password) {
        this.from = from;
        this.password = password;
        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.ciphers", "DHE+AES256+SHA256");
        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
    }

    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            inbox.add(new Email(to, subject, body));
        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }

    public void addEmail(String to, String subject, String body) {
        sendEmail(to, subject, body);
    }

    public void addAttachment(String fileName, byte[] attachment) {
        Email email = new Email(to, subject, body, fileName, attachment);
        inbox.add(email);
    }

    public List<Email> getInbox() {
        return inbox;
    }

    public static void main(String[] args) {
        EmailClient client = new EmailClient("your-email@gmail.com", "your-password");
        client.addEmail("recipient-email@example.com", "Test Email", "Test body");
        client.addAttachment("attachment.pdf", "attachment.pdf");
        client.getInbox().forEach(email -> System.out.println(email));
    }
}
```

```java
import java.io.File;
import java.io.IOException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimePart;

public class Attachment {
    private String fileName;
    private byte[] attachment;

    public Attachment(String fileName, byte[] attachment) {
        this.fileName = fileName;
        this.attachment = attachment;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "fileName='" + fileName + '\'' +
                ", attachment=" + attachment +
                '}';
    }
}
```