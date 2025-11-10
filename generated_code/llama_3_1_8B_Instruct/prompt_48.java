```java
// RideSharing.java

import java.util.ArrayList;
import java.util.List;

class Rider {
    private String id;
    private String name;
    private String phoneNumber;

    public Rider(String id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

class Driver {
    private String id;
    private String name;
    private String phoneNumber;
    private Vehicle vehicle;
    private boolean isAvailable;

    public Driver(String id, String name, String phoneNumber, Vehicle vehicle) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.vehicle = vehicle;
        this.isAvailable = true;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailability(boolean availability) {
        isAvailable = availability;
    }
}

class Vehicle {
    private String make;
    private String model;
    private String licensePlate;

    public Vehicle(String make, String model, String licensePlate) {
        this.make = make;
        this.model = model;
        this.licensePlate = licensePlate;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
}

class Ride {
    private String id;
    private Rider rider;
    private Driver driver;
    private Vehicle vehicle;

    public Ride(String id, Rider rider, Driver driver, Vehicle vehicle) {
        this.id = id;
        this.rider = rider;
        this.driver = driver;
        this.vehicle = vehicle;
    }

    public String getId() {
        return id;
    }

    public Rider getRider() {
        return rider;
    }

    public Driver getDriver() {
        return driver;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Rider> riders = new ArrayList<>();
        List<Driver> drivers = new ArrayList<>();
        List<Ride> rides = new ArrayList<>();

        Rider rider1 = new Rider("R1", "John Doe", "1234567890");
        Rider rider2 = new Rider("R2", "Jane Doe", "0987654321");

        riders.add(rider1);
        riders.add(rider2);

        Driver driver1 = new Driver("D1", "Bob Smith", "1111111111", new Vehicle("Toyota", "Camry", "ABC123"));
        Driver driver2 = new Driver("D2", "Alice Johnson", "2222222222", new Vehicle("Honda", "Civic", "DEF456"));

        drivers.add(driver1);
        drivers.add(driver2);

        Ride ride1 = new Ride("R1D1", rider1, driver1, driver1.getVehicle());
        Ride ride2 = new Ride("R2D2", rider2, driver2, driver2.getVehicle());

        rides.add(ride1);
        rides.add(ride2);

        System.out.println("Riders:");
        for (Rider rider : riders) {
            System.out.println("ID: " + rider.getId() + ", Name: " + rider.getName() + ", Phone Number: " + rider.getPhoneNumber());
        }

        System.out.println("\nDrivers:");
        for (Driver driver : drivers) {
            System.out.println("ID: " + driver.getId() + ", Name: " + driver.getName() + ", Phone Number: " + driver.getPhoneNumber());
            System.out.println("Vehicle: " + driver.getVehicle().getMake() + " " + driver.getVehicle().getModel() + ", License Plate: " + driver.getVehicle().getLicensePlate());
            System.out.println("Availability: " + driver.isAvailable());
            System.out.println();
        }

        System.out.println("\nRides:");
        for (Ride ride : rides) {
            System.out.println("ID: " + ride.getId());
            System.out.println("Rider: " + ride.getRider().getName());
            System.out.println("Driver: " + ride.getDriver().getName());
            System.out.println("Vehicle: " + ride.getDriver().getVehicle().getMake() + " " + ride.getDriver().getVehicle().getModel() + ", License Plate: " + ride.getDriver().getVehicle().getLicensePlate());
            System.out.println();
        }
    }
}
```