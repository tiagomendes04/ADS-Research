```java
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.*;

/* ---------- Domain Models ---------- */
class Book {
    private final String isbn;
    private final String title;

    public Book(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
}

class Member {
    private final String id;
    private final String name;
    private final String email;

    public Member(String id, String name, String email) {
        this.id = id; this.name = name; this.email = email;
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}

class Loan {
    private final Book book;
    private final Member member;
    private final LocalDate loanDate;
    private final LocalDate dueDate;
    private boolean returned = false;

    public Loan(Book book, Member member, LocalDate loanDate, int loanPeriodDays) {
        this.book = book;
        this.member = member;
        this.loanDate = loanDate;
        this.dueDate = loanDate.plusDays(loanPeriodDays);
    }
    public Book getBook() { return book; }
    public Member getMember() { return member; }
    public LocalDate getDueDate() { return dueDate; }
    public boolean isReturned() { return returned; }
    public void setReturned(boolean returned) { this.returned = returned; }
    public boolean isOverdue() {
        return !returned && LocalDate.now().isAfter(dueDate);
    }
    public long daysOverdue() {
        return Period.between(dueDate, LocalDate.now()).getDays();
    }
}

/* ---------- Email Infrastructure ---------- */
interface EmailSender {
    void sendEmail(String to, String subject, String body);
}

class ConsoleEmailSender implements EmailSender {
    @Override
    public void sendEmail(String to, String subject, String body) {
        System.out.println("=== Sending Email ===");
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body:\n" + body);
        System.out.println("=====================\n");
    }
}

/* ---------- Notification Service ---------- */
class NotificationService {
    private final EmailSender emailSender;
    private final Set<String> notifiedLoans = ConcurrentHashMap.newKeySet(); // loan unique key

    public NotificationService(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void notifyOverdue(Loan loan) {
        String key = loan.getMember().getId() + "_" + loan.getBook().getIsbn();
        if (notifiedLoans.contains(key)) return; // already notified

        String subject = "Overdue Notice: " + loan.getBook().getTitle();
        String body = String.format(
                "Dear %s,\n\n" +
                "Our records show that the following item is overdue:\n" +
                "Title: %s\n" +
                "ISBN: %s\n" +
                "Due Date: %s\n" +
                "Days Overdue: %d\n\n" +
                "Please return the item as soon as possible to avoid further penalties.\n\n" +
                "Thank you,\nLibrary Team",
                loan.getMember().getName(),
                loan.getBook().getTitle(),
                loan.getBook().getIsbn(),
                loan.getDueDate(),
                loan.daysOverdue()
        );
        emailSender.sendEmail(loan.getMember().getEmail(), subject, body);
        notifiedLoans.add(key);
    }

    public void resetNotification(Loan loan) {
        String key = loan.getMember().getId() + "_" + loan.getBook().getIsbn();
        notifiedLoans.remove(key);
    }
}

/* ---------- Overdue Checker ---------- */
class OverdueChecker implements Runnable {
    private final List<Loan> loans;
    private final NotificationService notificationService;

    public OverdueChecker(List<Loan> loans, NotificationService notificationService) {
        this.loans = loans;
        this.notificationService = notificationService;
    }

    @Override
    public void run() {
        for (Loan loan : loans) {
            if (loan.isOverdue()) {
                notificationService.notifyOverdue(loan);
            } else if (loan.isReturned()) {
                notificationService.resetNotification(loan);
            }
        }
    }
}

/* ---------- Main Application ---------- */
public class LibraryOverdueSystem {
    public static void main(String[] args) {
        // Sample data
        Book book1 = new Book("978-0134685991", "Effective Java");
        Book book2 = new Book("978-0596009205", "Head First Java");

        Member member1 = new Member("M001", "Alice Smith", "alice@example.com");
        Member member2 = new Member("M002", "Bob Johnson", "bob@example.com");

        List<Loan> loans = new ArrayList<>();
        loans.add(new Loan(book1, member1, LocalDate.now().minusDays(20), 14)); // overdue
        loans.add(new Loan(book2, member2, LocalDate.now().minusDays(5), 14));  // not overdue

        EmailSender emailSender = new ConsoleEmailSender();
        NotificationService notificationService = new NotificationService(emailSender);
        OverdueChecker checker = new OverdueChecker(loans, notificationService);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(checker, 0, 1, TimeUnit.DAYS);

        // For demo purposes, run the checker immediately and shutdown after a short wait
        checker.run();
        scheduler.shutdown();
    }
}
```