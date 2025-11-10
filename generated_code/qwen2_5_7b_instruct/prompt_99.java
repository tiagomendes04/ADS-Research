```java
import java.util.HashMap;
import java.util.Map;

public class SimpleEmailSpamFilter {

    private static final Map<String, Integer> SPAM_KEYWORDS = new HashMap<>();
    private static final int SPAM_THRESHOLD = 50;

    static {
        SPAM_KEYWORDS.put("free", 10);
        SPAM_KEYWORDS.put("win", 15);
        SPAM_KEYWORDS.put("prize", 20);
        SPAM_KEYWORDS.put("claim", 15);
        SPAM_KEYWORDS.put("urgent", 25);
        SPAM_KEYWORDS.put("limited", 15);
        SPAM_KEYWORDS.put("discount", 10);
        SPAM_KEYWORDS.put("offer", 10);
        SPAM_KEYWORDS.put("cashback", 15);
        SPAM_KEYWORDS.put("click", 10);
    }

    public static boolean isSpam(String emailBody) {
        String[] words = emailBody.toLowerCase().split("\\s+");
        int score = 0;

        for (String word : words) {
            if (SPAM_KEYWORDS.containsKey(word)) {
                score += SPAM_KEYWORDS.get(word);
            }
        }

        return score >= SPAM_THRESHOLD;
    }

    public static void main(String[] args) {
        String emailBody = "Congratulations! You have won a free prize. Click here to claim it!";
        boolean isSpam = isSpam(emailBody);
        System.out.println("Is spam: " + isSpam);
    }
}
```