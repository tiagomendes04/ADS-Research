```java
import java.util.*;

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

class Discount {
    String type;
    double percentage;

    public Discount(String type, double percentage) {
        this.type = type;
        this.percentage = percentage;
    }
}

public class ECommerceCart {
    private Map<String, Product> cart;
    private double total;
    private List<Discount> discounts;

    public ECommerceCart() {
        cart = new HashMap<>();
        total = 0.0;
        discounts = new ArrayList<>();
    }

    public void addItem(String name, double price, int quantity) {
        cart.put(name, new Product(name, price, quantity));
        total += price * quantity;
    }

    public void removeItem(String name) {
        if (cart.containsKey(name)) {
            total -= cart.get(name).price * cart.get(name).quantity;
            cart.remove(name);
        }
    }

    public void applyDiscount(String type, double percentage) {
        discounts.add(new Discount(type, percentage));
        for (Product product : cart.values()) {
            total -= product.price * product.quantity * (percentage / 100);
        }
    }

    public void displayCart() {
        System.out.println("Cart:");
        for (Product product : cart.values()) {
            System.out.println(product.name + " x " + product.quantity + " = $" + (product.price * product.quantity));
        }
        System.out.println("Total: $" + total);
    }

    public void calculateSubtotal() {
        double subtotal = 0.0;
        for (Product product : cart.values()) {
            subtotal += product.price * product.quantity;
        }
        System.out.println("Subtotal: $" + subtotal);
    }

    public static void main(String[] args) {
        ECommerceCart cart = new ECommerceCart();
        cart.addItem("Apple", 1.00, 3);
        cart.addItem("Banana", 0.50, 2);
        cart.displayCart();
        cart.applyDiscount("PERCENTAGE", 10.0);
        cart.displayCart();
        cart.calculateSubtotal();
    }
}
```