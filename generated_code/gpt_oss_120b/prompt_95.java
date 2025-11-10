```java
import java.util.*;

class Hotel {
    private final Map<Integer, Room> rooms = new HashMap<>();
    private final Map<Integer, Guest> guests = new HashMap<>();
    private final List<Bill> bills = new ArrayList<>();

    public Hotel(int totalRooms) {
        for (int i = 1; i <= totalRooms; i++) {
            rooms.put(i, new Room(i));
        }
    }

    public Room getRoom(int roomNumber) {
        return rooms.get(roomNumber);
    }

    public Guest checkIn(String name, int age, int roomNumber, int nights, double ratePerNight) {
        Room room = rooms.get(roomNumber);
        if (room == null) throw new IllegalArgumentException("Room not found");
        if (room.isOccupied()) throw new IllegalStateException("Room already occupied");

        Guest guest = new Guest(UUID.randomUUID(), name, age);
        guests.put(guest.getId().hashCode(), guest);
        room.assignGuest(guest, nights, ratePerNight);

        Bill bill = new Bill(guest, room, nights, ratePerNight);
        bills.add(bill);
        return guest;
    }

    public void checkOut(int roomNumber) {
        Room room = rooms.get(roomNumber);
        if (room == null) throw new IllegalArgumentException("Room not found");
        if (!room.isOccupied()) throw new IllegalStateException("Room is already vacant");

        room.vacate();
    }

    public List<Bill> getBills() {
        return Collections.unmodifiableList(bills);
    }

    public static void main(String[] args) {
        Hotel hotel = new Hotel(10);

        // Sample check‑in
        Guest g1 = hotel.checkIn("Alice Johnson", 34, 3, 2, 120.0);
        Guest g2 = hotel.checkIn("Bob Smith", 45, 5, 5, 150.0);

        // Display bills
        for (Bill b : hotel.getBills()) {
            System.out.println(b);
        }

        // Sample check‑out
        hotel.checkOut(3);
        hotel.checkOut(5);
    }
}

class Room {
    private final int number;
    private Guest currentGuest;
    private int nightsBooked;
    private double ratePerNight;

    public Room(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public boolean isOccupied() {
        return currentGuest != null;
    }

    public void assignGuest(Guest guest, int nights, double rate) {
        this.currentGuest = guest;
        this.nightsBooked = nights;
        this.ratePerNight = rate;
    }

    public void vacate() {
        this.currentGuest = null;
        this.nightsBooked = 0;
        this.ratePerNight = 0.0;
    }

    public Guest getCurrentGuest() {
        return currentGuest;
    }

    public int getNightsBooked() {
        return nightsBooked;
    }

    public double getRatePerNight() {
        return ratePerNight;
    }

    @Override
    public String toString() {
        return "Room{" +
                "number=" + number +
                ", occupied=" + isOccupied() +
                '}';
    }
}

class Guest {
    private final UUID id;
    private final String name;
    private final int age;

    public Guest(UUID id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Guest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

class Bill {
    private final Guest guest;
    private final Room room;
    private final int nights;
    private final double ratePerNight;
    private final double totalAmount;

    public Bill(Guest guest, Room room, int nights, double ratePerNight) {
        this.guest = guest;
        this.room = room;
        this.nights = nights;
        this.ratePerNight = ratePerNight;
        this.totalAmount = nights * ratePerNight;
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "guest=" + guest.getName() +
                ", room=" + room.getNumber() +
                ", nights=" + nights +
                ", ratePerNight=" + ratePerNight +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
```