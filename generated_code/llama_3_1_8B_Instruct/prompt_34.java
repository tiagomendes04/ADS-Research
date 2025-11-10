```java
import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.*;

public class EmailClient {
    private final String HOST = "smtp.gmail.com";
    private final String PORT = "587";
    private final String FROM_EMAIL = "your-email@gmail.com";
    private final String FROM_PASSWORD = "your-password";

    public void sendEmail(String toEmail, String subject, String body, String[] attachments) {
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, FROM_PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setText(body);

            if (attachments != null) {
                for (String attachment : attachments) {
                    MimeBodyPart mbp = new MimeBodyPart();
                    mbp.attachFile(new File(attachment));
                    mbp.setDisposition(MimeBodyPart.INLINE);

                    MimeMultipart multipart = new MimeMultipart();
                    multipart.addBodyPart(mbp);
                    message.setContent(multipart);
                }
            }

            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }

    public void listInbox() {
        Properties props = new Properties();
        props.put("mail.pop3.host", HOST);
        props.put("mail.pop3.port", "995");
        props.put("mail.pop3.auth", "true");
        props.put("mail.pop3.starttls.enable", "true");

        Session session = Session.getInstance(props);

        try {
            Store store = session.getStore("pop3");
            store.connect(HOST, "your-email@gmail.com", "your-password");

            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            Message[] messages = folder.getMessages();
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                System.out.println("Message " + (i + 1) + ":");
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Subject: " + message.getSubject());
                System.out.println("Date: " + message.getSentDate());
                System.out.println();
            }

            folder.close(false);
            store.close();
        } catch (MessagingException e) {
            System.out.println("Error listing inbox: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        EmailClient emailClient = new EmailClient();
        emailClient.listInbox();

        emailClient.sendEmail("to-email@gmail.com", "Test Email", "Hello, this is a test email.", new String[]{"attachment1.txt", "attachment2.pdf"});
    }
}
```

```java
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;

public class MimeUtilities {
    public static void addAttachment(MimeMessage message, File file, String disposition) throws Exception {
        MimeBodyPart mbp = new MimeBodyPart();
        mbp.attachFile(file);
        mbp.setDisposition(disposition);

        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(mbp);
        message.setContent(multipart);
    }
}
```

```java
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {
    public static byte[] readFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        fis.read(buffer);
        fis.close();
        return buffer;
    }
}
```