```java
package com.movie.ticket.booking.system;

import java.util.ArrayList;
import java.util.List;

public class MovieTicketBookingSystem {
    private List<Movie> movies;
    private List<Show> shows;

    public MovieTicketBookingSystem() {
        movies = new ArrayList<>();
        shows = new ArrayList<>();
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void addShow(Show show) {
        shows.add(show);
    }

    public void bookTicket(int showId, int seatNumber) {
        for (Show show : shows) {
            if (show.getId() == showId) {
                show.bookSeat(seatNumber);
                System.out.println("Ticket booked successfully for show " + show.getMovie().getName() + " at seat " + seatNumber);
                return;
            }
        }
        System.out.println("Show not found.");
    }

    public static void main(String[] args) {
        MovieTicketBookingSystem bookingSystem = new MovieTicketBookingSystem();

        Movie movie = new Movie("Inception", "Action", "Leonardo DiCaprio");
        bookingSystem.addMovie(movie);

        Show show = new Show(movie, 10, "2024-07-26 10:00 AM");
        bookingSystem.addShow(show);

        bookingSystem.bookTicket(show.getId(), 5);
    }
}
```

```java
package com.movie.ticket.booking.system;

public class Movie {
    private String name;
    private String genre;
    private String leadActor;

    public Movie(String name, String genre, String leadActor) {
        this.name = name;
        this.genre = genre;
        this.leadActor = leadActor;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getLeadActor() {
        return leadActor;
    }
}
```

```java
package com.movie.ticket.booking.system;

import java.util.ArrayList;
import java.util.List;

public class Show {
    private Movie movie;
    private int id;
    private String date;
    private List<Seat> seats;

    public Show(Movie movie, int id, String date) {
        this.movie = movie;
        this.id = id;
        this.date = date;
        this.seats = new ArrayList<>();
    }

    public void addSeat(Seat seat) {
        seats.add(seat);
    }

    public void bookSeat(int seatNumber) {
        for (Seat seat : seats) {
            if (seat.getSeatNumber() == seatNumber && seat.isAvailable()) {
                seat.book();
                System.out.println("Seat " + seatNumber + " booked successfully for show " + movie.getName());
                return;
            }
        }
        System.out.println("Seat not available or not found.");
    }

    public int getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getDate() {
        return date;
    }
}
```

```java
package com.movie.ticket.booking.system;

public class Seat {
    private int seatNumber;
    private boolean isAvailable;

    public Seat(int seatNumber) {
        this.seatNumber = seatNumber;
        this.isAvailable = true;
    }

    public void book() {
        isAvailable = false;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
```