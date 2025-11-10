```java
import java.util.*;
import java.util.stream.Collectors;

public class RecommendationEngine {

    // Map<UserId, Map<ItemId, Rating>>
    private final Map<String, Map<String, Double>> userRatings = new HashMap<>();

    // Map<ItemId, Set<UserId>>
    private final Map<String, Set<String>> itemUsers = new HashMap<>();

    /** Add or update a rating for a user-item pair */
    public void addRating(String userId, String itemId, double rating) {
        userRatings.computeIfAbsent(userId, k -> new HashMap<>()).put(itemId, rating);
        itemUsers.computeIfAbsent(itemId, k -> new HashSet<>()).add(userId);
    }

    /** Get top N recommendations for a user using user‑based collaborative filtering */
    public List<Recommendation> recommend(String userId, int topN) {
        Map<String, Double> targetRatings = userRatings.getOrDefault(userId, Collections.emptyMap());
        if (targetRatings.isEmpty()) return Collections.emptyList();

        // Compute similarity between target user and all other users
        Map<String, Double> similarities = new HashMap<>();
        for (String otherUser : userRatings.keySet()) {
            if (otherUser.equals(userId)) continue;
            double sim = pearsonCorrelation(targetRatings, userRatings.get(otherUser));
            if (sim > 0) similarities.put(otherUser, sim);
        }

        // Aggregate weighted ratings for items the target user hasn't rated
        Map<String, Double> scoreSums = new HashMap<>();
        Map<String, Double> weightSums = new HashMap<>();

        for (Map.Entry<String, Double> entry : similarities.entrySet()) {
            String otherUser = entry.getKey();
            double similarity = entry.getValue();
            for (Map.Entry<String, Double> ratingEntry : userRatings.get(otherUser).entrySet()) {
                String itemId = ratingEntry.getKey();
                if (targetRatings.containsKey(itemId)) continue; // already rated
                double rating = ratingEntry.getValue();

                scoreSums.merge(itemId, similarity * rating, Double::sum);
                weightSums.merge(itemId, Math.abs(similarity), Double::sum);
            }
        }

        // Compute final scores
        List<Recommendation> recommendations = new ArrayList<>();
        for (String itemId : scoreSums.keySet()) {
            double score = scoreSums.get(itemId) / weightSums.get(itemId);
            recommendations.add(new Recommendation(itemId, score));
        }

        // Return top N sorted by score descending
        return recommendations.stream()
                .sorted(Comparator.comparingDouble(Recommendation::getScore).reversed())
                .limit(topN)
                .collect(Collectors.toList());
    }

    /** Pearson correlation coefficient between two users' rating vectors */
    private double pearsonCorrelation(Map<String, Double> a, Map<String, Double> b) {
        Set<String> common = new HashSet<>(a.keySet());
        common.retainAll(b.keySet());
        int n = common.size();
        if (n == 0) return 0.0;

        double sumA = 0, sumB = 0, sumASq = 0, sumBSq = 0, sumProd = 0;
        for (String item : common) {
            double ra = a.get(item);
            double rb = b.get(item);
            sumA += ra;
            sumB += rb;
            sumASq += ra * ra;
            sumBSq += rb * rb;
            sumProd += ra * rb;
        }

        double num = sumProd - (sumA * sumB / n);
        double den = Math.sqrt((sumASq - (sumA * sumA / n)) * (sumBSq - (sumB * sumB / n)));
        return den == 0 ? 0.0 : num / den;
    }

    /** Simple DTO for recommendation results */
    public static class Recommendation {
        private final String itemId;
        private final double score;

        public Recommendation(String itemId, double score) {
            this.itemId = itemId;
            this.score = score;
        }

        public String getItemId() {
            return itemId;
        }

        public double getScore() {
            return score;
        }

        @Override
        public String toString() {
            return "Recommendation{itemId='" + itemId + "', score=" + score + '}';
        }
    }

    // Example usage
    public static void main(String[] args) {
        RecommendationEngine engine = new RecommendationEngine();

        // Sample data: user, item, rating (1-5)
        engine.addRating("U1", "I1", 5);
        engine.addRating("U1", "I2", 3);
        engine.addRating("U1", "I3", 4);

        engine.addRating("U2", "I1", 3);
        engine.addRating("U2", "I2", 4);
        engine.addRating("U2", "I4", 2);
        engine.addRating("U2", "I5", 5);

        engine.addRating("U3", "I1", 4);
        engine.addRating("U3", "I3", 5);
        engine.addRating("U3", "I4", 3);
        engine.addRating("U3", "I5", 2);

        List<Recommendation> recs = engine.recommend("U1", 3);
        recs.forEach(System.out::println);
    }
}
```