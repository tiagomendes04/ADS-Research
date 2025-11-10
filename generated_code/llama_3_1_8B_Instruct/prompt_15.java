```java
// Menu.java
public class Menu {
    private String name;
    private double price;
    private boolean isVegetarian;

    public Menu(String name, double price, boolean isVegetarian) {
        this.name = name;
        this.price = price;
        this.isVegetarian = isVegetarian;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    @Override
    public String toString() {
        return name + " - $" + price;
    }
}

// MenuItem.java
public class MenuItem extends Menu {
    private String description;

    public MenuItem(String name, double price, boolean isVegetarian, String description) {
        super(name, price, isVegetarian);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return super.toString() + " - " + description;
    }
}

// Order.java
import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Menu> items;

    public Order() {
        this.items = new ArrayList<>();
    }

    public void addItem(Menu item) {
        items.add(item);
    }

    public void removeItem(Menu item) {
        items.remove(item);
    }

    public double calculateTotal() {
        double total = 0;
        for (Menu item : items) {
            total += item.getPrice();
        }
        return total;
    }

    public List<Menu> getItems() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder order = new StringBuilder("Order:");
        for (Menu item : items) {
            order.append("\n- ").append(item.toString());
        }
        return order.toString();
    }
}

// Restaurant.java
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private List<MenuItem> menuItems;
    private List<Order> orders;

    public Restaurant() {
        this.menuItems = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public void addItem(MenuItem item) {
        menuItems.add(item);
    }

    public void placeOrder(Order order) {
        orders.add(order);
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void displayMenu() {
        System.out.println("Menu:");
        for (MenuItem item : menuItems) {
            System.out.println(item.toString());
        }
    }

    public void displayOrders() {
        System.out.println("Orders:");
        for (Order order : orders) {
            System.out.println(order.toString());
        }
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();

        // Add menu items
        MenuItem menuItem1 = new MenuItem("Burger", 10.99, false, "Beef patty, lettuce, tomato, cheese, onion, and pickles");
        MenuItem menuItem2 = new MenuItem("Salad", 8.99, true, "Mixed greens, cherry tomatoes, cucumber, carrots, and balsamic vinaigrette");
        MenuItem menuItem3 = new MenuItem("Fries", 3.99, false, "French fries, crispy and golden");

        restaurant.addItem(menuItem1);
        restaurant.addItem(menuItem2);
        restaurant.addItem(menuItem3);

        // Display menu
        restaurant.displayMenu();

        // Place order
        Order order = new Order();
        order.addItem(menuItem1);
        order.addItem(menuItem2);

        restaurant.placeOrder(order);

        // Display orders
        restaurant.displayOrders();

        // Calculate and display total
        System.out.println("\nTotal: $" + restaurant.getOrders().get(0).calculateTotal());
    }
}
```

```java
// CustomizableOrder.java
import java.util.ArrayList;
import java.util.List;

public class CustomizableOrder extends Order {
    private List<String> customizations;

    public CustomizableOrder() {
        super();
        this.customizations = new ArrayList<>();
    }

    public void addCustomization(String customization) {
        customizations.add(customization);
    }

    public void removeCustomization(String customization) {
        customizations.remove(customization);
    }

    public List<String> getCustomizations() {
        return customizations;
    }

    @Override
    public String toString() {
        StringBuilder order = new StringBuilder(super.toString());
        for (String customization : customizations) {
            order.append("\n  - ").append(customization);
        }
        return order.toString();
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();

        // Add menu items
        MenuItem menuItem1 = new MenuItem("Burger", 10.99, false, "Beef patty, lettuce, tomato, cheese, onion, and pickles");
        MenuItem menuItem2 = new MenuItem("Salad", 8.99, true, "Mixed greens, cherry tomatoes, cucumber, carrots, and balsamic vinaigrette");
        MenuItem menuItem3 = new MenuItem("Fries", 3.99, false, "French fries, crispy and golden");

        restaurant.addItem(menuItem1);
        restaurant.addItem(menuItem2);
        restaurant.addItem(menuItem3);

        // Display menu
        restaurant.displayMenu();

        // Place customizable order
        CustomizableOrder order = new CustomizableOrder();
        order.addItem(menuItem1);
        order.addItem(menuItem2);
        order.addCustomization("Extra cheese");
        order.addCustomization("No pickles");

        restaurant.placeOrder(order);

        // Display order
        System.out.println(order.toString());

        // Calculate and display total
        System.out.println("\nTotal: $" + order.calculateTotal());
    }
}
```