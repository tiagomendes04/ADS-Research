```java
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LibraryOverdueNotificationSystem {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/library";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "password";
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String EMAIL = "library@example.com";
    private static final String PASSWORD = "emailpassword";

    public static void main(String[] args) {
        try {
            checkOverdueBooksAndNotify();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void checkOverdueBooksAndNotify() throws Exception {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String query = "SELECT * FROM books WHERE due_date < ? AND is_overdue = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setDate(1, new Date(System.currentTimeMillis()));
            pstmt.setString(2, "N");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String bookId = rs.getString("book_id");
                String borrowerEmail = rs.getString("borrower_email");

                if (borrowerEmail != null && !borrowerEmail.isEmpty()) {
                    sendEmail(borrowerEmail, "Overdue Book Notification", "The book with ID " + bookId + " is overdue.");
                    updateBookStatus(bookId);
                }
            }
        }
    }

    private static void sendEmail(String toEmail, String subject, String message) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(props, null);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(EMAIL));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        msg.setSubject(subject);
        msg.setText(message);

        Transport.send(msg);
    }

    private static void updateBookStatus(String bookId) throws Exception {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String updateQuery = "UPDATE books SET is_overdue = 'Y' WHERE book_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(updateQuery);
            pstmt.setString(1, bookId);
            pstmt.executeUpdate();
        }
    }
}
```