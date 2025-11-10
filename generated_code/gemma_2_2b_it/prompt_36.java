```java
import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private String model;
    private String make;
    private String year;
    private boolean available;

    public Vehicle(String model, String make, String year) {
        this.model = model;
        this.make = make;
        this.year = year;
        this.available = true;
    }

    public String getModel() {
        return model;
    }

    public String getMake() {
        return make;
    }

    public String getYear() {
        return year;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

public class Booking {
    private String customerName;
    private Vehicle vehicle;
    private String startDate;
    private String endDate;

    public Booking(String customerName, Vehicle vehicle, String startDate, String endDate) {
        this.customerName = customerName;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}

public class RentalSystem {
    private List<Vehicle> vehicles;
    private List<Booking> bookings;

    public RentalSystem() {
        vehicles = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public Vehicle getVehicle(String model, String make, String year) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getModel().equals(model) && vehicle.getMake().equals(make) && vehicle.getYear().equals(year)) {
                return vehicle;
            }
        }
        return null;
    }

    public void makeBooking(Booking booking) {
        bookings.add(booking);
    }
}
```