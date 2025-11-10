```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibrarySystem {

    public static void main(String[] args) {
        Library library = new Library();
        library.addBook("Book1", LocalDate.of(2024, 1, 1));
        library.addBook("Book2", LocalDate.of(2024, 1, 15));
        library.addBook("Book3", LocalDate.of(2024, 2, 1));

        SendEmail email = new SendEmail();
        SendReminder reminder = new SendReminder();

        library.sendOverdueEmail("Book1", LocalDate.of(2024, 1, 31));
        library.sendOverdueEmail("Book2", LocalDate.of(2024, 2, 1));
        library.sendOverdueEmail("Book3", LocalDate.of(2024, 3, 1));

        library.sendReminderBook1();
        library.sendReminderBook2();
        library.sendReminderBook3();
    }

    public static class SendEmail {
        private String to;
        private String subject;
        private List<String> subjects;

        public SendEmail() {
            this.to = "";
            this.subject = "";
            this.subjects = new ArrayList<>();
        }

        public void addRecipient(String recipient, String subject) {
            this.subjects.add(subject);
            this.to = recipient;
        }

        public void sendEmail() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String emailBody = "";
            for (String subject : subjects) {
                emailBody += "Dear " + subject + ",\n";
                emailBody += "The book '" + to + "' is overdue.\n";
                emailBody += "Please return it by " + LocalDate.now().plusDays(7).format(formatter) + "\n";
                emailBody += "Thank you.\n";
                emailBody += "Best regards,\nSendEmail";
            }
            System.out.println(emailBody);
        }
    }

    public static class SendReminder {
        private String book;
        private LocalDate dueDate;

        public SendReminder() {
            this.book = "";
            this.dueDate = LocalDate.now().plusDays(7);
        }

        public void sendReminder() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String reminderBody = "";
            if (book.isEmpty()) {
                reminderBody = "You have an overdue book!";
            } else {
                reminderBody = "You have an overdue " + book + " due " + dueDate.format(formatter) + "!";
            }
            System.out.println(reminderBody);
        }
    }

    public static class Library {
        private Map<String, LocalDate> books;

        public Library() {
            this.books = new HashMap<>();
        }

        public void addBook(String title, LocalDate dueDate) {
            books.put(title, dueDate);
        }

        public void sendOverdueEmail(String title, LocalDate dueDate) {
            if (books.containsKey(title)) {
                LocalDate overdueDate = LocalDate.now().plusDays(7);
                if (overdueDate.isAfter(dueDate)) {
                    books.put(title, overdueDate);
                    System.out.println("Sending overdue email for " + title);
                } else {
                    System.out.println("Book " + title + " is not overdue.");
                }
            } else {
                System.out.println("Book " + title + " not found in library.");
            }
        }

        public void sendReminderBook(String title) {
            if (books.containsKey(title)) {
                LocalDate dueDate = books.get(title);
                if (dueDate.isBefore(LocalDate.now().minusDays(7))) {
                    System.out.println("Sending reminder for " + title);
                    sendReminder(title);
                }
            } else {
                System.out.println("Book " + title + " not found in library.");
            }
        }
    }
}
```