```java
import java.util.ArrayList;
import java.util.List;

class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

class Inventory {
    private List<Product> products;

    public Inventory() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(String productName) {
        products.removeIf(product -> product.getName().equals(productName));
    }

    public void updateQuantity(String productName, int newQuantity) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                product.setQuantity(newQuantity);
                break;
            }
        }
    }

    public void checkStock() {
        for (Product product : products) {
            if (product.getQuantity() <= 10) {
                System.out.println("Low stock alert: " + product.getName());
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        inventory.addProduct(new Product("Apple", 1.99, 20));
        inventory.addProduct(new Product("Banana", 0.99, 30));
        inventory.addProduct(new Product("Orange", 1.49, 10));

        inventory.updateQuantity("Banana", 5);

        inventory.checkStock();
    }
}
```