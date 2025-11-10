```java
import java.util.*;

class Room {
    String description;
    Map<String, Room> exits;
    List<Item> items;

    public Room(String description) {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item getItem(String itemName) {
        for (Item item : items) {
            if (item.name.equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public String getLongDescription() {
        return "You are " + description + ".\nExits: " + exits.keySet().toString() + "\nItems: " + items.toString();
    }
}

class Item {
    String name;
    String description;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }
}

public class TextAdventure {
    private Room currentRoom;
    private Map<String, Item> inventory;

    public TextAdventure() {
        createRooms();
        inventory = new HashMap<>();
    }

    private void createRooms() {
        Room outside, lobby, pub, study, bedroom;

        outside = new Room("outside the main entrance of the university");
        lobby = new Room("in the lobby of the main building");
        pub = new Room("in the campus pub");
        study = new Room("in a cozy study room");
        bedroom = new Room("in your bedroom");

        outside.setExit("east", lobby);
        outside.setExit("south", study);
        outside.setExit("west", pub);

        lobby.setExit("west", outside);

        pub.setExit("east", outside);

        study.setExit("north", outside);
        study.setExit("east", bedroom);

        bedroom.setExit("west", study);

        Item key = new Item("key", "a small brass key");
        Item pencil = new Item("pencil", "a sharp pencil");
        Item book = new Item("book", "an old, dusty book");

        study.addItem(key);
        pub.addItem(pencil);
        bedroom.addItem(book);

        currentRoom = outside;
    }

    private void printHelp() {
        System.out.println("Available commands are:");
        System.out.println("go quit help take look");
    }

    private boolean goRoom(String direction) {
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
        return false;
    }

    private boolean takeItem(String itemName) {
        Item item = currentRoom.getItem(itemName);
        if (item != null) {
            inventory.put(item.name, item);
            currentRoom.removeItem(item);
            System.out.println("You have taken the " + itemName);
        } else {
            System.out.println("There is no " + itemName + " here.");
        }
        return false;
    }

    private boolean lookItem() {
        System.out.println(currentRoom.getLongDescription());
        return false;
    }

    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;
        }
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        } else if (commandWord.equals("go")) {
            wantToQuit = goRoom(command.getSecondWord());
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        } else if (commandWord.equals("take")) {
            takeItem(command.getSecondWord());
        } else if (commandWord.equals("look")) {
            lookItem();
        }
        return wantToQuit;
    }

    private Command readCommand() {
        Scanner reader = new Scanner(System.in);
        System.out.print("> ");
        String inputLine = reader.nextLine();

        String[] words = inputLine.split(" ");

        if (words.length > 0) {
            return new Command(words[0], words.length > 1 ? words[1] : null);
        } else {
            return new Command(null, null);
        }
    }

    private void play() {
        printWelcome();

        boolean finished = false;
        while (!