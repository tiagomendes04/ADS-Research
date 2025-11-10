```java
import java.util.Scanner;

public class AdventureGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Rooms
        String[] rooms = {
                "You enter a dark forest.",
                "You find yourself in a dusty library.",
                "You are in a vast, open meadow.",
                "You stand before a shimmering portal."
        };

        // Items
        String[] items = {
                "a key",
                "a map",
                "a potion",
                "a sword"
        };

        // Puzzles
        String[] puzzles = {
                "To open the door, you need the key.",
                "The map shows the path to the treasure.",
                "Drink the potion to overcome the fear."
        };

        // Current room
        int currentRoom = 0;
        
        // Game loop
        while (true) {
            System.out.println(rooms[currentRoom]);
            System.out.println("What do you want to do?");
            System.out.println("1. Go North");
            System.out.println("2. Go South");
            System.out.println("3. Go West");
            System.out.println("4. Examine the area");
            System.out.println("5. Use item");
            System.out.println("6. Solve Puzzle");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    currentRoom = (currentRoom + 1) % rooms.length;
                    break;
                case 2:
                    currentRoom = (currentRoom - 1 + rooms.length) % rooms.length;
                    break;
                case 3:
                    currentRoom = (currentRoom + 2) % rooms.length;
                    break;
                case 4:
                    System.out.println("You see " + items[0] + " and " + puzzles[0]);
                    break;
                case 5:
                    System.out.println("You use " + items[0] + " to solve the puzzle.");
                    break;
                case 6:
                    System.out.println("You solve the puzzle.");
                    break;
                case 7:
                    System.out.println("Thanks for playing!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
```