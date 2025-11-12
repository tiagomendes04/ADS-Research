```java
import java.util.HashMap;
import java.util.Map;

class InventoryItem {
    private String name;
    private int quantity;
    private int threshold;

    public InventoryItem(String name, int quantity, int threshold) {
        this.name = name;
        this.quantity = quantity;
        this.threshold = threshold;
    }

    public void updateQuantity(int amount) {
        quantity += amount;
        checkThreshold();
    }

    private void checkThreshold() {
        if (quantity <= threshold) {
            System.out.println("Alert: " + name + " is below threshold. Quantity: " + quantity);
        }
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getThreshold() {
        return threshold;
    }
}

class InventorySystem {
    private Map<String, InventoryItem> inventory;

    public InventorySystem() {
        inventory = new HashMap<>();
    }

    public void addItem(String name, int quantity, int threshold) {
        inventory.put(name, new InventoryItem(name, quantity, threshold));
    }

    public void updateStock(String name, int amount) {
        InventoryItem item = inventory.get(name);
        if (item != null) {
            item.updateQuantity(amount);
        } else {
            System.out.println("Item not found in inventory.");
        }
    }

    public void displayInventory() {
        for (Map.Entry<String, InventoryItem> entry : inventory.entrySet()) {
            System.out.println("Item: " + entry.getValue().getName() + ", Quantity: " + entry.getValue().getQuantity() + ", Threshold: " + entry.getValue().getThreshold());
        }
    }
}

public class InventoryTracker {
    public static void main(String[] args) {
        InventorySystem system = new InventorySystem();
        system.addItem("Apples", 100, 20);
        system.addItem("Oranges", 50, 10);
        system.displayInventory();
        system.updateStock("Apples", -80);
        system.updateStock("Oranges", -45);
        system.displayInventory();
    }
}
```