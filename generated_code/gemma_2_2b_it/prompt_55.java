```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class OnlineStore {

    static Map<String, Product> products = new HashMap<>();
    static List<CartItem> cart = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    static {
        products.put("Laptop", new Product("Laptop", 1200.00));
        products.put("Mouse", new Product("Mouse", 25.00));
        products.put("Keyboard", new Product("Keyboard", 75.00));
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nOnline Store");
            System.out.println("1. View Products");
            System.out.println("2. Add to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Checkout");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    addToCart();
                    break;
                case 3:
                    viewCart();
                    break;
                case 4:
                    checkout();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void viewProducts() {
        for (Map.Entry<String, Product> entry : products.entrySet()) {
            System.out.println(entry.getKey() + ": $" + entry.getValue().getPrice());
        }
    }

    static void addToCart() {
        System.out.print("Enter product name: ");
        String productName = scanner.nextLine();

        if (products.containsKey(productName)) {
            Product product = products.get(productName);
            CartItem item = new CartItem(product);
            cart.add(item);
            System.out.println("Added to cart!");
        } else {
            System.out.println("Product not found!");
        }
    }

    static void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }
        System.out.println("\nYour Cart:");
        for (CartItem item : cart) {
            System.out.println(item);
        }
    }

    static void checkout() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty!");
            return;
        }
        System.out.println("\nCheckout:");
        System.out.println("Total: $" + calculateTotal());
        System.out.println("----------------------");
        System.out.println("Thank you for shopping!");
    }

    static double calculateTotal() {
        double total = 0;
        for (CartItem item : cart) {
            total += item.getPrice();
        }
        return total;
    }
}

class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
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

class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
```