```java
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

enum BookingStatus {
    PENDING,
    CONFIRMED,
    CANCELLED,
    COMPLETED
}

abstract class Vehicle {
    private final long id;
    private final String make;
    private final String model;
    private final int year;
    private boolean available = true;

    Vehicle(long id, String make, String model, int year) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
    }

    long getId() { return id; }
    String getMake() { return make; }
    String getModel() { return model; }
    int getYear() { return year; }
    boolean isAvailable() { return available; }
    void setAvailable(boolean available) { this.available = available; }
}

class Car extends Vehicle {
    private final int seats;

    Car(long id, String make, String model, int year, int seats) {
        super(id, make, model, year);
        this.seats = seats;
    }

    int getSeats() { return seats; }
}

class Truck extends Vehicle {
    private final double loadCapacity; // in tons

    Truck(long id, String make, String model, int year, double loadCapacity) {
        super(id, make, model, year);
        this.loadCapacity = loadCapacity;
    }

    double getLoadCapacity() { return loadCapacity; }
}

class Customer {
    private final long id;
    private final String name;
    private final String driverLicenseNumber;

    Customer(long id, String name, String driverLicenseNumber) {
        this.id = id;
        this.name = name;
        this.driverLicenseNumber = driverLicenseNumber;
    }

    long getId() { return id; }
    String getName() { return name; }
    String getDriverLicenseNumber() { return driverLicenseNumber; }
}

class Booking {
    private final long id;
    private final Vehicle vehicle;
    private final Customer customer;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private BookingStatus status;

    Booking(long id, Vehicle vehicle, Customer customer, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.vehicle = vehicle;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = BookingStatus.PENDING;
    }

    long getId() { return id; }
    Vehicle getVehicle() { return vehicle; }
    Customer getCustomer() { return customer; }
    LocalDate getStartDate() { return startDate; }
    LocalDate getEndDate() { return endDate; }
    BookingStatus getStatus() { return status; }
    void setStatus(BookingStatus status) { this.status = status; }
}

class RentalService {
    private final Map<Long, Vehicle> vehicles = new HashMap<>();
    private final Map<Long, Customer> customers = new HashMap<>();
    private final Map<Long, Booking> bookings = new HashMap<>();

    private final AtomicLong vehicleIdSeq = new AtomicLong(1);
    private final AtomicLong customerIdSeq = new AtomicLong(1);
    private final AtomicLong bookingIdSeq = new AtomicLong(1);

    // Vehicle management
    Vehicle addCar(String make, String model, int year, int seats) {
        long id = vehicleIdSeq.getAndIncrement();
        Car car = new Car(id, make, model, year, seats);
        vehicles.put(id, car);
        return car;
    }

    Vehicle addTruck(String make, String model, int year, double loadCapacity) {
        long id = vehicleIdSeq.getAndIncrement();
        Truck truck = new Truck(id, make, model, year, loadCapacity);
        vehicles.put(id, truck);
        return truck;
    }

    boolean removeVehicle(long vehicleId) {
        Vehicle v = vehicles.remove(vehicleId);
        if (v == null) return false;
        // Cancel any pending bookings for this vehicle
        bookings.values().removeIf(b -> b.getVehicle().getId() == vehicleId && b.getStatus() == BookingStatus.PENDING);
        return true;
    }

    List<Vehicle> findAvailableVehicles(LocalDate from, LocalDate to) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : vehicles.values()) {
            if (v.isAvailable() && isVehicleFree(v.getId(), from, to)) {
                result.add(v);
            }
        }
        return result;
    }

    private boolean isVehicleFree(long vehicleId, LocalDate from, LocalDate to) {
        for (Booking b : bookings.values()) {
            if (b.getVehicle().getId() == vehicleId && b.getStatus() == BookingStatus.CONFIRMED) {
                if (!(to.isBefore(b.getStartDate()) || from.isAfter(b.getEndDate()))) {
                    return false;
                }
            }
        }
        return true;
    }

    // Customer management
    Customer addCustomer(String name, String driverLicenseNumber) {
        long id = customerIdSeq.getAndIncrement();
        Customer c = new Customer(id, name, driverLicenseNumber);
        customers.put(id, c);
        return c;
    }

    // Booking management
    Booking createBooking(long vehicleId, long customerId, LocalDate start, LocalDate end) {
        Vehicle vehicle = vehicles.get(vehicleId);
        Customer customer = customers.get(customerId);
        if (vehicle == null || customer == null) throw new IllegalArgumentException("Invalid vehicle or customer");
        if (!isVehicleFree(vehicleId, start, end)) throw new IllegalStateException("Vehicle not available for the selected dates");
        long id = bookingIdSeq.getAndIncrement();
        Booking booking = new Booking(id, vehicle, customer, start, end);
        booking.setStatus(BookingStatus.CONFIRMED);
        bookings.put(id, booking);
        vehicle.setAvailable(false);
        return booking;
    }

    boolean cancelBooking(long bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking == null) return false;
        if (booking.getStatus() != BookingStatus.CONFIRMED) return false;
        booking.setStatus(BookingStatus.CANCELLED);
        // Re‑evaluate vehicle availability
        boolean stillBooked = bookings.values().stream()
                .anyMatch(b -> b.getVehicle().getId() == booking.getVehicle().getId()
                        && b.getStatus() == BookingStatus.CONFIRMED);
        if (!stillBooked) {
            booking.getVehicle().setAvailable(true);
        }
        return true;
    }

    List<Booking> getBookingsForCustomer(long customerId) {
        List<Booking> result = new ArrayList<>();
        for (Booking b : bookings.values()) {
            if (b.getCustomer().getId() == customerId) {
                result.add(b);
            }
        }
        return result;
    }

    List<Booking> getAllBookings() {
        return new ArrayList<>(bookings.values());
    }
}
```