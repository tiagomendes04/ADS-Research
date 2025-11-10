```java
import java.util.*;

public class UserPreferencesRecommender {
    private Map<String, List<String>> userPreferences;
    private Map<String, Map<String, Integer>> userItemRating;

    public UserPreferencesRecommender() {
        userPreferences = new HashMap<>();
        userItemRating = new HashMap<>();
    }

    public void addUserPreference(String user, List<String> items) {
        userPreferences.put(user, items);
    }

    public void rateItem(String user, String item, int rating) {
        userItemRating.putIfAbsent(user, new HashMap<>());
        userItemRating.get(user).put(item, rating);
    }

    public List<String> getRecommendations(String user) {
        if (!userPreferences.containsKey(user)) {
            return Collections.emptyList();
        }

        Map<String, Integer> userRatings = userItemRating.get(user);
        Map<String, Integer> recommendations = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : userPreferences.entrySet()) {
            if (!entry.getKey().equals(user)) {
                for (String item : entry.getValue()) {
                    if (!userRatings.containsKey(item)) {
                        recommendations.put(item, 1);
                    } else {
                        recommendations.put(item, userRatings.get(item) + 1);
                    }
                }
            }
        }

        List<String> sortedRecommendations = recommendations.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .limit(5)
                .collect(Collectors.toList());

        return sortedRecommendations;
    }

    public static void main(String[] args) {
        UserPreferencesRecommender recommender = new UserPreferencesRecommender();
        recommender.addUserPreference("user1", Arrays.asList("item1", "item2", "item3"));
        recommender.addUserPreference("user2", Arrays.asList("item2", "item3", "item4"));
        recommender.addUserPreference("user3", Arrays.asList("item1", "item4", "item5"));

        recommender.rateItem("user1", "item2", 5);
        recommender.rateItem("user2", "item3", 4);
        recommender.rateItem("user3", "item5", 3);

        System.out.println(recommender.getRecommendations("user1"));
    }
}
```