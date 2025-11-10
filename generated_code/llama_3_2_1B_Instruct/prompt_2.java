```java
import java.util.HashMap;
import java.util.Map;

class Product {
    private String id;
    private String name;
    private double price;
    private double discount;

    public Product(String id, String name, double price, double discount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }
}

class Cart {
    private Map<String, Product> products;

    public Cart() {
        this.products = new HashMap<>();
    }

    public void addItem(String id, String name, double price, double discount) {
        products.put(id, new Product(id, name, price, discount));
    }

    public double calculateTotal() {
        double total = 0;
        for (Product product : products.values()) {
            total += product.getPrice() * product.getDiscount();
        }
        return total;
    }

    public void applyDiscount(double discount) {
        for (Product product : products.values()) {
            product.setDiscount(product.getPrice() * (1 - discount / 100));
        }
    }

    public void printCart() {
        System.out.println("Cart Items:");
        for (Product product : products.values()) {
            System.out.println(product.getName() + ": $" + product.getPrice() * product.getDiscount());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Cart cart = new Cart();
        cart.addItem("1", "Product 1", 10.99, 5);
        cart.addItem("2", "Product 2", 19.99, 10);
        cart.applyDiscount(10);
        cart.printCart();
    }
}
```