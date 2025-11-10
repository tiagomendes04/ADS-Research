```java
import java.util.HashMap;
import java.util.Map;

public class SpamFilter {

    private Map<String, Integer> keywords;
    private int threshold;

    public SpamFilter(int threshold) {
        this.threshold = threshold;
        this.keywords = new HashMap<>();
        keywords.put("free", 3);
        keywords.put("win", 2);
        keywords.put("prize", 2);
        keywords.put("urgent", 1);
        keywords.put("click", 1);
        keywords.put("important", 1);
    }

    public int scoreEmail(String email) {
        int score = 0;
        String[] words = email.split(" ");
        for (String word : words) {
            if (keywords.containsKey(word)) {
                score += keywords.get(word);
            }
        }
        return score;
    }

    public boolean isSpam(String email) {
        int score = scoreEmail(email);
        return score > threshold;
    }
}
```