```java
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

// ---------- Domain Entities ----------
class Movie {
    private final long id;
    private final String title;
    private final int durationMinutes; // duration in minutes

    public Movie(long id, String title, int durationMinutes) {
        this.id = id;
        this.title = title;
        this.durationMinutes = durationMinutes;
    }

    public long getId() { return id; }
    public String getTitle() { return title; }
    public int getDurationMinutes() { return durationMinutes; }
}

class Seat {
    private final int row;
    private final int number;
    private boolean booked;

    public Seat(int row, int number) {
        this.row = row;
        this.number = number;
        this.booked = false;
    }

    public int getRow() { return row; }
    public int getNumber() { return number; }
    public boolean isBooked() { return booked; }

    void setBooked(boolean booked) { this.booked = booked; }

    @Override
    public String toString() {
        return "R" + row + "S" + number;
    }
}

class Auditorium {
    private final long id;
    private final String name;
    private final int rows;
    private final int seatsPerRow;
    private final Map<String, Seat> seatMap = new HashMap<>();

    public Auditorium(long id, String name, int rows, int seatsPerRow) {
        this.id = id;
        this.name = name;
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
        initSeats();
    }

    private void initSeats() {
        for (int r = 1; r <= rows; r++) {
            for (int s = 1; s <= seatsPerRow; s++) {
                Seat seat = new Seat(r, s);
                seatMap.put(key(r, s), seat);
            }
        }
    }

    private String key(int row, int number) {
        return row + "-" + number;
    }

    public Seat getSeat(int row, int number) {
        return seatMap.get(key(row, number));
    }

    public Collection<Seat> getAllSeats() {
        return seatMap.values();
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public int getRows() { return rows; }
    public int getSeatsPerRow() { return seatsPerRow; }
}

class Show {
    private final long id;
    private final Movie movie;
    private final Auditorium auditorium;
    private final LocalDateTime startTime;
    private final Map<String, Seat> seatMap; // snapshot of seats for this show

    public Show(long id, Movie movie, Auditorium auditorium, LocalDateTime startTime) {
        this.id = id;
        this.movie = movie;
        this.auditorium = auditorium;
        this.startTime = startTime;
        this.seatMap = new HashMap<>();
        copySeatsFromAuditorium();
    }

    private void copySeatsFromAuditorium() {
        for (Seat s : auditorium.getAllSeats()) {
            Seat copy = new Seat(s.getRow(), s.getNumber());
            seatMap.put(key(s), copy);
        }
    }

    private String key(Seat s) {
        return s.getRow() + "-" + s.getNumber();
    }

    public Seat getSeat(int row, int number) {
        return seatMap.get(row + "-" + number);
    }

    public Collection<Seat> getAllSeats() {
        return seatMap.values();
    }

    public long getId() { return id; }
    public Movie getMovie() { return movie; }
    public Auditorium getAuditorium() { return auditorium; }
    public LocalDateTime getStartTime() { return startTime; }
}

class Ticket {
    private static final AtomicLong COUNTER = new AtomicLong(1);
    private final long id;
    private final Show show;
    private final Seat seat;
    private final double price;

    public Ticket(Show show, Seat seat, double price) {
        this.id = COUNTER.getAndIncrement();
        this.show = show;
        this.seat = seat;
        this.price = price;
    }

    public long getId() { return id; }
    public Show getShow() { return show; }
    public Seat getSeat() { return seat; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return "Ticket#" + id + " [" + show.getMovie().getTitle() + " | " +
               show.getStartTime() + " | Seat " + seat + " | $" + price + "]";
    }
}

// ---------- Service Layer ----------
class BookingException extends RuntimeException {
    public BookingException(String message) { super(message); }
}

class BookingService {
    private final Map<Long, Show> shows = new HashMap<>();
    private final Map<Long, Ticket> tickets = new HashMap<>();

    // Register a new show
    public void addShow(Show show) {
        if (shows.containsKey(show.getId())) {
            throw new IllegalArgumentException("Show with id " + show.getId() + " already exists.");
        }
        shows.put(show.getId(), show);
    }

    // Retrieve a show by id
    public Show getShow(long showId) {
        Show show = shows.get(showId);
        if (show == null) throw new BookingException("Show not found.");
        return show;
    }

    // Check seat availability
    public boolean isSeatAvailable(long showId, int row, int number) {
        Seat seat = getShow(showId).getSeat(row, number);
        if (seat == null) throw new BookingException("Seat does not exist.");
        return !seat.isBooked();
    }

    // Book a single seat
    public Ticket bookSeat(long showId, int row, int number, double price) {
        Show show = getShow(showId);
        Seat seat = show.getSeat(row, number);
        if (seat == null) throw new BookingException("Seat does not exist.");
        synchronized (seat) {
            if (seat.isBooked()) throw new BookingException("Seat already booked.");
            seat.setBooked(true);
        }
        Ticket ticket = new Ticket(show, seat, price);
        tickets.put(ticket.getId(), ticket);
        return ticket;
    }

    // Book multiple seats atomically
    public List<Ticket> bookSeats(long showId, List<int[]> seatCoordinates, double pricePerSeat) {
        Show show = getShow(showId);
        List<Seat> seatsToBook = new ArrayList<>();
        // Validation phase
        for (int[] coord : seatCoordinates) {
            Seat seat = show.getSeat(coord[0], coord[1]);
            if (seat == null) throw new BookingException("Seat " + coord[0] + "-" + coord[1] + " does not exist.");
            if (seat.isBooked()) throw new BookingException("Seat " + seat + " already booked.");
            seatsToBook.add(seat);
        }
        // Booking phase
        List<Ticket> bookedTickets = new ArrayList<>();
        for (Seat seat : seatsToBook) {
            synchronized (seat) {
                if (seat.isBooked()) {
                    // rollback already booked seats in this transaction
                    for (Ticket t : bookedTickets) {
                        t.getSeat().setBooked(false);
                        tickets.remove(t.getId());
                    }
                    throw new BookingException("Seat " + seat + " became unavailable during booking.");
                }
                seat.setBooked(true);
                Ticket ticket = new Ticket(show, seat, pricePerSeat);
                tickets.put(ticket.getId(), ticket);
                bookedTickets.add(ticket);
            }
        }
        return bookedTickets;
    }

    // Cancel a ticket
    public void cancelTicket(long ticketId) {
        Ticket ticket = tickets.remove(ticketId);
        if (ticket == null) throw new BookingException("Ticket not found.");
        Seat seat = ticket.getSeat();
        synchronized (seat) {
            seat.setBooked(false);
        }
    }

    // Retrieve tickets for a show
    public List<Ticket> getTicketsForShow(long showId) {
        List<Ticket> result = new ArrayList<>();
        for (Ticket t : tickets.values()) {
            if (t.getShow().getId() == showId) result.add(t);
        }
        return result;
    }
}

// ---------- Demo ----------
public class MovieTicketBookingDemo {
    public static void main(String[] args) {
        // Setup movies and auditoriums
        Movie avengers = new Movie(1, "Avengers: Endgame", 181);
        Auditorium aud1 = new Auditorium(1, "Main Hall", 10, 15);

        // Create a show
        Show show = new Show(1001, avengers, aud1, LocalDateTime.of