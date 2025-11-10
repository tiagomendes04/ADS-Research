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
}

class User {
    String name;
    List<Movie> ratings;

    public User(String name) {
        this.name = name;
        this.ratings = new ArrayList<>();
    }

    public void rate(Movie movie, int rating) {
        this.ratings.add(new Movie(movie.title, movie.genre, rating));
    }
}

public class MovieRecommendationSystem {
    private Map<String, User> users;
    private List<Movie> movies;

    public MovieRecommendationSystem() {
        this.users = new HashMap<>();
        this.movies = new ArrayList<>();
    }

    public void addUser(User user) {
        this.users.put(user.name, user);
    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
    }

    public List<Movie> recommendMovies(String user, String genre, int minRating) {
        User u = users.get(user);
        List<Movie> userRatings = u.ratings;
        List<Movie> recommendedMovies = new ArrayList<>();

        for (Movie movie : movies) {
            if (movie.genre.equals(genre) && !userRatings.contains(movie)) {
                int sum = 0;
                int count = 0;
                for (Movie m : userRatings) {
                    if (m.genre.equals(movie.genre)) {
                        sum += m.rating;
                        count++;
                    }
                }
                if (count > 0) {
                    int avgRating = sum / count;
                    if (avgRating >= minRating) {
                        recommendedMovies.add(movie);
                    }
                }
            }
        }

        return recommendedMovies;
    }

    public static void main(String[] args) {
        MovieRecommendationSystem mrs = new MovieRecommendationSystem();

        User user1 = new User("John");
        user1.rate(new Movie("The Shawshank Redemption", "Drama", 5), 5);
        user1.rate(new Movie("The Godfather", "Crime", 5), 5);
        user1.rate(new Movie("The Dark Knight", "Action", 4), 4);

        mrs.addUser(user1);

        Movie movie1 = new Movie("The Shawshank Redemption", "Drama", 0);
        Movie movie2 = new Movie("The Godfather", "Crime", 0);
        Movie movie3 = new Movie("The Dark Knight", "Action", 0);
        Movie movie4 = new Movie("Inception", "Action", 0);
        Movie movie5 = new Movie("The Lord of the Rings", "Adventure", 0);

        mrs.addMovie(movie1);
        mrs.addMovie(movie2);
        mrs.addMovie(movie3);
        mrs.addMovie(movie4);
        mrs.addMovie(movie5);

        List<Movie> recommendedMovies = mrs.recommendMovies("John", "Action", 4);

        for (Movie movie : recommendedMovies) {
            System.out.println(movie.title);
        }
    }
}
```