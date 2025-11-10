```java
import java.util.HashMap;
import java.util.Scanner;

public class InventoryTracking {

    private static HashMap<String, Integer> inventory = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nInventory Tracking System");
            System.out.println("1. Add Item");
            System.out.println("2. Update Stock");
            System.out.println("3. View Inventory");
            System.out.println("4. Send Alert");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    updateStock();
                    break;
                case 3:
                    viewInventory();
                    break;
                case 4:
                    sendAlert();
                    break;
                case 5:
                    System.out.println("Exiting the system...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addItem() {
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter initial stock quantity: ");
        int initialStock = scanner.nextInt();
        scanner.nextLine();
        inventory.put(itemName, initialStock);
        System.out.println("Item added successfully.");
    }

    private static void updateStock() {
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine();
        if (inventory.containsKey(itemName)) {
            System.out.print("Enter new stock quantity: ");
            int newStock = scanner.nextInt();
            scanner.nextLine();
            inventory.put(itemName, newStock);
            System.out.println("Stock updated successfully.");
        } else {
            System.out.println("Item not found.");
        }
    }

    private static void viewInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("\nInventory:");
            for (String item : inventory.keySet()) {
                System.out.println(item + ": " + inventory.get(item));
            }
        }
    }

    private static void sendAlert() {
        System.out.print("Enter the item name: ");
        String itemName = scanner.nextLine();
        if (inventory.containsKey(itemName)) {
            System.out.print("Enter the alert threshold: ");
            int threshold = scanner.nextInt();
            scanner.nextLine();
            if (inventory.get(itemName) < threshold) {
                System.out.println("Alert: Low stock for " + itemName + ". Please replenish.");
            }
        } else {
            System.out.println("Item not found.");
        }
    }
}
```