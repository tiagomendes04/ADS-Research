```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

class ShoppingCart {
    private List<Product> products;

    public ShoppingCart() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public double getTotalPrice() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }

    public void viewCart() {
        if (products.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Products in your cart:");
            for (Product product : products) {
                System.out.println(product.getName() + " - $" + product.getPrice());
            }
        }
    }
}

class PaymentProcessor {
    public boolean processPayment(double amount) {
        System.out.println("Processing payment of $" + amount);
        // Simulate payment processing
        return true;
    }
}

public class OnlineStore {
    private static List<Product> availableProducts;
    private static ShoppingCart cart;
    private static PaymentProcessor paymentProcessor;

    static {
        availableProducts = new ArrayList<>();
        availableProducts.add(new Product("Laptop", 999.99));
        availableProducts.add(new Product("Smartphone", 499.99));
        availableProducts.add(new Product("Headphones", 199.99));
        cart = new ShoppingCart();
        paymentProcessor = new PaymentProcessor();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nOnline Store Menu:");
            System.out.println("1. View Available Products");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Checkout");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewAvailableProducts();
                    break;
                case 2:
                    addProductToCart(scanner);
                    break;
                case 3:
                    cart.viewCart();
                    break;
                case 4:
                    checkout();
                    break;
                case 5:
                    System.out.println("Exiting the store. Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);

        scanner.close();
    }

    private static void viewAvailableProducts() {
        System.out.println("Available Products:");
        for (int i = 0; i < availableProducts.size(); i++) {
            Product product = availableProducts.get(i);
            System.out.println((i + 1) + ". " + product.getName() + " - $" + product.getPrice());
        }
    }

    private static void addProductToCart(Scanner scanner) {
        System.out.print("Enter the product number to add to cart: ");
        int productNumber = scanner.nextInt();
        if (productNumber > 0 && productNumber <= availableProducts.size()) {
            Product selectedProduct = availableProducts.get(productNumber - 1);
            cart.addProduct(selectedProduct);
            System.out.println(selectedProduct.getName() + " added to cart.");
        } else {
            System.out.println("Invalid product number.");
        }
    }

    private static void checkout() {
        double totalPrice = cart.getTotalPrice();
        if (totalPrice == 0) {
            System.out.println("Your cart is empty. Nothing to checkout.");
        } else {
            System.out.println("Total Price: $" + totalPrice);
            System.out.print("Do you want to proceed with the payment? (yes/no): ");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine();
            if ("yes".equalsIgnoreCase(response)) {
                boolean paymentSuccess = paymentProcessor.processPayment(totalPrice);
                if (paymentSuccess) {
                    System.out.println("Payment successful. Your order has been placed.");
                    cart = new ShoppingCart(); // Clear cart after successful payment
                } else {
                    System.out.println("Payment failed. Please try again later.");
                }
            } else {
                System.out.println("Checkout cancelled.");
            }
        }
    }
}
```