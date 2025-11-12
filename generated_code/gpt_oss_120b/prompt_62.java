```java
import java.util.*;

public class AdventureGame {
    public static void main(String[] args) {
        new Game().start();
    }
}

/* Core game engine */
class Game {
    private final Scanner scanner = new Scanner(System.in);
    private final Map<String, Room> rooms = new HashMap<>();
    private final Player player = new Player();

    void start() {
        initWorld();
        System.out.println("Welcome to the Adventure!");
        System.out.println("Type 'help' for a list of commands.\n");
        player.setCurrentRoom(rooms.get("Entrance"));
        loop();
    }

    private void loop() {
        while (true) {
            System.out.print("\n> ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.isEmpty()) continue;

            String[] parts = input.split("\\s+", 2);
            String verb = parts[0];
            String rest = parts.length > 1 ? parts[1] : "";

            switch (verb) {
                case "go":
                    move(rest);
                    break;
                case "look":
                    look();
                    break;
                case "take":
                    take(rest);
                    break;
                case "inventory":
                case "i":
                    player.showInventory();
                    break;
                case "solve":
                    solve(rest);
                    break;
                case "help":
                    showHelp();
                    break;
                case "quit":
                case "exit":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("I don't understand that command.");
            }
        }
    }

    private void move(String direction) {
        if (direction.isEmpty()) {
            System.out.println("Go where?");
            return;
        }
        Room next = player.getCurrentRoom().getExit(direction);
        if (next == null) {
            System.out.println("You can't go that way.");
        } else if (next.isLocked()) {
            System.out.println("The door is locked.");
        } else {
            player.setCurrentRoom(next);
            look();
        }
    }

    private void look() {
        Room r = player.getCurrentRoom();
        System.out.println("\n" + r.getName());
        System.out.println(r.getDescription());

        if (!r.getItems().isEmpty()) {
            System.out.println("You see:");
            for (Item i : r.getItems()) {
                System.out.println(" - " + i.getName());
            }
        }

        if (!r.getExits().isEmpty()) {
            System.out.print("Exits: ");
            System.out.println(String.join(", ", r.getExits().keySet()));
        }

        if (r.getPuzzle() != null && !r.getPuzzle().isSolved()) {
            System.out.println("A puzzle lies here: " + r.getPuzzle().getPrompt());
        }
    }

    private void take(String itemName) {
        if (itemName.isEmpty()) {
            System.out.println("Take what?");
            return;
        }
        Room r = player.getCurrentRoom();
        Item item = r.removeItem(itemName);
        if (item == null) {
            System.out.println("There's no such item here.");
        } else {
            player.addItem(item);
            System.out.println("You took the " + item.getName() + ".");
        }
    }

    private void solve(String answer) {
        Room r = player.getCurrentRoom();
        Puzzle p = r.getPuzzle();
        if (p == null) {
            System.out.println("There's nothing to solve here.");
            return;
        }
        if (p.isSolved()) {
            System.out.println("You've already solved this puzzle.");
            return;
        }
        if (p.attempt(answer)) {
            System.out.println("Correct! " + p.getSuccessMessage());
            if (p.getRewardItem() != null) {
                r.addItem(p.getRewardItem());
                System.out.println("A " + p.getRewardItem().getName() + " appears.");
            }
            if (p.getUnlockRoom() != null) {
                p.getUnlockRoom().setLocked(false);
                System.out.println("A hidden passage opens somewhere.");
            }
        } else {
            System.out.println("Incorrect. Try again.");
        }
    }

    private void showHelp() {
        System.out.println("Commands:");
        System.out.println("  go <direction>   - Move north, south, east, west");
        System.out.println("  look             - Look around");
        System.out.println("  take <item>      - Pick up an item");
        System.out.println("  inventory (i)    - Show your items");
        System.out.println("  solve <answer>   - Attempt to solve a puzzle");
        System.out.println("  help             - Show this help");
        System.out.println("  quit/exit        - End the game");
    }

    private void initWorld() {
        // Rooms
        Room entrance = new Room("Entrance", "You stand at the entrance of a dimly lit cave.");
        Room hallway = new Room("Hallway", "A narrow hallway stretches before you.");
        Room chamber = new Room("Chamber", "A large chamber with ancient markings on the walls.");
        Room treasure = new Room("Treasure Room", "Glittering treasure piles up in the center.");
        treasure.setLocked(true); // locked initially

        // Exits
        entrance.addExit("north", hallway);
        hallway.addExit("south", entrance);
        hallway.addExit("east", chamber);
        chamber.addExit("west", hallway);
        chamber.addExit("north", treasure);
        treasure.addExit("south", chamber);

        // Items
        Item torch = new Item("torch", "A wooden torch. It might be useful.");
        entrance.addItem(torch);

        Item key = new Item("silver key", "A small silver key, perhaps it opens something.");
        chamber.addItem(key);

        // Puzzles
        Puzzle riddle = new Puzzle(
                "I speak without a mouth and hear without ears. I have nobody, but I come alive with wind. What am I?",
                "echo",
                "A hidden compartment slides open, revealing a silver key.",
                key,
                null
        );
        hallway.setPuzzle(riddle);

        Puzzle gate = new Puzzle(
                "What walks on four legs in the morning, two legs at noon, and three legs in the evening?",
                "man",
                "The stone gate grinds open.",
                null,
                treasure
        );
        chamber.setPuzzle(gate);

        // Register rooms
        rooms.put("Entrance", entrance);
        rooms.put("Hallway", hallway);
        rooms.put("Chamber", chamber);
        rooms.put("Treasure Room", treasure);
    }
}

/* Player */
class Player {
    private Room currentRoom;
    private final List<Item> inventory = new ArrayList<>();

    Room getCurrentRoom() {
        return currentRoom;
    }

    void setCurrentRoom(Room room) {
        this.currentRoom = room;
        System.out.println("\nYou enter: " + room.getName());
    }

    void addItem(Item item) {
        inventory.add(item);
    }

    void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("You are carrying nothing.");
        } else {
            System.out.println("You have:");
            for (Item i : inventory) {
                System.out.println(" - " + i.getName() + ": " + i.getDescription());
            }
        }
    }
}

/* Room */
class Room {
    private final String name;
    private final String description;
    private final Map<String, Room> exits = new HashMap<>();
    private final List<Item> items = new ArrayList<>();
    private Puzzle puzzle;
    private boolean locked = false;

    Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    String getName() {
        return name;
    }

    String getDescription() {
        return description;
    }

    void addExit(String direction, Room room) {
        exits.put(direction, room);
    }

    Map<String, Room> getExits() {
        return exits;
    }

    Room getExit(String direction) {
        return exits.get(direction);
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
        return items;
    }

    void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    Puzzle getPuzzle() {
        return puzzle;
    }

    boolean isLocked() {
        return locked;
    }

    void setLocked(boolean locked) {
        this.locked = locked;
    }
}

/* Item */
class Item {
    private final String name;
    private final String description;

    Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    String getName() {
        return name;
    }

    String getDescription() {
        return description;
    }
}

/* Puzzle */
class Puzzle {
    private final String prompt;
    private final String answer;
    private final String successMessage;
    private final Item rewardItem;
    private final Room unlockRoom;
    private boolean solved = false;

    Puzzle(String prompt, String answer, String successMessage, Item rewardItem, Room unlockRoom) {
        this.prompt = prompt;
        this.answer = answer.toLowerCase();
        this.successMessage = successMessage;
        this.rewardItem = rewardItem;
        this.unlockRoom = unlockRoom;
    }

    String getPrompt() {
        return prompt;
    }

    String getSuccessMessage() {
        return successMessage;
    }

    Item getRewardItem() {
        return rewardItem;
    }

    Room getUnlockRoom() {
        return unlockRoom;
    }

    boolean isSolved() {
        return solved;
    }

    boolean attempt(String attempt) {
        if (solved) return true;
        if (attempt.trim().toLowerCase().equals(answer)) {
            solved = true;
            return true;
        }
        return false;
    }
}
```