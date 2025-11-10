```java
import java.util.*;

public class EmailSpamFilter {
    private Map<String, Integer> keywordWeights;

    public EmailSpamFilter() {
        keywordWeights = new HashMap<>();
        keywordWeights.put("buy", 5);
        keywordWeights.put("discount", 4);
        keywordWeights.put("free", 3);
        keywordWeights.put("offer", 2);
        keywordWeights.put("winner", 1);
    }

    public int calculateSpamScore(String emailBody) {
        int score = 0;
        String[] keywords = keywordWeights.keySet().toArray(new String[0]);

        for (String keyword : keywords) {
            if (emailBody.toLowerCase().contains(keyword)) {
                score += keywordWeights.get(keyword);
            }
        }

        return score;
    }

    public String classifyEmail(String emailBody) {
        int score = calculateSpamScore(emailBody);

        if (score >= 10) {
            return "SPAM";
        } else if (score >= 5) {
            return "PROBABLE SPAM";
        } else {
            return "LEGIT";
        }
    }

    public static void main(String[] args) {
        EmailSpamFilter filter = new EmailSpamFilter();
        System.out.println(filter.classifyEmail("Get 20% discount on your next purchase!"));
        System.out.println(filter.classifyEmail("You are the winner of our contest!"));
        System.out.println(filter.classifyEmail("Hello, how are you?"));
    }
}
```