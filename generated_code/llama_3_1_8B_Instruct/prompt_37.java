```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class HotelBookingSystem {
    private Map<String, Room> rooms;
    private Map<String, Booking> bookings;

    public HotelBookingSystem() {
        this.rooms = new HashMap<>();
        this.bookings = new HashMap<>();
        this.rooms.put("101", new Room("101", 1, 200.0));
        this.rooms.put("102", new Room("102", 2, 300.0));
        this.rooms.put("103", new Room("103", 3, 400.0));
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nHotel Booking System");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel a Booking");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    viewAvailableRooms();
                    break;
                case 2:
                    bookRoom(scanner);
                    break;
                case 3:
                    cancelBooking(scanner);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewAvailableRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room room : rooms.values()) {
            if (!bookings.containsValue(room)) {
                System.out.println(room);
            }
        }
    }

    private void bookRoom(Scanner scanner) {
        System.out.print("Enter room number: ");
        String roomNumber = scanner.nextLine();
        if (rooms.containsKey(roomNumber) && !bookings.containsValue(rooms.get(roomNumber))) {
            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();
            System.out.print("Enter check-in date (yyyy-MM-dd): ");
            String checkInDate = scanner.nextLine();
            System.out.print("Enter check-out date (yyyy-MM-dd): ");
            String checkOutDate = scanner.nextLine();
            Booking booking = new Booking(guestName, checkInDate, checkOutDate, rooms.get(roomNumber));
            bookings.put(booking.getBookingId(), booking);
            System.out.println("Room booked successfully!");
        } else {
            System.out.println("Room is not available or already booked.");
        }
    }

    private void cancelBooking(Scanner scanner) {
        System.out.print("Enter booking ID: ");
        String bookingId = scanner.nextLine();
        if (bookings.containsKey(bookingId)) {
            Booking booking = bookings.get(bookingId);
            bookings.remove(bookingId);
            rooms.put(booking.getRoom().getRoomNumber(), booking.getRoom());
            System.out.println("Booking cancelled successfully!");
        } else {
            System.out.println("Booking not found.");
        }
    }

    public static void main(String[] args) {
        HotelBookingSystem hotelBookingSystem = new HotelBookingSystem();
        hotelBookingSystem.start();
    }
}

class Room {
    private String roomNumber;
    private int capacity;
    private double rate;

    public Room(String roomNumber, int capacity, double rate) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.rate = rate;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber + ", Capacity: " + capacity + ", Rate: $" + rate;
    }
}

class Booking {
    private String bookingId;
    private String guestName;
    private String checkInDate;
    private String checkOutDate;
    private Room room;

    public Booking(String guestName, String checkInDate, String checkOutDate, Room room) {
        this.bookingId = "B" + (bookings.size() + 1);
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.room = room;
        bookings.put(bookingId, this);
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public Room getRoom() {
        return room;
    }
}
```