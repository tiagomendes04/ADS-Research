```java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Product {
    String name;
    double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class ShoppingCart {
    Map<Product, Integer> items;

    public ShoppingCart() {
        this.items = new HashMap<>();
    }

    public void addItem(Product product, int quantity) {
        if (items.containsKey(product)) {
            items.put(product, items.get(product) + quantity);
        } else {
            items.put(product, quantity);
        }
    }

    public void removeItem(Product product, int quantity) {
        if (items.containsKey(product)) {
            if (items.get(product) <= quantity) {
                items.remove(product);
            } else {
                items.put(product, items.get(product) - quantity);
            }
        }
    }

    public double calculateTotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().price * entry.getValue();
        }
        return total;
    }
}

public class OnlineStore {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ShoppingCart cart = new ShoppingCart();

        while (true) {
            System.out.println("1. Add product to cart");
            System.out.println("2. Remove product from cart");
            System.out.println("3. View cart");
            System.out.println("4. Checkout");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("Enter product name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter product price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline left-over
                    cart.addItem(new Product(name, price), 1);
                    break;
                case 2:
                    System.out.print("Enter product name: ");
                    name = scanner.nextLine();
                    System.out.print("Enter product quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    cart.removeItem(new Product(name, 0), quantity);
                    break;
                case 3:
                    System.out.println("Cart:");
                    for (Map.Entry<Product, Integer> entry : cart.items.entrySet()) {
                        System.out.println(entry.getKey().name + " x " + entry.getValue());
                    }
                    System.out.println("Total: $" + cart.calculateTotal());
                    break;
                case 4:
                    System.out.println("Thank you for shopping with us!");
                    System.out.println("Total: $" + cart.calculateTotal());
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
```