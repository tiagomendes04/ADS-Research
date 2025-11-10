```java
import java.util.ArrayList;
import java.util.Scanner;

class Ingredient {
    private String name;
    private String quantity;

    public Ingredient(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }
}

class Step {
    private String description;

    public Step(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
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
}

class Recipe {
    private String name;
    private ArrayList<Category> categories;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;

    public Recipe(String name) {
        this.name = name;
        this.categories = new ArrayList<>();
        this.ingredients = new ArrayList<>();
        this.steps = new ArrayList<>();
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public void addStep(Step step) {
        this.steps.add(step);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }
}

public class RecipeManager {
    private ArrayList<Recipe> recipes;

    public RecipeManager() {
        this.recipes = new ArrayList<>();
    }

    public void addRecipe(Recipe recipe) {
        this.recipes.add(recipe);
    }

    public void displayRecipes() {
        for (int i = 0; i < recipes.size(); i++) {
            System.out.println("Recipe " + (i + 1) + ": " + recipes.get(i).getName());
        }
    }

    public void displayRecipeDetails(int index) {
        if (index >= 0 && index < recipes.size()) {
            Recipe recipe = recipes.get(index);
            System.out.println("Recipe Name: " + recipe.getName());
            System.out.println("Categories: ");
            for (Category category : recipe.getCategories()) {
                System.out.println(category.getName());
            }
            System.out.println("Ingredients: ");
            for (Ingredient ingredient : recipe.getIngredients()) {
                System.out.println(ingredient.getName() + ": " + ingredient.getQuantity());
            }
            System.out.println("Steps: ");
            for (Step step : recipe.getSteps()) {
                System.out.println(step.getDescription());
            }
        } else {
            System.out.println("Invalid recipe index.");
        }
    }

    public static void main(String[] args) {
        RecipeManager manager = new RecipeManager();

        Recipe recipe1 = new Recipe("Chicken Fajitas");
        Category category1 = new Category("Main Course");
        Category category2 = new Category("Vegetables");
        Ingredient ingredient1 = new Ingredient("Chicken Breast", "1 lb");
        Ingredient ingredient2 = new Ingredient("Bell Peppers", "2");
        Ingredient ingredient3 = new Ingredient("Onions", "1");
        Step step1 = new Step("Heat oil in a pan.");
        Step step2 = new Step("Add chicken and cook until browned.");
        Step step3 = new Step("Add bell peppers and onions and cook until tender.");
        recipe1.addCategory(category1);
        recipe1.addCategory(category2);
        recipe1.addIngredient(ingredient1);
        recipe1.addIngredient(ingredient2);
        recipe1.addIngredient(ingredient3);
        recipe1.addStep(step1);
        recipe1.addStep(step2);
        recipe1.addStep(step3);

        Recipe recipe2 = new Recipe("Beef Stroganoff");
        Category category3 = new Category("Main Course");
        Category category4 = new Category("Sauce");
        Ingredient ingredient4 = new Ingredient("Beef Strips", "1 lb");
        Ingredient ingredient5 = new Ingredient("Mushrooms", "1 cup");
        Ingredient ingredient6 = new Ingredient("Sour Cream", "1 cup");
        Step step4 = new Step("Cook beef and mushrooms in a pan.");
        Step step5 = new Step("Add sour cream and stir until combined.");
        recipe2.addCategory(category3);
        recipe2.addCategory(category4);
        recipe2.addIngredient(ingredient4);
        recipe2.addIngredient(ingredient5);
        recipe2.addIngredient(ingredient6);
        recipe2.addStep(step4);
        recipe2.addStep(step5);

        manager.addRecipe(recipe1);
        manager.addRecipe(recipe2);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Recipe Manager");
            System.out.println("1. Display Recipes");
            System.out.println("2. Display Recipe Details");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    manager.displayRecipes();
                    break;
                case 2:
                    System.out.print("Enter recipe index: ");
                    int index = scanner.nextInt();
                    manager.displayRecipeDetails(index - 1);
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }
}
```