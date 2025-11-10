```java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Recipe {
    private String name;
    private String category;
    private double rating;

    public Recipe(String name, String category, double rating) {
        this.name = name;
        this.category = category;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Recipe{name='" + name + "', category='" + category + "', rating=" + rating + "}";
    }
}

public class RecipeSearchEngine {
    private List<Recipe> recipes;

    public RecipeSearchEngine() {
        recipes = new ArrayList<>();
        recipes.add(new Recipe("Spaghetti Carbonara", "Pasta", 4.5));
        recipes.add(new Recipe("Chicken Curry", "Curry", 4.2));
        recipes.add(new Recipe("Beef Tacos", "Mexican", 4.7));
        recipes.add(new Recipe("Vegetable Stir-Fry", "Chinese", 4.3));
        recipes.add(new Recipe("Caesar Salad", "Salad", 4.0));
    }

    public List<Recipe> searchRecipes(String category, double minRating) {
        List<Recipe> filteredRecipes = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.getCategory().equalsIgnoreCase(category) && recipe.getRating() >= minRating) {
                filteredRecipes.add(recipe);
            }
        }
        filteredRecipes.sort(Comparator.comparingDouble(Recipe::getRating).reversed());
        return filteredRecipes;
    }

    public static void main(String[] args) {
        RecipeSearchEngine engine = new RecipeSearchEngine();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter category:");
        String category = scanner.nextLine();

        System.out.println("Enter minimum rating:");
        double minRating = scanner.nextDouble();

        List<Recipe> results = engine.searchRecipes(category, minRating);
        if (results.isEmpty()) {
            System.out.println("No recipes found.");
        } else {
            System.out.println("Found recipes:");
            for (Recipe recipe : results) {
                System.out.println(recipe);
            }
        }
        scanner.close();
    }
}
```