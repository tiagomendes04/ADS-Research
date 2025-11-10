```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Movie {
    String title;
    int capacity;
    List<Seat> seats;

    public Movie(String title, int capacity) {
        this.title = title;
        this.capacity = capacity;
        this.seats = new ArrayList<>();
    }

    public void addSeat(Seat seat) {
        seats.add(seat);
    }

    public void displaySeats() {
        System.out.println("Seats for " + title + ":");
        for (Seat seat : seats) {
            System.out.println(seat.getSeatNumber());
        }
    }
}

class Seat {
    int seatNumber;

    public Seat(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }
}

class Booking {
    private Movie movie;
    private List<Seat> selectedSeats;
    private int totalCost;

    public Booking(Movie movie, List<Seat> selectedSeats) {
        this.movie = movie;
        this.selectedSeats = selectedSeats;
        this.totalCost = calculateTotalCost();
    }

    public int calculateTotalCost() {
        // Implementation for calculating the total cost based on selected seats and 
        // any other relevant factors
        return 0;
    }

    public Movie getMovie() {
        return movie;
    }

    public List<Seat> getSelectedSeats() {
        return selectedSeats;
    }
    
    public int getTotalCost() {
        return totalCost;
    }
}

class TicketBookingSystem {
    Map<String, Movie> movies = new HashMap<>();
    List<Booking> bookings;

    public TicketBookingSystem() {
        // Add your initial movies here
    }

    public void addMovie(Movie movie) {
        movies.put(movie.title, movie);
    }

    public void bookTicket(String movieTitle, List<Seat> selectedSeats) {
        Movie movie = movies.get(movieTitle);
        Booking booking = new Booking(movie, selectedSeats);
        bookings.add(booking);
    }

    public void displayAllBookings() {
        System.out.println("All Bookings:");
        for (Booking booking : bookings) {
            System.out.println("Movie: " + booking.getMovie().title);
            System.out.println("Selected Seats: " + booking.getSelectedSeats());
            System.out.println("Total Cost: " + booking.getTotalCost());
            System.out.println("-------------------");
        }
    }
}
```
```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TicketBookingSystem bookingSystem = new TicketBookingSystem();
        Scanner scanner = new Scanner(System.in);

        // Add your movie data here
        bookingSystem.addMovie(new Movie("The Matrix", 100));
        bookingSystem.addMovie(new Movie("Spiderman", 200));

        // Example booking
        System.out.println("Enter movie title: ");
        String movieTitle = scanner.nextLine();
        System.out.println("Enter selected seats (separated by spaces): ");
        String selectedSeats = scanner.nextLine();

        List<Seat> seats = new ArrayList<>();
        try {
            seats = parseSelectedSeats(selectedSeats);
        } catch (Exception e) {
            System.err.println("Invalid seat selection: " + e.getMessage());
            return;
        }

        bookingSystem.bookTicket(movieTitle, seats);
        bookingSystem.displayAllBookings();
    }

    private static List<Seat> parseSelectedSeats(String selectedSeats) {
        // Implementation to parse selected seats from string input
        // (replace this with your actual logic)
        List<Seat> seats = new ArrayList<>();
        return seats;
    }
}
```