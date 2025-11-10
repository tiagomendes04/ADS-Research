```java
package com.example.restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private Map<String, Item> menu;

    public Menu() {
        menu = new HashMap<>();
        addMenuItems();
    }

    public void addMenuItems() {
        menu.put("Appetizers", new Item("Garlic Bread", 8.99, 1));
        menu.put("Entrees", new Item("Burger", 12.99, 1));
        menu.put("Entrees", new Item("Pizza", 15.99, 1));
        menu.put("Desserts", new Item("Tiramisu", 6.99, 1));
    }

    public void displayMenu() {
        System.out.println("Menu:");
        for (Map.Entry<String, Item> entry : menu.entrySet()) {
            System.out.println(entry.getKey() + " - $" + entry.getValue().getPrice());
        }
    }

    public Item getItem(String name) {
        return menu.get(name);
    }

    public void placeOrder(Scanner scanner) {
        displayMenu();
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine();
        Item item = getItem(itemName);
        if (item != null) {
            System.out.print("Quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();
            item.setQuantity(quantity);
            System.out.println("Order placed successfully!");
        } else {
            System.out.println("Item not found.");
        }
    }
}

class Item {
    private String name;
    private double price;
    private int quantity;

    public Item(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
```

```java
package com.example.restaurant;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        Scanner scanner = new Scanner(System.in);
        menu.placeOrder(scanner);
    }
}
```