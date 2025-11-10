```java
import java.util.*;

class Movie {
    String title;
    String genre;
    int rating;

    public Movie(String title, String genre, int rating) {
        this.title = title;
        this.genre = genre;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getRating() {
        return rating;
    }
}

class User {
    String name;
    List<Movie> ratedMovies;

    public User(String name) {
        this.name = name;
        this.ratedMovies = new ArrayList<>();
    }

    public void rateMovie(Movie movie, int rating) {
        movie.rating = rating;
        ratedMovies.add(movie);
    }

    public List<Movie> getRatedMovies() {
        return ratedMovies;
    }
}

class RecommendationSystem {
    Map<String, User> users;
    Map<String, Movie> movies;

    public RecommendationSystem() {
        this.users = new HashMap<>();
        this.movies = new HashMap<>();
    }

    public void addUser(String name) {
        users.put(name, new User(name));
    }

    public void addMovie(String title, String genre, int rating) {
        movies.put(title, new Movie(title, genre, rating));
    }

    public void rateMovie(String userName, String movieTitle, int rating) {
        User user = users.get(userName);
        Movie movie = movies.get(movieTitle);
        if (user != null && movie != null) {
            user.rateMovie(movie, rating);
        }
    }

    public List<Movie> recommendMovies(String userName) {
        User user = users.get(userName);
        if (user == null) {
            return new ArrayList<>();
        }

        Map<String, Integer> genreCount = new HashMap<>();
        Map<String, Integer> genreSum = new HashMap<>();

        for (Movie movie : user.getRatedMovies()) {
            String genre = movie.getGenre();
            int rating = movie.getRating();

            genreCount.put(genre, genreCount.getOrDefault(genre, 0) + 1);
            genreSum.put(genre, genreSum.getOrDefault(genre, 0) + rating);
        }

        List<Movie> recommendations = new ArrayList<>();
        for (Movie movie : movies.values()) {
            if (!user.getRatedMovies().contains(movie)) {
                String genre = movie.getGenre();
                int count = genreCount.getOrDefault(genre, 0);
                int sum = genreSum.getOrDefault(genre, 0);
                if (count > 0) {
                    recommendations.add(new Movie(movie.getTitle(), movie.getGenre(), sum / count));
                }
            }
        }

        return recommendations;
    }
}

public class Main {
    public static void main(String[] args) {
        RecommendationSystem rs = new RecommendationSystem();
        rs.addUser("Alice");
        rs.addMovie("Inception", "Sci-Fi", 8);
        rs.addMovie("Matrix", "Sci-Fi", 9);
        rs.addMovie("Lord of the Rings", "Fantasy", 7);
        rs.addMovie("Harry Potter", "Fantasy", 8);

        rs.rateMovie("Alice", "Inception", 8);
        rs.rateMovie("Alice", "Matrix", 9);

        List<Movie> recommendations = rs.recommendMovies("Alice");
        for (Movie movie : recommendations) {
            System.out.println(movie.getTitle() + " - " + movie.getRating());
        }
    }
}
```