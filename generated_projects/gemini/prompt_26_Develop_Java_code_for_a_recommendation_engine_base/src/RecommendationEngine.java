import java.util.*;

public class RecommendationEngine {

    private Map<String, Map<String, Double>> userPreferences;
    private List<String> items;

    public RecommendationEngine(Map<String, Map<String, Double>> userPreferences, List<String> items) {
        this.userPreferences = userPreferences;
        this.items = items;
    }

    public List<String> recommend(String userId, int numRecommendations) {
        if (!userPreferences.containsKey(userId)) {
            return new ArrayList<>(); // Or throw an exception
        }

        Map<String, Double> userPrefs = userPreferences.get(userId);
        Map<String, Double> itemScores = new HashMap<>();

        for (String item : items) {
            if (userPrefs.containsKey(item)) {
                continue; // User has already interacted with this item
            }

            double score = calculateScore(userId, item);
            itemScores.put(item, score);
        }

        List<String> recommendations = new ArrayList<>(itemScores.entrySet());
        recommendations.sort((a, b) -> b.getValue().compareTo(a.getValue())); // Sort by score descending

        List<String> topRecommendations = new ArrayList<>();
        for (int i = 0; i < Math.min(numRecommendations, recommendations.size()); i++) {
            topRecommendations.add(recommendations.get(i).getKey());
        }

        return topRecommendations;
    }

    private double calculateScore(String userId, String item) {
        double score = 0.0;
        int numUsers = 0;

        for (String otherUserId : userPreferences.keySet()) {
            if (otherUserId.equals(userId)) {
                continue;
            }

            Map<String, Double> otherUserPrefs = userPreferences.get(otherUserId);
            if (otherUserPrefs.containsKey(item) && userPreferences.get(userId).keySet().stream().anyMatch(otherUserPrefs::containsKey))
             {
                double similarity = calculateSimilarity(userId, otherUserId);
                score += similarity * otherUserPrefs.get(item);
                numUsers++;
            }
        }

        if (numUsers > 0) {
            return score / numUsers;
        } else {
            return 0.0;
        }
    }

    private double calculateSimilarity(String userId, String otherUserId) {
        Map<String, Double> userPrefs = userPreferences.get(userId);
        Map<String, Double> otherUserPrefs = userPreferences.get(otherUserId);

        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;

        Set<String> commonItems = new HashSet<>(userPrefs.keySet());
        commonItems.retainAll(otherUserPrefs.keySet());

        for (String item : commonItems) {
            dotProduct += userPrefs.get(item) * otherUserPrefs.get(item);
            magnitude1 += Math.pow(userPrefs.get(item), 2);
            magnitude2 += Math.pow(otherUserPrefs.get(item), 2);
        }

        if (magnitude1 == 0 || magnitude2 == 0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(magnitude1) * Math.sqrt(magnitude2));
    }


    public static void main(String[] args) {
        // Example Usage
        Map<String, Map<String, Double>> userPreferences = new HashMap<>();

        Map<String, Double> user1Prefs = new HashMap<>();
        user1Prefs.put("ItemA", 5.0);
        user1Prefs.put("ItemB", 4.0);
        user1Prefs.put("ItemC", 1.0);
        userPreferences.put("User1", user1Prefs);

        Map<String, Double> user2Prefs = new HashMap<>();
        user2Prefs.put("ItemA", 4.0);
        user2Prefs.put("ItemB", 5.0);
        user2Prefs.put("ItemD", 2.0);
        userPreferences.put("User2", user2Prefs);

        Map<String, Double> user3Prefs = new HashMap<>();
        user3Prefs.put("ItemB", 3.0);
        user3Prefs.put("ItemC", 5.0);
        user3Prefs.put("ItemD", 4.0);
        userPreferences.put("User3", user3Prefs);


        List<String> allItems = Arrays.asList("ItemA", "ItemB", "ItemC", "ItemD", "ItemE");


        RecommendationEngine engine = new RecommendationEngine(userPreferences, allItems);
        List<String> recommendations = engine.recommend("User1", 2);

        System.out.println("Recommendations for User1: " + recommendations); // Expected: [ItemD, ItemE]
    }
}