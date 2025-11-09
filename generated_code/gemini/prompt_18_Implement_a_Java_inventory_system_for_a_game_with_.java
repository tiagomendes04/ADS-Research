```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

interface Item {
    String getName();
    String getDescription();
    int getValue();
}

interface Equipable extends Item {
    String getSlot(); // E.g., "head", "chest", "weapon"
    int getArmor();
    int getAttack();
}

interface Consumable extends Item {
    void consume(); // Apply effects of consumption
}

interface Combinable extends Item {
    Item combine(Item other); // Returns the combined item, or null if incompatible
}

class GameItem implements Item {
    private final String name;
    private final String description;
    private final int value;

    public GameItem(String name, String description, int value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "GameItem{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", value=" + value +
                '}';
    }
}

class Sword implements Equipable {
    private final String name;
    private final String description;
    private final int value;
    private final int attack;

    public Sword(String name, String description, int value, int attack) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.attack = attack;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String getSlot() {
        return "weapon";
    }

    @Override
    public int getArmor() {
        return 0;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public String toString() {
        return "Sword{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", value=" + value +
                ", attack=" + attack +
                '}';
    }
}

class Potion implements Consumable {
    private final String name;
    private final String description;
    private final int value;
    private final int healAmount;

    public Potion(String name, String description, int value, int healAmount) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.healAmount = healAmount;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void consume() {
        System.out.println("Drank " + name + " and healed for " + healAmount + ".");
    }

    @Override
    public String toString() {
        return "Potion{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", value=" + value +
                ", healAmount=" + healAmount +
                '}';
    }
}

class ScrapMetal implements Combinable {

    private final String name;
    private final String description;
    private final int value;

    public ScrapMetal(String name, String description, int value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public Item combine(Item other) {
        if (other instanceof ScrapMetal) {
            return new GameItem("Reinforced Metal", "Stronger than normal metal", getValue() + other.getValue() + 5);
        }
        return null;
    }


    @Override
    public String toString() {
        return "ScrapMetal{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", value=" + value +
                '}';
    }
}


class Inventory {
    private final List<Item> items = new ArrayList<>();
    private final Map<String, Equipable> equippedItems = new HashMap<>(); // slot -> Item

    public void addItem(Item item) {
        items.add(item);
        System.out.println("Added " + item.getName() + " to inventory.");
    }

    public void removeItem(Item item) {
        items.remove(item);
        System.out.println("Removed " + item.getName() + " from inventory.");
    }

    public List<Item> getItems() {
        return new ArrayList<>(items); // Return a copy to prevent modification
    }

    public void equipItem(Equipable item) {
        String slot = item.getSlot();
        if (equippedItems.containsKey(slot)) {
            unequipItem(slot);
        }
        equippedItems.put(slot, item);
        removeItem(item);
        System.out.println("Equipped " + item.getName() + " in slot " + slot);
    }

    public void unequipItem(String slot) {
        Equipable item = equippedItems.remove(slot);
        if (item != null) {
            addItem(item);
            System.out.println("Unequipped " + item.getName() + " from slot " + slot);
        } else {
            System.out.println("No item equipped in slot " + slot);
        }
    }

    public Optional<Equipable> getEquippedItem(String slot) {
        return Optional.ofNullable(equippedItems.get(slot));
    }

    public void consumeItem(Consumable item) {
        if (items.contains(item)) {
            item.consume();
            removeItem(item);
        } else {
            System.out.println(item.getName() + " not found in inventory.");
        }
    }

    public Optional<Item> combineItems(Item item1, Item item2) {
        if (item1 instanceof Combinable && items.contains(item1)) {
            Combinable combinableItem1 = (Combinable) item1;

            Optional<Item> combined = Optional.ofNullable(combinableItem1.combine(item2));
            if (combined.isPresent() && items.contains(item2)) {
                removeItem(item1);
                removeItem(item2);
                addItem(combined.get());
                System.out.println("Combined " + item1.getName() + " and " + item2.getName() + " into " + combined.get().getName());
                return combined;
            }
        }
        else if (item2 instanceof Combinable && items.contains(item2)){
            Combinable combinableItem2 = (Combinable) item2;
            Optional<Item> combined = Optional.ofNullable(combinableItem2.combine(item1));
            if (combined.isPresent() && items.contains(item1)){
                removeItem(item1);
                removeItem(item2);
                addItem(combined.get());
                System.out.println("Combined " + item1.getName() + " and " + item2.getName() + " into " + combined.get().getName());
                return combined;
            }
        }

        System.out.println("Could not combine " + item1.getName() + " and " + item2.getName());
        return Optional.empty();
    }

    public void displayInventory() {
        System.out.println("Inventory:");
        if (items.isEmpty()) {
            System.out.println("  (Empty)");
        } else {
            for (Item item : items) {
                System.out.println("  - " + item.getName());
            }
        }

        System.out.println("Equipped Items:");
        if (equippedItems.isEmpty()) {
            System.out.println("  (None)");
        } else {
            for (Map.Entry<String, Equipable> entry : equippedItems.entrySet()) {
                System.out.println("  - " + entry.getKey() + ": " + entry.getValue().getName());
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        Sword ironSword = new Sword("Iron Sword", "A basic iron sword", 10, 5);
        Potion healthPotion = new Potion("Health Potion", "Restores some health", 5, 20);
        GameItem genericItem = new GameItem("Rock", "A simple rock.", 1);
        ScrapMetal metal1 = new ScrapMetal("Scrap Metal", "Some old metal", 2);
        ScrapMetal metal2 = new ScrapMetal("Scrap Metal", "More old metal", 3);


        inventory.addItem(ironSword);
        inventory.addItem(healthPotion);
        inventory.addItem(genericItem);
        inventory.addItem(metal1);
        inventory.addItem(metal2);

        inventory.displayInventory();

        inventory.equipItem(ironSword);

        inventory.displayInventory();

        inventory.consumeItem(healthPotion);

        inventory.displayInventory();

        inventory.unequipItem("weapon");

        inventory.displayInventory();

        inventory.combineItems(metal1, metal2);

        inventory.displayInventory();

        inventory.combineItems(metal1, genericItem); // Should fail.

    }
}
```