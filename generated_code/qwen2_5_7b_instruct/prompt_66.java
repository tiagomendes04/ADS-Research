```java
import java.util.ArrayList;
import java.util.List;

class Ingredient {
    private String name;
    private int quantity;
    private String unit;

    public Ingredient(String name, int quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return name + " (" + quantity + " " + unit + ")";
    }
}

class Step {
    private int stepNumber;
    private String description;

    public Step(int stepNumber, String description) {
        this.stepNumber = stepNumber;
        this.description = description;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Step " + stepNumber + ": " + description;
    }
}

class Category {
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

class Recipe {
    private String title;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private Category category;

    public Recipe(String title, Category category) {
        this.title = title;
        this.ingredients = new ArrayList<>();
        this.steps = new ArrayList<>();
        this.category = category;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void addStep(Step step) {
        steps.add(step);
    }

    public String getTitle() {
        return title;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Recipe: ").append(title).append("\nCategory: ").append(category).append("\nIngredients:\n");
        for (Ingredient ingredient : ingredients) {
            sb.append(ingredient).append("\n");
        }
        sb.append("\nSteps:\n");
        for (Step step : steps) {
            sb.append(step).append("\n");
        }
        return sb.toString();
    }
}

public class RecipeManager {
    public static void main(String[] args) {
        Category category = new Category("Dessert");
        Recipe recipe = new Recipe("Chocolate Cake", category);

        recipe.addIngredient(new Ingredient("Flour", 2, "cups"));
        recipe.addIngredient(new Ingredient("Sugar", 1, "cup"));
        recipe.addIngredient(new Ingredient("Cocoa Powder", 1, "cup"));
        recipe.addIngredient(new Ingredient("Eggs", 2, "each"));
        recipe.addIngredient(new Ingredient("Milk", 1, "cup"));
        recipe.addIngredient(new Ingredient("Vanilla Extract", 1, "teaspoon"));
        recipe.addIngredient(new Ingredient("Baking Powder", 1, "teaspoon"));
        recipe.addIngredient(new Ingredient("Salt", 1, "teaspoon"));

        recipe.addStep(new Step(1, "Preheat the oven to 350°F (175°C)."));
        recipe.addStep(new Step(2, "Whisk together the flour, sugar, cocoa powder, baking powder, and salt in a large bowl."));
        recipe.addStep(new Step(3, "Make a well in the center and add the eggs, milk, and vanilla extract. Stir until just combined."));
        recipe.addStep(new Step(4, "Pour the batter into a greased 9-inch cake pan and bake for 25-30 minutes or until a toothpick inserted in the center comes out clean."));
        recipe.addStep(new Step(5, "Let the cake cool in the pan for 10 minutes, then turn it out onto a wire rack to cool completely."));

        System.out.println(recipe);
    }
}
```