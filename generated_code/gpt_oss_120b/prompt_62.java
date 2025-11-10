```java
import java.util.*;

public class TextAdventure {
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
        player.setCurrentRoom(rooms.get("Entrance"));
    }

    public void start() {
        System.out.println("Welcome to the Adventure!");
        while (true) {
            System.out.println("\n" + player.getCurrentRoom().getLongDescription());
            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("quit") || input.equals("exit")) {
                System.out.println("Goodbye!");
                break;
            }
            handleCommand(input);
        }
    }

    private void handleCommand(String input) {
        String[] parts = input.split("\\s+", 2);
        String verb = parts[0];
        String noun = parts.length > 1 ? parts[1] : "";

        switch (verb) {
            case "go":
                if (!noun.isEmpty()) player.move(noun);
                else System.out.println("Go where?");
                break;
            case "look":
                System.out.println(player.getCurrentRoom().getLongDescription());
                break;
            case "take":
                if (!noun.isEmpty()) player.take(noun);
                else System.out.println("Take what?");
                break;
            case "inventory":
                player.showInventory();
                break;
            case "use":
                if (!noun.isEmpty()) player.use(noun);
                else System.out.println("Use what?");
                break;
            case "solve":
                if (!noun.isEmpty()) player.solvePuzzle(noun);
                else System.out.println("Solve what?");
                break;
            default:
                System.out.println("I don't understand that command.");
        }
    }

    private void createWorld() {
        // Rooms
        Room entrance = new Room("Entrance",
                "You stand at the entrance of a dimly lit cave.");
        Room hall = new Room("Hall",
                "A narrow hall stretches before you, walls covered in ancient glyphs.");
        Room chamber = new Room("Chamber",
                "A spacious chamber with a pedestal in the center.");
        Room treasury = new Room("Treasury",
                "Gold and jewels glitter in the faint light.");

        // Connections
        entrance.setExit("north", hall);
        hall.setExit("south", entrance);
        hall.setExit("east", chamber);
        chamber.setExit("west", hall);
        chamber.setExit("north", treasury);
        treasury.setExit("south", chamber);

        // Items
        Item torch = new Item("torch", "A wooden torch. It might be useful.");
        Item key = new Item("key", "A small iron key.");
        Item gem = new Item("gem", "A sparkling red gem.");

        entrance.addItem(torch);
        hall.addItem(key);
        treasury.addItem(gem);

        // Puzzles
        Puzzle glyphPuzzle = new Puzzle(
                "The glyphs form a riddle: 'I speak without a mouth and hear without ears. What am I?'",
                "echo"
        );
        hall.setPuzzle(glyphPuzzle);

        Puzzle pedestalPuzzle = new Puzzle(
                "The pedestal has a slot shaped like a key. Insert the key? (yes/no)",
                "yes"
        );
        chamber.setPuzzle(pedestalPuzzle);

        // Register rooms
        rooms.put("Entrance", entrance);
        rooms.put("Hall", hall);
        rooms.put("Chamber", chamber);
        rooms.put("Treasury", treasury);
    }
}

class Player {
    private Room currentRoom;
    private final List<Item> inventory = new ArrayList<>();

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void move(String direction) {
        Room next = currentRoom.getExit(direction);
        if (next == null) {
            System.out.println("You can't go that way.");
        } else if (next.getPuzzle() != null && !next.getPuzzle().isSolved()) {
            System.out.println("A puzzle blocks your way:");
            System.out.println(next.getPuzzle().getDescription());
        } else {
            currentRoom = next;
            System.out.println("You move " + direction + ".");
        }
    }

    public void take(String itemName) {
        Item item = currentRoom.removeItem(itemName);
        if (item == null) {
            System.out.println("There's no " + itemName + " here.");
        } else {
            inventory.add(item);
            System.out.println("You take the " + item.getName() + ".");
        }
    }

    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("You are carrying:");
            for (Item i : inventory) {
                System.out.println("- " + i.getName() + ": " + i.getDescription());
            }
        }
    }

    public void use(String itemName) {
        Item item = findInInventory(itemName);
        if (item == null) {
            System.out.println("You don't have a " + itemName + ".");
            return;
        }

        // Example usage: using key on pedestal puzzle
        Puzzle puzzle = currentRoom.getPuzzle();
        if (puzzle != null && !puzzle.isSolved() && item.getName().equals("key")) {
            System.out.println("You try using the key...");
            puzzle.attempt("yes");
            if (puzzle.isSolved()) {
                System.out.println("The pedestal accepts the key and reveals a hidden passage!");
                // Unlock a secret exit (for demo, open north to Treasury)
                Room treasury = currentRoom.getExit("north");
                if (treasury != null) {
                    currentRoom.setExit("north", treasury);
                }
            }
        } else {
            System.out.println("You can't use that here.");
        }
    }

    public void solvePuzzle(String answer) {
        Puzzle puzzle = currentRoom.getPuzzle();
        if (puzzle == null) {
            System.out.println("There's no puzzle here.");
            return;
        }
        if (puzzle.isSolved()) {
            System.out.println("You've already solved this puzzle.");
            return;
        }
        puzzle.attempt(answer);
        if (puzzle.isSolved()) {
            System.out.println("Puzzle solved!");
        } else {
            System.out.println("Incorrect answer.");
        }
    }

    private Item findInInventory(String name) {
        for (Item i : inventory) {
            if (i.getName().equalsIgnoreCase(name)) return i;
        }
        return null;
    }
}

class Room {
    private final String name;
    private final String description;
    private final Map<String, Room> exits = new HashMap<>();
    private final List<Item> items = new ArrayList<>();
    private Puzzle puzzle;

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

    public void addItem(Item item) {
        items.add(item);
    }

    public Item removeItem(String name) {
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

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public String getLongDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("== ").append(name).append(" ==\n");
        sb.append(description).append("\n");
        if (!items.isEmpty()) {
            sb.append("You see:\n");
            for (Item i : items) {
                sb.append("- ").append(i.getName