```java
import java.util.*;

class Product {
    private final String id;
    private final String name;
    private final double price;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId()   { return id; }
    public String getName() { return name; }
    public double getPrice(){ return price; }

    @Override
    public String toString() {
        return String.format("%s - %s ($%.2f)", id, name, price);
    }
}

class CartItem {
    private final Product product;
    private int quantity;

    public CartItem(Product product, int qty) {
        this.product = product;
        this.quantity = qty;
    }

    public Product getProduct() { return product; }
    public int getQuantity()    { return quantity; }
    public void addQuantity(int qty) { this.quantity += qty; }

    public double getTotal() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return String.format("%s x%d - $%.2f", product.getName(), quantity, getTotal());
    }
}

class ShoppingCart {
    private final Map<String, CartItem> items = new LinkedHashMap<>();

    public void addProduct(Product p, int qty) {
        CartItem ci = items.get(p.getId());
        if (ci == null) {
            items.put(p.getId(), new CartItem(p, qty));
        } else {
            ci.addQuantity(qty);
        }
    }

    public void removeProduct(String productId) {
        items.remove(productId);
    }

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public double getTotal() {
        return items.values().stream().mapToDouble(CartItem::getTotal).sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }
}

class Store {
    private final List<Product> catalog = new ArrayList<>();

    public Store() {
        catalog.add(new Product("P001", "Wireless Mouse", 25.99));
        catalog.add(new Product("P002", "Mechanical Keyboard", 79.49));
        catalog.add(new Product("P003", "HD Monitor 24\"", 149.99));
        catalog.add(new Product("P004", "USB-C Hub", 34.75));
        catalog.add(new Product("P005", "External SSD 1TB", 119.00));
    }

    public List<Product> getCatalog() {
        return Collections.unmodifiableList(catalog);
    }

    public Product findById(String id) {
        return catalog.stream()
                .filter(p -> p.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public boolean processPayment(double amount) {
        // Simulate payment processing (70% success rate)
        return new Random().nextInt(100) < 70;
    }
}

public class OnlineStoreApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Store store = new Store();
    private static final ShoppingCart cart = new ShoppingCart();

    public static void main(String[] args) {
        System.out.println("=== Welcome to the Online Store ===");
        boolean running = true;
        while (running) {
            showMainMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": showCatalog(); break;
                case "2": addToCart(); break;
                case "3": viewCart(); break;
                case "4": checkout(); break;
                case "5": running = false; break;
                default: System.out.println("Invalid option. Try again.");
            }
        }
        System.out.println("Thank you for visiting!");
    }

    private static void showMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1) View Product Catalog");
        System.out.println("2) Add Product to Cart");
        System.out.println("3) View Cart");
        System.out.println("4) Checkout");
        System.out.println("5) Exit");
        System.out.print("Select an option: ");
    }

    private static void showCatalog() {
        System.out.println("\n--- Product Catalog ---");
        for (Product p : store.getCatalog()) {
            System.out.println(p);
        }
    }

    private static void addToCart() {
        System.out.print("\nEnter Product ID to add: ");
        String id = scanner.nextLine().trim();
        Product p = store.findById(id);
        if (p == null) {
            System.out.println("Product not found.");
            return;
        }
        System.out.print("Enter quantity: ");
        int qty;
        try {
            qty = Integer.parseInt(scanner.nextLine().trim());
            if (qty <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity.");
            return;
        }
        cart.addProduct(p, qty);
        System.out.println("Added to cart: " + p.getName() + " x" + qty);
    }

    private static void viewCart() {
        System.out.println("\n--- Shopping Cart ---");
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        for (CartItem ci : cart.getItems()) {
            System.out.println(ci);
        }
        System.out.printf("Total: $%.2f%n", cart.getTotal());
    }

    private static void checkout() {
        if (cart.isEmpty()) {
            System.out.println("\nYour cart is empty. Add items before checkout.");
            return;
        }
        System.out.println("\n--- Checkout ---");
        viewCart();
        System.out.print("Proceed to payment? (y/n): ");
        String ans = scanner.nextLine().trim().toLowerCase();
        if (!ans.equals("y")) {
            System.out.println("Checkout cancelled.");
            return;
        }
        double amount = cart.getTotal();
        System.out.printf("Processing payment of $%.2f...%n", amount);
        boolean success = store.processPayment(amount);
        if (success) {
            System.out.println("Payment successful! Order placed.");
            cart.clear();
        } else {
            System.out.println("Payment failed. Please try again later.");
        }
    }
}
```