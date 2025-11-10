```java
import java.util.Scanner;

public class TextAdventureGame {
    enum Direction {
        NORTH, SOUTH, EAST, WEST
    }

    static class Room {
        String description;
        Room north, south, east, west;

        Room(String desc) {
            description = desc;
        }

        void connectRoom(Direction dir, Room room) {
            switch (dir) {
                case NORTH:
                    this.north = room;
                    break;
                case SOUTH:
                    this.south = room;
                    break;
                case EAST:
                    this.east = room;
                    break;
                case WEST:
                    this.west = room;
                    break;
            }
        }
    }

    static class Item {
        String name;
        String description;

        Item(String name, String desc) {
            this.name = name;
            this.description = desc;
        }
    }

    static class Puzzle {
        String description;
        boolean solved = false;

        Puzzle(String desc) {
            description = desc;
        }

        void solve() {
            System.out.println("You solved the puzzle!");
            solved = true;
        }
    }

    static class Player {
        Room currentRoom;
        Item inventory[] = new Item[10];
        int inventoryIndex = 0;

        Player(Room startRoom) {
            currentRoom = startRoom;
        }

        void move(Direction dir) {
            Room nextRoom = null;
            switch (dir) {
                case NORTH:
                    nextRoom = currentRoom.north;
                    break;
                case SOUTH:
                    nextRoom = currentRoom.south;
                    break;
                case EAST:
                    nextRoom = currentRoom.east;
                    break;
                case WEST:
                    nextRoom = currentRoom.west;
                    break;
            }
            if (nextRoom != null) {
                currentRoom = nextRoom;
                System.out.println(currentRoom.description);
            } else {
                System.out.println("There is no room in that direction.");
            }
        }

        void takeItem(Item item) {
            if (inventoryIndex < inventory.length) {
                inventory[inventoryIndex++] = item;
                System.out.println("You took the " + item.name + ".");
            } else {
                System.out.println("Your inventory is full.");
            }
        }

        void useItem(String itemName) {
            for (int i = 0; i < inventoryIndex; i++) {
                if (inventory[i].name.equalsIgnoreCase(itemName)) {
                    inventory[i].description = "Used " + itemName + " to solve a puzzle.";
                    inventory[i].description += " Puzzle solved!";
                    inventory[i].solved = true;
                    System.out.println(inventory[i].description);
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        Room[] rooms = new Room[5];
        Item[] items = new Item[3];
        Puzzle[] puzzles = new Puzzle[2];

        rooms[0] = new Room("You are in a dark room. There is a door to the north.");
        rooms[1] = new Room("You are in a bright room. There is a door to the south.");
        rooms[2] = new Room("You are in a mysterious room. There is a door to the east.");
        rooms[3] = new Room("You are in a locked room. There is a door to the west.");
        rooms[4] = new Room("You are in a treasure room. Congratulations, you won!");

        rooms[0].connectRoom(Direction.NORTH, rooms[1]);
        rooms[1].connectRoom(Direction.SOUTH, rooms[0]);
        rooms[1].connectRoom(Direction.EAST, rooms[2]);
        rooms[2].connectRoom(Direction.WEST, rooms[1]);
        rooms[2].connectRoom(Direction.EAST, rooms[3]);
        rooms[3].connectRoom(Direction.WEST, rooms[2]);
        rooms[3].connectRoom(Direction.NORTH, rooms[4]);

        items[0] = new Item("Key", "A rusty key.");
        items[1] = new Item("Map", "A map of the area.");
        items[2] = new Item("Puzzle Piece", "A puzzle piece with a strange symbol.");

        puzzles[0] = new Puzzle("Use the key to unlock the door.");
        puzzles[1] = new Puzzle("Fit the puzzle piece into the puzzle on the wall.");

        Player player = new Player(rooms[0]);

        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.println(player.currentRoom.description);
            System.out.print("What do you want to do? (move, take, use, quit): ");
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("quit")) {
                System.out.println("Thanks for playing!");
                break;
            } else if (input.equalsIgnoreCase("move")) {
                System.out.print("Which direction (north, south, east, west): ");
                input = scanner.nextLine();
                Direction dir = Direction.valueOf(input.toUpperCase());
                player.move(dir);
            } else if (input.equalsIgnoreCase("take")) {
                System.out.print("Which item do you want to take? ");
                input = scanner.nextLine();
                for (int i = 0; i < rooms[0].inventory.length; i++) {
                    if (rooms[0].inventory[i] != null && rooms[0].inventory[i].name.equalsIgnoreCase(input)) {
                        player.takeItem(rooms[0].inventory[i]);
                        rooms[0].inventory[i] = null;
                        break;
                    }
                }
            } else if (input.equalsIgnoreCase("use")) {
                System.out.print("Which item do you want to use? ");
                input = scanner.nextLine();
                player.useItem(input);
            }
        }
    }
}
```