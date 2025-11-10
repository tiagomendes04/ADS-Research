```java
import java.util.ArrayList;
import java.util.List;

class MenuItem {
    private String name;
    private double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class Order {
    private List<MenuItem> items;

    public Order() {
        this.items = new ArrayList<>();
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public double getTotalPrice() {
        double total = 0.0;
        for (MenuItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (MenuItem item : items) {
            sb.append(item.getName()).append(" - $").append(item.getPrice()).append("\n");
        }
        sb.append("Total: $").append(getTotalPrice());
        return sb.toString();
    }
}

class MenuSystem {
    private List<MenuItem> menuItems;

    public MenuSystem() {
        this.menuItems = new ArrayList<>();
    }

    public void addMenuItem(MenuItem item) {
        menuItems.add(item);
    }

    public void printMenu() {
        for (MenuItem item : menuItems) {
            System.out.println(item.getName() + " - $" + item.getPrice());
        }
    }

    public Order createOrder() {
        Order order = new Order();
        System.out.println("Enter the item numbers you want to order (0 to finish):");
        Scanner scanner = new Scanner(System.in);
        int itemNumber;
        while (true) {
            itemNumber = scanner.nextInt();
            if (itemNumber == 0) {
                break;
            }
            if (itemNumber > 0 && itemNumber <= menuItems.size()) {
                order.addItem(menuItems.get(itemNumber - 1));
            } else {
                System.out.println("Invalid item number. Please try again.");
            }
        }
        scanner.close();
        return order;
    }
}

import java.util.Scanner;

public class RestaurantApp {
    public static void main(String[] args) {
        MenuSystem menuSystem = new MenuSystem();
        menuSystem.addMenuItem(new MenuItem("Pizza", 12.99));
        menuSystem.addMenuItem(new MenuItem("Burger", 8.99));
        menuSystem.addMenuItem(new MenuItem("Salad", 5.99));
        menuSystem.printMenu();
        Order order = menuSystem.createOrder();
        System.out.println(order);
    }
}
```