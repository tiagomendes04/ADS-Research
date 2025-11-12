```java
import java.util.ArrayList;
import java.util.List;

class Ingredient {
    private String name;
    private double quantity;
    private String unit;

    public Ingredient(String name, double quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return quantity + " " + unit + " of " + name;
    }
}

class Step {
    private String description;

    public Step(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}

enum Category {
    APPETIZER, MAIN_COURSE, DESSERT, BEVERAGE
}

class Recipe {
    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private Category category;

    public Recipe(String name, Category category) {
        this.name = name;
        this.category = category;
        this.ingredients = new ArrayList<>();
        this.steps = new ArrayList<>();
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void addStep(Step step) {
        steps.add(step);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Recipe: ").append(name).append("\n");
        sb.append("Category: ").append(category).append("\n");
        sb.append("Ingredients:\n");
        for (Ingredient ingredient : ingredients) {
            sb.append("  - ").append(ingredient).append("\n");
        }
        sb.append("Steps:\n");
        for (int i = 0; i < steps.size(); i++) {
            sb.append("  ").append(i + 1).append(". ").append(steps.get(i)).append("\n");
        }
        return sb.toString();
    }
}

public class RecipeManager {
    private List<Recipe> recipes;

    public RecipeManager() {
        this.recipes = new ArrayList<>();
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public void displayRecipes() {
        for (Recipe recipe : recipes) {
            System.out.println(recipe);
        }
    }

    public static void main(String[] args) {
        RecipeManager manager = new RecipeManager();

        Recipe cake = new Recipe("Chocolate Cake", Category.DESSERT);
        cake.addIngredient(new Ingredient("flour", 2, "cups"));
        cake.addIngredient(new Ingredient("sugar", 1.5, "cups"));
        cake.addIngredient(new Ingredient("cocoa powder", 3, "tablespoons"));
        cake.addIngredient(new Ingredient("eggs", 3, "pieces"));
        cake.addIngredient(new Ingredient("milk", 1, "cup"));
        cake.addIngredient(new Ingredient("vegetable oil", 0.5, "cup"));
        cake.addIngredient(new Ingredient("vanilla extract", 2, "teaspoons"));
        cake.addStep(new Step("Preheat oven to 350 degrees F (175 degrees C)."));
        cake.addStep(new Step("Grease and flour a nine-inch round pan."));
        cake.addStep(new Step("In a large bowl, stir together the dry ingredients. Add the eggs, milk, oil, and vanilla. Mix until smooth."));
        cake.addStep(new Step("Pour the batter into the prepared pan."));
        cake.addStep(new Step("Bake in the preheated oven for 30 to 35 minutes, or until set."));
        manager.addRecipe(cake);

        Recipe pasta = new Recipe("Spaghetti Carbonara", Category.MAIN_COURSE);
        pasta.addIngredient(new Ingredient("spaghetti", 12, "ounces"));
        pasta.addIngredient(new Ingredient("pancetta", 6, "ounces"));
        pasta.addIngredient(new Ingredient("eggs", 4, "pieces"));
        pasta.addIngredient(new Ingredient("Pecorino Romano cheese", 0.5, "pound"));
        pasta.addIngredient(new Ingredient("black pepper", 1, "teaspoon"));
        pasta.addStep(new Step("Cook spaghetti in a large pot of boiling salted water until al dente."));
        pasta.addStep(new Step("Meanwhile, cut pancetta into small cubes and cook over medium heat until crisp."));
        pasta.addStep(new Step("In a large bowl, whisk together eggs, cheese, and pepper."));
        pasta.addStep(new Step("Drain spaghetti, reserving 1 cup of pasta water."));
        pasta.addStep(new Step("Add spaghetti to the bowl with the egg mixture and toss well. If too dry, add reserved pasta water 1 tablespoon at a time."));
        pasta.addStep(new Step("Stir in cooked pancetta and serve immediately."));
        manager.addRecipe(pasta);

        manager.displayRecipes();
    }
}
```