```java
import java.util.*;
import org.apache.commons.math3.stat.regression.SimpleRegression;

public class RecommendationEngine {
    public static void main(String[] args) {
        // User preferences
        Map<String, Double> userPreferences = new HashMap<>();
        userPreferences.put("John", 4.5);
        userPreferences.put("Alice", 3.5);
        userPreferences.put("Bob", 4.0);
        userPreferences.put("Charlie", 3.0);

        // Movies
        Map<String, Double> movies = new HashMap<>();
        movies.put("Movie1", 4.2);
        movies.put("Movie2", 3.8);
        movies.put("Movie3", 4.8);
        movies.put("Movie4", 3.2);
        movies.put("Movie5", 4.0);

        // User items
        Map<String, Double> userItems = new HashMap<>();
        userItems.put("John", 4.8);
        userItems.put("Alice", 3.8);
        userItems.put("Bob", 4.2);
        userItems.put("Charlie", 3.2);
        userItems.put("John", 3.8);
        userItems.put("Alice", 4.0);
        userItems.put("Bob", 4.2);
        userItems.put("Charlie", 3.8);

        // Search query
        String searchQuery = "Movie1";

        // Recommend movies
        List<String> recommendedMovies = recommendMovies(userPreferences, movies, userItems, searchQuery);

        // Print recommended movies
        System.out.println("Recommended movies for " + searchQuery + ":");
        for (String movie : recommendedMovies) {
            System.out.println(movie);
        }

        // Recommend items
        List<String> recommendedItems = recommendItems(userPreferences, movies, userItems, searchQuery);

        // Print recommended items
        System.out.println("Recommended items for " + searchQuery + ":");
        for (String item : recommendedItems) {
            System.out.println(item);
        }
    }

    public static List<String> recommendMovies(Map<String, Double> userPreferences, Map<String, Double> movies, Map<String, Double> userItems, String searchQuery) {
        SimpleRegression regression = new SimpleRegression();
        regression.addData(userPreferences, movies);
        regression.addData(userItems, movies);
        regression.addData(userPreferences, searchQuery);
        regression.addData(userItems, searchQuery);

        double[] coefficients = regression.getParameters();
        int numMovies = (int) coefficients[0];
        int numUsers = (int) coefficients[1];

        List<String> recommendedMovies = new ArrayList<>();
        for (int i = 0; i < numMovies; i++) {
            recommendedMovies.add(movies.keySet().toArray()[i]);
        }
        return recommendedMovies;
    }

    public static List<String> recommendItems(Map<String, Double> userPreferences, Map<String, Double> movies, Map<String, Double> userItems, String searchQuery) {
        SimpleRegression regression = new SimpleRegression();
        regression.addData(userPreferences, movies);
        regression.addData(userItems, movies);
        regression.addData(userPreferences, searchQuery);
        regression.addData(userItems, searchQuery);

        double[] coefficients = regression.getParameters();
        int numMovies = (int) coefficients[0];
        int numUsers = (int) coefficients[1];

        List<String> recommendedItems = new ArrayList<>();
        for (int i = 0; i < numMovies; i++) {
            recommendedItems.add(movies.keySet().toArray()[i]);
        }
        return recommendedItems;
    }
}
```