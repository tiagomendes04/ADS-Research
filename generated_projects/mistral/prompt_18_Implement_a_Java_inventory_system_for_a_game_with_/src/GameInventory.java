import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameInventory {
    private List<Item> items;
    private Map<String, Item> equippedItems;

    public GameInventory() {
        items = new ArrayList<>();
        equippedItems = new HashMap<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public boolean equipItem(String itemId) {
        Item item = findItem(itemId);
        if (item == null || !item.isEquippable()) {
            return false;
        }

        // Unequip the current item in the same slot if any
        String slot = item.getSlot();
        if (equippedItems.containsKey(slot)) {
            unequipItem(slot);
        }

        equippedItems.put(slot, item);
        return true;
    }

    public boolean unequipItem(String slot) {
        if (equippedItems.containsKey(slot)) {
            equippedItems.remove(slot);
            return true;
        }
        return false;
    }

    public boolean combineItems(String itemId1, String itemId2) {
        Item item1 = findItem(itemId1);
        Item item2 = findItem(itemId2);

        if (item1 == null || item2 == null || !item1.isCombinable() || !item2.isCombinable()) {
            return false;
        }

        Item combinedItem = item1.combine(item2);
        if (combinedItem != null) {
            items.remove(item1);
            items.remove(item2);
            items.add(combinedItem);
            return true;
        }
        return false;
    }

    public Item findItem(String itemId) {
        for (Item item : items) {
            if (item.getId().equals(itemId)) {
                return item;
            }
        }
        return null;
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    public Map<String, Item> getEquippedItems() {
        return new HashMap<>(equippedItems);
    }
}

abstract class Item {
    private String id;
    private String name;
    private String description;

    public Item(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract boolean isEquippable();
    public abstract String getSlot();
    public abstract boolean isCombinable();
    public abstract Item combine(Item other);
}

class Weapon extends Item {
    private int damage;

    public Weapon(String id, String name, String description, int damage) {
        super(id, name, description);
        this.damage = damage;
    }

    @Override
    public boolean isEquippable() {
        return true;
    }

    @Override
    public String getSlot() {
        return "weapon";
    }

    @Override
    public boolean isCombinable() {
        return true;
    }

    @Override
    public Item combine(Item other) {
        if (other instanceof Weapon) {
            Weapon otherWeapon = (Weapon) other;
            return new Weapon(
                "combined_" + this.getId() + "_" + other.getId(),
                this.getName() + " + " + other.getName(),
                "A powerful weapon created by combining " + this.getName() + " and " + other.getName(),
                this.damage + other.damage
            );
        }
        return null;
    }
}

class Armor extends Item {
    private int defense;

    public Armor(String id, String name, String description, int defense) {
        super(id, name, description);
        this.defense = defense;
    }

    @Override
    public boolean isEquippable() {
        return true;
    }

    @Override
    public String getSlot() {
        return "armor";
    }

    @Override
    public boolean isCombinable() {
        return true;
    }

    @Override
    public Item combine(Item other) {
        if (other instanceof Armor) {
            Armor otherArmor = (Armor) other;
            return new Armor(
                "combined_" + this.getId() + "_" + other.getId(),
                this.getName() + " + " + other.getName(),
                "A reinforced armor created by combining " + this.getName() + " and " + other.getName(),
                this.defense + other.defense
            );
        }
        return null;
    }
}

class Consumable extends Item {
    public Consumable(String id, String name, String description) {
        super(id, name, description);
    }

    @Override
    public boolean isEquippable() {
        return false;
    }

    @Override
    public String getSlot() {
        return null;
    }

    @Override
    public boolean isCombinable() {
        return false;
    }

    @Override
    public Item combine(Item other) {
        return null;
    }
}