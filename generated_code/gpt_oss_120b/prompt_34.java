```java
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.activation.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class EmailClient {

    private final Session smtpSession;
    private final Session imapSession;
    private final String smtpUser;
    private final String imapUser;

    public EmailClient(String smtpHost, int smtpPort, String smtpUser, String smtpPass,
                       String imapHost, int imapPort, String imapUser, String imapPass) {

        this.smtpUser = smtpUser;
        this.imapUser = imapUser;

        Properties smtpProps = new Properties();
        smtpProps.put("mail.smtp.auth", "true");
        smtpProps.put("mail.smtp.starttls.enable", "true");
        smtpProps.put("mail.smtp.host", smtpHost);
        smtpProps.put("mail.smtp.port", String.valueOf(smtpPort));
        smtpProps.put("mail.smtp.ssl.trust", smtpHost);
        this.smtpSession = Session.getInstance(smtpProps, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpUser, smtpPass);
            }
        });

        Properties imapProps = new Properties();
        imapProps.put("mail.store.protocol", "imaps");
        imapProps.put("mail.imaps.host", imapHost);
        imapProps.put("mail.imaps.port", String.valueOf(imapPort));
        imapProps.put("mail.imaps.ssl.trust", imapHost);
        this.imapSession = Session.getInstance(imapProps);
        this.imapSession.setDebug(false);
        this.imapPassword = imapPass;
    }

    private final String imapPassword;

    // ---------- INBOX MANAGEMENT ----------
    public List<Message> fetchInbox(int maxMessages) throws MessagingException {
        Store store = imapSession.getStore("imaps");
        store.connect(imapUser, imapPassword);
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);
        int count = Math.min(maxMessages, inbox.getMessageCount());
        Message[] msgs = inbox.getMessages(inbox.getMessageCount() - count + 1, inbox.getMessageCount());
        List<Message> list = Arrays.asList(msgs);
        inbox.close(false);
        store.close();
        return list;
    }

    public void deleteMessage(Message msg) throws MessagingException {
        Store store = imapSession.getStore("imaps");
        store.connect(imapUser, imapPassword);
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);
        msg.setFlag(Flags.Flag.DELETED, true);
        inbox.close(true);
        store.close();
    }

    // ---------- SENDING ----------
    public void sendEmail(String from,
                          List<String> to,
                          List<String> cc,
                          List<String> bcc,
                          String subject,
                          String body,
                          List<File> attachments) throws MessagingException, IOException {

        MimeMessage message = new MimeMessage(smtpSession);
        message.setFrom(new InternetAddress(from));

        addRecipients(message, Message.RecipientType.TO, to);
        addRecipients(message, Message.RecipientType.CC, cc);
        addRecipients(message, Message.RecipientType.BCC, bcc);

        message.setSubject(subject, "UTF-8");

        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(body, "UTF-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(textPart);

        if (attachments != null) {
            for (File file : attachments) {
                if (!file.exists()) continue;
                MimeBodyPart attachPart = new MimeBodyPart();
                DataSource source = new FileDataSource(file);
                attachPart.setDataHandler(new DataHandler(source));
                attachPart.setFileName(MimeUtility.encodeText(file.getName()));
                multipart.addBodyPart(attachPart);
            }
        }

        message.setContent(multipart);
        Transport.send(message);
    }

    private void addRecipients(MimeMessage msg, Message.RecipientType type, List<String> addresses) throws MessagingException {
        if (addresses == null) return;
        for (String addr : addresses) {
            if (addr != null && !addr.isBlank())
                msg.addRecipient(type, new InternetAddress(addr.trim()));
        }
    }

    // ---------- UTILITIES ----------
    public static void saveAttachments(Message msg, Path destDir) throws IOException, MessagingException {
        if (!Files.isDirectory(destDir)) Files.createDirectories(destDir);
        if (msg.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) msg.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart bp = mp.getBodyPart(i);
                if (Part.ATTACHMENT.equalsIgnoreCase(bp.getDisposition())
                        || (bp.getFileName() != null && !bp.getFileName().isEmpty())) {
                    String decodedName = MimeUtility.decodeText(bp.getFileName());
                    Path filePath = destDir.resolve(decodedName);
                    try (InputStream is = bp.getInputStream();
                         OutputStream os = Files.newOutputStream(filePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                        byte[] buffer = new byte[8192];
                        int len;
                        while ((len = is.read(buffer)) != -1) os.write(buffer, 0, len);
                    }
                }
            }
        }
    }

    // ---------- DEMO ----------
    public static void main(String[] args) throws Exception {
        // Configuration (replace with real credentials)
        String smtpHost = "smtp.example.com";
        int smtpPort = 587;
        String smtpUser = "user@example.com";
        String smtpPass = "smtpPassword";

        String imapHost = "imap.example.com";
        int imapPort = 993;
        String imapUser = "user@example.com";
        String imapPass = "imapPassword";

        EmailClient client = new EmailClient(smtpHost, smtpPort, smtpUser, smtpPass,
                                             imapHost, imapPort, imapUser, imapPass);

        // Send email with attachment
        List<String> to = List.of("recipient@example.com");
        List<File> attachments = List.of(new File("sample.txt"));
        client.sendEmail(smtpUser, to, null, null,
                         "Test Email", "Hello from JavaMail!", attachments);

        // Fetch recent 5 messages
        List<Message> inbox = client.fetchInbox(5);
        for (Message m : inbox) {
            System.out.println("From: " + Arrays.toString(m.getFrom()));
            System.out.println("Subject: " + m.getSubject());
            System.out.println("Sent: " + m.getSentDate());
            saveAttachments(m, Paths.get("downloads"));
            System.out.println("---");
        }
    }
}
```