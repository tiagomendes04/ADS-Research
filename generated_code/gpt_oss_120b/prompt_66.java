```java
import java.util.*;

public class RecipeManagerApp {

    // ----- Models -----
    public static class Ingredient {
        private final String name;
        private final String quantity; // e.g., "2 cups", "1 tbsp"

        public Ingredient(String name, String quantity) {
            this.name = name;
            this.quantity = quantity;
        }

        public String getName() { return name; }
        public String getQuantity() { return quantity; }

        @Override
        public String toString() {
            return quantity + " " + name;
        }
    }

    public static class Category {
        private final String name;

        public Category(String name) {
            this.name = name.toLowerCase();
        }

        public String getName() { return name; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Category)) return false;
            Category that = (Category) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() { return Objects.hash(name); }

        @Override
        public String toString() { return name; }
    }

    public static class Recipe {
        private final String title;
        private final Set<Category> categories = new HashSet<>();
        private final List<Ingredient> ingredients = new ArrayList<>();
        private final List<String> steps = new ArrayList<>();

        public Recipe(String title) {
            this.title = title;
        }

        public String getTitle() { return title; }

        public Set<Category> getCategories() { return Collections.unmodifiableSet(categories); }
        public List<Ingredient> getIngredients() { return Collections.unmodifiableList(ingredients); }
        public List<String> getSteps() { return Collections.unmodifiableList(steps); }

        public Recipe addCategory(Category cat) {
            categories.add(cat);
            return this;
        }

        public Recipe addIngredient(Ingredient ing) {
            ingredients.add(ing);
            return this;
        }

        public Recipe addStep(String step) {
            steps.add(step);
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== ").append(title).append(" ===\n");
            sb.append("Categories: ").append(categories).append("\n\n");
            sb.append("Ingredients:\n");
            for (Ingredient i : ingredients) sb.append("- ").append(i).append("\n");
            sb.append("\nSteps:\n");
            int idx = 1;
            for (String s : steps) sb.append(idx++).append(". ").append(s).append("\n");
            return sb.toString();
        }
    }

    // ----- Manager -----
    public static class RecipeManager {
        private final Map<String, Recipe> recipes = new LinkedHashMap<>();

        public boolean addRecipe(Recipe recipe) {
            if (recipes.containsKey(recipe.getTitle().toLowerCase())) return false;
            recipes.put(recipe.getTitle().toLowerCase(), recipe);
            return true;
        }

        public Recipe getRecipe(String title) {
            return recipes.get(title.toLowerCase());
        }

        public List<Recipe> listAll() {
            return new ArrayList<>(recipes.values());
        }

        public List<Recipe> findByCategory(String categoryName) {
            String key = categoryName.toLowerCase();
            List<Recipe> result = new ArrayList<>();
            for (Recipe r : recipes.values()) {
                for (Category c : r.getCategories()) {
                    if (c.getName().equals(key)) {
                        result.add(r);
                        break;
                    }
                }
            }
            return result;
        }

        public boolean removeRecipe(String title) {
            return recipes.remove(title.toLowerCase()) != null;
        }
    }

    // ----- Simple Console UI -----
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RecipeManager manager = new RecipeManager();

        while (true) {
            System.out.println("\n--- Recipe Manager ---");
            System.out.println("1) Add recipe");
            System.out.println("2) List all recipes");
            System.out.println("3) View recipe");
            System.out.println("4) Find by category");
            System.out.println("5) Delete recipe");
            System.out.println("0) Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    addRecipeUI(sc, manager);
                    break;
                case "2":
                    listAllUI(manager);
                    break;
                case "3":
                    viewRecipeUI(sc, manager);
                    break;
                case "4":
                    findByCategoryUI(sc, manager);
                    break;
                case "5":
                    deleteRecipeUI(sc, manager);
                    break;
                case "0":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void addRecipeUI(Scanner sc, RecipeManager manager) {
        System.out.print("Recipe title: ");
        String title = sc.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }

        Recipe recipe = new Recipe(title);

        // Categories
        System.out.print("Enter categories (comma separated): ");
        String[] cats = sc.nextLine().split(",");
        for (String c : cats) {
            String name = c.trim();
            if (!name.isEmpty()) recipe.addCategory(new Category(name));
        }

        // Ingredients
        System.out.println("Enter ingredients (type 'done' when finished):");
        while (true) {
            System.out.print("  Quantity (e.g., '2 cups'): ");
            String qty = sc.nextLine().trim();
            if (qty.equalsIgnoreCase("done")) break;
            System.out.print("  Ingredient name: ");
            String name = sc.nextLine().trim();
            if (name.equalsIgnoreCase("done")) break;
            if (!qty.isEmpty() && !name.isEmpty()) {
                recipe.addIngredient(new Ingredient(name, qty));
            }
        }

        // Steps
        System.out.println("Enter steps (type 'done' when finished):");
        int stepNum = 1;
        while (true) {
            System.out.print("  Step " + stepNum + ": ");
            String step = sc.nextLine().trim();
            if (step.equalsIgnoreCase("done")) break;
            if (!step.isEmpty()) {
                recipe.addStep(step);
                stepNum++;
            }
        }

        if (manager.addRecipe(recipe)) {
            System.out.println("Recipe added.");
        } else {
            System.out.println("A recipe with that title already exists.");
        }
    }

    private static void listAllUI(RecipeManager manager) {
        List<Recipe> all = manager.listAll();
        if (all.isEmpty()) {
            System.out.println("No recipes stored.");
            return;
        }
        System.out.println("\nRecipes:");
        for (Recipe r : all) {
            System.out.println("- " + r.getTitle());
        }
    }

    private static void viewRecipeUI(Scanner sc, RecipeManager manager) {
        System.out.print("Enter recipe title: ");
        String title = sc.nextLine().trim();
        Recipe r = manager.getRecipe(title);
        if (r == null) {
            System.out.println("Recipe not found.");
        } else {
            System.out.println("\n" + r);
        }
    }

    private static void findByCategoryUI(Scanner sc, RecipeManager manager) {
        System.out.print("Enter category: ");
        String cat = sc.nextLine().trim();
        List<Recipe> found = manager.findByCategory(cat);
        if (found.isEmpty()) {
            System.out.println("No recipes found for category '" + cat + "'.");
        } else {
            System.out.println("\nRecipes in '" + cat + "':");
            for (Recipe r : found) {
                System.out.println("- " + r.getTitle());
            }
        }
    }

    private static void deleteRecipeUI(Scanner sc, RecipeManager manager) {
        System.out.print("Enter recipe title to delete: ");
        String title = sc.nextLine().trim();
        if (manager.removeRecipe(title)) {
            System.out.println("Recipe removed.");
        } else {
            System.out.println("Recipe not found.");
        }
    }
}
```