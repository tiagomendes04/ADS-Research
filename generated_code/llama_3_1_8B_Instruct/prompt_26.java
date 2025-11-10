```java
public class Model {
    private String id;
    private String name;
    private double rating;

    public Model(String id, String name, double rating) {
        this.id = id;
        this.name = name;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String name;
    private List<Model> preferences;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.preferences = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addPreference(Model model) {
        this.preferences.add(model);
    }

    public List<Model> getPreferences() {
        return preferences;
    }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class RecommendationEngine {
    private List<User> users;
    private List<Model> models;

    public RecommendationEngine() {
        this.users = new ArrayList<>();
        this.models = new ArrayList<>();
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void addModel(Model model) {
        this.models.add(model);
    }

    public List<Model> recommend(User user) {
        List<Model> recommendedModels = new ArrayList<>();
        for (Model model : models) {
            boolean isRecommended = true;
            for (Model preference : user.getPreferences()) {
                if (model.getId().equals(preference.getId())) {
                    isRecommended = false;
                    break;
                }
            }
            if (isRecommended) {
                recommendedModels.add(model);
            }
        }
        return recommendedModels;
    }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        RecommendationEngine engine = new RecommendationEngine();

        User user1 = new User("1", "John");
        user1.addPreference(new Model("1", "Movie1", 4.5));
        user1.addPreference(new Model("2", "Movie2", 4.8));

        User user2 = new User("2", "Jane");
        user2.addPreference(new Model("2", "Movie2", 4.8));
        user2.addPreference(new Model("3", "Movie3", 4.2));

        engine.addUser(user1);
        engine.addUser(user2);
        engine.addModel(new Model("1", "Movie1", 4.5));
        engine.addModel(new Model("2", "Movie2", 4.8));
        engine.addModel(new Model("3", "Movie3", 4.2));
        engine.addModel(new Model("4", "Movie4", 4.9));

        List<Model> recommendations = engine.recommend(user1);
        System.out.println("Recommendations for John:");
        for (Model model : recommendations) {
            System.out.println(model.getName());
        }
    }
}
```