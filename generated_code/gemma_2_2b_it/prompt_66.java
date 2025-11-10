```java
import java.util.ArrayList;
import java.util.List;

public class RecipeManager {

    public static class Recipe {
        String name;
        List<Ingredient> ingredients;
        List<String> steps;
        String category;

        public Recipe(String name, List<Ingredient> ingredients, List<String> steps, String category) {
            this.name = name;
            this.ingredients = ingredients;
            this.steps = steps;
            this.category = category;
        }
    }

    public static class Ingredient {
        String name;
        int quantity;

        public Ingredient(String name, int quantity) {
            this.name = name;
            this.quantity = quantity;
        }
    }

    public static void main(String[] args) {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("Spaghetti Bolognese", List.of(new Ingredient("Spaghetti", 200), new Ingredient("Ground Beef", 500)), List.of("Boil spaghetti", "Brown beef", "Mix everything together"), "Italian"));
        recipes.add(new Recipe("Chocolate Cake", List.of(new Ingredient("Flour", 250), new Ingredient("Sugar", 200), new Ingredient("Eggs", 3)), List.of("Mix dry ingredients", "Add wet ingredients", "Bake"), "Dessert"));
    }
}
```