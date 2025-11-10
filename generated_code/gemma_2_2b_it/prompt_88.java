```java
import java.util.ArrayList;
import java.util.Scanner;

public class TextAdventure {

    static ArrayList<Room> rooms = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        rooms.add(new Room("Entrance", "You stand in a dark hallway with a single door to the right."));
        rooms.add(new Room("Garden", "Sunlight bathes the room, filled with flowers and butterflies. A path leads to the east."));
        rooms.add(new Room("Library", "Books line the shelves, and a large fireplace fills one corner. A ladder leads up to the attic."));

        System.out.println("You enter a dark hallway.");
        System.out.println("What do you do?");
        
        String input = scanner.nextLine();

        // TODO: Implement command handling
        if (input.equalsIgnoreCase("go north")) {
            // TODO: Move to the next room based on the input
        } else if (input.equalsIgnoreCase("take sword")) {
            // TODO: Implement item interaction
        } else {
            System.out.println("You don't understand that command.");
        }
    }
}

class Room {
    String name;
    String description;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
```