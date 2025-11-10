```java
import java.util.*;

class Newsletter {
    private String name;
    private String email;
    private String emailPattern;
    private Set<String> subscribers;
    private Calendar schedule;

    public Newsletter(String name, String email, String emailPattern, Calendar schedule) {
        this.name = name;
        this.email = email;
        this.emailPattern = emailPattern;
        this.subscribers = new HashSet<>();
        this.schedule = schedule;
    }

    public void addSubscriber(String email) {
        if (!emailPattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email address");
        }
        subscribers.add(email);
    }

    public void sendNotifications() {
        for (String email : subscribers) {
            System.out.println("Sending notification to " + email);
            // Simulate sending email notification
        }
    }

    public void updateSchedule() {
        schedule = new Calendar();
    }

    public void subscribe(String email) {
        addSubscriber(email);
    }
}

class Email {
    private String from;
    private String to;
    private String subject;
    private String body;

    public Email(String from, String to, String subject, String body) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}

class Calendar {
    private int start, end;

    public Calendar(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public boolean isDateInRange(String date) {
        String[] parts = date.split("-");
        int month = Integer.parseInt(parts[0]);
        int day = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        return (month == 1 && year >= start) || (month == 2 && year >= start && (day <= end || (day > end && (month == 2 && year > end))))) || (month == 3 && year >= start && (day <= end || (day > end && (month == 3 && year > end))))) || (month == 4 && year >= start && (day <= end || (day > end && (month == 4 && year > end))))) || (month == 5 && year >= start && (day <= end || (day > end && (month == 5 && year > end))))) || (month == 6 && year >= start && (day <= end || (day > end && (month == 6 && year > end))))) || (month == 7 && year >= start && (day <= end || (day > end && (month == 7 && year > end))))) || (month == 8 && year >= start && (day <= end || (day > end && (month == 8 && year > end))))) || (month == 9 && year >= start && (day <= end || (day > end && (month == 9 && year > end))))) || (month == 10 && year >= start && (day <= end || (day > end && (month == 10 && year > end))))) || (month == 11 && year >= start && (day <= end || (day > end && (month == 11 && year > end))))) {
        return true;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Newsletter> newsletters = new ArrayList<>();
        Newsletter newsletter1 = new Newsletter("Test", "test@example.com", "test@example.com", new Calendar(1, 31));
        newsletter1.subscribe("test@example.com");

        Email email1 = new Email("test@example.com", "test@example.com", "Test Email", "This is a test email");
        newsletter1.sendNotifications();

        Email email2 = new Email("test2@example.com", "test2@example.com", "Test Email 2", "This is another test email");
        newsletter1.subscribe("test2@example.com");
        newsletter1.sendNotifications();

        Calendar calendar = new Calendar(1, 31);
        newsletter1.updateSchedule();
        newsletter1.sendNotifications();
    }
}
```