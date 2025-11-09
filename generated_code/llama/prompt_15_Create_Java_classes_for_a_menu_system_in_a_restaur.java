**Menu.java**
```java
public class Menu {
    private String name;
    private double price;
    private String description;

    public Menu(String name, double price, String description) {
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
```

**Order.java**
```java
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
```

**MenuItem.java**
```java
public class MenuItem extends Menu {
    private String category;

    public MenuItem(String name, double price, String description, String category) {
        super(name, price, description);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
```

**Drink.java**
```java
public class Drink extends MenuItem {
    private String size;

    public Drink(String name, double price, String description, String category, String size) {
        super(name, price, description, category);
        this.size = size;
    }

    public String getSize() {
        return size;
    }
}
```

**Food.java**
```java
public class Food extends MenuItem {
    private String type;

    public Food(String name, double price, String description, String category, String type) {
        super(name, price, description, category);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
```

**Restaurant.java**
```java
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private List<Order> orders;

    public Restaurant() {
        this.orders = new ArrayList<>();
    }

    public void placeOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void clearOrders() {
        orders.clear();
    }
}
```

**Main.java**
```java
public class Main {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();

        Order order = new Order();
        order.addItem(new Food("Burger", 10.99, "Juicy beef burger", "Burgers", "Beef"));
        order.addItem(new Drink("Coke", 2.99, "Cold Coke", "Drinks", "Large"));

        restaurant.placeOrder(order);

        System.out.println("Order Total: $" + order.getTotal());
        System.out.println("Order Items:");
        for (Menu item : order.getItems()) {
            System.out.println(item.getName() + " - $" + item.getPrice());
        }
    }
}
```