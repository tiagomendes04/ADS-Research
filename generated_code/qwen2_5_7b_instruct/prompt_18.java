```java
import java.util.ArrayList;
import java.util.List;

class GameItem {
    private String name;
    private int power;
    private boolean isEquipped;

    public GameItem(String name, int power) {
        this.name = name;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public boolean isEquipped() {
        return isEquipped;
    }

    public void equip() {
        isEquipped = true;
    }

    public void unequip() {
        isEquipped = false;
    }

    @Override
    public String toString() {
        return name + " (Power: " + power + ", Equipped: " + isEquipped + ")";
    }
}

class InventorySystem {
    private List<GameItem> items;

    public InventorySystem() {
        items = new ArrayList<>();
    }

    public void addItem(GameItem item) {
        items.add(item);
    }

    public GameItem getItemByName(String name) {
        for (GameItem item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public void equipItem(String itemName) {
        GameItem item = getItemByName(itemName);
        if (item != null) {
            item.equip();
            System.out.println(item.getName() + " equipped.");
        } else {
            System.out.println("Item not found.");
        }
    }

    public void unequipItem(String itemName) {
        GameItem item = getItemByName(itemName);
        if (item != null) {
            item.unequip();
            System.out.println(item.getName() + " unequipped.");
        } else {
            System.out.println("Item not found.");
        }
    }

    public void combineItems(String itemName1, String itemName2) {
        GameItem item1 = getItemByName(itemName1);
        GameItem item2 = getItemByName(itemName2);
        if (item1 != null && item2 != null) {
            int newPower = item1.getPower() + item2.getPower();
            GameItem combinedItem = new GameItem(item1.getName() + " & " + item2.getName(), newPower);
            items.remove(item1);
            items.remove(item2);
            items.add(combinedItem);
            System.out.println(item1.getName() + " and " + item2.getName() + " combined into " + combinedItem.getName() + " with power " + newPower);
        } else {
            System.out.println("One or both items not found.");
        }
    }

    public void displayInventory() {
        for (GameItem item : items) {
            System.out.println(item);
        }
    }
}

public class GameInventory {
    public static void main(String[] args) {
        InventorySystem inventory = new InventorySystem();
        inventory.addItem(new GameItem("Sword", 10));
        inventory.addItem(new GameItem("Shield", 5));
        inventory.addItem(new GameItem("Potion", 2));

        inventory.displayInventory();

        inventory.equipItem("Sword");
        inventory.displayInventory();

        inventory.combineItems("Sword", "Shield");
        inventory.displayInventory();
    }
}
```