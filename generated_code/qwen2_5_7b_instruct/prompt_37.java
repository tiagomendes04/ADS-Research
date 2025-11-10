```java
import java.util.*;

class Room {
    private int roomNumber;
    private boolean isAvailable;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

class Booking {
    private int bookingId;
    private Room room;
    private Date startDate;
    private Date endDate;

    public Booking(int bookingId, Room room, Date startDate, Date endDate) {
        this.bookingId = bookingId;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getBookingId() {
        return bookingId;
    }

    public Room getRoom() {
        return room;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}

class HotelBookingSystem {
    private List<Room> rooms;
    private List<Booking> bookings;

    public HotelBookingSystem() {
        this.rooms = new ArrayList<>();
        this.bookings = new ArrayList<>();
        initRooms();
    }

    private void initRooms() {
        for (int i = 1; i <= 10; i++) {
            rooms.add(new Room(i));
        }
    }

    public Room allocateRoom(Date startDate, Date endDate) {
        for (Room room : rooms) {
            if (room.isAvailable()) {
                room.setAvailable(false);
                bookings.add(new Booking(bookings.size() + 1, room, startDate, endDate));
                return room;
            }
        }
        return null;
    }

    public void cancelBooking(int bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId() == bookingId) {
                Room room = booking.getRoom();
                room.setAvailable(true);
                bookings.remove(booking);
                return;
            }
        }
    }

    public void printAvailableRooms() {
        System.out.println("Available Rooms:");
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println(room.getRoomNumber());
            }
        }
    }
}

public class HotelBookingApp {
    public static void main(String[] args) {
        HotelBookingSystem hotelBookingSystem = new HotelBookingSystem();

        // Allocate rooms
        hotelBookingSystem.allocateRoom(new Date(), new Date());
        hotelBookingSystem.allocateRoom(new Date(), new Date());
        hotelBookingSystem.printAvailableRooms();

        // Cancel a booking
        hotelBookingSystem.cancelBooking(1);
        hotelBookingSystem.printAvailableRooms();
    }
}
```

```java
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

class DateUtil {
    public static Date parseDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(dateStr);
    }
}

public class DateInputExample {
    public static void main(String[] args) {
        try {
            Date startDate = DateUtil.parseDate("2023-10-01");
            Date endDate = DateUtil.parseDate("2023-10-05");
            HotelBookingSystem hotelBookingSystem = new HotelBookingSystem();
            hotelBookingSystem.allocateRoom(startDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
```