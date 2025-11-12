```java
import java.util.HashMap;
import java.util.Map;

public class EmailSpamFilter {
    private Map<String, Integer> spamKeywords;

    public EmailSpamFilter() {
        spamKeywords = new HashMap<>();
        spamKeywords.put("win", 5);
        spamKeywords.put("prize", 5);
        spamKeywords.put("lottery", 5);
        spamKeywords.put("free", 3);
        spamKeywords.put("offer", 3);
        spamKeywords.put("click", 2);
        spamKeywords.put("buy", 2);
        spamKeywords.put("now", 2);
        spamKeywords.put("guarantee", 4);
        spamKeywords.put("limited", 4);
    }

    public boolean isSpam(String emailContent) {
        int score = 0;
        String[] words = emailContent.toLowerCase().split("\\W+");
        for (String word : words) {
            if (spamKeywords.containsKey(word)) {
                score += spamKeywords.get(word);
            }
        }
        return score >= 10;
    }

    public static void main(String[] args) {
        EmailSpamFilter filter = new EmailSpamFilter();
        System.out.println(filter.isSpam("Congratulations! You have won a free prize in our lottery. Click now to claim your guarantee offer."));
        System.out.println(filter.isSpam("Hi John, can we schedule a meeting for tomorrow?"));
    }
}
```