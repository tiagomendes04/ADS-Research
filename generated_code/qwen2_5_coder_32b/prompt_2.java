```java
import java.util.ArrayList;
import java.util.List;

class Item {
    String name;
    double price;
    int quantity;

    public Item(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }
}

class ShoppingCart {
    List<Item> items;
    double discountRate;

    public ShoppingCart(double discountRate) {
        this.items = new ArrayList<>();
        this.discountRate = discountRate;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public double calculateTotal() {
        double total = 0;
        for (Item item : items) {
            total += item.getTotalPrice();
        }
        return total * (1 - discountRate);
    }

    public void applyDiscount(double discountRate) {
        this.discountRate = discountRate;
    }
}

public class ECommerceCart {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart(0.05); // 5% discount rate

        cart.addItem(new Item("Laptop", 999.99, 1));
        cart.addItem(new Item("Smartphone", 499.99, 2));
        cart.addItem(new Item("Headphones", 199.99, 1));

        System.out.println("Total after discount: $" + cart.calculateTotal());

        cart.applyDiscount(0.10); // Apply 10% discount rate
        System.out.println("Total after 10% discount: $" + cart.calculateTotal());
    }
}
```