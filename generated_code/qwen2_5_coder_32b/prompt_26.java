```java
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Item {
    String id;
    Map<String, Double> features;

    Item(String id) {
        this.id = id;
        this.features = new HashMap<>();
    }

    void addFeature(String feature, double value) {
        features.put(feature, value);
    }
}

class User {
    String id;
    Map<String, Double> preferences;

    User(String id) {
        this.id = id;
        this.preferences = new HashMap<>();
    }

    void addPreference(String preference, double value) {
        preferences.put(preference, value);
    }
}

class RecommendationEngine {
    List<Item> items;

    RecommendationEngine() {
        items = new ArrayList<>();
    }

    void addItem(Item item) {
        items.add(item);
    }

    List<Item> recommend(User user, int topN) {
        List<Item> recommendedItems = new ArrayList<>(items);
        Collections.sort(recommendedItems, Comparator.comparingDouble(item -> -similarity(user.preferences, item.features)));
        return recommendedItems.subList(0, Math.min(topN, recommendedItems.size()));
    }

    private double similarity(Map<String, Double> userPrefs, Map<String, Double> itemFeatures) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (String key : userPrefs.keySet()) {
            if (itemFeatures.containsKey(key)) {
                dotProduct += userPrefs.get(key) * itemFeatures.get(key);
            }
            normA += Math.pow(userPrefs.get(key), 2);
        }
        for (double value : itemFeatures.values()) {
            normB += Math.pow(value, 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
```