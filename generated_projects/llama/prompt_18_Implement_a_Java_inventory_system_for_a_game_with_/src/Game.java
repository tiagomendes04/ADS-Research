import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Item {
    private String name;
    private String description;
    private int weight;
    private int value;
    private boolean equipped;
    private Map<String, Item> combinedWith;

    public Item(String name, String description, int weight, int value) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.value = value;
        this.equipped = false;
        this.combinedWith = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    public void combine(Item item) {
        this.combinedWith.put(item.getName(), item);
    }

    public Map<String, Item> getCombinedWith() {
        return combinedWith;
    }
}

class Inventory {
    private ArrayList<Item> items;
    private Map<String, Item> equippedItems;

    public Inventory() {
        this.items = new ArrayList<>();
        this.equippedItems = new HashMap<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void equipItem(Item item) {
        equippedItems.put(item.getName(), item);
        item.setEquipped(true);
    }

    public void unequipItem(Item item) {
        equippedItems.remove(item.getName());
        item.setEquipped(false);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public Map<String, Item> getEquippedItems() {
        return equippedItems;
    }
}

public class Game {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        Item sword = new Item("Sword", "A sharp sword.", 5, 100);
        Item shield = new Item("Shield", "A sturdy shield.", 3, 50);
        Item potion = new Item("Potion", "A healing potion.", 1, 20);

        inventory.addItem(sword);
        inventory.addItem(shield);
        inventory.addItem(potion);

        inventory.equipItem(sword);

        System.out.println("Equipped Items:");
        for (Map.Entry<String, Item> entry : inventory.getEquippedItems().entrySet()) {
            System.out.println(entry.getValue().getName());
        }

        sword.combine(potion);
        System.out.println("Combined Items:");
        for (Item item : sword.getCombinedWith().values()) {
            System.out.println(item.getName());
        }
    }
}