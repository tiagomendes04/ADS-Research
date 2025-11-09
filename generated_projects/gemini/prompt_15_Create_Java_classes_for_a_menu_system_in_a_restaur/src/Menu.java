// Menu class
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<MenuCategory> categories;

    public Menu() {
        this.categories = new ArrayList<>();
    }

    public void addCategory(MenuCategory category) {
        this.categories.add(category);
    }

    public List<MenuCategory> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (MenuCategory category : categories) {
            sb.append(category).append("\n");
        }
        return sb.toString();
    }
}