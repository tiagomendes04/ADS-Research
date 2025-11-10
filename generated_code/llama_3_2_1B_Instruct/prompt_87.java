```java
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Integer> stockLevels;

    public Inventory() {
        stockLevels = new HashMap<>();
    }

    public void addStock(String item, int quantity) {
        stockLevels.put(item, stockLevels.getOrDefault(item, 0) + quantity);
    }

    public int getStock(int item) {
        return stockLevels.getOrDefault(item, 0);
    }

    public void updateStock(String item, int quantity) {
        if (stockLevels.containsKey(item)) {
            stockLevels.put(item, stockLevels.get(item) + quantity);
        } else {
            throw new RuntimeException("Item not found in inventory");
        }
    }

    public void removeStock(String item, int quantity) {
        if (stockLevels.containsKey(item)) {
            int currentQuantity = stockLevels.get(item);
            stockLevels.put(item, currentQuantity - quantity);
            if (stockLevels.get(item) <= 0) {
                removeItem(item);
            }
        } else {
            throw new RuntimeException("Item not found in inventory");
        }
    }

    private void removeItem(String item) {
        stockLevels.remove(item);
    }

    public void printInventory() {
        for (Map.Entry<String, Integer> entry : stockLevels.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        inventory.addStock("Product A", 100);
        inventory.addStock("Product B", 50);
        inventory.printInventory();
        inventory.updateStock("Product A", 20);
        inventory.printInventory();
        inventory.removeStock("Product A", 30);
        inventory.printInventory();
    }
}
```