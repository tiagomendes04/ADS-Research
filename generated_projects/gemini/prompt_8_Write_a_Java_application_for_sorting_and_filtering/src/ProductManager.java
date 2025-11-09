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