```java
import java.util.*;

class Vehicle {
    int id;
    String type;
    int capacity;
    double fare;

    public Vehicle(int id, String type, int capacity, double fare) {
        this.id = id;
        this.type = type;
        this.capacity = capacity;
        this.fare = fare;
    }
}

class Driver {
    int id;
    String name;
    List<Vehicle> vehicles;

    public Driver(int id, String name) {
        this.id = id;
        this.name = name;
        this.vehicles = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    public void bookVehicle(Vehicle vehicle) {
        if (vehicles.contains(vehicle)) {
            System.out.println("Vehicle booked successfully.");
        } else {
            System.out.println("Vehicle not available.");
        }
    }

    public void cancelBooking(Vehicle vehicle) {
        if (vehicles.contains(vehicle)) {
            vehicles.remove(vehicle);
            System.out.println("Booking cancelled successfully.");
        } else {
            System.out.println("Vehicle not available.");
        }
    }

    public void displayVehicles() {
        System.out.println("Available Vehicles:");
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle.id + " " + vehicle.type + " " + vehicle.capacity + " " + vehicle.fare);
        }
    }
}

class Rider {
    int id;
    String name;
    List<Vehicle> vehicles;

    public Rider(int id, String name) {
        this.id = id;
        this.name = name;
        this.vehicles = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    public void bookVehicle(Vehicle vehicle) {
        if (vehicles.contains(vehicle)) {
            System.out.println("Vehicle booked successfully.");
        } else {
            System.out.println("Vehicle not available.");
        }
    }

    public void cancelBooking(Vehicle vehicle) {
        if (vehicles.contains(vehicle)) {
            vehicles.remove(vehicle);
            System.out.println("Booking cancelled successfully.");
        } else {
            System.out.println("Vehicle not available.");
        }
    }

    public void displayVehicles() {
        System.out.println("Available Vehicles:");
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle.id + " " + vehicle.type + " " + vehicle.capacity + " " + vehicle.fare);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Driver driver = new Driver(1, "John Doe");
        Rider rider = new Rider(2, "Jane Doe");

        Vehicle car = new Vehicle(1, "Car", 4, 100);
        Vehicle bike = new Vehicle(2, "Bike", 3, 50);
        Vehicle taxi = new Vehicle(3, "Taxi", 2, 200);

        driver.addVehicle(car);
        driver.addVehicle(bike);
        driver.addVehicle(taxi);

        rider.addVehicle(car);
        rider.addVehicle(bike);
        rider.addVehicle(taxi);

        driver.displayVehicles();
        rider.displayVehicles();

        driver.bookVehicle(car);
        rider.bookVehicle(bike);

        driver.cancelBooking(car);
        driver.cancelBooking(bike);

        driver.displayVehicles();
        rider.displayVehicles();
    }
}
```