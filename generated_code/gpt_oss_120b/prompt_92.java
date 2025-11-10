```java
import java.util.*;
import java.util.stream.*;

class Movie {
    private final String id;
    private final String title;
    private final String genre;
    private final List<Integer> ratings = new ArrayList<>();

    public Movie(String id, String title, String genre) {
        this.id = id;
        this.title = title;
        this.genre = genre;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }

    public void addRating(int rating) {
        if (rating >= 1 && rating <= 5) ratings.add(rating);
    }

    public double getAverageRating() {
        return ratings.isEmpty() ? 0.0 : ratings.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }
}

class User {
    private final String id;
    private final Map<String, Integer> ratedMovies = new HashMap<>();

    public User(String id) { this.id = id; }

    public String getId() { return id; }

    public void rateMovie(String movieId, int rating) {
        if (rating >= 1 && rating <= 5) ratedMovies.put(movieId, rating);
    }

    public Map<String, Integer> getRatings() { return Collections.unmodifiableMap(ratedMovies); }
}

class RecommendationEngine {
    private final Map<String, Movie> movies = new HashMap<>();
    private final Map<String, User> users = new HashMap<>();

    public void addMovie(Movie movie) { movies.put(movie.getId(), movie); }

    public void addUser(User user) { users.put(user.getId(), user); }

    public void rateMovie(String userId, String movieId, int rating) {
        User user = users.get(userId);
        Movie movie = movies.get(movieId);
        if (user != null && movie != null) {
            user.rateMovie(movieId, rating);
            movie.addRating(rating);
        }
    }

    public List<Movie> recommendByGenre(String genre, int limit) {
        return movies.values().stream()
                .filter(m -> m.getGenre().equalsIgnoreCase(genre))
                .sorted(Comparator.comparingDouble(Movie::getAverageRating).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<Movie> recommendForUser(String userId, int limit) {
        User user = users.get(userId);
        if (user == null) return Collections.emptyList();

        Set<String> rated = user.getRatings().keySet();
        return movies.values().stream()
                .filter(m -> !rated.contains(m.getId()))
                .sorted(Comparator.comparingDouble(Movie::getAverageRating).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public Collection<Movie> getAllMovies() { return movies.values(); }
    public Collection<User> getAllUsers() { return users.values(); }
}

public class MovieRecommenderApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final RecommendationEngine engine = new RecommendationEngine();

    public static void main(String[] args) {
        seedData();
        while (true) {
            System.out.println("\n1) Rate a movie");
            System.out.println("2) Get recommendations by genre");
            System.out.println("3) Get personalized recommendations");
            System.out.println("4) Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": handleRating(); break;
                case "2": handleGenreRecommendation(); break;
                case "3": handlePersonalRecommendation(); break;
                case "4": return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void seedData() {
        engine.addMovie(new Movie("M1", "The Matrix", "Sci-Fi"));
        engine.addMovie(new Movie("M2", "Inception", "Sci-Fi"));
        engine.addMovie(new Movie("M3", "The Godfather", "Crime"));
        engine.addMovie(new Movie("M4", "Pulp Fiction", "Crime"));
        engine.addMovie(new Movie("M5", "Spirited Away", "Animation"));
        engine.addUser(new User("U1"));
        engine.addUser(new User("U2"));
    }

    private static void handleRating() {
        System.out.print("User ID: ");
        String userId = scanner.nextLine().trim();
        System.out.print("Movie ID: ");
        String movieId = scanner.nextLine().trim();
        System.out.print("Rating (1-5): ");
        int rating;
        try { rating = Integer.parseInt(scanner.nextLine().trim()); } catch (Exception e) { System.out.println("Invalid rating."); return; }
        engine.rateMovie(userId, movieId, rating);
        System.out.println("Rating recorded.");
    }

    private static void handleGenreRecommendation() {
        System.out.print("Genre: ");
        String genre = scanner.nextLine().trim();
        System.out.print("Number of recommendations: ");
        int limit;
        try { limit = Integer.parseInt(scanner.nextLine().trim()); } catch (Exception e) { System.out.println("Invalid number."); return; }
        List<Movie> recs = engine.recommendByGenre(genre, limit);
        if (recs.isEmpty()) {
            System.out.println("No movies found for this genre.");
        } else {
            System.out.println("Recommendations:");
            recs.forEach(m -> System.out.printf("%s (%s) - Avg Rating: %.2f%n", m.getTitle(), m.getId(), m.getAverageRating()));
        }
    }

    private static void handlePersonalRecommendation() {
        System.out.print("User ID: ");
        String userId = scanner.nextLine().trim();
        System.out.print("Number of recommendations: ");
        int limit;
        try { limit = Integer.parseInt(scanner.nextLine().trim()); } catch (Exception e) { System.out.println("Invalid number."); return; }
        List<Movie> recs = engine.recommendForUser(userId, limit);
        if (recs.isEmpty()) {
            System.out.println("No recommendations available.");
        } else {
            System.out.println("Personalized Recommendations:");
            recs.forEach(m -> System.out.printf("%s (%s) - Avg Rating: %.2f%n", m.getTitle(), m.getId(), m.getAverageRating()));
        }
    }
}
```