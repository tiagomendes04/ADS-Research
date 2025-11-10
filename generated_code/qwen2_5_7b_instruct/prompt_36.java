```java
import java.util.Date;

public class Vehicle {
    private String id;
    private String model;
    private String make;
    private String plateNumber;

    public Vehicle(String id, String model, String make, String plateNumber) {
        this.id = id;
        this.model = model;
        this.make = make;
        this.plateNumber = plateNumber;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
}

class Booking {
    private String id;
    private Vehicle vehicle;
    private Date startDate;
    private Date endDate;

    public Booking(String id, Vehicle vehicle, Date startDate, Date endDate) {
        this.id = id;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

class AvailabilityManager {
    private Vehicle vehicle;
    private Booking[] bookings;

    public AvailabilityManager(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.bookings = new Booking[0];
    }

    public void addBooking(Booking booking) {
        boolean isAvailable = true;
        for (Booking existingBooking : bookings) {
            if (isDateOverlap(booking.getStartDate(), booking.getEndDate(), existingBooking.getStartDate(), existingBooking.getEndDate())) {
                isAvailable = false;
                break;
            }
        }

        if (isAvailable) {
            Booking[] newBookings = new Booking[bookings.length + 1];
            System.arraycopy(bookings, 0, newBookings, 0, bookings.length);
            newBookings[bookings.length] = booking;
            bookings = newBookings;
            System.out.println("Booking added successfully.");
        } else {
            System.out.println("Vehicle is not available for the requested dates.");
        }
    }

    private boolean isDateOverlap(Date startDate1, Date endDate1, Date startDate2, Date endDate2) {
        return (startDate1.before(endDate2) && endDate1.after(startDate2));
    }

    public Booking[] getBookings() {
        return bookings;
    }
}

public class VehicleRentalSystem {
    public static void main(String[] args) {
        Vehicle vehicle = new Vehicle("V1", "Toyota Camry", "Toyota", "ABC123");
        AvailabilityManager manager = new AvailabilityManager(vehicle);

        Booking booking1 = new Booking("B1", vehicle, new Date("2023-01-01"), new Date("2023-01-03"));
        Booking booking2 = new Booking("B2", vehicle, new Date("2023-01-02"), new Date("2023-01-04"));

        manager.addBooking(booking1);
        manager.addBooking(booking2);

        for (Booking booking : manager.getBookings()) {
            System.out.println("Booking ID: " + booking.getId() + ", Vehicle: " + booking.getVehicle().getMake() + " " + booking.getVehicle().getModel());
            System.out.println("Start Date: " + booking.getStartDate() + ", End Date: " + booking.getEndDate());
        }
    }
}
```