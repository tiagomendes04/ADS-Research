```java
import java.util.*;

public class EmailSpamFilter {
    public static int calculateScore(String email, String keyword) {
        int score = 0;
        if (email.contains(keyword)) {
            score += 10;
        }
        if (email.contains("free") || email.contains("software") || email.contains("offer")) {
            score += 10;
        }
        if (email.contains("buy now") || email.contains("instantly")) {
            score += 10;
        }
        return score;
    }

    public static void main(String[] args) {
        String email = "your_email@example.com";
        String keyword = "free software";
        int score = calculateScore(email, keyword);
        System.out.println("Score: " + score);
    }
}
```