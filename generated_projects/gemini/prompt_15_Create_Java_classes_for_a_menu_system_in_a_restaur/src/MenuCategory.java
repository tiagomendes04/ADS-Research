// MenuCategory class
import java.util.ArrayList;
import java.util.List;

public class MenuCategory {
    private String name;
    private List<MenuItem> items;

    public MenuCategory(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addItem(MenuItem item) {
        this.items.add(item);
    }

    public List<MenuItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(":\n");
        for (MenuItem item : items) {
            sb.append(item).append("\n");
        }
        return sb.toString();
    }
}