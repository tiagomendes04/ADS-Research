```java
import java.util.*;

class Product {
    private String name;
    private double price;
    private String category;

    public Product(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Product A", 10.99, "Electronics"));
        products.add(new Product("Product B", 5.99, "Clothing"));
        products.add(new Product("Product C", 7.99, "Electronics"));
        products.add(new Product("Product D", 12.99, "Clothing"));
        products.add(new Product("Product E", 3.99, "Electronics"));

        System.out.println("Original List:");
        printProducts(products);

        // Sort by price
        Collections.sort(products, Comparator.comparingDouble(Product::getPrice));
        System.out.println("\nSorted by Price:");
        printProducts(products);

        // Filter by category
        List<Product> filteredProducts = products.stream()
                .filter(product -> product.getCategory().equals("Electronics"))
                .toList();
        System.out.println("\nFiltered by Category (Electronics):");
        printProducts(filteredProducts);

        // Filter by name
        filteredProducts = products.stream()
                .filter(product -> product.getName().startsWith("Product"))
                .toList();
        System.out.println("\nFiltered by Name (starts with 'Product'): ");
        printProducts(filteredProducts);
    }

    private static void printProducts(List<Product> products) {
        products.forEach(product -> System.out.println(product.getName() + ", " + product.getPrice() + ", " + product.getCategory()));
    }
}
```