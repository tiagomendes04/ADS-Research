import java.util.ArrayList;
import java.util.List;

public class RestaurantApp {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.addItem(new MenuItem("Burger", 8.99, "Beef patty with lettuce and tomato"));
        menu.addItem(new MenuItem("Pizza", 12.99, "Cheese pizza with pepperoni"));
        menu.addItem(new MenuItem("Salad", 6.99, "Mixed greens with dressing"));

        Order order = new Order();
        order.addItem(menu.getItem("Burger"), 2);
        order.addItem(menu.getItem("Pizza"), 1);
        order.addItem(menu.getItem("Salad"), 1);

        System.out.println("Order Summary:");
        System.out.println(order.getSummary());
        System.out.println("Total: $" + order.getTotal());
    }
}

class Menu {
    private List<MenuItem> items;

    public Menu() {
        this.items = new ArrayList<>();
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public MenuItem getItem(String name) {
        for (MenuItem item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public List<MenuItem> getAllItems() {
        return new ArrayList<>(items);
    }
}

class MenuItem {
    private String name;
    private double price;
    private String description;

    public MenuItem(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}

class Order {
    private List<OrderItem> items;

    public Order() {
        this.items = new ArrayList<>();
    }

    public void addItem(MenuItem menuItem, int quantity) {
        items.add(new OrderItem(menuItem, quantity));
    }

    public String getSummary() {
        StringBuilder summary = new StringBuilder();
        for (OrderItem item : items) {
            summary.append(item.getQuantity()).append("x ").append(item.getMenuItem().getName())
                   .append(" - $").append(item.getTotalPrice()).append("\n");
        }
        return summary.toString();
    }

    public double getTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }
}

class OrderItem {
    private MenuItem menuItem;
    private int quantity;

    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return menuItem.getPrice() * quantity;
    }
}