```java
public class Movie {
    private String title;
    private int duration;

    public Movie(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }
}

public class Seat {
    private int row;
    private int column;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }
}

public class CinemaHall {
    private int rows;
    private int columns;
    private Seat[][] seats;

    public CinemaHall(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.seats = new Seat[rows][columns];
        initializeSeats();
    }

    private void initializeSeats() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                seats[i][j] = new Seat(i + 1, j + 1);
            }
        }
    }

    public Seat getSeat(int row, int column) {
        if (row > 0 && row <= rows && column > 0 && column <= columns) {
            return seats[row - 1][column - 1];
        }
        return null;
    }

    public void bookSeat(int row, int column) {
        Seat seat = getSeat(row, column);
        if (seat != null && !seat.isBooked()) {
            seat.setBooked(true);
            System.out.println("Seat " + seat + " booked successfully.");
        } else {
            System.out.println("Seat " + seat + " is already booked or invalid.");
        }
    }

    public boolean isSeatAvailable(int row, int column) {
        Seat seat = getSeat(row, column);
        return seat != null && !seat.isBooked();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Seat seat = seats[i][j];
                sb.append(seat.isBooked() ? "X" : "O");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

public class BookingSystem {
    public static void main(String[] args) {
        CinemaHall hall = new CinemaHall(5, 10);
        System.out.println("Initial seats:\n" + hall);
        hall.bookSeat(2, 3);
        hall.bookSeat(2, 4);
        System.out.println("\nSeats after booking:\n" + hall);
        System.out.println("Is seat (2, 3) available? " + hall.isSeatAvailable(2, 3));
        System.out.println("Is seat (3, 3) available? " + hall.isSeatAvailable(3, 3));
    }
}
```