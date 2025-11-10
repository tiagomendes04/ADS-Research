```java
import java.util.HashMap;
import java.util.Map;

public class InventorySystem {
    private Map<String, Integer> stockLevels;

    public InventorySystem() {
        stockLevels = new HashMap<>();
    }

    public void addItem(String item, int quantity) {
        stockLevels.put(item, stockLevels.getOrDefault(item, 0) + quantity);
    }

    public void removeItem(String item, int quantity) {
        if (stockLevels.containsKey(item)) {
            int currentQuantity = stockLevels.get(item);
            if (currentQuantity > quantity) {
                stockLevels.put(item, currentQuantity - quantity);
            } else if (currentQuantity == quantity) {
                stockLevels.remove(item);
            } else {
                System.out.println("Not enough stock to remove.");
            }
        } else {
            System.out.println("Item does not exist in inventory.");
        }
    }

    public int getStockLevel(String item) {
        return stockLevels.getOrDefault(item, 0);
    }

    public void displayInventory() {
        for (Map.Entry<String, Integer> entry : stockLevels.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        InventorySystem inventory = new InventorySystem();
        inventory.addItem("Apples", 30);
        inventory.addItem("Oranges", 20);
        inventory.removeItem("Apples", 10);
        inventory.displayInventory();
        System.out.println("Stock level of Apples: " + inventory.getStockLevel("Apples"));
    }
}
```