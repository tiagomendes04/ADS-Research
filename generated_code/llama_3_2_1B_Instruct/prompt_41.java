```java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Product {
    String name;
    double price;
    int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}

public class InventorySystem {
    static Map<String, Product> inventory = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Add Product");
            System.out.println("2. Update Stock");
            System.out.println("3. Check Stock");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (option) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    updateStock();
                    break;
                case 3:
                    checkStock();
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }

    static void addProduct() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline left-over
        System.out.print("Enter product quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        inventory.put(name, new Product(name, price, quantity));
        System.out.println("Product added successfully.");
    }

    static void updateStock() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        if (inventory.containsKey(name)) {
            System.out.print("Enter new quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            if (quantity < 0) {
                System.out.println("Quantity cannot be negative.");
            } else {
                inventory.get(name).quantity = quantity;
                System.out.println("Stock updated successfully.");
            }
        } else {
            System.out.println("Product not found in inventory.");
        }
    }

    static void checkStock() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        if (inventory.containsKey(name)) {
            System.out.println("Product: " + inventory.get(name).name);
            System.out.println("Price: $" + inventory.get(name).price);
            System.out.println("Quantity: " + inventory.get(name).quantity);
        } else {
            System.out.println("Product not found in inventory.");
        }
    }
}
```