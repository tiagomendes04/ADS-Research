```java
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Vehicle {
    private String id;
    private String model;
    private boolean isAvailable;

    public Vehicle(String id, String model) {
        this.id = id;
        this.model = model;
        this.isAvailable = true;
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

class Booking {
    private String bookingId;
    private Vehicle vehicle;
    private Date startDate;
    private Date endDate;

    public Booking(String bookingId, Vehicle vehicle, Date startDate, Date endDate) {
        this.bookingId = bookingId;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}

class RentalSystem {
    private List<Vehicle> vehicles;
    private List<Booking> bookings;

    public RentalSystem() {
        this.vehicles = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void addBooking(Booking booking) {
        if (isVehicleAvailableDuringPeriod(booking.getVehicle(), booking.getStartDate(), booking.getEndDate())) {
            bookings.add(booking);
            booking.getVehicle().setAvailable(false);
        } else {
            throw new IllegalArgumentException("Vehicle not available during the specified period.");
        }
    }

    private boolean isVehicleAvailableDuringPeriod(Vehicle vehicle, Date startDate, Date endDate) {
        for (Booking booking : bookings) {
            if (booking.getVehicle().getId().equals(vehicle.getId()) &&
                ((startDate.before(booking.getEndDate()) && endDate.after(booking.getStartDate())) ||
                 startDate.equals(booking.getEndDate()) || endDate.equals(booking.getStartDate()))) {
                return false;
            }
        }
        return true;
    }

    public List<Vehicle> getAvailableVehicles(Date startDate, Date endDate) {
        List<Vehicle> availableVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (isVehicleAvailableDuringPeriod(vehicle, startDate, endDate)) {
                availableVehicles.add(vehicle);
            }
        }
        return availableVehicles;
    }
}
```