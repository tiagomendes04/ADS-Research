```java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Item {
    String name;
    String description;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

class Room {
    String name;
    String description;
    Map<String, Room> exits;
    Map<String, Item> items;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<>();
        this.items = new HashMap<>();
    }

    public void addExit(String direction, Room room) {
        this.exits.put(direction, room);
    }

    public void addItem(String name, Item item) {
        this.items.put(name, item);
    }
}

class Player {
    Room currentRoom;
    Map<String, Item> inventory;

    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
        this.inventory = new HashMap<>();
    }
}

public class TextAdventure {
    public static void main(String[] args) {
        Room kitchen = new Room("Kitchen", "A bright and cheerful kitchen.");
        Room garden = new Room("Garden", "A beautiful and serene garden.");

        kitchen.addExit("north", garden);
        garden.addExit("south", kitchen);

        Item key = new Item("key", "A shiny golden key.");
        kitchen.addItem("key", key);

        Player player = new Player(kitchen);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(player.currentRoom.description);
            System.out.println("Exits: " + String.join(", ", player.currentRoom.exits.keySet()));
            System.out.println("Items: " + String.join(", ", player.currentRoom.items.keySet()));
            System.out.print("Command: ");
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("quit")) {
                break;
            } else if (command.startsWith("go")) {
                String[] parts = command.split(" ");
                if (parts.length > 1) {
                    String direction = parts[1];
                    Room nextRoom = player.currentRoom.exits.get(direction);
                    if (nextRoom != null) {
                        player.currentRoom = nextRoom;
                    } else {
                        System.out.println("You can't go that way.");
                    }
                }
            } else if (command.startsWith("take")) {
                String[] parts = command.split(" ");
                if (parts.length > 1) {
                    String itemName = parts[1];
                    Item item = player.currentRoom.items.get(itemName);
                    if (item != null) {
                        player.inventory.put(itemName, item);
                        player.currentRoom.items.remove(itemName);
                        System.out.println("You took the " + item.name);
                    } else {
                        System.out.println("There is no " + itemName + " here.");
                    }
                }
            } else if (command.startsWith("drop")) {
                String[] parts = command.split(" ");
                if (parts.length > 1) {
                    String itemName = parts[1];
                    Item item = player.inventory.get(itemName);
                    if (item != null) {
                        player.currentRoom.items.put(itemName, item);
                        player.inventory.remove(itemName);
                        System.out.println("You dropped the " + item.name);
                    } else {
                        System.out.println("You don't have a " + itemName + ".");
                    }
                }
            } else {
                System.out.println("Unknown command.");
            }
        }

        scanner.close();
    }
}
```