```java
import java.util.*;
import java.util.stream.Collectors;

class Movie {
    private String title;
    private Set<String> genres;
    private double averageRating;

    public Movie(String title, Set<String> genres) {
        this.title = title;
        this.genres = genres;
        this.averageRating = 0.0;
    }

    public void addRating(double rating) {
        if (averageRating == 0.0) {
            averageRating = rating;
        } else {
            averageRating = (averageRating + rating) / 2;
        }
    }

    public String getTitle() {
        return title;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public double getAverageRating() {
        return averageRating;
    }
}

class User {
    private String name;
    private Map<Movie, Double> ratings;

    public User(String name) {
        this.name = name;
        this.ratings = new HashMap<>();
    }

    public void rateMovie(Movie movie, double rating) {
        ratings.put(movie, rating);
        movie.addRating(rating);
    }

    public Set<String> getPreferredGenres() {
        return ratings.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .map(Map.Entry::getKey)
                .flatMap(movie -> movie.getGenres().stream())
                .collect(Collectors.toCollection(TreeSet::new));
    }
}

class RecommendationSystem {
    private List<Movie> movies;
    private List<User> users;

    public RecommendationSystem() {
        this.movies = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<Movie> recommendMovies(User user, int limit) {
        Set<String> preferredGenres = user.getPreferredGenres();
        return movies.stream()
                .filter(movie -> !user.ratings.containsKey(movie))
                .filter(movie -> preferredGenres.stream().anyMatch(movie.getGenres()::contains))
                .sorted((m1, m2) -> Double.compare(m2.getAverageRating(), m1.getAverageRating()))
                .limit(limit)
                .collect(Collectors.toList());
    }
}

public class Main {
    public static void main(String[] args) {
        RecommendationSystem rs = new RecommendationSystem();

        Movie movie1 = new Movie("Inception", new HashSet<>(Arrays.asList("Action", "Sci-Fi")));
        Movie movie2 = new Movie("The Godfather", new HashSet<>(Arrays.asList("Crime", "Drama")));
        Movie movie3 = new Movie("The Dark Knight", new HashSet<>(Arrays.asList("Action", "Crime", "Drama")));
        Movie movie4 = new Movie("Pulp Fiction", new HashSet<>(Arrays.asList("Crime", "Drama")));

        rs.addMovie(movie1);
        rs.addMovie(movie2);
        rs.addMovie(movie3);
        rs.addMovie(movie4);

        User user1 = new User("Alice");
        user1.rateMovie(movie1, 5.0);
        user1.rateMovie(movie2, 4.5);

        User user2 = new User("Bob");
        user2.rateMovie(movie3, 4.8);
        user2.rateMovie(movie4, 5.0);

        rs.addUser(user1);
        rs.addUser(user2);

        System.out.println("Recommendations for Alice:");
        rs.recommendMovies(user1, 2).forEach(movie -> System.out.println(movie.getTitle()));

        System.out.println("Recommendations for Bob:");
        rs.recommendMovies(user2, 2).forEach(movie -> System.out.println(movie.getTitle()));
    }
}
```