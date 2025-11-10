```java
import java.util.ArrayList;
import java.util.List;

class RideSharingApp {
    private List<Rider> riders;
    private List<Driver> drivers;

    public RideSharingApp() {
        this.riders = new ArrayList<>();
        this.drivers = new ArrayList<>();
    }

    public void addRider(Rider rider) {
        riders.add(rider);
    }

    public void addDriver(Driver driver) {
        drivers.add(driver);
    }

    public void matchRiderWithDriver(Rider rider, Driver driver) {
        if (rider != null && driver != null) {
            driver.acceptRide(rider);
        }
    }

    public void displayRiders() {
        for (Rider rider : riders) {
            System.out.println("Rider ID: " + rider.getId() + ", Name: " + rider.getName());
        }
    }

    public void displayDrivers() {
        for (Driver driver : drivers) {
            System.out.println("Driver ID: " + driver.getId() + ", Name: " + driver.getName());
        }
    }
}

class Person {
    private String id;
    private String name;

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class Rider extends Person {
    public Rider(String id, String name) {
        super(id, name);
    }
}

class Driver extends Person {
    private boolean isAvailable;

    public Driver(String id, String name) {
        super(id, name);
        this.isAvailable = true;
    }

    public void acceptRide(Rider rider) {
        System.out.println("Driver " + this.getName() + " has accepted the ride from " + rider.getName());
        isAvailable = false;
    }
}

public class Main {
    public static void main(String[] args) {
        RideSharingApp app = new RideSharingApp();

        Driver driver1 = new Driver("D1", "Driver1");
        Driver driver2 = new Driver("D2", "Driver2");

        Rider rider1 = new Rider("R1", "Rider1");
        Rider rider2 = new Rider("R2", "Rider2");

        app.addDriver(driver1);
        app.addDriver(driver2);

        app.addRider(rider1);
        app.addRider(rider2);

        app.matchRiderWithDriver(rider1, driver1);
        app.matchRiderWithDriver(rider2, driver2);

        app.displayRiders();
        app.displayDrivers();
    }
}
```