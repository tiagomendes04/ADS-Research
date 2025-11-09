import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

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
        products.add(new Product("Product E", 8.99, "Electronics"));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Sort by price");
            System.out.println("2. Sort by name");
            System.out.println("3. Sort by category");
            System.out.println("4. Filter by price");
            System.out.println("5. Filter by name");
            System.out.println("6. Filter by category");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (option) {
                case 1:
                    Collections.sort(products, Comparator.comparingDouble(Product::getPrice));
                    printProducts(products);
                    break;
                case 2:
                    Collections.sort(products, Comparator.comparing(Product::getName));
                    printProducts(products);
                    break;
                case 3:
                    Collections.sort(products, Comparator.comparing(Product::getCategory));
                    printProducts(products);
                    break;
                case 4:
                    System.out.print("Enter price: ");
                    double minPrice = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline left-over
                    System.out.print("Enter max price: ");
                    double maxPrice = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline left-over
                    List<Product> filteredProducts = filterByPrice(products, minPrice, maxPrice);
                    printProducts(filteredProducts);
                    break;
                case 5:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    List<Product> filteredProductsByName = filterByName(products, name);
                    printProducts(filteredProductsByName);
                    break;
                case 6:
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    List<Product> filteredProductsByCategory = filterByCategory(products, category);
                    printProducts(filteredProductsByCategory);
                    break;
                case 7:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }

    private static void printProducts(List<Product> products) {
        for (Product product : products) {
            System.out.println(product.getName() + " - " + product.getPrice() + " - " + product.getCategory());
        }
    }

    private static List<Product> filterByPrice(List<Product> products, double minPrice, double maxPrice) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    private static List<Product> filterByName(List<Product> products, String name) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().contains(name)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    private static List<Product> filterByCategory(List<Product> products, String category) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategory().contains(category)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }
}