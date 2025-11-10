```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Room room1 = new Room("Start", "You are in a dark room.", new String[]{"key", "map"});
        Room room2 = new Room("Room 2", "You are in a room with a door.", new String[]{"sword", "key"});
        Room room3 = new Room("Room 3", "You are in a room with a door.", new String[]{"key", "sword"});

        Item item1 = new Item("map", "A map of the castle.", "You are in a dark room.", "A map of the castle.");
        Item item2 = new Item("sword", "A sword.", "You are in a room with a door.", "A sword.");
        Item item3 = new Item("key", "A key.", "You are in a room with a door.", "A key.");

        Puzzle puzzle1 = new Puzzle("puzzle1", "A puzzle.", "You are in a room with a door.", "A puzzle.");
        Puzzle puzzle2 = new Puzzle("puzzle2", "A puzzle.", "You are in a room with a door.", "A puzzle.");

        room1.addItems(item1, item2, item3);
        room1.addPuzzle(puzzle1);
        room2.addItems(item2, item3);
        room2.addPuzzle(puzzle2);
        room3.addItems(item1, item3);
        room3.addPuzzle(puzzle1);

        Player player = new Player(room1, item1);
        player.goToRoom(room2);
    }
}

class Room {
    String name;
    List<Item> items = new ArrayList<>();
    List<Puzzle> puzzles = new ArrayList<>();
    Player player;

    public Room(String name, String description, Item[] items) {
        this.name = name;
        this.items = items;
        this.player = null;
    }

    public void addItems(Item[] items) {
        this.items = items;
    }

    public void addPuzzle(Puzzle puzzle) {
        this.puzzles.add(puzzle);
    }

    public Player getPlayer() {
        return player;
    }
}

class Item {
    String name;
    String description;
    String[] items;
    String[] puzzles;

    public Item(String name, String description, String[] items, String[] puzzles) {
        this.name = name;
        this.description = description;
        this.items = items;
        this.puzzles = puzzles;
    }
}

class Puzzle {
    String name;
    String description;
    String[] items;
    String[] puzzles;

    public Puzzle(String name, String description, String[] items, String[] puzzles) {
        this.name = name;
        this.description = description;
        this.items = items;
        this.puzzles = puzzles;
    }
}

class Player {
    String name;
    Room currentRoom;

    public Player(Room currentRoom, Item item) {
        this.name = currentRoom.getName();
        this.currentRoom = currentRoom;
    }

    public String getName() {
        return name;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }
}
```