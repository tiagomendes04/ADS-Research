```java
import java.util.ArrayList;
import java.util.List;

class Room {
    private int roomNumber;
    private boolean isAvailable;
    private double rate;

    public Room(int roomNumber, double rate) {
        this.roomNumber = roomNumber;
        this.rate = rate;
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

    public double getRate() {
        return rate;
    }
}

class Guest {
    private String name;
    private String contactInfo;

    public Guest(String name, String contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }
}

class Hotel {
    private List<Room> rooms;
    private List<Reservation> reservations;

    public Hotel(List<Room> rooms) {
        this.rooms = rooms;
        this.reservations = new ArrayList<>();
    }

    public boolean makeReservation(Guest guest, int numberOfNights) {
        for (Room room : rooms) {
            if (room.isAvailable()) {
                room.setAvailable(false);
                reservations.add(new Reservation(guest, room, numberOfNights));
                return true;
            }
        }
        return false;
    }

    public double checkOut(int roomNumber) {
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().getRoomNumber() == roomNumber) {
                Room room = reservation.getRoom();
                double totalBill = room.getRate() * reservation.getNumberOfNights();
                room.setAvailable(true);
                reservations.remove(reservation);
                return totalBill;
            }
        }
        return 0;
    }
}

class Reservation {
    private Guest guest;
    private Room room;
    private int numberOfNights;

    public Reservation(Guest guest, Room room, int numberOfNights) {
        this.guest = guest;
        this.room = room;
        this.numberOfNights = numberOfNights;
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }
}

public class HotelCheckInSystem {
    public static void main(String[] args) {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room(101, 150.0));
        rooms.add(new Room(102, 200.0));
        Hotel hotel = new Hotel(rooms);

        Guest guest1 = new Guest("John Doe", "john.doe@example.com");
        Guest guest2 = new Guest("Jane Smith", "jane.smith@example.com");

        hotel.makeReservation(guest1, 3);
        hotel.makeReservation(guest2, 2);

        System.out.println("Total bill for room 101: $" + hotel.checkOut(101));
        System.out.println("Total bill for room 102: $" + hotel.checkOut(102));
    }
}
```