```java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Product {
    String id;
    String name;
    double price;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

class Cart {
    Map<String, Product> products;

    public Cart() {
        products = new HashMap<>();
    }

    public void addProduct(String id, String name, double price) {
        products.put(id, new Product(id, name, price));
    }

    public void removeProduct(String id) {
        products.remove(id);
    }

    public double getTotal() {
        double total = 0;
        for (Product product : products.values()) {
            total += product.price;
        }
        return total;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Product product : products.values()) {
            sb.append(product.name).append(" - $").append(product.price).append("\n");
        }
        return sb.toString();
    }
}

class Order {
    String id;
    String customerName;
    Cart cart;

    public Order(String id, String customerName, Cart cart) {
        this.id = id;
        this.customerName = customerName;
        this.cart = cart;
    }

    public void checkout() {
        System.out.println("Order ID: " + id);
        System.out.println("Customer Name: " + customerName);
        System.out.println("Cart: " + cart.toString());
        System.out.println("Total: $" + cart.getTotal());
        System.out.println("Payment: $" + cart.getTotal());
    }
}

class Payment {
    String id;

    public Payment(String id) {
        this.id = id;
    }
}

class OnlineStore {
    String id;
    Cart cart;
    List<Payment> payments;

    public OnlineStore(String id) {
        this.id = id;
        this.cart = new Cart();
        this.payments = new ArrayList<>();
    }

    public void makePayment(Payment payment) {
        payments.add(payment);
    }

    public void displayPayments() {
        for (Payment payment : payments) {
            System.out.println("Payment ID: " + payment.id);
        }
    }

    public void processOrder(Order order) {
        System.out.println("Processing order for " + order.customerName);
        order.checkout();
    }
}

public class Main {
    public static void main(String[] args) {
        OnlineStore onlineStore = new OnlineStore("123");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add product to cart");
            System.out.println("2. View cart");
            System.out.println("3. Remove product from cart");
            System.out.println("4. Check out");
            System.out.println("5. Make payment");
            System.out.println("6. Display payments");
            System.out.println("7. Exit");

            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (option) {
                case 1:
                    System.out.print("Enter product ID: ");
                    String productId = scanner.nextLine();
                    System.out.print("Enter product name: ");
                    String productName = scanner.nextLine();
                    System.out.print("Enter product price: ");
                    double productPrice = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline left-over
                    onlineStore.cart.addProduct(productId, productName, productPrice);
                    break;
                case 2:
                    System.out.println(onlineStore.cart.toString());
                    break;
                case 3:
                    System.out.print("Enter product ID: ");
                    productId = scanner.nextLine();
                    onlineStore.cart.removeProduct(productId);
                    break;
                case 4:
                    if (onlineStore.cart.getTotal() == 0) {
                        System.out.println("Cart is empty");
                    } else {
                        onlineStore.processOrder(new Order("order123", "John Doe", onlineStore.cart));
                    }
                    break;
                case 5:
                    System.out.print("Enter payment ID: ");
                    String paymentId = scanner.nextLine();
                    onlineStore.makePayment(new Payment(paymentId));
                    break;
                case 6:
                    onlineStore.displayPayments();
                    break;
                case 7:
                    System.exit(0);
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
```