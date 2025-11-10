```java
import java.util.ArrayList;
import java.util.List;

public class RideSharingApp {

    static class Driver {
        String name;
        String licenseNumber;
        int carId;
        List<Ride> rides;

        public Driver(String name, String licenseNumber, int carId) {
            this.name = name;
            this.licenseNumber = licenseNumber;
            this.carId = carId;
            this.rides = new ArrayList<>();
        }
    }

    static class Rider {
        String name;
        String location;
        String destination;
        List<Ride> rides;

        public Rider(String name, String location, String destination) {
            this.name = name;
            this.location = location;
            this.destination = destination;
            this.rides = new ArrayList<>();
        }
    }

    static class Ride {
        int id;
        Driver driver;
        Rider rider;
        String status;
        String date;
        String startTime;

        public Ride(int id, Driver driver, Rider rider, String status, String date, String startTime) {
            this.id = id;
            this.driver = driver;
            this.rider = rider;
            this.status = status;
            this.date = date;
            this.startTime = startTime;
        }
    }

    public static void main(String[] args) {
        // Example usage
        Driver driver1 = new Driver("John Doe", "ABC1234", 12345);
        Rider rider1 = new Rider("Jane Smith", "123 Main Street", "456 Elm Street");
        Ride ride1 = new Ride(1, driver1, rider1, "Active", "2023-10-26", "2023-10-26 08:00:00");

        System.out.println(driver1.name + " is driving a ride for " + rider1.name);
    }
}
```