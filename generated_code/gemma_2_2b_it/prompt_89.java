```java
import java.util.*;

public class RecipeSearchEngine {

    private Map<String, List<Recipe>> recipes;

    public RecipeSearchEngine() {
        recipes = new HashMap<>();
    }

    public void addRecipe(Recipe recipe) {
        String name = recipe.getName();
        if (!recipes.containsKey(name)) {
            recipes.put(name, new ArrayList<>());
        }
        recipes.get(name).add(recipe);
    }

    public List<Recipe> search(String query) {
        List<Recipe> matchingRecipes = new ArrayList<>();
        query = query.toLowerCase();
        for (Map.Entry<String, List<Recipe>> entry : recipes.entrySet()) {
            String recipeName = entry.getKey().toLowerCase();
            if (recipeName.contains(query)) {
                matchingRecipes.addAll(entry.getValue());
            }
        }
        return matchingRecipes;
    }

    public List<Recipe> filter(String category, String rating) {
        List<Recipe> filteredRecipes = new ArrayList<>();
        if (category != null) {
            for (Recipe recipe : search(category)) {
                filteredRecipes.add(recipe);
            }
        }
        if (rating != null) {
            for (Recipe recipe : filteredRecipes) {
                if (recipe.getRating() != null && recipe.getRating().contains(rating)) {
                    filteredRecipes.add(recipe);
                }
            }
        }
        return filteredRecipes;
    }

    public static class Recipe {
        private String name;
        private String description;
        private String category;
        private String rating;
        private int ratingCount;
        public Recipe(String name, String description, String category, String rating, int ratingCount) {
            this.name = name;
            this.description = description;
            this.category = category;
            this.rating = rating;
            this.ratingCount = ratingCount;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getCategory() {
            return category;
        }

        public String getRating() {
            return rating;
        }

        public int getRatingCount() {
            return ratingCount;
        }
    }
}
```