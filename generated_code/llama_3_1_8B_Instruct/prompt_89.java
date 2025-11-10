```java
import java.util.*;

class Recipe {
    private String name;
    private String description;
    private String ingredients;
    private String instructions;
    private double rating;

    public Recipe(String name, String description, String ingredients, String instructions) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.rating = 0;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}

class RecipeSearchEngine {
    private Map<String, Recipe> recipeMap;

    public RecipeSearchEngine() {
        this.recipeMap = new HashMap<>();
    }

    public void addRecipe(Recipe recipe) {
        recipeMap.put(recipe.getName(), recipe);
    }

    public List<Recipe> searchRecipes(String keyword) {
        List<Recipe> matchingRecipes = new ArrayList<>();
        for (Recipe recipe : recipeMap.values()) {
            if (recipe.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                    recipe.getDescription().toLowerCase().contains(keyword.toLowerCase()) ||
                    recipe.getIngredients().toLowerCase().contains(keyword.toLowerCase()) ||
                    recipe.getInstructions().toLowerCase().contains(keyword.toLowerCase())) {
                matchingRecipes.add(recipe);
            }
        }
        return matchingRecipes;
    }

    public List<Recipe> filterRecipesByIngredient(String ingredient) {
        List<Recipe> matchingRecipes = new ArrayList<>();
        for (Recipe recipe : recipeMap.values()) {
            if (recipe.getIngredients().toLowerCase().contains(ingredient.toLowerCase())) {
                matchingRecipes.add(recipe);
            }
        }
        return matchingRecipes;
    }

    public List<Recipe> filterRecipesByRating(double minRating) {
        List<Recipe> matchingRecipes = new ArrayList<>();
        for (Recipe recipe : recipeMap.values()) {
            if (recipe.getRating() >= minRating) {
                matchingRecipes.add(recipe);
            }
        }
        return matchingRecipes;
    }
}

public class Main {
    public static void main(String[] args) {
        RecipeSearchEngine searchEngine = new RecipeSearchEngine();

        Recipe recipe1 = new Recipe("Chicken Parmesan", "Italian dish made with chicken, tomato sauce, and mozzarella cheese", "1 lb chicken, 1 cup tomato sauce, 1 cup mozzarella cheese", "Bread the chicken and fry it in a pan. Top with tomato sauce and mozzarella cheese.");
        recipe1.setRating(4.5);

        Recipe recipe2 = new Recipe("Spaghetti Bolognese", "Italian dish made with spaghetti, ground beef, and tomato sauce", "1 lb spaghetti, 1 lb ground beef, 1 cup tomato sauce", "Cook the spaghetti and ground beef in a pan. Add tomato sauce and serve.");

        searchEngine.addRecipe(recipe1);
        searchEngine.addRecipe(recipe2);

        List<Recipe> searchResults = searchEngine.searchRecipes("chicken");
        System.out.println("Search results for 'chicken':");
        for (Recipe recipe : searchResults) {
            System.out.println(recipe.getName());
        }

        List<Recipe> filteredRecipes = searchEngine.filterRecipesByIngredient("tomato sauce");
        System.out.println("Filtered recipes by ingredient 'tomato sauce':");
        for (Recipe recipe : filteredRecipes) {
            System.out.println(recipe.getName());
        }

        List<Recipe> ratedRecipes = searchEngine.filterRecipesByRating(4.0);
        System.out.println("Filtered recipes by rating >= 4.0:");
        for (Recipe recipe : ratedRecipes) {
            System.out.println(recipe.getName() + " - Rating: " + recipe.getRating());
        }
    }
}
```

```java
import java.util.*;

class RatingSystem {
    private Map<String, Double> ratings;

    public RatingSystem() {
        this.ratings = new HashMap<>();
    }

    public void rateRecipe(String recipeName, double rating) {
        ratings.put(recipeName, rating);
    }

    public double getRating(String recipeName) {
        return ratings.get(recipeName);
    }
}

public class Main {
    public static void main(String[] args) {
        RatingSystem ratingSystem = new RatingSystem();

        ratingSystem.rateRecipe("Chicken Parmesan", 4.5);
        ratingSystem.rateRecipe("Spaghetti Bolognese", 4.0);

        System.out.println("Rating for 'Chicken Parmesan': " + ratingSystem.getRating("Chicken Parmesan"));
        System.out.println("Rating for 'Spaghetti Bolognese': " + ratingSystem.getRating("Spaghetti Bolognese"));
    }
}
```