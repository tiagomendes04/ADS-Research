import java.util.*;

public class RecommendationEngine {
    private Map<String, Set<String>> userPreferences;
    private Map<String, Set<String>> itemCategories;

    public RecommendationEngine() {
        userPreferences = new HashMap<>();
        itemCategories = new HashMap<>();
    }

    public void addUserPreference(String userId, String itemId, String category) {
        userPreferences.computeIfAbsent(userId, k -> new HashSet<>()).add(itemId);
        itemCategories.computeIfAbsent(itemId, k -> new HashSet<>()).add(category);
    }

    public List<String> recommendItems(String userId, int count) {
        Set<String> preferredCategories = new HashSet<>();
        Set<String> preferredItems = userPreferences.getOrDefault(userId, new HashSet<>());

        for (String itemId : preferredItems) {
            preferredCategories.addAll(itemCategories.getOrDefault(itemId, new HashSet<>()));
        }

        List<String> recommendations = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : itemCategories.entrySet()) {
            String itemId = entry.getKey();
            if (!preferredItems.contains(itemId) && !preferredCategories.isEmpty()) {
                boolean matchesCategory = false;
                for (String category : entry.getValue()) {
                    if (preferredCategories.contains(category)) {
                        matchesCategory = true;
                        break;
                    }
                }
                if (matchesCategory) {
                    recommendations.add(itemId);
                }
            }
        }

        Collections.shuffle(recommendations);
        return recommendations.subList(0, Math.min(count, recommendations.size()));
    }

    public static void main(String[] args) {
        RecommendationEngine engine = new RecommendationEngine();

        engine.addUserPreference("user1", "item1", "books");
        engine.addUserPreference("user1", "item2", "books");
        engine.addUserPreference("user1", "item3", "movies");

        engine.addUserPreference("user2", "item4", "books");
        engine.addUserPreference("user2", "item5", "music");

        List<String> recommendations = engine.recommendItems("user1", 2);
        System.out.println("Recommended items for user1: " + recommendations);
    }
}