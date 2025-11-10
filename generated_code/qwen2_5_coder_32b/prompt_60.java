```java
import java.util.ArrayList;
import java.util.List;

class Movie {
    private String title;
    private String genre;
    private int duration; // in minutes

    public Movie(String title, String genre, int duration) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }
}

class Show {
    private Movie movie;
    private String date;
    private String time;
    private Screen screen;

    public Show(Movie movie, String date, String time, Screen screen) {
        this.movie = movie;
        this.date = date;
        this.time = time;
        this.screen = screen;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Screen getScreen() {
        return screen;
    }
}

class Seat {
    private String seatNumber;
    private boolean isBooked;

    public Seat(String seatNumber) {
        this.seatNumber = seatNumber;
        this.isBooked = false;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void bookSeat() {
        this.isBooked = true;
    }

    public void unbookSeat() {
        this.isBooked = false;
    }
}

class Screen {
    private int rows;
    private int columns;
    private List<Seat> seats;

    public Screen(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.seats = new ArrayList<>();
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                seats.add(new Seat(i + "" + (char) ('A' + j - 1)));
            }
        }
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void displaySeats() {
        for (Seat seat : seats) {
            System.out.print(seat.getSeatNumber() + (seat.isBooked() ? "[X] " : "[O] "));
        }
        System.out.println();
    }

    public boolean bookSeat(String seatNumber) {
        for (Seat seat : seats) {
            if (seat.getSeatNumber().equals(seatNumber) && !seat.isBooked()) {
                seat.bookSeat();
                return true;
            }
        }
        return false;
    }

    public boolean unbookSeat(String seatNumber) {
        for (Seat seat : seats) {
            if (seat.getSeatNumber().equals(seatNumber) && seat.isBooked()) {
                seat.unbookSeat();
                return true;
            }
        }
        return false;
    }
}

class BookingSystem {
    private List<Show> shows;

    public BookingSystem() {
        this.shows = new ArrayList<>();
    }

    public void addShow(Show show) {
        this.shows.add(show);
    }

    public List<Show> getShows() {
        return shows;
    }

    public void displayAvailableShows() {
        for (int i = 0; i < shows.size(); i++) {
            Show show = shows.get(i);
            System.out.println((i + 1) + ". " + show.getMovie().getTitle() + " on " + show.getDate() + " at " + show.getTime());
        }
    }

    public Show getShowByIndex(int index) {
        if (index >= 1 && index <= shows.size()) {
            return shows.get(index - 1);
        }
        return null;
    }
}

class Ticket {
    private Show show;
    private Seat seat;

    public Ticket(Show show, Seat seat) {
        this.show = show;
        this.seat = seat;
    }

    public Show getShow() {
        return show;
    }

    public Seat getSeat() {
        return seat;
    }

    @Override
    public String toString() {
        return "Ticket for " + show.getMovie().getTitle() + " on " + show.getDate() + " at " + show.getTime() + ", Seat: " + seat.getSeatNumber();
    }
}

class User {
    private String name;
    private List<Ticket> tickets;

    public User(String name) {
        this.name = name;
        this.tickets = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public void displayTickets() {
        for (Ticket