```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmailClient {

    private String emailAddress;
    private String password;
    private List<Email> inbox;

    public EmailClient(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.inbox = new ArrayList<>();
    }

    public void sendEmail(String to, String subject, String body, String filename) {
        Email email = new Email(to, subject, body, filename);
        System.out.println("Email sent successfully!");
    }

    public void receiveEmail(String sender) {
        System.out.println("Email received from: " + sender);
    }

    public void displayInbox() {
        for (Email email : inbox) {
            System.out.println(email);
        }
    }
    
    public void addEmailToInbox(Email email) {
        inbox.add(email);
    }

    public static void main(String[] args) {
        // TODO
    }
}

class Email {
    private String to;
    private String subject;
    private String body;
    private String filename;

    public Email(String to, String subject, String body, String filename) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "To: " + to + "\nSubject: " + subject + "\nBody: " + body + "\nFilename: " + filename;
    }
}
```