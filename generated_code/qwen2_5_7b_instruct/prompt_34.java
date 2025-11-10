```java
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailClient {
    private String username;
    private String password;
    private Session session;

    public EmailClient(String username, String password) {
        this.username = username;
        this.password = password;
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void sendEmail(String to, String subject, String body, String attachmentPath) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        if (attachmentPath != null && !attachmentPath.isEmpty()) {
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.attachFile(attachmentPath);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);
        }

        Transport.send(message);
    }

    public void checkInbox() throws MessagingException {
        Store store = session.getStore("pop3");
        store.connect("pop.gmail.com", username, password);
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);

        Message[] messages = folder.getMessages();
        for (Message message : messages) {
            System.out.println("From: " + message.getFrom()[0]);
            System.out.println("Subject: " + message.getSubject());
            System.out.println("Text: " + message.getContent().toString());
        }

        folder.close(false);
        store.close();
    }
}
```