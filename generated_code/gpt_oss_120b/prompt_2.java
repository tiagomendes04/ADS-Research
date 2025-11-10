```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Item {
    private final String name;
    private final double unitPrice;
    private int quantity;

    public Item(String name, double unitPrice, int quantity) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public double getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }
    public double getTotalPrice() { return unitPrice * quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}

class Cart {
    private final List<Item> items = new ArrayList<>();
    private double discountRate = 0.0; // e.g., 0.10 for 10%

    public void addItem(Item item) {
        for (Item i : items) {
            if (i.getName().equalsIgnoreCase(item.getName())) {
                i.setQuantity(i.getQuantity() + item.getQuantity());
                return;
            }
        }
        items.add(item);
    }

    public double getSubtotal() {
        double sum = 0.0;
        for (Item i : items) {
            sum += i.getTotalPrice();
        }
        return sum;
    }

    public void setDiscountRate(double rate) {
        if (rate < 0) rate = 0;
        if (rate > 1) rate = 1;
        this.discountRate = rate;
    }

    public double getDiscountAmount() {
        return getSubtotal() * discountRate;
    }

    public double getTotal() {
        return getSubtotal() - getDiscountAmount();
    }

    public void displayCart() {
        System.out.println("\n--- Cart Contents ---");
        for (Item i : items) {
            System.out.printf("%s x%d @ $%.2f each = $%.2f%n",
                    i.getName(), i.getQuantity(), i.getUnitPrice(), i.getTotalPrice());
        }
        System.out.printf("Subtotal: $%.2f%n", getSubtotal());
        System.out.printf("Discount (%.0f%%): -$%.2f%n", discountRate * 100, getDiscountAmount());
        System.out.printf("Total: $%.2f%n", getTotal());
    }
}

public class ECommerceCart {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Cart cart = new Cart();

    private static void showMenu() {
        System.out.println("\n=== E-Commerce Cart ===");
        System.out.println("1. Add item");
        System.out.println("2. Apply discount");
        System.out.println("3. View cart");
        System.out.println("4. Checkout (exit)");
        System.out.print("Select an option: ");
    }

    private static void addItem() {
        System.out.print("Item name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Unit price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Quantity: ");
        int qty = Integer.parseInt(scanner.nextLine());
        cart.addItem(new Item(name, price, qty));
        System.out.println("Item added.");
    }

    private static void applyDiscount() {
        System.out.print("Enter discount percentage (e.g., 10 for 10%): ");
        double perc = Double.parseDouble(scanner.nextLine());
        cart.setDiscountRate(perc / 100.0);
        System.out.println("Discount applied.");
    }

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addItem();
                case "2" -> applyDiscount();
                case "3" -> cart.displayCart();
                case "4" -> {
                    cart.displayCart();
                    System.out.println("\nThank you for shopping!");
                    running = false;
                }
                default -> System.out.println("Invalid option.");
            }
        }
        scanner.close();
    }
}
```