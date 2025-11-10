```java
import java.util.*;

public class RecommendationEngine {

    private Map<String, List<String>> userPreferences;
    private Map<String, Double> userScores;

    public RecommendationEngine() {
        userPreferences = new HashMap<>();
        userScores = new HashMap<>();
    }

    public void addPreference(String userId, String item) {
        if (!userPreferences.containsKey(userId)) {
            userPreferences.put(userId, new ArrayList<>());
        }
        userPreferences.get(userId).add(item);
    }

    public void setScore(String userId, String item, double score) {
        userScores.put(userId, score);
    }

    public List<String> getRecommendations(String userId) {
        List<String> recommendations = new ArrayList<>();
        if (userPreferences.containsKey(userId)) {
            for (String item : userPreferences.get(userId)) {
                recommendations.add(item);
            }
            
            // Implement recommendation logic (e.g., collaborative filtering)
            double score = userScores.getOrDefault(userId, 0.0); 
            for (String item : userPreferences.get(userId)) {
                if (userScores.containsKey(userId) && userScores.getOrDefault(userId, 0.0) > 0.5) {
                    recommendations.add(item);
                }
            }
        }
        return recommendations;
    }
}
```