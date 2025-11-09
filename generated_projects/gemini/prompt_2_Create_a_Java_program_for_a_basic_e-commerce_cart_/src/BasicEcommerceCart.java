import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BasicEcommerceCart {

    public static class Item {
        private String name;
        private double price;

        public Item(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return name + " - $" + String.format("%.2f", price);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item item = (Item) o;
            return Double.compare(item.price, price) == 0 && Objects.equals(name, item.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, price);
        }
    }

    public static class ShoppingCart {
        private List<Item> items;

        public ShoppingCart() {
            this.items = new ArrayList<>();
        }

        public void addItem(Item item) {
            if (item != null) {
                this.items.add(item);
                System.out.println("Added: " + item.getName());
            }
        }

        public void removeItem(Item item) {
            if (item != null && this.items.remove(item)) {
                System.out.println("Removed: " + item.getName());
            } else {
                System.out.println("Item not found in cart: " + (item != null ? item.getName() : "null"));
            }
        }

        public double calculateSubtotal() {
            return items.stream()
                        .mapToDouble(Item::getPrice)
                        .sum();
        }

        public double calculateTotal(double discountPercentage) {
            if (discountPercentage < 0 || discountPercentage > 100) {
                System.out.println("Warning: Invalid discount percentage. Must be between 0 and 100.");
                return calculateSubtotal();
            }
            double subtotal = calculateSubtotal();
            double discountAmount = subtotal * (discountPercentage / 100.0);
            return subtotal - discountAmount;
        }

        public void displayCart() {
            System.out.println("\n--- Your Shopping Cart ---");
            if (items.isEmpty()) {
                System.out.println("Cart is empty.");
            } else {
                for (int i = 0; i < items.size(); i++) {
                    System.out.println((i + 1) + ". " + items.get(i));
                }
            }
            System.out.println("--------------------------");
        }
    }

    public static void main(String[] args) {
        // Create some items
        Item laptop = new Item("Gaming Laptop", 1200.00);
        Item mouse = new Item("Wireless Mouse", 25.50);
        Item keyboard = new Item("Mechanical Keyboard", 75.99);
        Item monitor = new Item("4K Monitor", 350.00);
        Item headphones = new Item("Noise Cancelling Headphones", 150.00);

        // Create a shopping cart
        ShoppingCart myCart = new ShoppingCart();

        // Add items to the cart
        myCart.addItem(laptop);
        myCart.addItem(mouse);
        myCart.addItem(keyboard);
        myCart.addItem(headphones);

        // Display current cart contents
        myCart.displayCart();

        // Calculate and display subtotal
        double subtotal = myCart.calculateSubtotal();
        System.out.println("Subtotal: $" + String.format("%.2f", subtotal));

        // Add another item
        myCart.addItem(monitor);
        myCart.displayCart();

        // Calculate subtotal after adding another item
        subtotal = myCart.calculateSubtotal();
        System.out.println("Subtotal: $" + String.format("%.2f", subtotal));

        // Apply a discount and calculate total
        double discountPercent = 10.0; // 10% discount
        double totalWithDiscount = myCart.calculateTotal(discountPercent);
        System.out.println("Discount Applied: " + String.format("%.0f%%", discountPercent));
        System.out.println("Total after discount: $" + String.format("%.2f", totalWithDiscount));

        // Try removing an item
        myCart.removeItem(mouse);
        myCart.displayCart();
        subtotal = myCart.calculateSubtotal();
        System.out.println("Subtotal after removal: $" + String.format("%.2f", subtotal));

        // Try applying an invalid discount
        double invalidDiscountTotal = myCart.calculateTotal(110.0);
        System.out.println("Total with invalid discount attempt: $" + String.format("%.2f", invalidDiscountTotal));

        // Calculate final total with a valid discount after removal
        double finalDiscount = 5.0; // 5% discount
        double finalTotal = myCart.calculateTotal(finalDiscount);
        System.out.println("Final Discount Applied: " + String.format("%.0f%%", finalDiscount));
        System.out.println("Final Total: $" + String.format("%.2f", finalTotal));
    }
}