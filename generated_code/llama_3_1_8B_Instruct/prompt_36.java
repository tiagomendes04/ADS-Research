```java
public class Vehicle {
    private int id;
    private String make;
    private String model;
    private String type;
    private boolean available;

    public Vehicle(int id, String make, String model, String type) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.type = type;
        this.available = true;
    }

    public int getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getType() {
        return type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
```

```java
public class Customer {
    private int id;
    private String name;
    private String email;

    public Customer(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
```

```java
public class Booking {
    private int id;
    private Customer customer;
    private Vehicle vehicle;
    private String startDate;
    private String endDate;

    public Booking(int id, Customer customer, Vehicle vehicle, String startDate, String endDate) {
        this.id = id;
        this.customer = customer;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
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
```

```java
import java.util.ArrayList;
import java.util.List;

public class VehicleRentalSystem {
    private List<Vehicle> vehicles;
    private List<Booking> bookings;

    public VehicleRentalSystem() {
        this.vehicles = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
        vehicle.setAvailable(false);
    }

    public void removeBooking(int id) {
        for (Booking booking : bookings) {
            if (booking.getId() == id) {
                bookings.remove(booking);
                vehicle.setAvailable(true);
                return;
            }
        }
    }

    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> availableVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.isAvailable()) {
                availableVehicles.add(vehicle);
            }
        }
        return availableVehicles;
    }

    public List<Vehicle> getUnavailableVehicles() {
        List<Vehicle> unavailableVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (!vehicle.isAvailable()) {
                unavailableVehicles.add(vehicle);
            }
        }
        return unavailableVehicles;
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        VehicleRentalSystem system = new VehicleRentalSystem();

        Vehicle vehicle1 = new Vehicle(1, "Toyota", "Camry", "Sedan");
        Vehicle vehicle2 = new Vehicle(2, "Honda", "Civic", "Hatchback");

        system.addVehicle(vehicle1);
        system.addVehicle(vehicle2);

        Customer customer = new Customer(1, "John Doe", "john@example.com");

        Booking booking = new Booking(1, customer, vehicle1, "2024-07-26", "2024-07-31");
        system.addBooking(booking);

        List<Vehicle> availableVehicles = system.getAvailableVehicles();
        for (Vehicle vehicle : availableVehicles) {
            System.out.println(vehicle.getMake() + " " + vehicle.getModel());
        }

        List<Vehicle> unavailableVehicles = system.getUnavailableVehicles();
        for (Vehicle vehicle : unavailableVehicles) {
            System.out.println(vehicle.getMake() + " " + vehicle.getModel());
        }

        system.removeBooking(1);

        availableVehicles = system.getAvailableVehicles();
        for (Vehicle vehicle : availableVehicles) {
            System.out.println(vehicle.getMake() + " " + vehicle.getModel());
        }
    }
}
```