```java
import java.util.*;

class User {
    String id;
    int rating;
    List<String> genres;

    public User(String id, int rating, List<String> genres) {
        this.id = id;
        this.rating = rating;
        this.genres = genres;
    }
}

class Genre {
    String name;

    public Genre(String name) {
        this.name = name;
    }
}

class Movie {
    String id;
    String title;
    int rating;
    List<Genre> genres;

    public Movie(String id, String title, int rating, List<Genre> genres) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.genres = genres;
    }
}

class MovieRecommendationSystem {
    private Map<String, User> users;
    private Map<String, List<Genre>> genres;

    public MovieRecommendationSystem() {
        users = new HashMap<>();
        genres = new HashMap<>();
    }

    public void addUser(User user) {
        users.put(user.id, user);
    }

    public void addGenre(Genre genre) {
        genres.put(genre.name, new ArrayList<>(genre.genres));
    }

    public List<String> getMovieRecommendations(String userId, int minRating, String genreName) {
        User user = users.get(userId);
        if (user == null) {
            return new ArrayList<>();
        }

        List<String> recommendations = new ArrayList<>();
        for (String genre : genres.get(genreName)) {
            if (user.genres.contains(genre.name)) {
                recommendations.add(genre.name);
            }
        }
        return recommendations;
    }
}

public class Main {
    public static void main(String[] args) {
        MovieRecommendationSystem movieRecommender = new MovieRecommendationSystem();

        User user1 = new User("1", 5, Arrays.asList("Action", "Comedy"));
        User user2 = new User("2", 4, Arrays.asList("Action", "Thriller"));
        User user3 = new User("3", 5, Arrays.asList("Romance", "Drama"));

        movieRecommender.addUser(user1);
        movieRecommender.addUser(user2);
        movieRecommender.addUser(user3);

        movieRecommender.addGenre(new Genre("Action"));
        movieRecommender.addGenre(new Genre("Comedy"));
        movieRecommender.addGenre(new Genre("Thriller"));
        movieRecommender.addGenre(new Genre("Romance"));
        movieRecommender.addGenre(new Genre("Drama"));

        String userId = "1";
        int minRating = 3;
        String genreName = "Action";

        List<String> recommendations = movieRecommender.getMovieRecommendations(userId, minRating, genreName);
        System.out.println("Movie recommendations for user " + userId + ": " + recommendations);
    }
}
```