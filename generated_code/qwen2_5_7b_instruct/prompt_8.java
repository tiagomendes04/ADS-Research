```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Product {
    String name;
    String category;
    double price;

    public Product(String name, String category, double price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }
}

public class ProductSortFilter {

    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Laptop", "Electronics", 1200.0));
        products.add(new Product("Coffee Maker", "Appliances", 60.0));
        products.add(new Product("Coffee", "Groceries", 10.0));
        products.add(new Product("Smartphone", "Electronics", 800.0));

        System.out.println("Original List: ");
        System.out.println(products);

        System.out.println("\nSorted by Price (Ascending): ");
        sortProducts(products, Comparator.comparingDouble(Product::getPrice));
        System.out.println(products);

        System.out.println("\nSorted by Name (Ascending): ");
        sortProducts(products, Comparator.comparing(Product::getName));
        System.out.println(products);

        System.out.println("\nSorted by Category (Ascending): ");
        sortProducts(products, Comparator.comparing(Product::getCategory));
        System.out.println(products);

        System.out.println("\nFiltered by Category (Electronics): ");
        filterProducts(products, "Electronics");
        System.out.println(products);
    }

    private static void sortProducts(List<Product> products, Comparator<Product> comparator) {
        Collections.sort(products, comparator);
    }

    private static void filterProducts(List<Product> products, String category) {
        products.removeIf(product -> !product.category.equals(category));
    }
}
```