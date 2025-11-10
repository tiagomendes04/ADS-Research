```java
import java.util.HashMap;
import java.util.Map;

public class InventorySystem {
    private Map<String, Integer> stockLevels;

    public InventorySystem() {
        this.stockLevels = new HashMap<>();
    }

    public void addProduct(String productName, int quantity) {
        stockLevels.put(productName, quantity);
    }

    public void updateQuantity(String productName, int quantity) {
        if (stockLevels.containsKey(productName)) {
            int currentQuantity = stockLevels.get(productName);
            stockLevels.put(productName, currentQuantity + quantity);
        } else {
            System.out.println("Product not found in inventory.");
        }
    }

    public void checkStock(String productName) {
        if (stockLevels.containsKey(productName)) {
            System.out.println("Stock level for " + productName + ": " + stockLevels.get(productName));
        } else {
            System.out.println("Product not found in inventory.");
        }
    }

    public void removeProduct(String productName) {
        if (stockLevels.containsKey(productName)) {
            stockLevels.remove(productName);
        } else {
            System.out.println("Product not found in inventory.");
        }
    }

    public static void main(String[] args) {
        InventorySystem inventory = new InventorySystem();
        inventory.addProduct("Laptop", 100);
        inventory.addProduct("Mouse", 200);
        inventory.updateQuantity("Laptop", 50);
        inventory.checkStock("Laptop");
        inventory.removeProduct("Mouse");
        inventory.checkStock("Mouse");
    }
}
```