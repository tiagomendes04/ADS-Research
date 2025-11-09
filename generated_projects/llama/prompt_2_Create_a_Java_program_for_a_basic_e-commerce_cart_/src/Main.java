import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}

class Cart {
    private ArrayList<Product> items;
    private double subtotal;
    private double taxRate;
    private double discountRate;

    public Cart(double taxRate, double discountRate) {
        this.items = new ArrayList<>();
        this.subtotal = 0;
        this.taxRate = taxRate;
        this.discountRate = discountRate;
    }

    public void addItem(Product product) {
        items.add(product);
        subtotal += product.getPrice() * product.getQuantity();
    }

    public void removeItem(Product product) {
        items.remove(product);
        subtotal -= product.getPrice() * product.getQuantity();
    }

    public void calculateTax() {
        double tax = subtotal * taxRate / 100;
        System.out.println("Tax: $" + String.format("%.2f", tax));
    }

    public void calculateDiscount() {
        double discount = subtotal * discountRate / 100;
        System.out.println("Discount: -$" + String.format("%.2f", discount));
    }

    public void calculateTotal() {
        calculateTax();
        calculateDiscount();
        double total = subtotal - (subtotal * discountRate / 100);
        System.out.println("Total: $" + String.format("%.2f", total));
    }

    public void displayCart() {
        System.out.println("Cart:");
        for (Product product : items) {
            System.out.println(product.getName() + " x " + product.getQuantity() + " = $" + String.format("%.2f", product.getPrice() * product.getQuantity()));
        }
        System.out.println("Subtotal: $" + String.format("%.2f", subtotal));
    }
}

public class Main {
    public static void main(String[] args) {
        Cart cart = new Cart(8, 10);
        cart.addItem(new Product("Apple", 1.99, 2));
        cart.addItem(new Product("Banana", 0.99, 3));
        cart.addItem(new Product("Orange", 2.49, 1));
        cart.displayCart();
        cart.calculateTotal();
    }
}