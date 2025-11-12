```java
import java.util.*;

class User {
    private String name;
    private String id;
    private double rating;

    public User(String name, String id) {
        this.name = name;
        this.id = id;
        this.rating = 5.0;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public double getRating() {
        return rating;
    }

    public void updateRating(double newRating) {
        this.rating = newRating;
    }
}

class Driver extends User {
    private boolean isAvailable;

    public Driver(String name, String id) {
        super(name, id);
        this.isAvailable = true;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailability(boolean availability) {
        this.isAvailable = availability;
    }
}

class Rider extends User {
    public Rider(String name, String id) {
        super(name, id);
    }
}

class RideSharingApp {
    private Map<String, Driver> drivers;
    private Map<String, Rider> riders;

    public RideSharingApp() {
        this.drivers = new HashMap<>();
        this.riders = new HashMap<>();
    }

    public void addDriver(Driver driver) {
        drivers.put(driver.getId(), driver);
    }

    public void addRider(Rider rider) {
        riders.put(rider.getId(), rider);
    }

    public Driver findAvailableDriver() {
        for (Driver driver : drivers.values()) {
            if (driver.isAvailable()) {
                return driver;
            }
        }
        return null;
    }

    public void requestRide(String riderId) {
        Rider rider = riders.get(riderId);
        if (rider == null) {
            System.out.println("Rider not found.");
            return;
        }
        Driver driver = findAvailableDriver();
        if (driver == null) {
            System.out.println("No available drivers.");
            return;
        }
        System.out.println("Ride requested by " + rider.getName() + ". Driver " + driver.getName() + " is on the way.");
        driver.setAvailability(false);
    }

    public void completeRide(String driverId) {
        Driver driver = drivers.get(driverId);
        if (driver == null) {
            System.out.println("Driver not found.");
            return;
        }
        System.out.println("Ride completed by driver " + driver.getName());
        driver.setAvailability(true);
    }

    public void rateDriver(String driverId, double rating) {
        Driver driver = drivers.get(driverId);
        if (driver == null) {
            System.out.println("Driver not found.");
            return;
        }
        driver.updateRating(rating);
        System.out.println("Driver " + driver.getName() + " rated: " + rating);
    }
}

public class Main {
    public static void main(String[] args) {
        RideSharingApp app = new RideSharingApp();

        Driver driver1 = new Driver("Alice", "D001");
        Driver driver2 = new Driver("Bob", "D002");
        Rider rider1 = new Rider("Charlie", "R001");
        Rider rider2 = new Rider("David", "R002");

        app.addDriver(driver1);
        app.addDriver(driver2);
        app.addRider(rider1);
        app.addRider(rider2);

        app.requestRide("R001");
        app.completeRide("D001");
        app.rateDriver("D001", 4.5);

        app.requestRide("R002");
        app.completeRide("D002");
        app.rateDriver("D002", 4.8);
    }
}
```