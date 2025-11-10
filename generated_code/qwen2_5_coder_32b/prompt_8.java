```java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
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
        return "Product{name='" + name + "', price=" + price + ", category='" + category + "'}";
    }
}

public class ProductSorter {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Laptop", 1200.00, "Electronics"));
        products.add(new Product("Smartphone", 700.00, "Electronics"));
        products.add(new Product("Coffee Maker", 150.00, "Home Appliances"));
        products.add(new Product("Blender", 100.00, "Home Appliances"));
        products.add(new Product("Jeans", 45.00, "Clothing"));
        products.add(new Product("T-Shirt", 20.00, "Clothing"));

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter filter category (or press Enter to skip):");
        String filterCategory = scanner.nextLine().trim();

        System.out.println("Enter sort criteria (name, price, category):");
        String sortCriteria = scanner.nextLine().trim().toLowerCase();

        List<Product> filteredProducts = products.stream()
                .filter(p -> filterCategory.isEmpty() || p.getCategory().equalsIgnoreCase(filterCategory))
                .collect(Collectors.toList());

        Comparator<Product> comparator;
        switch (sortCriteria) {
            case "price":
                comparator = Comparator.comparingDouble(Product::getPrice);
                break;
            case "category":
                comparator = Comparator.comparing(Product::getCategory);
                break;
            default:
                comparator = Comparator.comparing(Product::getName);
        }

        List<Product> sortedProducts = filteredProducts.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        System.out.println("Sorted and Filtered Products:");
        sortedProducts.forEach(System.out::println);

        scanner.close();
    }
}
```