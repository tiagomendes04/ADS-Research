```java
import java.util.ArrayList;
import java.util.List;

class Movie {
    private String title;
    private String time;
    private int duration;

    public Movie(String title, String time, int duration) {
        this.title = title;
        this.time = time;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public int getDuration() {
        return duration;
    }
}

class Seat {
    private int row;
    private int column;
    private boolean isBooked;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.isBooked = false;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void bookSeat() {
        this.isBooked = true;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "row=" + row +
                ", column=" + column +
                ", isBooked=" + isBooked +
                '}';
    }
}

class CinemaHall {
    private List<List<Seat>> seats;

    public CinemaHall(int rows, int columns) {
        seats = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            List<Seat> rowSeats = new ArrayList<>();
            for (int j = 0; j < columns; j++) {
                rowSeats.add(new Seat(i + 1, j + 1));
            }
            seats.add(rowSeats);
        }
    }

    public List<List<Seat>> getSeats() {
        return seats;
    }

    public boolean bookSeat(int row, int column) {
        if (row > 0 && row <= seats.size() && column > 0 && column <= seats.get(0).size()) {
            Seat seat = seats.get(row - 1).get(column - 1);
            if (!seat.isBooked()) {
                seat.bookSeat();
                return true;
            }
        }
        return false;
    }

    public void displaySeats() {
        for (List<Seat> row : seats) {
            for (Seat seat : row) {
                System.out.print(seat + " ");
            }
            System.out.println();
        }
    }
}

class BookingSystem {
    private List<Movie> movies;
    private List<CinemaHall> cinemaHalls;

    public BookingSystem() {
        movies = new ArrayList<>();
        cinemaHalls = new ArrayList<>();
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void addCinemaHall(CinemaHall cinemaHall) {
        cinemaHalls.add(cinemaHall);
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public List<CinemaHall> getCinemaHalls() {
        return cinemaHalls;
    }

    public boolean bookTicket(int movieIndex, int hallIndex, int row, int column) {
        if (movieIndex >= 0 && movieIndex < movies.size() &&
            hallIndex >= 0 && hallIndex < cinemaHalls.size()) {
            return cinemaHalls.get(hallIndex).bookSeat(row, column);
        }
        return false;
    }

    public void displayMovies() {
        for (int i = 0; i < movies.size(); i++) {
            System.out.println(i + ": " + movies.get(i).getTitle() + " at " + movies.get(i).getTime());
        }
    }

    public void displaySeats(int hallIndex) {
        if (hallIndex >= 0 && hallIndex < cinemaHalls.size()) {
            cinemaHalls.get(hallIndex).displaySeats();
        }
    }
}
```