```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Item {
    String name;
    int cost;
    int level;

    public Item(String name, int cost, int level) {
        this.name = name;
        this.cost = cost;
        this.level = level;
    }
}

class Inventory {
    Map<String, Item> items;

    public Inventory() {
        items = new HashMap<>();
    }

    public void equipItem(String itemName, int equipCost) {
        if (items.containsKey(itemName)) {
            items.put(itemName, new Item(itemName, items.get(itemName).cost + equipCost, items.get(itemName).level + 1));
        } else {
            System.out.println("Item not found in inventory.");
        }
    }

    public void combineItems(String item1Name, String item2Name) {
        if (items.containsKey(item1Name) && items.containsKey(item2Name)) {
            int cost1 = items.get(item1Name).cost;
            int cost2 = items.get(item2Name).cost;
            int level1 = items.get(item1Name).level;
            int level2 = items.get(item2Name).level;

            int combinedCost = cost1 + cost2;
            int combinedLevel = Math.max(level1, level2);

            if (combinedCost <= 100) {
                items.put(item1Name, new Item(item1Name, combinedCost, combinedLevel));
                items.put(item2Name, new Item(item2Name, 0, combinedLevel));
            } else {
                System.out.println("Equipment cannot be combined.");
            }
        } else {
            System.out.println("Item not found in inventory.");
        }
    }

    public void removeItem(String itemName) {
        if (items.containsKey(itemName)) {
            items.remove(itemName);
        } else {
            System.out.println("Item not found in inventory.");
        }
    }

    public void displayInventory() {
        for (Map.Entry<String, Item> entry : items.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().name + " - Cost: " + entry.getValue().cost + ", Level: " + entry.getValue().level);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        inventory equipItem("Sword", 50);
        inventory equipItem("Shield", 30);

        inventory.combineItems("Sword", "Shield");

        inventory.removeItem("Sword");

        inventory.displayInventory();
    }
}
```