```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ProductManager {

    static class Product {
        String name;
        double price;
        String category;

        public Product(String name, double price, String category) {
            this.name = name;
            this.price = price;
            this.category = category;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Product> products = new ArrayList<>();

        // Add some sample products
        products.add(new Product("Laptop", 1200.0, "Electronics"));
        products.add(new Product("Phone", 500.0, "Electronics"));
        products.add(new Product("Book", 20.0, "Books"));
        products.add(new Product("Pen", 1.0, "Office Supplies"));
        products.add(new Product("Shirt", 30.0, "Clothing"));

        System.out.println("Select sorting criteria:");
        System.out.println("1. Price");
        System.out.println("2. Name");
        System.out.println("3. Category");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                sortProductsByPrice(products);
                break;
            case 2:
                sortProductsByName(products);
                break;
            case 3:
                sortProductsByCategory(products);
                break;
            default:
                System.out.println("Invalid choice.");
        }

        System.out.println("\nProducts:");
        for (Product product : products) {
            System.out.println(product.name + " (" + product.price + ") - " + product.category);
        }
    }

    static void sortProductsByPrice(List<Product> products) {
        Collections.sort(products, Comparator.comparingDouble(Product::getPrice));
    }

    static void sortProductsByName(List<Product> products) {
        Collections.sort(products, Comparator.comparing(Product::getName));
    }

    static void sortProductsByCategory(List<Product> products) {
        Collections.sort(products, Comparator.comparing(Product::getCategory));
    }
}
```