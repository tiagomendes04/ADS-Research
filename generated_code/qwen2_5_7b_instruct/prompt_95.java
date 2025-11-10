```java
import java.util.ArrayList;
import java.util.List;

class Room {
    private int roomNumber;
    private boolean isAvailable;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

class Guest {
    private String name;
    private String id;

    public Guest(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}

class Billing {
    private double totalAmount;

    public Billing() {
        totalAmount = 0.0;
    }

    public void addCharge(double amount) {
        totalAmount += amount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}

class HotelCheckInSystem {
    private List<Room> rooms;
    private List<Guest> guests;
    private Billing billing;

    public HotelCheckInSystem() {
        this.rooms = new ArrayList<>();
        this.guests = new ArrayList<>();
        this.billing = new Billing();
        initializeRooms();
    }

    private void initializeRooms() {
        for (int i = 1; i <= 10; i++) {
            rooms.add(new Room(i));
        }
    }

    public void checkIn(Guest guest) {
        for (Room room : rooms) {
            if (room.isAvailable()) {
                room.setAvailable(false);
                guests.add(guest);
                billing.addCharge(100.0); // Assuming $100 per night
                System.out.println(guest.getName() + " checked in to room " + room.getRoomNumber());
                return;
            }
        }
        System.out.println("No available rooms for " + guest.getName());
    }

    public void checkOut(String guestId) {
        for (Room room : rooms) {
            if (!room.isAvailable()) {
                for (Guest guest : guests) {
                    if (guest.getId().equals(guestId)) {
                        room.setAvailable(true);
                        guests.remove(guest);
                        System.out.println(guest.getName() + " checked out from room " + room.getRoomNumber());
                        return;
                    }
                }
            }
        }
        System.out.println("Guest not found or no check-in record.");
    }

    public void printBilling() {
        System.out.println("Total billing: $" + billing.getTotalAmount());
    }

    public static void main(String[] args) {
        HotelCheckInSystem system = new HotelCheckInSystem();
        Guest guest1 = new Guest("John Doe", "12345");
        Guest guest2 = new Guest("Jane Smith", "67890");

        system.checkIn(guest1);
        system.checkIn(guest2);
        system.checkOut("12345");
        system.printBilling();
    }
}
```