```java
import java.util.*;
import java.util.regex.*;

public class SpamFilter {

    private final Map<String, Integer> keywordScores;
    private final int spamThreshold;

    public SpamFilter(Map<String, Integer> keywordScores, int spamThreshold) {
        this.keywordScores = new HashMap<>(keywordScores);
        this.spamThreshold = spamThreshold;
    }

    public int scoreEmail(Email email) {
        int score = 0;
        String content = (email.getSubject() + " " + email.getBody()).toLowerCase();

        for (Map.Entry<String, Integer> entry : keywordScores.entrySet()) {
            String keyword = entry.getKey().toLowerCase();
            int weight = entry.getValue();

            // count occurrences (whole word match)
            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(keyword) + "\\b");
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                score += weight;
            }
        }
        return score;
    }

    public boolean isSpam(Email email) {
        return scoreEmail(email) >= spamThreshold;
    }

    // Simple demo
    public static void main(String[] args) {
        Map<String, Integer> keywords = new HashMap<>();
        keywords.put("free", 5);
        keywords.put("win", 4);
        keywords.put("winner", 4);
        keywords.put("prize", 5);
        keywords.put("click", 3);
        keywords.put("offer", 2);
        keywords.put("money", 4);
        keywords.put("urgent", 3);
        keywords.put("buy now", 6);
        keywords.put("discount", 2);

        SpamFilter filter = new SpamFilter(keywords, 10);

        List<Email> inbox = Arrays.asList(
                new Email("Congratulations! You are a winner", "Click here to claim your prize now."),
                new Email("Meeting agenda", "Please find attached the agenda for tomorrow's meeting."),
                new Email("Limited time offer", "Buy now and get a 50% discount!"),
                new Email("Hello friend", "Just wanted to catch up. How are you?")
        );

        for (Email e : inbox) {
            System.out.println("Subject: " + e.getSubject());
            System.out.println("Score: " + filter.scoreEmail(e));
            System.out.println("Spam? " + (filter.isSpam(e) ? "YES" : "NO"));
            System.out.println("---");
        }
    }
}

class Email {
    private final String subject;
    private final String body;

    public Email(String subject, String body) {
        this.subject = subject != null ? subject : "";
        this.body = body != null ? body : "";
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
```