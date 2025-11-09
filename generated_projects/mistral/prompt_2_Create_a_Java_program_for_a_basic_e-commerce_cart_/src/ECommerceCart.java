import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class CartItem {
    private String name;
    private double price;
    private int quantity;

    public CartItem(String name, double price, int quantity) {
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

    public double getTotal() {
        return price * quantity;
    }
}

class ShoppingCart {
    private List<CartItem> items;
    private double discountRate;

    public ShoppingCart() {
        items = new ArrayList<>();
        discountRate = 0.0;
    }

    public void addItem(String name, double price, int quantity) {
        items.add(new CartItem(name, price, quantity));
    }

    public void applyDiscount(double rate) {
        if (rate >= 0 && rate <= 1.0) {
            discountRate = rate;
        }
    }

    public double calculateTotal() {
        double subtotal = 0.0;
        for (CartItem item : items) {
            subtotal += item.getTotal();
        }
        return subtotal * (1 - discountRate);
    }

    public void displayCart() {
        System.out.println("Shopping Cart:");
        for (CartItem item : items) {
            System.out.printf("%s - $%.2f x %d = $%.2f%n",
                item.getName(), item.getPrice(), item.getQuantity(), item.getTotal());
        }
        System.out.printf("Subtotal: $%.2f%n", calculateTotalWithoutDiscount());
        System.out.printf("Discount: %.0f%%%n", discountRate * 100);
        System.out.printf("Total: $%.2f%n", calculateTotal());
    }

    private double calculateTotalWithoutDiscount() {
        double subtotal = 0.0;
        for (CartItem item : items) {
            subtotal += item.getTotal();
        }
        return subtotal;
    }
}

public class ECommerceCart {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ShoppingCart cart = new ShoppingCart();

        while (true) {
            System.out.println("\nE-Commerce Cart Menu:");
            System.out.println("1. Add Item");
            System.out.println("2. Apply Discount");
            System.out.println("3. View Cart");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter item name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter item price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter item quantity: ");
                    int quantity = scanner.nextInt();
                    cart.addItem(name, price, quantity);
                    break;
                case 2:
                    System.out.print("Enter discount rate (0-1.0): ");
                    double rate = scanner.nextDouble();
                    cart.applyDiscount(rate);
                    break;
                case 3:
                    cart.displayCart();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}