```java
import java.util.*;

class Game {
    private Room currentRoom;
    private Map<String, Room> rooms;
    private Inventory inventory;

    public Game() {
        rooms = new HashMap<>();
        inventory = new Inventory();
        createRooms();
        currentRoom = rooms.get("Entrance");
    }

    private void createRooms() {
        Room entrance = new Room("Entrance", "You are at the entrance of a dark castle. There is a door to the north.");
        Room hall = new Room("Hall", "A long, narrow hallway. There are doors to the east and west.");
        Room kitchen = new Room("Kitchen", "A smelly kitchen filled with various cooking utensils and ingredients.");
        Room bedroom = new Room("Bedroom", "A cozy bedroom with a bed, a chest, and a window.");

        Item key = new Item("Key", "A rusty iron key.");
        Item hammer = new Item("Hammer", "A sturdy hammer.");
        Puzzle puzzle = new Puzzle("Chest", "The chest is locked. You need a key to open it.", "key");

        kitchen.addItem(hammer);
        bedroom.addItem(key);
        bedroom.addPuzzle(puzzle);

        entrance.setExit("north", hall);
        hall.setExit("east", kitchen);
        hall.setExit("west", bedroom);

        rooms.put("Entrance", entrance);
        rooms.put("Hall", hall);
        rooms.put("Kitchen", kitchen);
        rooms.put("Bedroom", bedroom);
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        String command = "";

        while (!command.equals("quit")) {
            System.out.println(currentRoom.getDescription());
            if (currentRoom.getPuzzles().size() > 0) {
                System.out.println("Puzzles: " + currentRoom.getPuzzlesString());
            }
            if (currentRoom.getItems().size() > 0) {
                System.out.println("Items: " + currentRoom.getItemsString());
            }
            System.out.print("> ");
            command = scanner.nextLine().toLowerCase();

            switch (command) {
                case "go":
                    goRoom(scanner.next());
                    break;
                case "take":
                    takeItem(scanner.next());
                    break;
                case "use":
                    useItem(scanner.next());
                    break;
                case "look":
                    lookAround();
                    break;
                case "inventory":
                    checkInventory();
                    break;
                case "quit":
                    System.out.println("Thank you for playing!");
                    break;
                default:
                    System.out.println("Unknown command. Try 'go', 'take', 'use', 'look', 'inventory', or 'quit'.");
            }
        }
    }

    private void goRoom(String direction) {
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("There is no door in that direction.");
        } else {
            currentRoom = nextRoom;
        }
    }

    private void takeItem(String itemName) {
        Item item = currentRoom.removeItem(itemName);
        if (item != null) {
            inventory.addItem(item);
            System.out.println("You took the " + item.getName() + ".");
        } else {
            System.out.println("There is no " + itemName + " here.");
        }
    }

    private void useItem(String itemName) {
        Item item = inventory.removeItem(itemName);
        if (item != null) {
            boolean solved = false;
            for (Puzzle puzzle : currentRoom.getPuzzles()) {
                if (puzzle.solve(item)) {
                    System.out.println("You solved the puzzle with the " + item.getName() + ": " + puzzle.getDescription());
                    solved = true;
                    break;
                }
            }
            if (!solved) {
                System.out.println("There is nothing to use the " + item.getName() + " on.");
                inventory.addItem(item);
            }
        } else {
            System.out.println("You don't have the " + itemName + " in your inventory.");
        }
    }

    private void lookAround() {
        System.out.println(currentRoom.getDescription());
        if (currentRoom.getPuzzles().size() > 0) {
            System.out.println("Puzzles: " + currentRoom.getPuzzlesString());
        }
        if (currentRoom.getItems().size() > 0) {
            System.out.println("Items: " + currentRoom.getItemsString());
        }
    }

    private void checkInventory() {
        System.out.println("Inventory: " + inventory.getItemsString());
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}

class Room {
    private String name;
    private String description;
    private Map<String, Room> exits;
    private List<Item> items;
    private List<Puzzle> puzzles;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
        puzzles = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        StringBuilder sb = new StringBuilder(description);
        sb.append("\nExits: ");
        for (String exit : exits.keySet()) {
            sb.append(exit).append(" ");
        }
        return sb.toString().trim();
    }

    public void setExit(String direction, Room room) {
        exits.put(direction, room);
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item removeItem(String itemName) {
        for (Iterator<Item> it = items.iterator(); it.hasNext(); ) {
            Item item = it.next();
            if (item.getName().equalsIgnoreCase(itemName)) {
                it.remove();
                return item;
            }
        }
        return null;
    }

    public String getItemsString() {
        StringBuilder sb = new StringBuilder();
        for (Item item : items) {
            sb.append(item.getName()).append(", ");
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 2) : "none";
    }

    public void addPuzzle(Puzzle puzzle) {
        puzzles.add(puzzle);
    }

    public List<Puzzle> getPuzzles() {
        return puzzles;
    }

    public String getPuzzlesString() {
        StringBuilder sb = new StringBuilder();
        for (Puzzle puzzle : puzzles) {
            sb.append(puzzle.getName()).append(", ");
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 2) : "none";
    }
}

class Item {
    private String name;
    private String description;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

class Puzzle {
    private String name;
    private String description;
    private String solution;

    public Puzzle(String name, String description, String solution) {
        this.name = name;
        this.description = description;
        this.solution = solution;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean solve(Item item) {
        return item.getName().equalsIgnoreCase(solution);
    }
}

class Inventory {
    private List<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item removeItem(String itemName) {
        for (Iterator<Item> it = items.iterator(); it.hasNext(); ) {
            Item item = it.next();
            if (item.getName().equalsIgnoreCase(itemName)) {
                it.remove();
                return item;
            }
        }
        return null;
    }

    public String getItemsString() {
        StringBuilder sb = new StringBuilder();
        for (Item item : items) {
            sb.append(item.getName()).append(", ");
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 2) : "empty";
    }
}
```