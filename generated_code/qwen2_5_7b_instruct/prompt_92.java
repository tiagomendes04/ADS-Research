```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Movie {
    private String title;
    private List<String> genres;

    public Movie(String title, List<String> genres) {
        this.title = title;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getGenres() {
        return genres;
    }
}

class UserRating {
    private String userId;
    private String movieId;
    private int rating;

    public UserRating(String userId, String movieId, int rating) {
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public int getRating() {
        return rating;
    }
}

class MovieRecommendationSystem {
    private Map<String, Movie> movies;
    private Map<String, UserRating> userRatings;

    public MovieRecommendationSystem() {
        this.movies = new HashMap<>();
        this.userRatings = new HashMap<>();
    }

    public void addMovie(Movie movie) {
        movies.put(movie.getTitle(), movie);
    }

    public void addUserRating(UserRating rating) {
        userRatings.put(rating.getMovieId(), rating);
    }

    public List<Movie> getRecommendations(String userId, List<String> genres, int minRating) {
        List<Movie> recommendations = new ArrayList<>();
        for (Movie movie : movies.values()) {
            if (movie.getGenres().stream().anyMatch(genres::contains) && 
                userRatings.values().stream().anyMatch(r -> r.getUserId().equals(userId) && r.getMovieId().equals(movie.getTitle()) && r.getRating() >= minRating)) {
                recommendations.add(movie);
            }
        }
        return recommendations;
    }
}

public class Main {
    public static void main(String[] args) {
        MovieRecommendationSystem system = new MovieRecommendationSystem();

        Movie movie1 = new Movie("The Matrix", List.of("Sci-Fi", "Action"));
        Movie movie2 = new Movie("Inception", List.of("Sci-Fi", "Action"));
        Movie movie3 = new Movie("The Godfather", List.of("Crime", "Drama"));
        Movie movie4 = new Movie("Pulp Fiction", List.of("Crime", "Drama"));

        system.addMovie(movie1);
        system.addMovie(movie2);
        system.addMovie(movie3);
        system.addMovie(movie4);

        UserRating rating1 = new UserRating("user1", "movie1", 5);
        UserRating rating2 = new UserRating("user1", "movie2", 4);
        UserRating rating3 = new UserRating("user1", "movie3", 3);

        system.addUserRating(rating1);
        system.addUserRating(rating2);
        system.addUserRating(rating3);

        List<String> selectedGenres = List.of("Sci-Fi", "Action");
        int minRating = 4;

        List<Movie> recommendations = system.getRecommendations("user1", selectedGenres, minRating);
        for (Movie movie : recommendations) {
            System.out.println(movie.getTitle());
        }
    }
}
```