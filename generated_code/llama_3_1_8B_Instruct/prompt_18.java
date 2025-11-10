```java
// Item.java
public abstract class Item {
    protected String name;
    protected int weight;

    public Item(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public abstract boolean canBeEquipped();
    public abstract boolean canBeCombined();
    public abstract Item combine(Item item);
    public abstract Item equip();
}

// ConsumableItem.java
public class ConsumableItem extends Item {
    public ConsumableItem(String name, int weight) {
        super(name, weight);
    }

    @Override
    public boolean canBeEquipped() {
        return false;
    }

    @Override
    public boolean canBeCombined() {
        return false;
    }

    @Override
    public Item combine(Item item) {
        return this; // Consumable items cannot be combined
    }

    @Override
    public Item equip() {
        return this; // Consumable items cannot be equipped
    }
}

// EquipmentItem.java
public abstract class EquipmentItem extends Item {
    public EquipmentItem(String name, int weight) {
        super(name, weight);
    }

    @Override
    public boolean canBeEquipped() {
        return true;
    }

    @Override
    public boolean canBeCombined() {
        return false;
    }
}

// ArmorItem.java
public class ArmorItem extends EquipmentItem {
    public ArmorItem(String name, int weight) {
        super(name, weight);
    }

    @Override
    public Item equip() {
        // Simulate equipping an armor item
        System.out.println("Equipped " + name);
        return this;
    }
}

// WeaponItem.java
public class WeaponItem extends EquipmentItem {
    public WeaponItem(String name, int weight) {
        super(name, weight);
    }

    @Override
    public Item equip() {
        // Simulate equipping a weapon item
        System.out.println("Equipped " + name);
        return this;
    }
}

// Inventory.java
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void equipItem(Item item) {
        if (item.canBeEquipped()) {
            item.equip();
        } else {
            System.out.println("Cannot equip " + item.getName());
        }
    }

    public void combineItems(Item item1, Item item2) {
        if (item1.canBeCombined() && item2.canBeCombined()) {
            Item combinedItem = item1.combine(item2);
            removeItem(item1);
            removeItem(item2);
            addItem(combinedItem);
        } else {
            System.out.println("Cannot combine items");
        }
    }

    public void listItems() {
        for (Item item : items) {
            System.out.println(item.getName() + " (" + item.getWeight() + " weight)");
        }
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        ArmorItem armor = new ArmorItem("Iron Armor", 10);
        WeaponItem sword = new WeaponItem("Steel Sword", 5);
        ConsumableItem potion = new ConsumableItem("Healing Potion", 1);

        inventory.addItem(armor);
        inventory.addItem(sword);
        inventory.addItem(potion);

        inventory.listItems();

        inventory.equipItem(armor);
        inventory.equipItem(sword);

        inventory.combineItems(potion, armor);

        inventory.listItems();
    }
}
```