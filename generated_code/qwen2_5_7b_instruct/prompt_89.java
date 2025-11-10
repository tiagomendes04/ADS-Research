```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Recipe {
    private String name;
    private String cuisine;
    private int rating;

    public Recipe(String name, String cuisine, int rating) {
        this.name = name;
        this.cuisine = cuisine;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", cuisine='" + cuisine + '\'' +
                ", rating=" + rating +
                '}';
    }
}

class RecipeSearchEngine {
    private List<Recipe> recipes;

    public RecipeSearchEngine() {
        this.recipes = new ArrayList<>();
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public List<Recipe> searchByCuisine(String cuisine) {
        List<Recipe> results = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.getCuisine().equalsIgnoreCase(cuisine)) {
                results.add(recipe);
            }
        }
        return results;
    }

    public List<Recipe> searchByRating(int minRating) {
        List<Recipe> results = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.getRating() >= minRating) {
                results.add(recipe);
            }
        }
        return results;
    }

    public void displayRecipes(List<Recipe> recipes) {
        for (Recipe recipe : recipes) {
            System.out.println(recipe);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        RecipeSearchEngine searchEngine = new RecipeSearchEngine();

        // Adding recipes
        searchEngine.addRecipe(new Recipe("Pasta", "Italian", 4));
        searchEngine.addRecipe(new Recipe("Sushi", "Japanese", 5));
        searchEngine.addRecipe(new Recipe("Tacos", "Mexican", 3));
        searchEngine.addRecipe(new Recipe("Biryani", "Indian", 4));
        searchEngine.addRecipe(new Recipe("Pho", "Vietnamese", 4));

        Scanner scanner = new Scanner(System.in);

        System.out.println("Search by cuisine (or type 'exit' to quit):");
        String cuisine = scanner.nextLine();
        while (!cuisine.equalsIgnoreCase("exit")) {
            List<Recipe> results = searchEngine.searchByCuisine(cuisine);
            if (results.isEmpty()) {
                System.out.println("No recipes found for cuisine: " + cuisine);
            } else {
                System.out.println("Recipes found for cuisine: " + cuisine);
                searchEngine.displayRecipes(results);
            }
            System.out.println("Search by cuisine (or type 'exit' to quit):");
            cuisine = scanner.nextLine();
        }

        System.out.println("Search by minimum rating (or type 'exit' to quit):");
        int rating = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        while (rating >= 1 && rating <= 5) {
            List<Recipe> results = searchEngine.searchByRating(rating);
            if (results.isEmpty()) {
                System.out.println("No recipes found with rating " + rating + " or higher");
            } else {
                System.out.println("Recipes with rating " + rating + " or higher:");
                searchEngine.displayRecipes(results);
            }
            System.out.println("Search by minimum rating (or type 'exit' to quit):");
            rating = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        }
    }
}
```