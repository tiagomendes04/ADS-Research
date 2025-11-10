```java
// HotelCheckInSystem.java

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class Room {
    private String roomNumber;
    private double ratePerNight;
    private boolean isAvailable;

    public Room(String roomNumber, double ratePerNight) {
        this.roomNumber = roomNumber;
        this.ratePerNight = ratePerNight;
        this.isAvailable = true;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public double getRatePerNight() {
        return ratePerNight;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailability(boolean availability) {
        isAvailable = availability;
    }
}

class Guest {
    private String name;
    private String email;
    private String phone;

    public Guest(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}

class Invoice {
    private Guest guest;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalAmount;

    public Invoice(Guest guest, Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalAmount = calculateTotalAmount();
    }

    private double calculateTotalAmount() {
        int nights = getNumberOfNights();
        return room.getRatePerNight() * nights;
    }

    private int getNumberOfNights() {
        return LocalDate.from(checkOutDate).until(LocalDate.from(checkInDate), LocalDate::from).days + 1;
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}

public class HotelCheckInSystem {
    private List<Room> rooms;
    private List<Guest> guests;
    private List<Invoice> invoices;

    public HotelCheckInSystem() {
        this.rooms = new ArrayList<>();
        this.guests = new ArrayList<>();
        this.invoices = new ArrayList<>();
    }

    public void addRoom(String roomNumber, double ratePerNight) {
        rooms.add(new Room(roomNumber, ratePerNight));
    }

    public void addGuest(String name, String email, String phone) {
        guests.add(new Guest(name, email, phone));
    }

    public void checkIn(Guest guest, Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        if (room.isAvailable()) {
            room.setAvailability(false);
            Invoice invoice = new Invoice(guest, room, checkInDate, checkOutDate);
            invoices.add(invoice);
            System.out.println("Check-in successful.");
        } else {
            System.out.println("Room is not available.");
        }
    }

    public void printInvoices() {
        for (Invoice invoice : invoices) {
            System.out.println("Invoice for Guest: " + invoice.getGuest().getName());
            System.out.println("Room Number: " + invoice.getRoom().getRoomNumber());
            System.out.println("Check-in Date: " + invoice.getCheckInDate());
            System.out.println("Check-out Date: " + invoice.getCheckOutDate());
            System.out.println("Total Amount: $" + invoice.getTotalAmount());
            System.out.println();
        }
    }

    public static void main(String[] args) {
        HotelCheckInSystem hotel = new HotelCheckInSystem();

        hotel.addRoom("101", 100.0);
        hotel.addRoom("102", 120.0);

        hotel.addGuest("John Doe", "johndoe@example.com", "1234567890");
        hotel.addGuest("Jane Doe", "janedoe@example.com", "0987654321");

        hotel.checkIn(hotel.guests.get(0), hotel.rooms.get(0), LocalDate.of(2024, 7, 26), LocalDate.of(2024, 7, 28));
        hotel.checkIn(hotel.guests.get(1), hotel.rooms.get(1), LocalDate.of(2024, 7, 29, 10, 0), LocalDate.of(2024, 7, 31));

        hotel.printInvoices();
    }
}
```