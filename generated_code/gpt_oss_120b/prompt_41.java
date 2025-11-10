```java
import java.util.*;

class Item {
    private final String id;
    private final String name;
    private int quantity;
    private final int reorderLevel;

    public Item(String id, String name, int quantity, int reorderLevel) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public int getReorderLevel() { return reorderLevel; }

    public void addStock(int amount) {
        if (amount > 0) {
            quantity += amount;
        }
    }

    public void removeStock(int amount) {
        if (amount > 0 && amount <= quantity) {
            quantity -= amount;
        }
    }

    public boolean needsReorder() {
        return quantity <= reorderLevel;
    }

    @Override
    public String toString() {
        return String.format("%s (%s): %d units", name, id, quantity);
    }
}

class Inventory {
    private final Map<String, Item> items = new HashMap<>();

    public void addItem(Item item) {
        items.put(item.getId(), item);
    }

    public void receiveStock(String itemId, int amount) {
        Item item = items.get(itemId);
        if (item != null) {
            item.addStock(amount);
            System.out.println("Received " + amount + " of " + item.getName());
        } else {
            System.out.println("Item ID not found: " + itemId);
        }
    }

    public void dispenseStock(String itemId, int amount) {
        Item item = items.get(itemId);
        if (item != null) {
            if (amount <= item.getQuantity()) {
                item.removeStock(amount);
                System.out.println("Dispensed " + amount + " of " + item.getName());
                if (item.needsReorder()) {
                    System.out.println("ALERT: Stock for " + item.getName() + " is low (" + item.getQuantity() + " units). Reorder needed.");
                }
            } else {
                System.out.println("Insufficient stock for " + item.getName());
            }
        } else {
            System.out.println("Item ID not found: " + itemId);
        }
    }

    public void printInventory() {
        System.out.println("Current Inventory:");
        for (Item item : items.values()) {
            System.out.println(item);
        }
    }
}

public class InventorySystem {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        inventory.addItem(new Item("A001", "Widget", 50, 10));
        inventory.addItem(new Item("B002", "Gadget", 20, 5));
        inventory.addItem(new Item("C003", "Doohickey", 5, 5));

        inventory.printInventory();

        inventory.dispenseStock("A001", 45);
        inventory.dispenseStock("B002", 16);
        inventory.dispenseStock("C003", 1);

        inventory.receiveStock("C003", 20);

        inventory.printInventory();
    }
}
```