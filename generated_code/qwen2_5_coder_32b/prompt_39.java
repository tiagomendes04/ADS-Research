```java
import java.util.*;
import java.util.stream.Collectors;

class Movie {
    String title;
    Set<String> genres;
    double rating;

    public Movie(String title, Set<String> genres, double rating) {
        this.title = title;
        this.genres = genres;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public double getRating() {
        return rating;
    }
}

class RecommendationSystem {
    List<Movie> movies;

    public RecommendationSystem(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> recommendMovies(Set<String> preferredGenres, int topN) {
        return movies.stream()
                .filter(movie -> !Collections.disjoint(movie.getGenres(), preferredGenres))
                .sorted(Comparator.comparingDouble(Movie::getRating).reversed())
                .limit(topN)
                .collect(Collectors.toList());
    }
}

public class Main {
    public static void main(String[] args) {
        List<Movie> movies = Arrays.asList(
                new Movie("Inception", new HashSet<>(Arrays.asList("Action", "Adventure", "Sci-Fi")), 8.8),
                new Movie("The Godfather", new HashSet<>(Arrays.asList("Crime", "Drama")), 9.2),
                new Movie("Pulp Fiction", new HashSet<>(Arrays.asList("Crime", "Drama")), 8.9),
                new Movie("Interstellar", new HashSet<>(Arrays.asList("Adventure", "Drama", "Sci-Fi")), 8.6),
                new Movie("The Dark Knight", new HashSet<>(Arrays.asList("Action", "Crime", "Drama")), 9.0),
                new Movie("Fight Club", new HashSet<>(Arrays.asList("Action", "Drama")), 8.8)
        );

        RecommendationSystem rs = new RecommendationSystem(movies);
        Set<String> preferredGenres = new HashSet<>(Arrays.asList("Action", "Sci-Fi"));
        List<Movie> recommendations = rs.recommendMovies(preferredGenres, 3);

        recommendations.forEach(movie -> System.out.println(movie.getTitle() + " - Rating: " + movie.getRating()));
    }
}
```