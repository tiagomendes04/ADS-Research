```java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Room {
    int id;
    String type;
    int capacity;
    int available;

    public Room(int id, String type, int capacity) {
        this.id = id;
        this.type = type;
        this.capacity = capacity;
        this.available = capacity;
    }
}

class Hotel {
    Map<Integer, Room> rooms;
    Map<String, Integer> guests;

    public Hotel() {
        rooms = new HashMap<>();
        guests = new HashMap<>();
    }

    public void addRoom(int roomId, String roomType, int capacity) {
        Room room = new Room(roomId, roomType, capacity);
        rooms.put(roomId, room);
    }

    public void bookRoom(int roomId, int guestId) {
        Room room = rooms.get(roomId);
        if (room.available >= 1) {
            room.available--;
            guests.put(guestId, room.id);
            System.out.println("Room " + roomId + " booked successfully.");
        } else {
            System.out.println("Room " + roomId + " is fully booked.");
        }
    }

    public void cancelBooking(int guestId) {
        if (guests.containsKey(guestId)) {
            Room room = rooms.get(guests.get(guestId));
            if (room.available >= 1) {
                room.available++;
                guests.remove(guestId);
                System.out.println("Booking cancelled successfully for guest " + guestId + ".");
            } else {
                System.out.println("No room available for cancellation.");
            }
        } else {
            System.out.println("No guest found.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Room");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Enter room ID: ");
                    int roomId = scanner.nextInt();
                    System.out.print("Enter room type: ");
                    String roomType = scanner.next();
                    System.out.print("Enter capacity: ");
                    int capacity = scanner.nextInt();
                    hotel.addRoom(roomId, roomType, capacity);
                    break;
                case 2:
                    System.out.print("Enter room ID: ");
                    roomId = scanner.nextInt();
                    System.out.print("Enter guest ID: ");
                    guestId = scanner.nextInt();
                    hotel.bookRoom(roomId, guestId);
                    break;
                case 3:
                    System.out.print("Enter guest ID: ");
                    guestId = scanner.nextInt();
                    hotel.cancelBooking(guestId);
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
```