```java
// Room.java
public class Room {
    private int number;
    private int capacity;
    private Guest[] guests;
    private double price;

    public Room(int number, int capacity) {
        this.number = number;
        this.capacity = capacity;
        this.guests = new Guest[0];
        this.price = 0.0;
    }

    public void addGuest(Guest guest) {
        if (guests.length == capacity) {
            System.out.println("Room is full. Cannot add guest.");
            return;
        }
        Guest[] newGuests = new Guest[guests.length + 1];
        System.arraycopy(guests, 0, newGuests, 0, guests.length);
        newGuests[newGuests.length - 1] = guest;
        guests = newGuests;
    }

    public void removeGuest(int index) {
        if (index < 0 || index >= guests.length) {
            System.out.println("Invalid index.");
            return;
        }
        for (int i = 0; i < index; i++) {
            Guest[] newGuests = new Guest[guests.length - 1];
            System.arraycopy(guests, 0, newGuests, 0, i);
            System.arraycopy(guests, i + 1, newGuests, i, guests.length - i - 1);
            guests = newGuests;
        }
        guests = guests.length > 0 ? guests : new Guest[0];
    }

    public void updatePrice(double price) {
        this.price = price;
    }

    public void displayInfo() {
        System.out.println("Room Number: " + number + "\n");
        if (guests.length > 0) {
            System.out.println("Guests: ");
            for (int i = 0; i < guests.length; i++) {
                System.out.println("Guest " + (i + 1) + ": Name = " + guests[i].getName() + ", Age = " + guests[i].getAge());
            }
        }
        System.out.println("Price: $" + String.format("%.2f", price));
    }
}

// Guest.java
public class Guest {
    private String name;
    private int age;

    public Guest(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}

// RoomReservation.java
import java.util.ArrayList;
import java.util.Scanner;

public class RoomReservation {
    private Room room;
    private ArrayList<RoomReservation> reservations;

    public RoomReservation(Room room) {
        this.room = room;
        this.reservations = new ArrayList<>();
    }

    public void reserveRoom(int number, int capacity) {
        room.addGuest(new Guest("Guest " + (reservations.size() + 1), 30));
        room.updatePrice(10.0);
        System.out.println("Room " + number + " reserved successfully.");
    }

    public void cancelReservation(int number) {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getNumber() == number) {
                room.removeGuest(i);
                reservations.remove(i);
                System.out.println("Reservation for room " + number + " cancelled.");
                return;
            }
        }
        System.out.println("Room " + number + " does not exist.");
    }

    public void displayReservations() {
        for (int i = 0; i < reservations.size(); i++) {
            room.updatePrice(10.0);
            System.out.println("Reservation for room " + reservations.get(i).getNumber() + ": " + reservations.get(i).getName() + " (" + reservations.get(i).getAge() + ") - Price: $" + String.format("%.2f", reservations.get(i).getPrice()));
            room.displayInfo();
            System.out.println();
        }
    }
}

// Hotel.java
public class Hotel {
    private ArrayList<RoomReservation> reservations;
    private Room room;

    public Hotel(Room room) {
        this.reservations = new ArrayList<>();
        this.room = room;
    }

    public void makeReservation(int number, int capacity) {
        room.reserveRoom(number, capacity);
    }

    public void cancelReservation(int number) {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getNumber() == number) {
                room.cancelReservation(i);
                reservations.remove(i);
                System.out.println("Reservation for room " + number + " cancelled.");
                return;
            }
        }
        System.out.println("Room " + number + " does not exist.");
    }

    public void displayReservations() {
        room.displayInfo();
        System.out.println();
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel(new Room(101, 10));
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Make a reservation");
            System.out.println("2. Cancel a reservation");
            System.out.println("3. Display all reservations");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    System.out.print("Enter room number: ");
                    int number = scanner.nextInt();
                    System.out.print("Enter capacity: ");
                    int capacity = scanner.nextInt();
                    hotel.makeReservation(number, capacity);
                    break;
                case 2:
                    System.out.print("Enter room number: ");
                    number = scanner.nextInt();
                    hotel.cancelReservation(number);
                    break;
                case 3:
                    hotel.displayReservations();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
```