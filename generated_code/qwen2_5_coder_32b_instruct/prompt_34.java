```java
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.io.*;

public class EmailClient {

    private Session session;
    private Store store;

    public EmailClient(String host, String port, final String username, final String password) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", host);
        properties.put("mail.imaps.port", port);

        session = Session.getInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        store = session.getStore("imaps");
        store.connect(host, username, password);
    }

    public void sendEmail(String to, String subject, String body, File attachment) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(session.getProperty("mail.smtp.user")));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);

        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(body);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(textPart);

        if (attachment != null && attachment.exists()) {
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(attachment);
            multipart.addBodyPart(attachmentPart);
        }

        message.setContent(multipart);

        Transport.send(message);
    }

    public List<Message> getInboxMessages() throws MessagingException {
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
        return Arrays.asList(folder.getMessages());
    }

    public void close() throws MessagingException {
        store.close();
    }

    public static void main(String[] args) {
        try {
            EmailClient client = new EmailClient("imap.gmail.com", "993", "your-email@gmail.com", "your-password");

            // Sending an email with attachment
            client.sendEmail("recipient-email@gmail.com", "Test Subject", "Hello, this is a test email.", new File("path/to/attachment.txt"));

            // Fetching messages from inbox
            List<Message> messages = client.getInboxMessages();
            for (Message msg : messages) {
                System.out.println("Subject: " + msg.getSubject());
                System.out.println("From: " + Arrays.toString(msg.getFrom()));
                System.out.println("Text: " + msg.getContent().toString());
                System.out.println("----------------------------------");
            }

            client.close();
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }
}
```