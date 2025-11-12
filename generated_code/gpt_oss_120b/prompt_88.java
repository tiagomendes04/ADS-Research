```java
import java.util.*;

public class TextAdventure {

    public static void main(String[] args) {
        new Game().start();
    }
}

class Game {
    private final Scanner scanner = new Scanner(System.in);
    private final Map<String, Room> rooms = new HashMap<>();
    private final List<Item> inventory = new ArrayList<>();
    private Room currentRoom;

    void start() {
        initWorld();
        printWelcome();

        while (true) {
            System.out.print("\n> ");
            String line = scanner.nextLine().trim().toLowerCase();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+", 2);
            String verb = parts[0];
            String noun = parts.length > 1 ? parts[1] : "";

            switch (verb) {
                case "go":
                case "move":
                    go(noun);
                    break;
                case "look":
                case "l":
                    look();
                    break;
                case "take":
                case "get":
                    take(noun);
                    break;
                case "drop":
                    drop(noun);
                    break;
                case "inventory":
                case "i":
                    showInventory();
                    break;
                case "quit":
                case "exit":
                    System.out.println("Goodbye!");
                    return;
                case "help":
                    showHelp();
                    break;
                default:
                    System.out.println("I don't understand that command.");
            }
        }
    }

    private void initWorld() {
        Room foyer = new Room("Foyer", "A small entrance hall with a dusty chandelier.");
        Room kitchen = new Room("Kitchen", "A kitchen with a lingering smell of spices.");
        Room library = new Room("Library", "Rows of ancient books line the walls.");
        Room garden = new Room("Garden", "A tranquil garden with blooming flowers.");

        foyer.setExit("north", library);
        foyer.setExit("east", kitchen);
        kitchen.setExit("west", foyer);
        library.setExit("south", foyer);
        library.setExit("east", garden);
        garden.setExit("west", library);

        foyer.addItem(new Item("key", "A small rusty key."));
        kitchen.addItem(new Item("apple", "A fresh red apple."));
        library.addItem(new Item("book", "An old tome titled 'Adventures in Code'."));
        garden.addItem(new Item("flower", "A beautiful blue flower."));

        rooms.put("foyer", foyer);
        rooms.put("kitchen", kitchen);
        rooms.put("library", library);
        rooms.put("garden", garden);

        currentRoom = foyer;
    }

    private void printWelcome() {
        System.out.println("Welcome to the Text Adventure!");
        System.out.println("Type 'help' for a list of commands.\n");
        look();
    }

    private void go(String direction) {
        if (direction.isEmpty()) {
            System.out.println("Go where?");
            return;
        }
        Room next = currentRoom.getExit(direction);
        if (next == null) {
            System.out.println("You can't go that way.");
        } else {
            currentRoom = next;
            look();
        }
    }

    private void look() {
        System.out.println("\n== " + currentRoom.getName() + " ==");
        System.out.println(currentRoom.getDescription());
        if (!currentRoom.getItems().isEmpty()) {
            System.out.println("You see:");
            for (Item item : currentRoom.getItems()) {
                System.out.println(" - " + item.getName() + ": " + item.getDescription());
            }
        }
        if (!currentRoom.getExits().isEmpty()) {
            System.out.print("Exits: ");
            System.out.println(String.join(", ", currentRoom.getExits().keySet()));
        }
    }

    private void take(String itemName) {
        if (itemName.isEmpty()) {
            System.out.println("Take what?");
            return;
        }
        Item item = currentRoom.removeItem(itemName);
        if (item == null) {
            System.out.println("There is no such item here.");
        } else {
            inventory.add(item);
            System.out.println("You take the " + item.getName() + ".");
        }
    }

    private void drop(String itemName) {
        if (itemName.isEmpty()) {
            System.out.println("Drop what?");
            return;
        }
        Item toDrop = null;
        for (Item i : inventory) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                toDrop = i;
                break;
            }
        }
        if (toDrop == null) {
            System.out.println("You don't have that item.");
        } else {
            inventory.remove(toDrop);
            currentRoom.addItem(toDrop);
            System.out.println("You drop the " + toDrop.getName() + ".");
        }
    }

    private void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("You are not carrying anything.");
        } else {
            System.out.println("You are carrying:");
            for (Item item : inventory) {
                System.out.println(" - " + item.getName() + ": " + item.getDescription());
            }
        }
    }

    private void showHelp() {
        System.out.println("Available commands:");
        System.out.println("  go <direction>   - Move to another room (north, south, east, west)");
        System.out.println("  look             - Look around the current room");
        System.out.println("  take <item>      - Pick up an item");
        System.out.println("  drop <item>      - Drop an item from inventory");
        System.out.println("  inventory (i)    - Show carried items");
        System.out.println("  quit (exit)      - Exit the game");
        System.out.println("  help             - Show this help message");
    }
}

class Room {
    private final String name;
    private final String description;
    private final Map<String, Room> exits = new HashMap<>();
    private final List<Item> items = new ArrayList<>();

    Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    void setExit(String direction, Room neighbor) {
        exits.put(direction.toLowerCase(), neighbor);
    }

    Room getExit(String direction) {
        return exits.get(direction.toLowerCase());
    }

    Map<String, Room> getExits() {
        return Collections.unmodifiableMap(exits);
    }

    void addItem(Item item) {
        items.add(item);
    }

    Item removeItem(String name) {
        Iterator<Item> it = items.iterator();
        while (it.hasNext()) {
            Item i = it.next();
            if (i.getName().equalsIgnoreCase(name)) {
                it.remove();
                return i;
            }
        }
        return null;
    }

    List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    String getName() {
        return name;
    }

    String getDescription() {
        return description;
    }
}

class Item {
    private final String name;
    private final String description;

    Item(String name, String description) {
        this.name = name.toLowerCase();
        this.description = description;
    }

    String getName() {
        return name;
    }

    String getDescription() {
        return description;
    }
}
```