```java
// Product.java
package com.example.productapp;

import java.util.Objects;

public class Product {
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

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
               "name='" + name + '\'' +
               ", price=" + String.format("%.2f", price) +
               ", category='" + category + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
               Objects.equals(name, product.name) &&
               Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, category);
    }
}
```

```java
// ProductManager.java
package com.example.productapp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductManager {
    private List<Product> products;

    public ProductManager() {
        this.products = new ArrayList<>();
    }

    public ProductManager(List<Product> initialProducts) {
        this.products = new ArrayList<>(initialProducts);
    }

    public void addProduct(Product product) {
        if (product != null) {
            this.products.add(product);
        }
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(this.products); // Return a copy to prevent external modification
    }

    /**
     * Sorts the internal list of products by price.
     *
     * @param ascending true for ascending order, false for descending.
     */
    public void sortByPrice(boolean ascending) {
        Comparator<Product> comparator = Comparator.comparingDouble(Product::getPrice);
        if (!ascending) {
            comparator = comparator.reversed();
        }
        products.sort(comparator);
    }

    /**
     * Sorts the internal list of products by name.
     *
     * @param ascending true for ascending order, false for descending.
     */
    public void sortByName(boolean ascending) {
        Comparator<Product> comparator = Comparator.comparing(Product::getName, String.CASE_INSENSITIVE_ORDER);
        if (!ascending) {
            comparator = comparator.reversed();
        }
        products.sort(comparator);
    }

    /**
     * Sorts the internal list of products by category.
     *
     * @param ascending true for ascending order, false for descending.
     */
    public void sortByCategory(boolean ascending) {
        Comparator<Product> comparator = Comparator.comparing(Product::getCategory, String.CASE_INSENSITIVE_ORDER);
        if (!ascending) {
            comparator = comparator.reversed();
        }
        products.sort(comparator);
    }

    /**
     * Filters products by a minimum price.
     *
     * @param minPrice The minimum price.
     * @return A new list of products meeting the criteria.
     */
    public List<Product> filterByMinPrice(double minPrice) {
        return products.stream()
                       .filter(p -> p.getPrice() >= minPrice)
                       .collect(Collectors.toList());
    }

    /**
     * Filters products by a maximum price.
     *
     * @param maxPrice The maximum price.
     * @return A new list of products meeting the criteria.
     */
    public List<Product> filterByMaxPrice(double maxPrice) {
        return products.stream()
                       .filter(p -> p.getPrice() <= maxPrice)
                       .collect(Collectors.toList());
    }

    /**
     * Filters products by a specific category (case-insensitive).
     *
     * @param category The category to filter by.
     * @return A new list of products meeting the criteria.
     */
    public List<Product> filterByCategory(String category) {
        return products.stream()
                       .filter(p -> p.getCategory().equalsIgnoreCase(category))
                       .collect(Collectors.toList());
    }

    /**
     * Filters products by a keyword in their name (case-insensitive).
     *
     * @param keyword The keyword to search for in product names.
     * @return A new list of products meeting the criteria.
     */
    public List<Product> filterByNameKeyword(String keyword) {
        return products.stream()
                       .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                       .collect(Collectors.toList());
    }
}
```

```java
// Main.java
package com.example.productapp;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Initialize ProductManager with some sample products
        ProductManager manager = new ProductManager(Arrays.asList(
                new Product("Laptop Pro", 1200.00, "Electronics"),
                new Product("Keyboard RGB", 75.50, "Peripherals"),
                new Product("Mouse Wireless", 30.00, "Peripherals"),
                new Product("Smartphone X", 850.00, "Electronics"),
                new Product("Monitor 27 inch", 350.00, "Electronics"),
                new Product("Desk Chair Ergonomic", 250.00, "Furniture"),
                new Product("Webcam HD", 60.00, "Peripherals"),
                new Product("Gaming PC Ultra", 2500.00, "Electronics"),
                new Product("Printer Laser", 180.00, "Office"),
                new Product("Notebook Basic", 15.00, "Office")
        ));

        printProducts("--- Initial Product List ---", manager.getAllProducts());

        // --- Sorting Examples ---
        System.out.println("\n--- Sorting by Price (Ascending) ---");
        manager.sortByPrice(true);
        printProducts("Sorted by Price (Asc):", manager.getAllProducts());

        System.out.println("\n--- Sorting by Price (Descending) ---");
        manager.sortByPrice(false);
        printProducts("Sorted by Price (Desc):", manager.getAllProducts());

        System.out.println("\n--- Sorting by Name (Ascending) ---");
        manager.sortByName(true);
        printProducts("Sorted by Name (Asc):", manager.getAllProducts());

        System.out.println("\n--- Sorting by Category (Descending) ---");
        manager.sortByCategory(false);
        printProducts("Sorted by Category (Desc):", manager.getAllProducts());

        // Reset to initial state for filtering demonstrations (optional, but good for clarity)
        manager = new ProductManager(Arrays.asList(
                new Product("Laptop Pro", 1200.00, "Electronics"),
                new Product("Keyboard RGB", 75.50, "Peripherals"),
                new Product("Mouse Wireless", 30.00, "Peripherals"),
                new Product("Smartphone X", 850.00, "Electronics"),
                new Product("Monitor 27 inch", 350.00, "Electronics"),
                new Product("Desk Chair Ergonomic", 250.00, "Furniture"),
                new Product("Webcam HD", 60.00, "Peripherals"),
                new Product("Gaming PC Ultra", 2500.00, "Electronics"),
                new Product("Printer Laser", 180.00, "Office"),
                new Product("Notebook Basic", 15.00, "Office")
        ));

        // --- Filtering Examples ---
        System.out.println("\n--- Filtering by Category: 'Electronics' ---");
        List<Product> electronics = manager.filterByCategory("Electronics");
        printProducts("Electronics Products:", electronics);

        System.out.println("\n--- Filtering by Minimum Price: $100.00 ---");
        List<Product> expensiveProducts = manager.filterByMinPrice(100.00);
        printProducts("Products >= $100.00:", expensiveProducts);

        System.out.println("\n--- Filtering by Maximum Price: $200.00 ---");
        List<Product> cheapProducts = manager.filterByMaxPrice(200.00);
        printProducts("Products <= $200.00:", cheapProducts);

        System.out.println("\n--- Filtering by Name Keyword: 'pro' ---");
        List<Product> productsWithPro = manager.filterByNameKeyword("pro");
        printProducts("Products with 'pro' in name:", productsWithPro);

        // --- Chaining Filters ---
        System.out.println("\n--- Chaining Filters: Electronics between $500 and $1500 ---");
        List<Product> filteredAndChained = manager.filterByCategory("Electronics").stream()
                .filter(p -> p.getPrice() >= 500.00)
                .filter(p -> p.getPrice() <= 1500.00)
                .collect(Collectors.toList());
        printProducts("Electronics $500-$1500:", filteredAndChained);

        System.out.println("\n--- Chaining Filters: Peripherals with price < $100, then sorted by name ---");
        List<Product> peripheralCheapSorted = manager.filterByCategory("Peripherals").stream()
                .filter(p -> p.getPrice() < 100.00)
                .sorted(Comparator.comparing(Product::getName))
                .collect(Collectors.toList());
        printProducts("Cheap Peripherals (Sorted by Name):", peripheralCheapSorted);
    }

    private static void printProducts(String title, List<Product> products) {
        System.out.println(title);
        if (products.isEmpty()) {
            System.out.println("  No products found.");
        } else {
            products.forEach(product -> System.out.println("  " + product));
        }
    }
}
```