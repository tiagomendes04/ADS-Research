```java
import java.util.*;

class Seat {
    int row;
    int col;

    public Seat(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

class Ticket {
    int seat;
    String customerName;

    public Ticket(int seat, String customerName) {
        this.seat = seat;
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "Seat: " + seat + ", Customer Name: " + customerName;
    }
}

class Customer {
    String name;

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer Name: " + name;
    }
}

class BookingSystem {
    private Map<Integer, Seat> seats;
    private Map<Integer, Customer> customers;

    public BookingSystem() {
        seats = new HashMap<>();
        customers = new HashMap<>();
    }

    public void addSeat(int row, int col) {
        seats.put(row * 100 + col, new Seat(row, col));
    }

    public void addCustomer(String name) {
        customers.put(name, new Customer(name));
    }

    public void bookSeat(int seatNumber, String customerName) {
        if (isSeatAvailable(seatNumber)) {
            Customer customer = customers.get(customerName);
            seats.get(seatNumber).setCustomer(customer);
            System.out.println("Seat booked successfully for " + customerName);
        } else {
            System.out.println("Seat is not available");
        }
    }

    public void displaySeats() {
        for (Seat seat : seats.values()) {
            System.out.println("Seat: " + seat.row + ", " + seat.col);
        }
    }

    public void displayCustomers() {
        for (Customer customer : customers.values()) {
            System.out.println(customer);
        }
    }

    public boolean isSeatAvailable(int seatNumber) {
        return !seats.containsKey(seatNumber);
    }
}

public class Main {
    public static void main(String[] args) {
        BookingSystem bookingSystem = new BookingSystem();
        bookingSystem.addSeat(1, 1);
        bookingSystem.addSeat(2, 2);
        bookingSystem.addSeat(3, 3);

        bookingSystem.addCustomer("John Doe");
        bookingSystem.addCustomer("Jane Doe");

        bookingSystem.bookSeat(1, "John Doe");
        bookingSystem.bookSeat(2, "Jane Doe");
        bookingSystem.bookSeat(3, "Bob Smith");

        bookingSystem.displaySeats();
        bookingSystem.displayCustomers();
    }
}
```