```java
import java.util.*;

class Movie {
    String title;
    double rating;
    Set<String> genres;

    public Movie(String title, double rating, Set<String> genres) {
        this.title = title;
        this.rating = rating;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public double getRating() {
        return rating;
    }

    public Set<String> getGenres() {
        return genres;
    }
}

class User {
    String name;
    Map<String, Double> ratings;

    public User(String name) {
        this.name = name;
        this.ratings = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<String, Double> getRatings() {
        return ratings;
    }

    public void rateMovie(String movieTitle, double rating) {
        ratings.put(movieTitle, rating);
    }
}

class RecommendationSystem {
    Map<String, Movie> movies;
    Map<String, User> users;

    public RecommendationSystem() {
        this.movies = new HashMap<>();
        this.users = new HashMap<>();
    }

    public void addMovie(String title, double rating, Set<String> genres) {
        Movie movie = new Movie(title, rating, genres);
        movies.put(title, movie);
    }

    public void addUser(String name) {
        User user = new User(name);
        users.put(name, user);
    }

    public void rateMovie(String userName, String movieTitle, double rating) {
        User user = users.get(userName);
        if (user != null) {
            user.rateMovie(movieTitle, rating);
        }
    }

    public Map<String, Double> getRecommendations(String userName) {
        User user = users.get(userName);
        if (user == null) {
            return new HashMap<>();
        }

        Map<String, Double> movieRatings = user.getRatings();
        Map<String, Double> ratings = new HashMap<>();
        for (Map.Entry<String, Double> entry : movieRatings.entrySet()) {
            Movie movie = movies.get(entry.getKey());
            if (movie != null) {
                ratings.put(entry.getKey(), entry.getValue() * movie.getRating());
            }
        }

        List<Movie> sortedMovies = new ArrayList<>(movies.values());
        sortedMovies.sort((m1, m2) -> Double.compare(m2.getRating(), m1.getRating()));
        Map<String, Double> recommendations = new HashMap<>();
        for (Movie movie : sortedMovies) {
            if (!movieRatings.containsKey(movie.getTitle())) {
                recommendations.put(movie.getTitle(), movie.getRating());
            }
        }

        return recommendations;
    }
}

public class Main {
    public static void main(String[] args) {
        RecommendationSystem system = new RecommendationSystem();

        Set<String> genres = new HashSet<>(Arrays.asList("Action", "Comedy"));
        system.addMovie("Movie1", 8.5, genres);
        system.addMovie("Movie2", 7.5, new HashSet<>(Arrays.asList("Drama", "Romance")));
        system.addMovie("Movie3", 9.0, new HashSet<>(Arrays.asList("Action", "Thriller")));

        system.addUser("User1");
        system.addUser("User2");

        system.rateMovie("User1", "Movie1", 9.0);
        system.rateMovie("User1", "Movie2", 8.0);
        system.rateMovie("User2", "Movie3", 9.5);

        Map<String, Double> recommendations = system.getRecommendations("User1");
        System.out.println(recommendations);
    }
}
```