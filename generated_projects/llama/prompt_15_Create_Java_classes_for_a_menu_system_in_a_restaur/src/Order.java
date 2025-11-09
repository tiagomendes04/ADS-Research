import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Menu> items;
    private double total;

    public Order() {
        this.items = new ArrayList<>();
        this.total = 0;
    }

    public void addItem(Menu item) {
        items.add(item);
        total += item.getPrice();
    }

    public void removeItem(Menu item) {
        items.remove(item);
        total -= item.getPrice();
    }

    public List<Menu> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public void clear() {
        items.clear();
        total = 0;
    }
}