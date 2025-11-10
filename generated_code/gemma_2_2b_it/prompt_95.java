```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class HotelCheckinSystem {

    static class Room {
        int roomId;
        String roomType;
        boolean isAvailable;

        Room(int roomId, String roomType) {
            this.roomId = roomId;
            this.roomType = roomType;
            this.isAvailable = true;
        }
    }

    static class Guest {
        int guestId;
        String name;
        String phoneNumber;
        String email;
        int roomId;
        int checkinTime;

        Guest(int guestId, String name, String phoneNumber, String email) {
            this.guestId = guestId;
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.email = email;
        }

        public void checkIn() {
            System.out.println("Guest " + name + " has checked in to room " + roomId);
        }
    }

    static class Billing {
        int billAmount;
        int guestId;
        int totalDays;
        int roomRate;
        int totalCost;
        
        Billing(int billAmount, int guestId, int totalDays, int roomRate) {
            this.billAmount = billAmount;
            this.guestId = guestId;
            this.totalDays = totalDays;
            this.roomRate = roomRate;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Room> rooms = new ArrayList<>();
        List<Guest> guests = new ArrayList<>();

        // Create rooms
        rooms.add(new Room(1, "Single"));
        rooms.add(new Room(2, "Double"));
        rooms.add(new Room(3, "Suite"));

        // Create guests
        guests.add(new Guest(1, "Alice", "1234567890", "alice@email.com"));
        guests.add(new Guest(2, "Bob", "9876543210", "bob@email.com"));

        // Check-in guests
        System.out.println("Enter guest ID to check in:");
        int guestId = scanner.nextInt();
        Guest guest = guests.stream().filter(g -> g.guestId == guestId).findFirst().get();
        guest.checkIn();

        // Calculate billing
        Billing billing = new Billing(1000, guest.guestId, 3, 150);
        System.out.println("Bill amount: " + billing.billAmount);
        System.out.println("Total cost: " + billing.totalCost);
    }
}
```