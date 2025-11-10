```java
import java.util.HashMap;
import java.util.Scanner;

public class WarehouseInventory {

    static HashMap<String, Integer> inventory = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Add item");
            System.out.println("2. View inventory");
            System.out.println("3. Update stock");
            System.out.println("4. Remove item");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addItem(scanner);
                    break;
                case 2:
                    viewInventory();
                    break;
                case 3:
                    updateStock(scanner);
                    break;
                case 4:
                    removeItem(scanner);
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void addItem(Scanner scanner) {
        System.out.print("Enter item name: ");
        String name = scanner.next();
        System.out.print("Enter initial stock quantity: ");
        int quantity = scanner.nextInt();
        inventory.put(name, quantity);
        System.out.println("Item added successfully!");
    }

    static void viewInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        System.out.println("-----------------------");
        for (String name : inventory.keySet()) {
            System.out.println(name + ": " + inventory.get(name));
        }
        System.out.println("-----------------------");
    }

    static void updateStock(Scanner scanner) {
        System.out.print("Enter item name to update: ");
        String name = scanner.next();
        if (inventory.containsKey(name)) {
            System.out.print("Enter new stock quantity: ");
            int quantity = scanner.nextInt();
            inventory.put(name, quantity);
            System.out.println("Stock updated successfully!");
        } else {
            System.out.println("Item not found in inventory.");
        }
    }

    static void removeItem(Scanner scanner) {
        System.out.print("Enter item name to remove: ");
        String name = scanner.next();
        if (inventory.containsKey(name)) {
            inventory.remove(name);
            System.out.println("Item removed successfully!");
        } else {
            System.out.println("Item not found in inventory.");
        }
    }
}
```