```java
import java.util.HashMap;
import java.util.Map;

class Item {
    private String name;
    private boolean isEquippable;
    private Map<String, Integer> combineRequirements;

    public Item(String name, boolean isEquippable) {
        this.name = name;
        this.isEquippable = isEquippable;
        this.combineRequirements = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public boolean isEquippable() {
        return isEquippable;
    }

    public void addCombineRequirement(Item item, int quantity) {
        combineRequirements.put(item.getName(), quantity);
    }

    public Map<String, Integer> getCombineRequirements() {
        return combineRequirements;
    }
}

class Inventory {
    private Map<String, Integer> items;

    public Inventory() {
        items = new HashMap<>();
    }

    public void addItem(Item item, int quantity) {
        items.put(item.getName(), items.getOrDefault(item.getName(), 0) + quantity);
    }

    public boolean hasItem(String itemName, int quantity) {
        return items.getOrDefault(itemName, 0) >= quantity;
    }

    public void removeItem(String itemName, int quantity) {
        if (hasItem(itemName, quantity)) {
            items.put(itemName, items.get(itemName) - quantity);
        } else {
            throw new IllegalArgumentException("Not enough " + itemName + " in inventory.");
        }
    }

    public Item combineItems(Item resultItem, String... itemNames) {
        for (String itemName : itemNames) {
            Item item = getItemByName(itemName);
            if (!item.getCombineRequirements().isEmpty()) {
                for (Map.Entry<String, Integer> requirement : item.getCombineRequirements().entrySet()) {
                    if (!hasItem(requirement.getKey(), requirement.getValue())) {
                        throw new IllegalArgumentException("Missing required item: " + requirement.getKey());
                    }
                    removeItem(requirement.getKey(), requirement.getValue());
                }
                addItem(resultItem, 1);
                return resultItem;
            }
        }
        throw new IllegalArgumentException("Invalid combination attempt.");
    }

    public Item getItemByName(String name) {
        for (String itemName : items.keySet()) {
            if (itemName.equals(name)) {
                return new Item(itemName, false); // Simplified for demonstration
            }
        }
        throw new IllegalArgumentException("Item not found in inventory: " + name);
    }

    public void equipItem(Item item) {
        if (!item.isEquippable()) {
            throw new IllegalArgumentException("Item cannot be equipped: " + item.getName());
        }
        System.out.println("Equipped: " + item.getName());
    }

    public void displayInventory() {
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

public class GameInventorySystem {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        Item sword = new Item("Sword", true);
        Item shield = new Item("Shield", true);
        Item ironBar = new Item("Iron Bar", false);
        Item steelBar = new Item("Steel Bar", false);

        ironBar.addCombineRequirement(ironBar, 2);
        steelBar.addCombineRequirement(ironBar, 3);

        Item ironSword = new Item("Iron Sword", true);
        Item steelSword = new Item("Steel Sword", true);

        ironSword.addCombineRequirement(sword, 1);
        ironSword.addCombineRequirement(ironBar, 2);

        steelSword.addCombineRequirement(sword, 1);
        steelSword.addCombineRequirement(steelBar, 1);

        inventory.addItem(sword, 1);
        inventory.addItem(shield, 1);
        inventory.addItem(ironBar, 5);

        inventory.displayInventory();

        try {
            inventory.combineItems(ironSword, "Sword");
            inventory.displayInventory();
            inventory.equipItem(ironSword);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            inventory.combineItems(steelSword, "Sword");
            inventory.displayInventory();
            inventory.equipItem(steelSword);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
```