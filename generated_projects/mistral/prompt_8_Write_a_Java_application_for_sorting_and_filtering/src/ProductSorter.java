import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }
}

public class ProductSorter {
    public static void main(String[] args) {
        List<Product> products = Arrays.asList(
                new Product("Laptop", 999.99, "Electronics"),
                new Product("Smartphone", 699.99, "Electronics"),
                new Product("T-Shirt", 19.99, "Clothing"),
                new Product("Jeans", 49.99, "Clothing"),
                new Product("Coffee", 5.99, "Groceries")
        );

        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose sorting option:");
        System.out.println("1. Sort by price");
        System.out.println("2. Sort by name");
        System.out.println("3. Sort by category");
        System.out.print("Enter your choice: ");
        int sortChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter filter category (or leave empty for no filter): ");
        String filterCategory = scanner.nextLine();

        List<Product> filteredProducts = products.stream()
                .filter(p -> filterCategory.isEmpty() || p.getCategory().equalsIgnoreCase(filterCategory))
                .sorted(getComparator(sortChoice))
                .collect(Collectors.toList());

        System.out.println("\nFiltered and Sorted Products:");
        filteredProducts.forEach(System.out::println);
    }

    private static Comparator<Product> getComparator(int choice) {
        return switch (choice) {
            case 1 -> Comparator.comparingDouble(Product::getPrice);
            case 2 -> Comparator.comparing(Product::getName);
            case 3 -> Comparator.comparing(Product::getCategory);
            default -> throw new IllegalArgumentException("Invalid choice");
        };
    }
}