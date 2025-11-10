```java
import java.util.HashMap;
import java.util.Map;

public class InventoryTrackingSystem {

    private Map<String, Integer> inventory;

    public InventoryTrackingSystem() {
        inventory = new HashMap<>();
    }

    public void addProduct(String productId, int quantity) {
        inventory.put(productId, quantity);
    }

    public void updateStock(String productId, int quantity) {
        if (inventory.containsKey(productId)) {
            int newQuantity = inventory.get(productId) + quantity;
            inventory.put(productId, newQuantity);
        } else {
            System.out.println("Product not found in inventory.");
        }
    }

    public void checkStock(String productId, int threshold) {
        if (inventory.containsKey(productId)) {
            int currentQuantity = inventory.get(productId);
            if (currentQuantity < threshold) {
                System.out.println("Alert: Stock for " + productId + " is below " + threshold);
            }
        } else {
            System.out.println("Product not found in inventory.");
        }
    }

    public static void main(String[] args) {
        InventoryTrackingSystem uts = new InventoryTrackingSystem();
        uts.addProduct("P001", 100);
        uts.updateStock("P001", 50);
        uts.checkStock("P001", 75);
    }
}
```