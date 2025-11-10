```java
import java.util.*;

public class Adventure {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}

class Game {
    private final Scanner scanner = new Scanner(System.in);
    private final Player player = new Player();
    private final Map<String, Room> rooms = new HashMap<>();

    public Game() {
        createWorld();
    }

    private void createWorld() {
        Room foyer = new Room("Foyer", "You are standing in a small foyer. A dusty chandelier hangs above.");
        Room library = new Room("Library", "Rows of ancient books line the walls.");
        Room kitchen = new Room("Kitchen", "The kitchen smells of stale bread.");
        Room garden = new Room("Garden", "A quiet garden with overgrown paths.");

        foyer.setExit("north", library);
        foyer.setExit("east", kitchen);
        library.setExit("south", foyer);
        kitchen.setExit("west", foyer);
        kitchen.setExit("north", garden);
        garden.setExit("south", kitchen);

        Item key = new Item("key", "A small rusty key.");
        Item book = new Item("book", "An old tome titled 'Secrets of the Ancients'.");
        Item apple = new Item("apple", "A fresh red apple.");

        foyer.addItem(key);
        library.addItem(book);
        kitchen.addItem(apple);

        rooms.put("foyer", foyer);
        rooms.put("library", library);
        rooms.put("kitchen", kitchen);
        rooms.put("garden", garden);

        player.setCurrentRoom(foyer);
    }

    public void start() {
        System.out.println("Welcome to the Text Adventure!");
        System.out.println("Type 'help' for a list of commands.\n");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.isEmpty()) continue;
            if (input.equals("quit") || input.equals("exit")) {
                System.out.println("Goodbye!");
                break;
            }
            processCommand(input);
        }
    }

    private void processCommand(String input) {
        String[] parts = input.split("\\s+", 2);
        String verb = parts[0];
        String noun = parts.length > 1 ? parts[1] : "";

        switch (verb) {
            case "help":
                printHelp();
                break;
            case "look":
                player.look();
                break;
            case "go":
                if (noun.isEmpty()) {
                    System.out.println("Go where?");
                } else {
                    player.move(noun);
                }
                break;
            case "take":
                if (noun.isEmpty()) {
                    System.out.println("Take what?");
                } else {
                    player.take(noun);
                }
                break;
            case "drop":
                if (noun.isEmpty()) {
                    System.out.println("Drop what?");
                } else {
                    player.drop(noun);
                }
                break;
            case "inventory":
            case "i":
                player.showInventory();
                break;
            case "examine":
                if (noun.isEmpty()) {
                    System.out.println("Examine what?");
                } else {
                    player.examine(noun);
                }
                break;
            default:
                System.out.println("I don't understand that command.");
        }
    }

    private void printHelp() {
        System.out.println("Available commands:");
        System.out.println("  look               - Look around the current room");
        System.out.println("  go <direction>     - Move north, south, east, or west");
        System.out.println("  take <item>        - Pick up an item");
        System.out.println("  drop <item>        - Drop an item from inventory");
        System.out.println("  examine <item>     - Look at an item in detail");
        System.out.println("  inventory (i)      - Show your inventory");
        System.out.println("  help               - Show this help message");
        System.out.println("  quit / exit        - Exit the game");
    }
}

class Player {
    private Room currentRoom;
    private final List<Item> inventory = new ArrayList<>();

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
        look();
    }

    public void look() {
        System.out.println("\n" + currentRoom.getName());
        System.out.println(currentRoom.getDescription());
        if (!currentRoom.getItems().isEmpty()) {
            System.out.print("You see: ");
            for (Item item : currentRoom.getItems()) {
                System.out.print(item.getName() + " ");
            }
            System.out.println();
        }
        if (!currentRoom.getExits().isEmpty()) {
            System.out.print("Exits: ");
            for (String dir : currentRoom.getExits().keySet()) {
                System.out.print(dir + " ");
            }
            System.out.println();
        }
    }

    public void move(String direction) {
        Room next = currentRoom.getExit(direction);
        if (next == null) {
            System.out.println("You can't go that way.");
        } else {
            currentRoom = next;
            look();
        }
    }

    public void take(String itemName) {
        Item item = currentRoom.removeItem(itemName);
        if (item == null) {
            System.out.println("There is no " + itemName + " here.");
        } else {
            inventory.add(item);
            System.out.println("You take the " + item.getName() + ".");
        }
    }

    public void drop(String itemName) {
        Item item = removeFromInventory(itemName);
        if (item == null) {
            System.out.println("You don't have a " + itemName + ".");
        } else {
            currentRoom.addItem(item);
            System.out.println("You drop the " + item.getName() + ".");
        }
    }

    public void examine(String itemName) {
        Item item = findInInventory(itemName);
        if (item == null) {
            item = currentRoom.findItem(itemName);
        }
        if (item == null) {
            System.out.println("You see no " + itemName + " here.");
        } else {
            System.out.println(item.getDescription());
        }
    }

    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("You are not carrying anything.");
        } else {
            System.out.print("You are carrying: ");
            for (Item item : inventory) {
                System.out.print(item.getName() + " ");
            }
            System.out.println();
        }
    }

    private Item findInInventory(String name) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(name)) return item;
        }
        return null;
    }

    private Item removeFromInventory(String name) {
        Iterator<Item> it = inventory.iterator();
        while (it.hasNext()) {
            Item item = it.next();
            if (item.getName().equalsIgnoreCase(name)) {
                it.remove();
                return item;
            }
        }
        return null;
    }
}

class Room {
    private final String name;
    private final String description;
    private final Map<String, Room> exits = new HashMap<>();
    private final List<Item> items = new ArrayList<>();

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction.toLowerCase(), neighbor);
    }

    public Room getExit(String direction) {
        return exits.get(direction.toLowerCase());
    }

    public Map<String, Room> getExits() {
        return Collections.unmodifiableMap(exits);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item removeItem(String name) {
        Iterator<Item> it = items.iterator();
        while (it.hasNext()) {
            Item item = it.next();
            if (item.getName().equalsIgnoreCase(name)) {
                it.remove();
                return item;
            }
        }
        return null;
    }

    public Item findItem(String name) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) return item;
        }
        return null;
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public String getName() {
        return name;
    }

    public String getDescription()