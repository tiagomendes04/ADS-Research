```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Product {
    String name;
    int quantity;
    double price;

    public Product(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

public class WarehouseInventory {
    private List<Product> products;

    public WarehouseInventory() {
        this.products = new ArrayList<>();
    }

    public void addProduct(String name, int quantity, double price) {
        Product product = new Product(name, quantity, price);
        products.add(product);
    }

    public void viewInventory() {
        for (Product product : products) {
            System.out.println("Product: " + product.getName());
            System.out.println("Quantity: " + product.getQuantity());
            System.out.println("Price: $" + String.format("%.2f", product.getPrice()));
            System.out.println();
        }
    }

    public void restockProduct(String name, int quantity) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                product.setQuantity(product.getQuantity() + quantity);
                System.out.println("Product restocked successfully.");
                return;
            }
        }
        System.out.println("Product not found.");
    }

    public void sellProduct(String name, int quantity) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                if (product.getQuantity() >= quantity) {
                    product.setQuantity(product.getQuantity() - quantity);
                    System.out.println("Product sold successfully.");
                } else {
                    System.out.println("Insufficient stock.");
                }
                return;
            }
        }
        System.out.println("Product not found.");
    }

    public static void main(String[] args) {
        WarehouseInventory inventory = new WarehouseInventory();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Product");
            System.out.println("2. View Inventory");
            System.out.println("3. Restock Product");
            System.out.println("4. Sell Product");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (option) {
                case 1:
                    System.out.print("Enter product name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter product quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    System.out.print("Enter product price: $");
                    double price = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline left-over
                    inventory.addProduct(name, quantity, price);
                    break;
                case 2:
                    inventory.viewInventory();
                    break;
                case 3:
                    System.out.print("Enter product name: ");
                    String productName = scanner.nextLine();
                    System.out.print("Enter quantity to restock: ");
                    int restockQuantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    inventory.restockProduct(productName, restockQuantity);
                    break;
                case 4:
                    System.out.print("Enter product name: ");
                    String sellName = scanner.nextLine();
                    System.out.print("Enter quantity to sell: ");
                    int sellQuantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    inventory.sellProduct(sellName, sellQuantity);
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }
}
```