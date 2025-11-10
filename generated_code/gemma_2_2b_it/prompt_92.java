```java
import java.util.*;

public class MovieRecommendationSystem {

    static class Movie {
        String title;
        List<Integer> ratings;
        List<String> genres;
        
        public Movie(String title, List<Integer> ratings, List<String> genres) {
            this.title = title;
            this.ratings = ratings;
            this.genres = genres;
        }
    }

    static class User {
        String name;
        Map<String, List<Integer>> ratings;
        
        public User(String name) {
            this.name = name;
            this.ratings = new HashMap<>();
        }
    }

    public static void main(String[] args) {
        // Sample data
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Shawshank Redemption", Arrays.asList(5, 4, 3, 4, 5), Arrays.asList("Drama", "Thriller")));
        movies.add(new Movie("The Godfather", Arrays.asList(5, 4, 5, 5, 5), Arrays.asList("Crime", "Drama")));
        movies.add(new Movie("Inception", Arrays.asList(4, 3, 4, 4, 5), Arrays.asList("Sci-Fi", "Action", "Thriller")));
        movies.add(new Movie("The Dark Knight", Arrays.asList(5, 4, 5, 5, 5), Arrays.asList("Action", "Thriller", "Crime")));
        movies.add(new Movie("Pulp Fiction", Arrays.asList(4, 5, 5, 4, 4), Arrays.asList("Crime", "Drama")));
        
        // Sample user data
        List<User> users = new ArrayList<>();
        users.add(new User("Alice"));
        users.add(new User("Bob"));

        // Recommendation logic (example)
        for (User user : users) {
            System.out.println("User: " + user.name);
            for (Movie movie : movies) {
                if (user.ratings.containsKey(movie.title)) {
                    System.out.println("\t" + movie.title + " - Rating: " + user.ratings.get(movie.title));
                }
            }
        }
    }
}
```