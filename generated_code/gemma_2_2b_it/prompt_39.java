```java
import java.util.*;

public class MovieRecommendationSystem {

    static class Movie {
        String title;
        int id;
        Map<String, Double> ratings;
        Map<String, Integer> genres;

        public Movie(String title, int id) {
            this.title = title;
            this.id = id;
            this.ratings = new HashMap<>();
            this.genres = new HashMap<>();
        }
    }

    static class User {
        int id;
        Map<String, Double> ratings;

        public User(int id) {
            this.id = id;
            this.ratings = new HashMap<>();
        }
    }

    static Map<String, Double> calculateSimilarity(Movie movie1, Movie movie2) {
        // ... (implementation for calculating similarity based on ratings and genres)
        return new HashMap<>();
    }

    static List<Movie> recommendMovies(User user, Map<String, Double> movieRatings) {
        // ... (implementation for recommending movies based on user's ratings)
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        // Example data
        Movie movie1 = new Movie("The Shawshank Redemption", 1);
        Movie movie2 = new Movie("The Dark Knight", 2);
        Movie movie3 = new Movie("Inception", 3);
        Movie movie4 = new Movie("Interstellar", 4);
        Movie movie5 = new Movie("The Godfather", 5);

        User user = new User(1);

        // ... (input example data for ratings)
        
        // Recommend movies
        List<Movie> recommendations = recommendMovies(user, movieRatings);
    }
}
```