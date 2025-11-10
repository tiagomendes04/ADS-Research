```java
import java.util.*;
import java.util.stream.*;

class Product {
    private final String name;
    private final String category;
    private final double price;

    public Product(String name, String category, double price) {
        this.name = Objects.requireNonNull(name);
        this.category = Objects.requireNonNull(category);
        this.price = price;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return String.format("Product{name='%s', category='%s', price=%.2f}", name, category, price);
    }
}

enum SortField { NAME, PRICE, CATEGORY }

enum SortOrder { ASC, DESC }

class ProductRepository {
    private final List<Product> products = new ArrayList<>();

    public void add(Product p) { products.add(p); }

    public List<Product> getAll() { return Collections.unmodifiableList(products); }
}

class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> filterByName(String keyword) {
        return repo.getAll().stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Product> filterByCategory(String category) {
        return repo.getAll().stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<Product> filterByPriceRange(double min, double max) {
        return repo.getAll().stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
    }

    public List<Product> sort(List<Product> list, SortField field, SortOrder order) {
        Comparator<Product> comparator;
        switch (field) {
            case NAME:
                comparator = Comparator.comparing(Product::getName, String.CASE_INSENSITIVE_ORDER);
                break;
            case PRICE:
                comparator = Comparator.comparingDouble(Product::getPrice);
                break;
            case CATEGORY:
                comparator = Comparator.comparing(Product::getCategory, String.CASE_INSENSITIVE_ORDER);
                break;
            default:
                throw new IllegalArgumentException("Unsupported sort field");
        }
        if (order == SortOrder.DESC) {
            comparator = comparator.reversed();
        }
        return list.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<Product> getAllSorted(SortField field, SortOrder order) {
        return sort(repo.getAll(), field, order);
    }
}

public class ProductApp {
    public static void main(String[] args) {
        ProductRepository repo = new ProductRepository();
        repo.add(new Product("Apple iPhone 15", "Electronics", 999.99));
        repo.add(new Product("Samsung Galaxy S24", "Electronics", 899.99));
        repo.add(new Product("Sony Headphones", "Audio", 199.99));
        repo.add(new Product("Dell XPS 13", "Computers", 1199.00));
        repo.add(new Product("Logitech Mouse", "Accessories", 49.99));
        repo.add(new Product("Apple MacBook Pro", "Computers", 2399.00));
        repo.add(new Product("Banana", "Food", 0.99));
        repo.add(new Product("Almond Milk", "Food", 3.49));

        ProductService service = new ProductService(repo);

        System.out.println("=== All Products Sorted by Price ASC ===");
        service.getAllSorted(SortField.PRICE, SortOrder.ASC).forEach(System.out::println);

        System.out.println("\n=== Filtered by Category 'Computers' ===");
        service.filterByCategory("Computers").forEach(System.out::println);

        System.out.println("\n=== Filtered by Name containing 'apple' (case‑insensitive) ===");
        service.filterByName("apple").forEach(System.out::println);

        System.out.println("\n=== Filtered by Price Range 50 - 1000 and Sorted by Name DESC ===");
        List<Product> filtered = service.filterByPriceRange(50, 1000);
        List<Product> sorted = service.sort(filtered, SortField.NAME, SortOrder.DESC);
        sorted.forEach(System.out::println);
    }
}
```