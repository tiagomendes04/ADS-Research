```java
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerLogMonitor {
    private static final String LOG_FILE_PATTERN = "log_(\\d{4}-\\d{2}-\\d{2}).txt";
    private static final Pattern ALERT_PATTERN = Pattern.compile("ERROR|CRITICAL");
    private static final int ALERT_THRESHOLD = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter log file path:");
        String logFilePath = scanner.nextLine();
        scanner.close();

        File logFile = new File(logFilePath);
        if (logFile.exists()) {
            System.out.println("Monitoring log file: " + logFile.getName());
            monitorLogFile(logFile);
        } else {
            System.out.println("Log file not found.");
        }
    }

    private static void monitorLogFile(File logFile) {
        int errorCount = 0;
        try (Scanner scanner = new Scanner(logFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Matcher matcher = ALERT_PATTERN.matcher(line);
                if (matcher.find()) {
                    errorCount++;
                    System.out.println("ALERT: " + line);
                    if (errorCount >= ALERT_THRESHOLD) {
                        sendAlert();
                        errorCount = 0;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading log file: " + e.getMessage());
        }
    }

    private static void sendAlert() {
        System.out.println("Sending alert via email/SMS...");
        // Implement email/SMS sending logic here
    }
}
```

```java
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class AlertSender {
    public static void sendAlert(String subject, String body, String filePath) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("your-email@gmail.com", "your-password");
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("your-email@gmail.com"));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress("recipient-email@gmail.com"));
            message.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filePath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filePath);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Alert sent successfully.");
        } catch (MessagingException e) {
            System.out.println("Error sending alert: " + e.getMessage());
        }
    }
}
```