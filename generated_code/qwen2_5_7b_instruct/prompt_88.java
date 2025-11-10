```java
import java.util.Scanner;

class Item {
    String name;
    String description;

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

class Room {
    String description;
    Room north, south, east, west;
    Item[] items;

    public Room(String description) {
        this.description = description;
        this.items = new Item[5];
    }

    public void addItem(Item item, int index) {
        if (index >= 0 && index < 5) {
            this.items[index] = item;
        }
    }

    public Item[] getItems() {
        return this.items;
    }

    public String getDescription() {
        return description;
    }
}

class Player {
    Room currentRoom;
    Item[] inventory;

    public Player(Room startRoom) {
        this.currentRoom = startRoom;
        this.inventory = new Item[5];
    }

    public void move(String direction) {
        Room nextRoom = null;
        switch (direction) {
            case "north":
                nextRoom = currentRoom.north;
                break;
            case "south":
                nextRoom = currentRoom.south;
                break;
            case "east":
                nextRoom = currentRoom.east;
                break;
            case "west":
                nextRoom = currentRoom.west;
                break;
        }
        if (nextRoom != null) {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getDescription());
        } else {
            System.out.println("You can't go that way.");
        }
    }

    public void takeItem(String item) {
        for (int i = 0; i < currentRoom.getItems().length; i++) {
            if (currentRoom.getItems()[i] != null && currentRoom.getItems()[i].getName().equalsIgnoreCase(item)) {
                for (int j = 0; j < inventory.length; j++) {
                    if (inventory[j] == null) {
                        inventory[j] = currentRoom.getItems()[i];
                        currentRoom.getItems()[i] = null;
                        System.out.println("You picked up the " + item);
                        return;
                    }
                }
                System.out.println("Inventory is full.");
                return;
            }
        }
        System.out.println("There is no " + item + " here.");
    }

    public void useItem(String item) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null && inventory[i].getName().equalsIgnoreCase(item)) {
                System.out.println("You used the " + item);
                inventory[i] = null;
                return;
            }
        }
        System.out.println("You don't have a " + item + " in your inventory.");
    }
}

public class TextAdventureEngine {
    public static void main(String[] args) {
        Room room1 = new Room("You are in a dark room. There is a door to the north.");
        Room room2 = new Room("You are in a bright room. There is a door to the south.");
        Room room3 = new Room("You are in a small room. There is a door to the east.");
        Room room4 = new Room("You are in a large room. There is a door to the west.");

        Item key = new Item("key", "A shiny key.");
        Item sword = new Item("sword", "A rusty sword.");

        room1.addItem(key, 0);
        room2.addItem(sword, 1);
        room3.north = room1;
        room4.west = room1;
        room1.north = room2;
        room1.east = room3;
        room1.west = room4;

        Player player = new Player(room1);

        Scanner scanner = new Scanner(System.in);
        String command;
        while (true) {
            System.out.print("> ");
            command = scanner.nextLine();
            String[] parts = command.split(" ");
            if (parts.length == 0) {
                continue;
            }
            String action = parts[0];
            String item = null;
            if (parts.length > 1) {
                item = parts[1];
            }
            switch (action) {
                case "go":
                    player.move(parts[1]);
                    break;
                case "take":
                    player.takeItem(item);
                    break;
                case "use":
                    player.useItem(item);
                    break;
                case "quit":
                    System.exit(0);
                default:
                    System.out.println("Unknown command.");
            }
        }
    }
}
```