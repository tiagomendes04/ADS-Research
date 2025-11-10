```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class HotelBookingSystem {
    static HashMap<String, Room> rooms = new HashMap<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        rooms.put("101", new Room("101", "Double", 100));
        rooms.put("102", new Room("102", "Queen", 80));
        rooms.put("103", new Room("103", "Single", 50));

        while (true) {
            System.out.println("----- Hotel Booking System -----");
            System.out.println("1. View Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewRooms();
                    break;
                case 2:
                    bookRoom();
                    break;
                case 3:
                    cancelBooking();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static void viewRooms() {
        System.out.println("Available Rooms:");
        for (Room room : rooms.values()) {
            System.out.println(room);
        }
    }

    static void bookRoom() {
        System.out.println("Enter room number: ");
        String roomNumber = scanner.nextLine();

        if (rooms.containsKey(roomNumber)) {
            Room room = rooms.get(roomNumber);
            System.out.println("Available rooms:");
            System.out.println(room);
            System.out.println("Enter your name: ");
            String name = scanner.nextLine();
            System.out.println("Enter check-in date: ");
            String checkInDate = scanner.nextLine();
            System.out.println("Enter check-out date: ");
            String checkOutDate = scanner.nextLine();
            Booking booking = new Booking(name, room, checkInDate, checkOutDate);
            bookings.add(booking);
            System.out.println("Booking confirmed! Booking ID: " + booking.getId());
        } else {
            System.out.println("Room not found.");
        }
    }

    static void cancelBooking() {
        System.out.println("Enter Booking ID: ");
        String bookingId = scanner.nextLine();
        for (Booking booking : bookings) {
            if (booking.getId().equals(bookingId)) {
                bookings.remove(booking);
                System.out.println("Booking cancelled.");
                return;
            }
        }
        System.out.println("Booking not found.");
    }
}

class Room {
    private String roomNumber;
    private String roomType;
    private int price;

    public Room(String roomNumber, String roomType, int price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber='" + roomNumber + '\'' +
                ", roomType='" + roomType + '\'' +
                ", price=" + price +
                '}';
    }
}

class Booking {
    private String name;
    private Room room;
    private String checkInDate;
    private String checkOutDate;
    private int bookingId;

    public Booking(String name, Room room, String checkInDate, String checkOutDate) {
        this.name = name;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public int getId() {
        return bookingId;
    }

    public String getName() {
        return name;
    }

    public Room getRoom() {
        return room;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "name='" + name + '\'' +
                ", room=" + room +
                ", checkInDate='" + checkInDate + '\'' +
                ", checkOutDate='" + checkOutDate + '\'' +
                '}';
    }
}
```