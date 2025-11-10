```java
import java.util.ArrayList;
import java.util.List;

class Item {
    private String name;
    private double price;
    private int quantity;

    public Item(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}

class ShoppingCart {
    private List<Item> items;
    private double discount;

    public ShoppingCart(double discount) {
        this.items = new ArrayList<>();
        this.discount = discount;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public double calculateTotal() {
        double total = items.stream()
                            .mapToDouble(Item::getTotalPrice)
                            .sum();
        return total * (1 - discount / 100.0);
    }

    public void printCart() {
        for (Item item : items) {
            System.out.println(item.getName() + " - $" + item.getTotalPrice());
        }
        System.out.println("Total: $" + calculateTotal());
    }
}

public class ECommerceCart {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart(10); // 10% discount
        cart.addItem(new Item("Laptop", 1000, 1));
        cart.addItem(new Item("Mouse", 20, 2));
        cart.addItem(new Item("Keyboard", 50, 1));

        cart.printCart();
    }
}
```