```java
import java.util.*;

class Movie {
    String id;
    String title;
    String genre;
    int rating;

    public Movie(String id, String title, String genre, int rating) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.rating = rating;
    }
}

class RecommendationSystem {
    Map<String, Map<String, Double>> genreRatings = new HashMap<>();
    Map<String, List<Movie>> movies;

    public void addMovie(String id, String title, String genre, int rating) {
        movies.computeIfAbsent(id, k -> new ArrayList<>());
        movies.get(id).add(new Movie(id, title, genre, rating));
    }

    public void addGenre(String genre, Map<String, Double> ratings) {
        genreRatings.put(genre, ratings);
    }

    public void generateRecommendations(String userId, int maxRecommendations) {
        Map<String, List<Movie>> recommendations = new HashMap<>();
        for (Map.Entry<String, Map<String, Double>> entry : genreRatings.entrySet()) {
            String genre = entry.getKey();
            Map<String, Double> ratings = entry.getValue();
            if (ratings.containsKey(userId)) {
                double sum = 0;
                for (int i = 0; i < Math.min(maxRecommendations, ratings.size()); i++) {
                    sum += ratings.get(userId);
                }
                recommendations.put(genre, new ArrayList<>(movies.get(userId).subList(0, Math.min(maxRecommendations, ratings.size()))));
            }
        }
        if (recommendations.isEmpty()) {
            recommendations.put("all", new ArrayList<>());
        }
        recommendations.put("all", recommendations.values());
        System.out.println(recommendations);
    }
}

public class Main {
    public static void main(String[] args) {
        RecommendationSystem system = new RecommendationSystem();
        system.addMovie("1", "Movie1", "Action", 8);
        system.addMovie("1", "Movie2", "Action", 9);
        system.addMovie("2", "Movie3", "Comedy", 7);
        system.addMovie("3", "Movie4", "Action", 6);
        system.addMovie("4", "Movie5", "Comedy", 8);
        system.addGenre("Action", new HashMap<String, Double>() {{
            put("Movie1", 7.0);
            put("Movie2", 8.0);
        }});
        system.addGenre("Comedy", new HashMap<String, Double>() {{
            put("Movie3", 6.0);
            put("Movie4", 7.0);
        }});
        system.generateRecommendations("1", 3);
    }
}
```