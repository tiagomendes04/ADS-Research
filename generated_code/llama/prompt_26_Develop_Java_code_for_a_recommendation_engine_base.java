```java
import java.util.*;

class User {
    String id;
    Map<String, Double> preferences;

    public User(String id) {
        this.id = id;
        this.preferences = new HashMap<>();
    }

    public void addPreference(String item, double rating) {
        preferences.put(item, rating);
    }

    public Map<String, Double> getPreferences() {
        return preferences;
    }
}

class Item {
    String id;
    Map<String, Double> features;

    public Item(String id) {
        this.id = id;
        this.features = new HashMap<>();
    }

    public void addFeature(String feature, double value) {
        features.put(feature, value);
    }

    public Map<String, Double> getFeatures() {
        return features;
    }
}

class RecommendationEngine {
    Map<String, User> users;
    Map<String, Item> items;

    public RecommendationEngine() {
        this.users = new HashMap<>();
        this.items = new HashMap<>();
    }

    public void addUser(User user) {
        users.put(user.id, user);
    }

    public void addItem(Item item) {
        items.put(item.id, item);
    }

    public List<String> recommend(String userId, int numRecommendations) {
        User user = users.get(userId);
        if (user == null) {
            return new ArrayList<>();
        }

        Map<String, Double> userPreferences = user.getPreferences();
        Map<String, Double> itemFeatures = new HashMap<>();

        for (Item item : items.values()) {
            double similarity = 0;
            for (Map.Entry<String, Double> entry : userPreferences.entrySet()) {
                if (item.getFeatures().containsKey(entry.getKey())) {
                    similarity += entry.getValue() * item.getFeatures().get(entry.getKey());
                }
            }
            itemFeatures.put(item.id, similarity);
        }

        List<Map.Entry<String, Double>> sortedItems = new ArrayList<>(itemFeatures.entrySet());
        sortedItems.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        List<String> recommendations = new ArrayList<>();
        for (int i = 0; i < numRecommendations; i++) {
            recommendations.add(sortedItems.get(i).getKey());
        }

        return recommendations;
    }
}

public class Main {
    public static void main(String[] args) {
        RecommendationEngine engine = new RecommendationEngine();

        User user1 = new User("1");
        user1.addPreference("item1", 5.0);
        user1.addPreference("item2", 3.0);
        engine.addUser(user1);

        Item item1 = new Item("item1");
        item1.addFeature("feature1", 1.0);
        item1.addFeature("feature2", 2.0);
        engine.addItem(item1);

        Item item2 = new Item("item2");
        item2.addFeature("feature1", 2.0);
        item2.addFeature("feature2", 1.0);
        engine.addItem(item2);

        List<String> recommendations = engine.recommend("1", 2);
        System.out.println(recommendations);
    }
}
```