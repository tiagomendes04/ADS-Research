```java
package game.inventory;

import java.util.*;
import java.util.function.BiFunction;

/* ---------- Item hierarchy ---------- */
public interface Item {
    String getName();
    String getDescription();
    boolean isEquipable();
    boolean isConsumable();
    Optional<Item> combine(Item other);
}

/* ---------- Base implementation ---------- */
abstract class BaseItem implements Item {
    private final String name;
    private final String description;

    protected BaseItem(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override public String getName() { return name; }
    @Override public String getDescription() { return description; }
    @Override public boolean isEquipable() { return false; }
    @Override public boolean isConsumable() { return false; }
    @Override public Optional<Item> combine(Item other) { return CombinationRegistry.combine(this, other); }
}

/* ---------- Equipable item ---------- */
class EquipableItem extends BaseItem {
    private final Slot slot;
    private final Map<Stat, Integer> bonuses;

    public EquipableItem(String name, String description, Slot slot, Map<Stat, Integer> bonuses) {
        super(name, description);
        this.slot = slot;
        this.bonuses = new EnumMap<>(bonuses);
    }

    @Override public boolean isEquipable() { return true; }
    public Slot getSlot() { return slot; }
    public Map<Stat, Integer> getBonuses() { return Collections.unmodifiableMap(bonuses); }
}

/* ---------- Consumable item ---------- */
class ConsumableItem extends BaseItem {
    private final BiFunction<Player, ConsumableItem, Void> effect;

    public ConsumableItem(String name, String description,
                          BiFunction<Player, ConsumableItem, Void> effect) {
        super(name, description);
        this.effect = effect;
    }

    @Override public boolean isConsumable() { return true; }
    public void apply(Player player) { effect.apply(player, this); }
}

/* ---------- Combined item (result of a recipe) ---------- */
class CombinedItem extends BaseItem {
    public CombinedItem(String name, String description) {
        super(name, description);
    }
}

/* ---------- Slots & Stats enums ---------- */
enum Slot { HEAD, CHEST, LEGS, WEAPON, SHIELD, ACCESSORY }
enum Stat { ATTACK, DEFENSE, SPEED, HEALTH }

/* ---------- Combination registry ---------- */
class CombinationRegistry {
    private static final Map<Set<String>, BiFunction<Item, Item, Item>> RECIPES = new HashMap<>();

    static {
        // Example recipe: "Wood" + "Stone" => "Stone Axe"
        register("Wood", "Stone", (a, b) ->
                new EquipableItem("Stone Axe",
                        "A crude axe made from wood and stone.",
                        Slot.WEAPON,
                        Map.of(Stat.ATTACK, 5)));
        // Add more recipes here
    }

    private static void register(String nameA, String nameB,
                                 BiFunction<Item, Item, Item> creator) {
        Set<String> key = Set.of(nameA, nameB);
        RECIPES.put(key, creator);
    }

    public static Optional<Item> combine(Item a, Item b) {
        Set<String> key = Set.of(a.getName(), b.getName());
        BiFunction<Item, Item, Item> creator = RECIPES.get(key);
        if (creator != null) {
            return Optional.ofNullable(creator.apply(a, b));
        }
        return Optional.empty();
    }
}

/* ---------- Inventory ---------- */
class Inventory {
    private final List<Item> items = new ArrayList<>();

    public void add(Item item) { items.add(item); }
    public void remove(Item item) { items.remove(item); }
    public List<Item> getItems() { return Collections.unmodifiableList(items); }

    public Optional<Item> findByName(String name) {
        return items.stream().filter(i -> i.getName().equalsIgnoreCase(name)).findFirst();
    }

    public Optional<Item> combine(String nameA, String nameB) {
        Optional<Item> aOpt = findByName(nameA);
        Optional<Item> bOpt = findByName(nameB);
        if (aOpt.isEmpty() || bOpt.isEmpty()) return Optional.empty();

        Item a = aOpt.get();
        Item b = bOpt.get();

        return a.combine(b).map(result -> {
            remove(a);
            remove(b);
            add(result);
            return result;
        });
    }
}

/* ---------- Player ---------- */
class Player {
    private final Inventory inventory = new Inventory();
    private final Map<Slot, EquipableItem> equipment = new EnumMap<>(Slot.class);
    private final Map<Stat, Integer> baseStats = new EnumMap<>(Stat.class);
    private final Map<Stat, Integer> currentStats = new EnumMap<>(Stat.class);

    public Player() {
        for (Stat s : Stat.values()) {
            baseStats.put(s, 10);
            currentStats.put(s, 10);
        }
    }

    public Inventory getInventory() { return inventory; }

    public boolean equip(String itemName) {
        Optional<Item> opt = inventory.findByName(itemName);
        if (opt.isEmpty() || !opt.get().isEquipable()) return false;

        EquipableItem eq = (EquipableItem) opt.get();
        Slot slot = eq.getSlot();

        // unequip current if any
        equipment.computeIfPresent(slot, (s, old) -> {
            inventory.add(old);
            old.getBonuses().forEach((stat, val) ->
                    currentStats.put(stat, currentStats.get(stat) - val));
            return null;
        });

        // equip new
        equipment.put(slot, eq);
        eq.getBonuses().forEach((stat, val) ->
                currentStats.put(stat, currentStats.get(stat) + val));
        inventory.remove(eq);
        return true;
    }

    public boolean useConsumable(String itemName) {
        Optional<Item> opt = inventory.findByName(itemName);
        if (opt.isEmpty() || !opt.get().isConsumable()) return false;

        ConsumableItem cons = (ConsumableItem) opt.get();
        cons.apply(this);
        inventory.remove(cons);
        return true;
    }

    public void modifyStat(Stat stat, int delta) {
        currentStats.put(stat, currentStats.getOrDefault(stat, 0) + delta);
    }

    public int getStat(Stat stat) {
        return currentStats.getOrDefault(stat, 0);
    }

    // Example: print equipment & stats
    public void printStatus() {
        System.out.println("=== Equipment ===");
        equipment.forEach((s, i) -> System.out.println(s + ": " + i.getName()));
        System.out.println("=== Stats ===");
        currentStats.forEach((s, v) -> System.out.println(s + ": " + v));
    }
}

/* ---------- Demo (main) ---------- */
public class InventoryDemo {
    public static void main(String[] args) {
        Player player = new Player();

        // add raw materials
        player.getInventory().add(new BaseItem("Wood", "A piece of timber.") {});
        player.getInventory().add(new BaseItem("Stone", "A hard rock.") {});

        // combine into Stone Axe
        player.getInventory().combine("Wood", "Stone")
                .ifPresent(i -> System.out.println("Created: " + i.getName()));

        // equip the axe
        player.equip("Stone Axe");
        player.printStatus();

        // add a health potion (consumable)
        ConsumableItem potion = new ConsumableItem(
                "Health Potion",
                "Restores 20 health.",
                (p, it) -> {
                    p.modifyStat(Stat.HEALTH, 20);
                    System.out.println(p.getStat(Stat.HEALTH) + " health after using potion.");
                    return null;
                });
        player.getInventory().add(potion);

        // use potion
        player.useConsumable("Health Potion");
        player.printStatus();
    }
}
```