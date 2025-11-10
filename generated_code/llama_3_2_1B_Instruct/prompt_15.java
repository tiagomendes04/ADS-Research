```java
public class MenuItem {
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

public class Order {
    private String name;
    private List<MenuItem> items;

    public Order(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public String getName() {
        return name;
    }

    public List<MenuItem> getItems() {
        return items;
    }
}

public class Menu {
    private Map<String, MenuItem> menuItems;

    public Menu() {
        this.menuItems = new HashMap<>();
    }

    public void addItem(MenuItem item) {
        menuItems.put(item.getName(), item);
    }

    public MenuItem getMenuItem(String name) {
        return menuItems.get(name);
    }
}

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();

        MenuItem burger = new MenuItem("Burger", 10.99, "Juicy beef burger with cheese and bacon");
        MenuItem fries = new MenuItem("Fries", 2.99, "Crispy French fries");
        MenuItem salad = new MenuItem("Salad", 5.99, "Mixed greens with vegetables and dressing");

        menu.addItem(burger);
        menu.addItem(fries);
        menu.addItem(salad);

        Order order = new Order("My Order");

        order.addItem(burger);
        order.addItem(salad);

        System.out.println("My Order: " + order.getName());
        for (MenuItem item : order.getItems()) {
            System.out.println("  - " + item.getName());
        }
    }
}
```