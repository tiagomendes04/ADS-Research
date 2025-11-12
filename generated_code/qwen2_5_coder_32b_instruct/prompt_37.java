```java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class HotelBookingSystem {
    private Map<Integer, Boolean> rooms;

    public HotelBookingSystem(int numberOfRooms) {
        rooms = new HashMap<>();
        for (int i = 1; i <= numberOfRooms; i++) {
            rooms.put(i, false);
        }
    }

    public void bookRoom(int roomNumber) {
        if (rooms.containsKey(roomNumber) && !rooms.get(roomNumber)) {
            rooms.put(roomNumber, true);
            System.out.println("Room " + roomNumber + " has been booked.");
        } else {
            System.out.println("Room " + roomNumber + " is not available.");
        }
    }

    public void cancelBooking(int roomNumber) {
        if (rooms.containsKey(roomNumber) && rooms.get(roomNumber)) {
            rooms.put(roomNumber, false);
            System.out.println("Booking for room " + roomNumber + " has been cancelled.");
        } else {
            System.out.println("Room " + roomNumber + " was not booked.");
        }
    }

    public void displayRoomStatus() {
        System.out.println("Room Status:");
        for (Map.Entry<Integer, Boolean> entry : rooms.entrySet()) {
            System.out.println("Room " + entry.getKey() + ": " + (entry.getValue() ? "Booked" : "Available"));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HotelBookingSystem hotel = new HotelBookingSystem(10);

        while (true) {
            System.out.println("\nHotel Booking System");
            System.out.println("1. Book Room");
            System.out.println("2. Cancel Booking");
            System.out.println("3. Display Room Status");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter room number to book: ");
                    int bookRoomNumber = scanner.nextInt();
                    hotel.bookRoom(bookRoomNumber);
                    break;
                case 2:
                    System.out.print("Enter room number to cancel booking: ");
                    int cancelRoomNumber = scanner.nextInt();
                    hotel.cancelBooking(cancelRoomNumber);
                    break;
                case 3:
                    hotel.displayRoomStatus();
                    break;
                case 4:
                    System.out.println("Exiting the system.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
```