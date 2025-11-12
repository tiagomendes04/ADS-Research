```java
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/* ---------- Enums ---------- */
enum SeatStatus {
    AVAILABLE, RESERVED, SOLD
}

/* ---------- Core Entities ---------- */
class Seat {
    private final int row;
    private final int number;
    private SeatStatus status = SeatStatus.AVAILABLE;

    public Seat(int row, int number) {
        this.row = row;
        this.number = number;
    }

    public int getRow() { return row; }
    public int getNumber() { return number; }
    public SeatStatus getStatus() { return status; }

    void setStatus(SeatStatus status) {
        this.status = status;
    }

    public String getKey() {
        return row + "-" + number;
    }

    @Override
    public String toString() {
        return "Seat{" + "row=" + row + ", number=" + number + ", status=" + status + '}';
    }
}

class Screen {
    private final String name;
    private final int rows;
    private final int seatsPerRow;

    public Screen(String name, int rows, int seatsPerRow) {
        this.name = name;
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
    }

    public String getName() { return name; }
    public int getRows() { return rows; }
    public int getSeatsPerRow() { return seatsPerRow; }

    public Map<String, Seat> createSeatMap() {
        Map<String, Seat> map = new LinkedHashMap<>();
        for (int r = 1; r <= rows; r++) {
            for (int s = 1; s <= seatsPerRow; s++) {
                Seat seat = new Seat(r, s);
                map.put(seat.getKey(), seat);
            }
        }
        return map;
    }
}

class Movie {
    private final String title;
    private final int durationMinutes; // e.g., 120
    private final String rating;        // e.g., PG-13

    public Movie(String title, int durationMinutes, String rating) {
        this.title = title;
        this.durationMinutes = durationMinutes;
        this.rating = rating;
    }

    public String getTitle() { return title; }
    public int getDurationMinutes() { return durationMinutes; }
    public String getRating() { return rating; }

    @Override
    public String toString() {
        return "Movie{" + "title='" + title + '\'' + ", duration=" + durationMinutes + ", rating='" + rating + '\'' + '}';
    }
}

/* ---------- Show (Screening) ---------- */
class Show {
    private static final AtomicInteger SHOW_ID_SEQ = new AtomicInteger(1);

    private final int showId;
    private final Movie movie;
    private final Screen screen;
    private final LocalDateTime startTime;
    private final Map<String, Seat> seats; // key = "row-number"

    public Show(Movie movie, Screen screen, LocalDateTime startTime) {
        this.showId = SHOW_ID_SEQ.getAndIncrement();
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;
        this.seats = screen.createSeatMap();
    }

    public int getShowId() { return showId; }
    public Movie getMovie() { return movie; }
    public Screen getScreen() { return screen; }
    public LocalDateTime getStartTime() { return startTime; }
    public Collection<Seat> getAllSeats() { return seats.values(); }

    public Optional<Seat> getSeat(int row, int number) {
        return Optional.ofNullable(seats.get(row + "-" + number));
    }

    public synchronized boolean reserveSeat(int row, int number) {
        Seat seat = seats.get(row + "-" + number);
        if (seat != null && seat.getStatus() == SeatStatus.AVAILABLE) {
            seat.setStatus(SeatStatus.RESERVED);
            return true;
        }
        return false;
    }

    public synchronized boolean purchaseSeat(int row, int number) {
        Seat seat = seats.get(row + "-" + number);
        if (seat != null && (seat.getStatus() == SeatStatus.AVAILABLE || seat.getStatus() == SeatStatus.RESERVED)) {
            seat.setStatus(SeatStatus.SOLD);
            return true;
        }
        return false;
    }

    public synchronized void releaseSeat(int row, int number) {
        Seat seat = seats.get(row + "-" + number);
        if (seat != null && seat.getStatus() == SeatStatus.RESERVED) {
            seat.setStatus(SeatStatus.AVAILABLE);
        }
    }

    @Override
    public String toString() {
        return "Show{" + "showId=" + showId + ", movie=" + movie + ", screen=" + screen.getName() + ", startTime=" + startTime + '}';
    }
}

/* ---------- Booking & Ticket ---------- */
class Booking {
    private static final AtomicInteger BOOKING_ID_SEQ = new AtomicInteger(1);
    private static final AtomicInteger TICKET_ID_SEQ = new AtomicInteger(1);

    private final int bookingId;
    private final Show show;
    private final List<Seat> reservedSeats = new ArrayList<>();
    private final LocalDateTime bookingTime = LocalDateTime.now();
    private boolean confirmed = false;

    public Booking(Show show) {
        this.bookingId = BOOKING_ID_SEQ.getAndIncrement();
        this.show = show;
    }

    public int getBookingId() { return bookingId; }
    public Show getShow() { return show; }
    public List<Seat> getReservedSeats() { return Collections.unmodifiableList(reservedSeats); }
    public boolean isConfirmed() { return confirmed; }

    public boolean addSeat(int row, int number) {
        if (show.reserveSeat(row, number)) {
            show.getSeat(row, number).ifPresent(reservedSeats::add);
            return true;
        }
        return false;
    }

    public void cancel() {
        for (Seat seat : reservedSeats) {
            show.releaseSeat(seat.getRow(), seat.getNumber());
        }
        reservedSeats.clear();
    }

    public List<Ticket> confirm() {
        if (confirmed) throw new IllegalStateException("Booking already confirmed");
        List<Ticket> tickets = new ArrayList<>();
        for (Seat seat : reservedSeats) {
            show.purchaseSeat(seat.getRow(), seat.getNumber());
            tickets.add(new Ticket(this, seat));
        }
        confirmed = true;
        return tickets;
    }

    @Override
    public String toString() {
        return "Booking{" + "bookingId=" + bookingId + ", showId=" + show.getShowId() + ", seats=" + reservedSeats + ", confirmed=" + confirmed + '}';
    }
}

class Ticket {
    private final int ticketId;
    private final Booking booking;
    private final Seat seat;
    private final LocalDateTime issueTime = LocalDateTime.now();

    public Ticket(Booking booking, Seat seat) {
        this.ticketId = Booking.TICKET_ID_SEQ.getAndIncrement();
        this.booking = booking;
        this.seat = seat;
    }

    public int getTicketId() { return ticketId; }
    public Booking getBooking() { return booking; }
    public Seat getSeat() { return seat; }
    public LocalDateTime getIssueTime() { return issueTime; }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", movie='" + booking.getShow().getMovie().getTitle() + '\'' +
                ", screen='" + booking.getShow().getScreen().getName() + '\'' +
                ", startTime=" + booking.getShow().getStartTime() +
                ", seat=" + seat.getRow() + "-" + seat.getNumber() +
                '}';
    }
}

/* ---------- Booking System Facade ---------- */
class BookingSystem {
    private final Map<Integer, Movie> movies = new HashMap<>();
    private final Map<String, Screen> screens = new HashMap<>();
    private final Map<Integer, Show> shows = new HashMap<>();

    /* ---- Movie Management ---- */
    public Movie addMovie(String title, int durationMinutes, String rating) {
        Movie movie = new Movie(title, durationMinutes, rating);
        movies.put(movies.size() + 1, movie);
        return movie;
    }

    public Collection<Movie> listMovies() {
        return Collections.unmodifiableCollection(movies.values());
    }

    /* ---- Screen Management ---- */
    public Screen addScreen(String name, int rows, int seatsPerRow) {
        Screen screen = new Screen(name, rows, seatsPerRow);
        screens.put(name, screen);
        return screen;
    }

    public Collection<Screen> listScreens() {
        return Collections.unmodifiableCollection(screens.values());
    }

    /* ---- Show Scheduling ---- */
    public Show scheduleShow(int movieId, String screenName, LocalDateTime startTime) {
        Movie movie = movies.get(movieId);
        Screen screen = screens.get(screenName);
        if (movie == null) throw new IllegalArgumentException("Movie not found");
        if (screen == null) throw new IllegalArgumentException("Screen not found");
        Show show = new Show(movie, screen, startTime);
        shows.put(show.getShowId(), show);
        return show;
    }

    public Collection<Show> listShows() {
        return Collections.unmodifiableCollection(shows.values());
    }

    public Optional<Show> findShow(int showId) {
        return Optional.ofNullable(shows.get(showId));
    }

    /* ---- Booking Operations ---- */
    public Booking startBooking(int showId) {
        Show show = shows.get(showId);
        if (show == null) throw new IllegalArgumentException("Show not found");
        return new Booking(show);
    }
}

/* ---------- Example Usage (Optional) ---------- */
class Main {
    public static void main(String[] args) {
        BookingSystem system = new BookingSystem();

        // Add data
        system.addMovie("Inception", 148, "PG-13");
        system.addScreen("Screen-1", 10, 12);
        Show show = system.scheduleShow(1, "Screen-1", LocalDateTime.now().plusDays(1));

        // Start a booking
        Booking booking = system.startBooking(show.getShowId());
        booking.addSeat(3, 5);
        booking.addSeat(3, 6);
        List<Ticket> tickets = booking.confirm();

        // Print tickets
        tickets.forEach(System.out::println);
    }
}
```