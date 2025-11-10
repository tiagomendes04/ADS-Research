```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EcommerceCart {

    private Map<String, Integer> items;
    private double total;

    public EcommerceCart() {
        items = new HashMap<>();
        total = 0;
    }

    public void addItem(String itemName, int quantity) {
        items.put(itemName, items.getOrDefault(itemName, 0) + quantity);
        total += quantity * calculateItemPrice(itemName);
    }

    public void removeItem(String itemName) {
        if (items.containsKey(itemName)) {
            int quantity = items.get(itemName);
            items.remove(itemName);
            total -= quantity * calculateItemPrice(itemName);
        }
    }

    public double calculateItemPrice(String itemName) {
        return 10.0; 
    }

    public double getTotal() {
        return total;
    }

    public void applyDiscount(double discountPercentage) {
        total *= (1 - discountPercentage / 100);
    }

    public static void main(String[] args) {
        EcommerceCart cart = new EcommerceCart();

        cart.addItem("Apple", 2);
        cart.addItem("Banana", 1);
        cart.addItem("Orange", 3);

        System.out.println("Cart Items: " + cart.items);
        System.out.println("Total: $" + cart.getTotal());

        cart.applyDiscount(10);

        System.out.println("Total after discount: $" + cart.getTotal());
    }
}
```