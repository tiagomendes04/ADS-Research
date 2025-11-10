```java
import java.util.*;

class Room {
    String description;
    Map<String, Room> exits;
    List<Item> items;
    Puzzle puzzle;

    Room(String description) {
        this.description = description;
        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
        this.puzzle = null;
    }

    void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    void addItem(Item item) {
        items.add(item);
    }

    void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    Room getExit(String direction) {
        return exits.get(direction);
    }

    List<Item> getItems() {
        return items;
    }

    Puzzle getPuzzle() {
        return puzzle;
    }
}

class Item {
    String name;
    String description;

    Item(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

class Puzzle {
    String question;
    String answer;
    boolean solved;

    Puzzle(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.solved = false;
    }

    boolean solve(String input) {
        if (input.equalsIgnoreCase(answer)) {
            solved = true;
            return true;
        }
        return false;
    }
}

class Player {
    Room currentRoom;
    List<Item> inventory;

    Player(Room startRoom) {
        this.currentRoom = startRoom;
        this.inventory = new ArrayList<>();
    }

    void move(String direction) {
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom != null) {
            currentRoom = nextRoom;
        } else {
            System.out.println("There is no exit in that direction!");
        }
    }

    void look() {
        System.out.println(currentRoom.description);
        if (!currentRoom.getItems().isEmpty()) {
            System.out.println("Items in the room:");
            for (Item item : currentRoom.getItems()) {
                System.out.println("- " + item.name + ": " + item.description);
            }
        }
        if (currentRoom.getPuzzle() != null && !currentRoom.getPuzzle().solved) {
            System.out.println("Puzzle in the room: " + currentRoom.getPuzzle().question);
        }
    }

    void takeItem(String itemName) {
        List<Item> items = currentRoom.getItems();
        Item itemToTake = null;
        for (Item item : items) {
            if (item.name.equalsIgnoreCase(itemName)) {
                itemToTake = item;
                break;
            }
        }
        if (itemToTake != null) {
            inventory.add(itemToTake);
            items.remove(itemToTake);
            System.out.println("You have taken " + itemToTake.name + ".");
        } else {
            System.out.println("There is no " + itemName + " in the room.");
        }
    }

    void solvePuzzle(String answer) {
        Puzzle puzzle = currentRoom.getPuzzle();
        if (puzzle != null && !puzzle.solved) {
            if (puzzle.solve(answer)) {
                System.out.println("Puzzle solved!");
            } else {
                System.out.println("Incorrect answer. Try again.");
            }
        } else {
            System.out.println("No puzzle to solve here.");
        }
    }

    void checkInventory() {
        if (!inventory.isEmpty()) {
            System.out.println("Inventory:");
            for (Item item : inventory) {
                System.out.println("- " + item.name + ": " + item.description);
            }
        } else {
            System.out.println("Your inventory is empty.");
        }
    }
}

public class AdventureGame {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Room entrance = new Room("You are at the entrance of a mysterious castle. There are doors to the north and east.");
        Room northHall = new Room("You are in a long hall with paintings on the walls. Exits are south and east.");
        Room eastTower = new Room("You are in a tall tower with a view of the castle grounds. Exit is west.");
        Room treasureRoom = new Room("Congratulations! You've found the treasure room. You win!");

        entrance.setExit("north", northHall);
        entrance.setExit("east", eastTower);
        northHall.setExit("south", entrance);
        northHall.setExit("east", treasureRoom);
        eastTower.setExit("west", entrance);

        Item sword = new Item("sword", "A sharp, silver sword.");
        Item key = new Item("key", "A small, rusty key.");

        entrance.addItem(sword);
        northHall.addItem(key);

        Puzzle doorPuzzle = new Puzzle("What has keys but can't open locks?", "piano");
        treasureRoom.setPuzzle(doorPuzzle);

        Player player = new Player(entrance);

        while (true