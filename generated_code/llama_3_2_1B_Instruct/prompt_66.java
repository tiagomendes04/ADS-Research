```java
import java.util.*;

class Recipe {
    String id;
    String name;
    List<String> ingredients;
    List<String> steps;
    String category;

    public Recipe(String id, String name, List<String> ingredients, List<String> steps, String category) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.category = category;
    }
}

class Ingredient {
    String name;
    String unit;

    public Ingredient(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }
}

class Step {
    String description;
    Recipe recipe;

    public Step(String description, Recipe recipe) {
        this.description = description;
        this.recipe = recipe;
    }
}

class RecipeManager {
    Map<String, Recipe> recipes = new HashMap<>();

    public void addRecipe(String id, String name, List<String> ingredients, List<String> steps, String category) {
        Recipe recipe = new Recipe(id, name, ingredients, steps, category);
        recipes.put(id, recipe);
    }

    public void printRecipe(String id) {
        if (recipes.containsKey(id)) {
            Recipe recipe = recipes.get(id);
            System.out.println("Recipe ID: " + id);
            System.out.println("Recipe Name: " + recipe.name);
            System.out.println("Ingredients: " + recipe.ingredients);
            System.out.println("Steps: " + recipe.steps);
            System.out.println("Category: " + recipe.category);
        }
    }

    public void addIngredient(String id, String name, String unit) {
        if (recipes.containsKey(id)) {
            List<Ingredient> ingredients = new ArrayList<>();
            ingredients.add(new Ingredient(name, unit));
            recipes.get(id).ingredients = ingredients;
        }
    }

    public void addStep(String id, String description, Recipe recipe) {
        if (recipes.containsKey(id)) {
            recipes.get(id).steps = new ArrayList<>(Arrays.asList(new Step(description, recipe)));
        }
    }

    public void printRecipeDetails(String id) {
        if (recipes.containsKey(id)) {
            Recipe recipe = recipes.get(id);
            System.out.println("Recipe ID: " + id);
            System.out.println("Recipe Name: " + recipe.name);
            System.out.println("Ingredients:");
            for (Ingredient ingredient : recipe.ingredients) {
                System.out.println("  - " + ingredient.name + " (" + ingredient.unit + ")");
            }
            System.out.println("Steps:");
            for (Step step : recipe.steps) {
                System.out.println("  - " + step.description);
            }
            System.out.println("Category: " + recipe.category);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        RecipeManager recipeManager = new RecipeManager();
        recipeManager.addRecipe("R001", "Grilled Chicken", Arrays.asList("chicken", "salt", "pepper"), Arrays.asList("Preheat grill to medium-high heat.", "Grill chicken for 5-7 minutes per side."), "Main Course");
        recipeManager.addIngredient("R001", "flour", "all-purpose");
        recipeManager.addIngredient("R001", "eggs", "large");
        recipeManager.addStep("R001", "Preheat grill to medium-high heat.", recipeManager.recipes.get("R001"));
        recipeManager.printRecipeDetails("R001");
    }
}
```