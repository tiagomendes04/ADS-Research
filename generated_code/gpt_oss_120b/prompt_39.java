```java
import java.util.*;
import java.util.stream.*;

class Movie {
    private final int id;
    private final String title;
    private final Set<String> genres;
    private final double averageRating;

    public Movie(int id, String title, Set<String> genres, double averageRating) {
        this.id = id;
        this.title = title;
        this.genres = new HashSet<>(genres);
        this.averageRating = averageRating;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public Set<String> getGenres() { return Collections.unmodifiableSet(genres); }
    public double getAverageRating() { return averageRating; }
}

class User {
    private final int id;
    private final Map<Integer, Double> ratedMovies; // movieId -> rating

    public User(int id) {
        this.id = id;
        this.ratedMovies = new HashMap<>();
    }

    public int getId() { return id; }

    public void rateMovie(int movieId, double rating) {
        ratedMovies.put(movieId, rating);
    }

    public Map<Integer, Double> getRatings() {
        return Collections.unmodifiableMap(ratedMovies);
    }

    public Set<String> getPreferredGenres(Map<Integer, Movie> movieCatalog) {
        Map<String, Double> genreScore = new HashMap<>();
        for (Map.Entry<Integer, Double> entry : ratedMovies.entrySet()) {
            Movie m = movieCatalog.get(entry.getKey());
            if (m == null) continue;
            double weight = entry.getValue();
            for (String g : m.getGenres()) {
                genreScore.merge(g, weight, Double::sum);
            }
        }
        double max = genreScore.values().stream().mapToDouble(d -> d).max().orElse(0);
        return genreScore.entrySet().stream()
                .filter(e -> e.getValue() >= max * 0.5) // keep top 50% genres
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}

class RecommendationEngine {
    private final Map<Integer, Movie> movieCatalog; // movieId -> Movie

    public RecommendationEngine(Collection<Movie> movies) {
        this.movieCatalog = movies.stream()
                .collect(Collectors.toMap(Movie::getId, m -> m));
    }

    public List<Movie> recommend(User user, int limit) {
        Set<String> preferredGenres = user.getPreferredGenres(movieCatalog);
        Set<Integer> alreadySeen = user.getRatings().keySet();

        // Score = (genre match weight) * (movie average rating)
        return movieCatalog.values().stream()
                .filter(m -> !alreadySeen.contains(m.getId()))
                .map(m -> {
                    long genreMatches = m.getGenres().stream()
                            .filter(preferredGenres::contains)
                            .count();
                    double genreWeight = (double) genreMatches / m.getGenres().size();
                    double score = genreWeight * m.getAverageRating();
                    return new AbstractMap.SimpleEntry<>(m, score);
                })
                .filter(e -> e.getValue() > 0)
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .limit(limit)
                .map(AbstractMap.SimpleEntry::getKey)
                .collect(Collectors.toList());
    }
}

// Example usage
public class MovieRecommenderDemo {
    public static void main(String[] args) {
        List<Movie> movies = Arrays.asList(
                new Movie(1, "Inception", Set.of("Sci-Fi", "Action", "Thriller"), 4.8),
                new Movie(2, "The Godfather", Set.of("Crime", "Drama"), 4.9),
                new Movie(3, "Interstellar", Set.of("Sci-Fi", "Adventure", "Drama"), 4.7),
                new Movie(4, "The Dark Knight", Set.of("Action", "Crime", "Drama"), 4.9),
                new Movie(5, "Pulp Fiction", Set.of("Crime", "Drama"), 4.6),
                new Movie(6, "The Matrix", Set.of("Sci-Fi", "Action"), 4.7),
                new Movie(7, "Forrest Gump", Set.of("Drama", "Romance"), 4.8),
                new Movie(8, "Guardians of the Galaxy", Set.of("Sci-Fi", "Action", "Comedy"), 4.5)
        );

        RecommendationEngine engine = new RecommendationEngine(movies);

        User user = new User(101);
        user.rateMovie(1, 5.0); // Inception
        user.rateMovie(4, 4.5); // The Dark Knight
        user.rateMovie(5, 4.0); // Pulp Fiction

        List<Movie> recommendations = engine.recommend(user, 5);
        System.out.println("Recommended movies for user " + user.getId() + ":");
        for (Movie m : recommendations) {
            System.out.printf("- %s (Rating: %.2f, Genres: %s)%n",
                    m.getTitle(), m.getAverageRating(), String.join(", ", m.getGenres()));
        }
    }
}
```