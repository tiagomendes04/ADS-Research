```java
package com.example.recipesearch;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* ---------- Model ---------- */
class Recipe {
    private final UUID id;
    private final String name;
    private final List<String> ingredients;
    private final String cuisine;
    private final int prepTimeMinutes;
    private final List<Integer> ratings = new ArrayList<>();

    public Recipe(String name, List<String> ingredients, String cuisine, int prepTimeMinutes) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.ingredients = new ArrayList<>(ingredients);
        this.cuisine = cuisine;
        this.prepTimeMinutes = prepTimeMinutes;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public List<String> getIngredients() { return Collections.unmodifiableList(ingredients); }
    public String getCuisine() { return cuisine; }
    public int getPrepTimeMinutes() { return prepTimeMinutes; }

    public void addRating(int rating) {
        if (rating < 1 || rating > 5) throw new IllegalArgumentException("Rating must be 1..5");
        ratings.add(rating);
    }

    public double getAverageRating() {
        return ratings.isEmpty() ? 0.0 :
                ratings.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %d min - %.2f★", name, cuisine, prepTimeMinutes, getAverageRating());
    }
}

/* ---------- Repository ---------- */
class RecipeRepository {
    private final List<Recipe> recipes = new ArrayList<>();

    public void add(Recipe recipe) { recipes.add(recipe); }

    public List<Recipe> getAll() { return Collections.unmodifiableList(recipes); }
}

/* ---------- Search Engine ---------- */
class RecipeSearchEngine {
    private final RecipeRepository repository;

    public RecipeSearchEngine(RecipeRepository repository) {
        this.repository = repository;
    }

    public List<Recipe> search(Predicate<Recipe> filter) {
        return repository.getAll().stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    // Convenience filters
    public static Predicate<Recipe> containsIngredient(String ingredient) {
        String lower = ingredient.toLowerCase();
        return r -> r.getIngredients().stream()
                .anyMatch(i -> i.toLowerCase().contains(lower));
    }

    public static Predicate<Recipe> cuisineIs(String cuisine) {
        String lower = cuisine.toLowerCase();
        return r -> r.getCuisine().toLowerCase().equals(lower);
    }

    public static Predicate<Recipe> maxPrepTime(int minutes) {
        return r -> r.getPrepTimeMinutes() <= minutes;
    }

    public static Predicate<Recipe> minAverageRating(double rating) {
        return r -> r.getAverageRating() >= rating;
    }

    // Combine multiple predicates with AND
    @SafeVarargs
    public static Predicate<Recipe> allOf(Predicate<Recipe>... predicates) {
        return Arrays.stream(predicates)
                .reduce(r -> true, Predicate::and);
    }
}

/* ---------- Demo ---------- */
public class RecipeSearchDemo {
    public static void main(String[] args) {
        RecipeRepository repo = new RecipeRepository();

        Recipe r1 = new Recipe("Spaghetti Carbonara",
                Arrays.asList("spaghetti", "egg", "parmesan", "pancetta", "pepper"),
                "Italian", 30);
        r1.addRating(5);
        r1.addRating(4);

        Recipe r2 = new Recipe("Chicken Tikka Masala",
                Arrays.asList("chicken", "tomato", "yogurt", "garam masala", "cream"),
                "Indian", 45);
        r2.addRating(5);
        r2.addRating(5);
        r2.addRating(4);

        Recipe r3 = new Recipe("Guacamole",
                Arrays.asList("avocado", "lime", "onion", "cilantro", "salt"),
                "Mexican", 10);
        r3.addRating(4);
        r3.addRating(3);

        repo.add(r1);
        repo.add(r2);
        repo.add(r3);

        RecipeSearchEngine engine = new RecipeSearchEngine(repo);

        // Example: Italian recipes with prep time <= 40 minutes and rating >= 4.5
        Predicate<Recipe> filter = RecipeSearchEngine.allOf(
                RecipeSearchEngine.cuisineIs("Italian"),
                RecipeSearchEngine.maxPrepTime(40),
                RecipeSearchEngine.minAverageRating(4.5)
        );

        List<Recipe> results = engine.search(filter);
        System.out.println("Search results:");
        results.forEach(System.out::println);
    }
}
```