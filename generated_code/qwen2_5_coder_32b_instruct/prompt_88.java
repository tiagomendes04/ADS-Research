```java
import java.util.*;

class Item {
    String name;
    String description;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return name + ": " + description;
    }
}

class Room {
    String description;
    Map<String, Room> exits;
    List<Item> items;

    public Room(String description) {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
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

    public List<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return description;
    }
}

public class TextAdventureGame {
    private Room currentRoom;

    public TextAdventureGame() {
        createRooms();
    }

    private void createRooms() {
        Room outside, lobby, pub, study, bedroom;

        outside = new Room("You are outside a large, dark house. There is a door to the north.");
        lobby = new Room("You are in the lobby of the house. There are doors to the south, east, and west.");
        pub = new Room("You are in the pub. A barman is serving drinks. There is a door to the west.");
        study = new Room("You are in the study. The walls are covered with bookshelves. There is a door to the east.");
        bedroom = new Room("You are in the bedroom. There is a bed and a window.");

        outside.setExit("north", lobby);
        lobby.setExit("south", outside);
        lobby.setExit("east", pub);
        lobby.setExit("west", study);
        pub.setExit("west", lobby);
        study.setExit("east", lobby);
        study.addItem(new Item("book", "A dusty old book"));
        bedroom.addItem(new Item("key", "A shiny key"));

        currentRoom = outside;
    }

    private void printWelcome() {
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom);
    }

    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander around the house.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("go quit help look take inventory");
    }

    private void goRoom(String direction) {
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom);
            System.out.print("Items: ");
            if (currentRoom.getItems().isEmpty()) {
                System.out.println("None");
            } else {
                for (Item item : currentRoom.getItems()) {
                    System.out.print(item + " ");
                }
                System.out.println();
            }
        }
    }

    private void look() {
        System.out.println(currentRoom);
        System.out.print("Items: ");
        if (currentRoom.getItems().isEmpty()) {
            System.out.println("None");
        } else {
            for (Item item : currentRoom.getItems()) {
                System.out.print(item + " ");
            }
            System.out.println();
        }
    }

    private void takeItem(String itemName) {
        Iterator<Item> it = currentRoom.getItems().iterator();
        while (it.hasNext()) {
            Item item = it.next();
            if (item.name.equals(itemName)) {
                it.remove();
                System.out.println("You took the " + itemName);
                return;
            }
        }
        System.out.println("There is no " + itemName + " here.");
    }

    private void inventory(List<Item> inventory) {
        if (inventory.isEmpty()) {
            System.out.println("You have nothing.");
        } else {
            System.out.print("Inventory: ");
            for (Item item : inventory) {
                System.out.print(item + " ");
            }
            System.out.println();
        }
    }

    private boolean processCommand(Command command, List<Item> inventory) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        } else if (commandWord.equals("go")) {
            goRoom(command.getSecondWord());
        } else if (commandWord.equals("quit")) {
            wantToQuit = true;
        } else if (commandWord.equals("look")) {
            look();
        } else if (commandWord.equals("take")) {
            takeItem(command.getSecondWord());
        } else if (commandWord.equals("inventory")) {
            inventory(inventory);
        }
        return wantToQuit;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TextAdventureGame game = new TextAdventureGame();
        List<Item> inventory = new ArrayList<>();
        game.printWelcome();

        while (true) {
            Command command = Parser.parseCommand(scanner.nextLine(), inventory);
            if (game.processCommand(command, inventory)) {
                break;
            }
        }
        System.out.println("Thank you for playing. Goodbye.");
    }
}

class Command {
    private String commandWord;
    private String secondWord;

    public Command(String firstWord, String secondWord) {
        this.commandWord = firstWord;
        this.secondWord = secondWord;
    }

    public String getCommandWord() {
        return commandWord;
    }

    public String getSecondWord() {
        return secondWord;
    }

    public boolean isUnknown() {
        return commandWord == null;
    }

    public boolean hasSecondWord() {
        return secondWord != null;
    }
}

class Parser {
    private static final Set<String> validCommands = new HashSet<>(Arrays.asList("go", "quit", "help", "look", "take", "inventory"));

    public static Command parseCommand(String inputLine, List<Item> inventory) {
        String[] words = inputLine.trim().toLowerCase().split("\\s+");
        String word1 = null;
        String word2 = null;
        if (words.length >= 1) {
            word1 = words[0];
        }
        if (words.length >= 2) {
            word2 = words[1];
        }

        if (validCommands.contains(word1)) {
            return new Command(word1, word2);
        } else if (word1.equals("take") && inventory.contains(new Item(word2, ""))) {
            System.out.println("You already have that item.");
        } else {
            return new Command(null, word2);
        }
        return null;
    }
}
```