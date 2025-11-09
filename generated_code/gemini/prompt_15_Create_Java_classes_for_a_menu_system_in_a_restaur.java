```java
// MenuItem class
public class MenuItem {
    private String name;
    private String description;
    private double price;
    private boolean isVegetarian;

    public MenuItem(String name, String description, double price, boolean isVegetarian) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isVegetarian = isVegetarian;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    @Override
    public String toString() {
        return name + " - $" + price + "\n" + description + (isVegetarian ? " (Vegetarian)" : "");
    }
}
```

```java
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
```

```java
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
```

```java
// OrderItem class
public class OrderItem {
    private MenuItem menuItem;
    private int quantity;
    private String customization;

    public OrderItem(MenuItem menuItem, int quantity) {
        this(menuItem, quantity, null);
    }

    public OrderItem(MenuItem menuItem, int quantity, String customization) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.customization = customization;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCustomization() {
        return customization;
    }

    public double getTotalPrice() {
        return menuItem.getPrice() * quantity;
    }

    @Override
    public String toString() {
        String str =  menuItem.getName() + " x " + quantity;
        if (customization != null && !customization.isEmpty()) {
            str += " (Customization: " + customization + ")";
        }
        return str;
    }
}
```

```java
// Order class
import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<OrderItem> items;
    private String customerName;
    private String orderNotes;

    public Order(String customerName) {
        this(customerName, null);
    }

    public Order(String customerName, String orderNotes) {
        this.items = new ArrayList<>();
        this.customerName = customerName;
        this.orderNotes = orderNotes;
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getOrderNotes() {
        return orderNotes;
    }

    public double getTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order for: ").append(customerName).append("\n");
        if (orderNotes != null && !orderNotes.isEmpty()) {
            sb.append("Notes: ").append(orderNotes).append("\n");
        }
        for (OrderItem item : items) {
            sb.append("- ").append(item).append("\n");
        }
        sb.append("Total: $").append(getTotal());
        return sb.toString();
    }
}
```

```java
// Main class (Example Usage)
public class Main {
    public static void main(String[] args) {
        // Create Menu Items
        MenuItem burger = new MenuItem("Burger", "Classic beef burger with lettuce and tomato", 9.99, false);
        MenuItem fries = new MenuItem("Fries", "Crispy golden fries", 3.99, true);
        MenuItem salad = new MenuItem("Salad", "Fresh garden salad with vinaigrette dressing", 7.99, true);

        // Create Menu Categories
        MenuCategory mains = new MenuCategory("Mains");
        mains.addItem(burger);
        mains.addItem(salad);

        MenuCategory sides = new MenuCategory("Sides");
        sides.addItem(fries);

        // Create Menu
        Menu menu = new Menu();
        menu.addCategory(mains);
        menu.addCategory(sides);

        // Print Menu
        System.out.println("Menu:\n" + menu);

        // Create Order
        Order order = new Order("John Doe", "Extra napkins please");
        order.addItem(new OrderItem(burger, 1));
        order.addItem(new OrderItem(fries, 2, "Extra salt"));
        order.addItem(new OrderItem(salad, 1));


        // Print Order
        System.out.println("\nOrder Details:\n" + order);
    }
}
```
