```java
import java.util.*;

public class Vehicle {
    private int id;
    private String make;
    private String model;
    private int capacity;

    public Vehicle(int id, String make, String model, int capacity) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.capacity = capacity;
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

    public int getCapacity() {
        return capacity;
    }
}

public class Booking {
    private int id;
    private int vehicleId;
    private int day;
    private int returnDay;
    private int isAvailable;

    public Booking(int id, int vehicleId, int day, int returnDay) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.day = day;
        this.returnDay = returnDay;
        this.isAvailable = true;
    }

    public int getId() {
        return id;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public int getDay() {
        return day;
    }

    public int getReturnDay() {
        return returnDay;
    }

    public int getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}

public class VehicleRentalSystem {
    private Map<Integer, List<Vehicle>> vehicles;
    private Map<Integer, List<Booking>> bookings;
    private Map<Integer, Boolean> availableVehicles;

    public VehicleRentalSystem() {
        vehicles = new HashMap<>();
        bookings = new HashMap<>();
        availableVehicles = new HashMap<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.put(vehicle.getId(), new ArrayList<>(Arrays.asList(vehicle)));
    }

    public void addBooking(Booking booking) {
        bookings.put(booking.getId(), new ArrayList<>(Arrays.asList(booking)));
    }

    public void removeVehicle(int id) {
        if (vehicles.containsKey(id)) {
            vehicles.get(id).clear();
        }
    }

    public void removeBooking(int id) {
        if (bookings.containsKey(id)) {
            bookings.get(id).clear();
        }
    }

    public void checkAvailability(int vehicleId) {
        if (availableVehicles.containsKey(vehicleId)) {
            availableVehicles.put(vehicleId, false);
        }
    }

    public void checkAvailability(int day, int returnDay) {
        if (availableVehicles.containsKey(day) && availableVehicles.containsKey(returnDay)) {
            availableVehicles.put(day, false);
            availableVehicles.put(returnDay, false);
        }
    }

    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> availableVehicles = new ArrayList<>();
        for (Map.Entry<Integer, List<Vehicle>> entry : availableVehicles.entrySet()) {
            for (Vehicle vehicle : entry.getValue()) {
                if (vehicle.getCapacity() > 0) {
                    availableVehicles.add(vehicle);
                }
            }
        }
        return availableVehicles;
    }
}
```