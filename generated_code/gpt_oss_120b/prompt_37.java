```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Room {
    private final int number;
    private final String type;
    private boolean occupied;

    public Room(int number, String type) {
        this.number = number;
        this.type = type;
        this.occupied = false;
    }

    public int getNumber() { return number; }
    public String getType() { return type; }
    public boolean isOccupied() { return occupied; }
    public void setOccupied(boolean occupied) { this.occupied = occupied; }

    @Override
    public String toString() {
        return String.format("Room %d (%s) - %s", number, type, occupied ? "Occupied" : "Available");
    }
}

class Booking {
    private static int counter = 1;
    private final int id;
    private final String guestName;
    private final int roomNumber;
    private final LocalDate checkIn;
    private final LocalDate checkOut;

    public Booking(String guestName, int roomNumber, LocalDate checkIn, LocalDate checkOut) {
        this.id = counter++;
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public int getId() { return id; }
    public String getGuestName() { return guestName; }
    public int getRoomNumber() { return roomNumber; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }

    @Override
    public String toString() {
        return String.format("Booking #%d: %s - Room %d from %s to %s",
                id, guestName, roomNumber,
                checkIn.format(DateTimeFormatter.ISO_DATE),
                checkOut.format(DateTimeFormatter.ISO_DATE));
    }
}

class Hotel {
    private final Map<Integer, Room> rooms = new HashMap<>();
    private final Map<Integer, Booking> bookings = new HashMap<>();

    public Hotel() {
        addRoom(new Room(101, "Single"));
        addRoom(new Room(102, "Single"));
        addRoom(new Room(201, "Double"));
        addRoom(new Room(202, "Double"));
        addRoom(new Room(301, "Suite"));
    }

    private void addRoom(Room room) {
        rooms.put(room.getNumber(), room);
    }

    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms.values());
    }

    public List<Room> getAvailableRooms() {
        List<Room> list = new ArrayList<>();
        for (Room r : rooms.values()) {
            if (!r.isOccupied()) list.add(r);
        }
        return list;
    }

    public Optional<Room> findRoom(int number) {
        return Optional.ofNullable(rooms.get(number));
    }

    public Booking bookRoom(String guestName, int roomNumber, LocalDate checkIn, LocalDate checkOut) {
        Room room = rooms.get(roomNumber);
        if (room == null || room.isOccupied())
            throw new IllegalArgumentException("Room not available");
        room.setOccupied(true);
        Booking booking = new Booking(guestName, roomNumber, checkIn, checkOut);
        bookings.put(booking.getId(), booking);
        return booking;
    }

    public Booking cancelBooking(int bookingId) {
        Booking booking = bookings.remove(bookingId);
        if (booking == null)
            throw new IllegalArgumentException("Booking not found");
        Room room = rooms.get(booking.getRoomNumber());
        if (room != null) room.setOccupied(false);
        return booking;
    }

    public Collection<Booking> getAllBookings() {
        return bookings.values();
    }
}

public class HotelBookingApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Hotel hotel = new Hotel();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> listAllRooms();
                case "2" -> listAvailableRooms();
                case "3" -> makeBooking();
                case "4" -> cancelBooking();
                case "5" -> listAllBookings();
                case "0" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== Hotel Booking System ===");
        System.out.println("1. List all rooms");
        System.out.println("2. List available rooms");
        System.out.println("3. Book a room");
        System.out.println("4. Cancel a booking");
        System.out.println("5. List all bookings");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
    }

    private static void listAllRooms() {
        System.out.println("\nAll Rooms:");
        hotel.getAllRooms().forEach(System.out::println);
    }

    private static void listAvailableRooms() {
        System.out.println("\nAvailable Rooms:");
        hotel.getAvailableRooms().forEach(System.out::println);
    }

    private static void makeBooking() {
        try {
            System.out.print("Guest name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Room number: ");
            int roomNum = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Check-in date (YYYY-MM-DD): ");
            LocalDate checkIn = LocalDate.parse(scanner.nextLine().trim());

            System.out.print("Check-out date (YYYY-MM-DD): ");
            LocalDate checkOut = LocalDate.parse(scanner.nextLine().trim());

            Booking booking = hotel.bookRoom(name, roomNum, checkIn, checkOut);
            System.out.println("Booking successful: " + booking);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void cancelBooking() {
        try {
            System.out.print("Booking ID to cancel: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            Booking removed = hotel.cancelBooking(id);
            System.out.println("Cancelled: " + removed);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listAllBookings() {
        System.out.println("\nCurrent Bookings:");
        if (hotel.getAllBookings().isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            hotel.getAllBookings().forEach(System.out::println);
        }
    }
}
```