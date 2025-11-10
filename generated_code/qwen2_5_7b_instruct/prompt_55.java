```java
import java.util.Scanner;

class Product {
    String name;
    double price;

    Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class ShoppingCart {
    private Product[] products;
    private int size;

    ShoppingCart(int capacity) {
        products = new Product[capacity];
        size = 0;
    }

    void addProduct(Product product) {
        if (size < products.length) {
            products[size++] = product;
        } else {
            System.out.println("Shopping cart is full.");
        }
    }

    double calculateTotal() {
        double total = 0;
        for (int i = 0; i < size; i++) {
            total += products[i].price;
        }
        return total;
    }
}

class Checkout {
    ShoppingCart cart;

    Checkout(ShoppingCart cart) {
        this.cart = cart;
    }

    void checkout() {
        double total = cart.calculateTotal();
        System.out.println("Total amount: $" + total);
        pay(total);
    }

    private void pay(double amount) {
        System.out.println("Payment of $" + amount + " is being processed...");
        // Simulate payment processing
        System.out.println("Payment successful!");
    }
}

public class OnlineStore {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize products
        Product apple = new Product("Apple", 1.0);
        Product banana = new Product("Banana", 0.5);
        Product orange = new Product("Orange", 1.2);

        // Initialize shopping cart
        ShoppingCart cart = new ShoppingCart(5);

        // Add products to cart
        cart.addProduct(apple);
        cart.addProduct(banana);
        cart.addProduct(orange);

        // Initialize checkout
        Checkout checkout = new Checkout(cart);

        // Proceed to checkout
        checkout.checkout();

        scanner.close();
    }
}
```