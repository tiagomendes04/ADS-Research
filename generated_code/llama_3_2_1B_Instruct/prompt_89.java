```java
import java.util.*;

class Recipe {
    String id;
    String name;
    double rating;
    String ingredients;
    String instructions;

    public Recipe(String id, String name, double rating, String ingredients, String instructions) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return "id: " + id + "\nname: " + name + "\nrating: " + rating + "\ningredients: " + ingredients + "\ninstructions: " + instructions;
    }
}

class RecipeEngine {
    Map<String, Recipe> recipes;
    Map<String, Integer> ratings;

    public RecipeEngine() {
        recipes = new HashMap<>();
        ratings = new HashMap<>();
    }

    public void addRecipe(Recipe recipe) {
        recipes.put(recipe.id, recipe);
    }

    public void search(String query) {
        List<Recipe> results = new ArrayList<>();
        for (Recipe recipe : recipes.values()) {
            if (recipe.name.toLowerCase().contains(query.toLowerCase())) {
                results.add(recipe);
            }
        }
        Collections.sort(results, Comparator.comparingDouble(recipe -> recipe.rating));

        if (results.isEmpty()) {
            System.out.println("No recipes found matching the search query.");
            return;
        }

        System.out.println("Search results:");
        for (Recipe result : results) {
            System.out.println(result);
        }
    }

    public void rateRecipe(String recipeId, double rating) {
        if (ratings.containsKey(recipeId)) {
            ratings.put(recipeId, ratings.get(recipeId) + rating);
        } else {
            ratings.put(recipeId, rating);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        RecipeEngine engine = new RecipeEngine();

        engine.addRecipe(new Recipe("1", "Chicken Fajitas", 4.5, "chicken, peppers, onions", "sauté chicken, peppers, onions with tortillas"));
        engine.addRecipe(new Recipe("2", "Spaghetti Bolognese", 4.8, "spaghetti, ground beef, tomato sauce", "sauté ground beef, spaghetti, tomato sauce"));
        engine.addRecipe(new Recipe("3", "Roasted Vegetables", 4.2, "carrots, broccoli, potatoes", "roast vegetables in the oven"));

        engine.search("fajitas");
        engine.search("spaghetti");
        engine.search("vegetables");

        engine.rateRecipe("1", 4.7);
        engine.rateRecipe("2", 4.9);
        engine.rateRecipe("3", 4.1);
    }
}
```

```java
import java.util.*;

class Recipe {
    String id;
    String name;
    double rating;
    String ingredients;
    String instructions;

    public Recipe(String id, String name, double rating, String ingredients, String instructions) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return "id: " + id + "\nname: " + name + "\nrating: " + rating + "\ningredients: " + ingredients + "\ninstructions: " + instructions;
    }
}

class RecipeEngine {
    Map<String, Recipe> recipes;
    Map<String, Integer> ratings;

    public RecipeEngine() {
        recipes = new HashMap<>();
        ratings = new HashMap<>();
    }

    public void addRecipe(Recipe recipe) {
        recipes.put(recipe.id, recipe);
    }

    public void search(String query) {
        List<Recipe> results = new ArrayList<>();
        for (Recipe recipe : recipes.values()) {
            if (recipe.name.toLowerCase().contains(query.toLowerCase())) {
                results.add(recipe);
            }
        }
        Collections.sort(results, Comparator.comparingDouble(recipe -> recipe.rating));

        if (results.isEmpty()) {
            System.out.println("No recipes found matching the search query.");
            return;
        }

        System.out.println("Search results:");
        for (Recipe result : results) {
            System.out.println(result);
        }
    }

    public void rateRecipe(String recipeId, double rating) {
        if (ratings.containsKey(recipeId)) {
            ratings.put(recipeId, ratings.get(recipeId) + rating);
        } else {
            ratings.put(recipeId, rating);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        RecipeEngine engine = new RecipeEngine();

        engine.addRecipe(new Recipe("1", "Chicken Fajitas", 4.5, "chicken, peppers, onions", "sauté chicken, peppers, onions with tortillas"));
        engine.addRecipe(new Recipe("2", "Spaghetti Bolognese", 4.8, "spaghetti, ground beef, tomato sauce", "sauté ground beef, spaghetti, tomato sauce"));
        engine.addRecipe(new Recipe("3", "Roasted Vegetables", 4.2, "carrots, broccoli, potatoes", "roast vegetables in the oven"));

        engine.search("fajitas");
        engine.search("spaghetti");
        engine.search("vegetables");

        engine.rateRecipe("1", 4.7);
        engine.rateRecipe("2", 4.9);
        engine.rateRecipe("3", 4.1);
    }
}
```