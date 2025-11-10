```java
import java.util.HashMap;
import java.util.Map;

class InventoryItem {
    private final String sku;
    private final String name;
    private int quantity;

    public InventoryItem(String sku, String name, int initialQty) {
        this.sku = sku;
        this.name = name;
        this.quantity = Math.max(initialQty, 0);
    }

    public String getSku() { return sku; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }

    public void addStock(int amount) {
        if (amount > 0) quantity += amount;
    }

    public boolean removeStock(int amount) {
        if (amount > 0 && amount <= quantity) {
            quantity -= amount;
            return true;
        }
        return false;
    }
}

class WarehouseInventory {
    private final Map<String, InventoryItem> items = new HashMap<>();

    public boolean addNewItem(String sku, String name, int initialQty) {
        if (items.containsKey(sku)) return false;
        items.put(sku, new InventoryItem(sku, name, initialQty));
        return true;
    }

    public boolean receiveStock(String sku, int amount) {
        InventoryItem item = items.get(sku);
        if (item == null) return false;
        item.addStock(amount);
        return true;
    }

    public boolean shipStock(String sku, int amount) {
        InventoryItem item = items.get(sku);
        return item != null && item.removeStock(amount);
    }

    public Integer getStockLevel(String sku) {
        InventoryItem item = items.get(sku);
        return (item != null) ? item.getQuantity() : null;
    }

    public void printReport() {
        System.out.println("=== Warehouse Inventory Report ===");
        for (InventoryItem item : items.values()) {
            System.out.printf("SKU: %s | Name: %s | Qty: %d%n",
                    item.getSku(), item.getName(), item.getQuantity());
        }
        System.out.println("=== End of Report ===");
    }
}

public class InventoryDemo {
    public static void main(String[] args) {
        WarehouseInventory inventory = new WarehouseInventory();

        inventory.addNewItem("A100", "Widget", 50);
        inventory.addNewItem("B200", "Gadget", 30);
        inventory.addNewItem("C300", "Doohickey", 0);

        inventory.receiveStock("C300", 15);
        inventory.shipStock("A100", 10);
        inventory.shipStock("B200", 5);
        inventory.shipStock("C300", 3);

        inventory.printReport();

        // Example of querying a specific SKU
        String querySku = "A100";
        Integer qty = inventory.getStockLevel(querySku);
        System.out.printf("Current stock for %s: %s%n", querySku,
                (qty != null ? qty : "Item not found"));
    }
}
```