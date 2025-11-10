```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product {
    private String name;
    private int price;
    private String category;

    public Product(String name, int price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }
}

public class Main {

    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();

        // Add products
        products.add(new Product("Apple iPhone", 999, "Electronics"));
        products.add(new Product("Samsung TV", 9999, "Electronics"));
        products.add(new Product("Sony Headphones", 99, "Electronics"));
        products.add(new Product("Laptop", 1299, "Electronics"));
        products.add(new Product("Book", 49, "Electronics"));
        products.add(new Product("Tablet", 699, "Electronics"));
        products.add(new Product("Laptop", 1599, "Electronics"));
        products.add(new Product("Headphones", 299, "Electronics"));
        products.add(new Product("TV", 9999, "Electronics"));
        products.add(new Product("Book", 49, "Electronics"));
        products.add(new Product("Tablet", 699, "Electronics"));
        products.add(new Product("Headphones", 299, "Electronics"));
        products.add(new Product("Laptop", 1599, "Electronics"));

        // Sort by price
        products.sort(Comparator.comparingInt(P.product -> P.price));

        // Filter by price
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getPrice() < 1000) {
                filteredProducts.add(product);
            }
        }

        // Filter by name
        filteredProducts = products.stream()
                .filter(p -> p.getName().equals("Laptop"))
                .toList();

        // Filter by category
        Map<String, List<Product>> categories = new HashMap<>();
        categories.put("Electronics", new ArrayList<>());
        categories.get("Electronics").add(products);
        filteredProducts = categories.get("Electronics").stream()
                .filter(p -> p.getName().equals("Headphones"))
                .toList();

        // Print results
        System.out.println("Sorted by price:");
        for (Product product : products) {
            System.out.println(product);
        }
        System.out.println("\nFiltered by price:");
        for (Product product : filteredProducts) {
            System.out.println(product);
        }
        System.out.println("\nFiltered by name:");
        for (Product product : filteredProducts) {
            System.out.println(product);
        }
        System.out.println("\nFiltered by category");
        for (Map.Entry<String, List<Product>> entry : categories.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
```