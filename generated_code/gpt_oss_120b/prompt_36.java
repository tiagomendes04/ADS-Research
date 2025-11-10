```java
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/* ---------- Enums ---------- */
enum VehicleType {
    CAR, TRUCK, MOTORCYCLE
}

enum VehicleStatus {
    AVAILABLE, RENTED, MAINTENANCE
}

enum BookingStatus {
    PENDING, CONFIRMED, CANCELLED, COMPLETED
}

/* ---------- Core Domain ---------- */
abstract class Vehicle {
    private final long id;
    private final String make;
    private final String model;
    private final int year;
    private final VehicleType type;
    private VehicleStatus status = VehicleStatus.AVAILABLE;

    protected Vehicle(long id, String make, String model, int year, VehicleType type) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.type = type;
    }

    public long getId() { return id; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public VehicleType getType() { return type; }
    public VehicleStatus getStatus() { return status; }
    public void setStatus(VehicleStatus status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("%s-%d (%s %s)", type, id, make, model);
    }
}

class Car extends Vehicle {
    private final int seatCount;

    public Car(long id, String make, String model, int year, int seatCount) {
        super(id, make, model, year, VehicleType.CAR);
        this.seatCount = seatCount;
    }

    public int getSeatCount() { return seatCount; }
}

class Truck extends Vehicle {
    private final double payloadCapacityKg;

    public Truck(long id, String make, String model, int year, double payloadCapacityKg) {
        super(id, make, model, year, VehicleType.TRUCK);
        this.payloadCapacityKg = payloadCapacityKg;
    }

    public double getPayloadCapacityKg() { return payloadCapacityKg; }
}

class Motorcycle extends Vehicle {
    private final boolean hasSidecar;

    public Motorcycle(long id, String make, String model, int year, boolean hasSidecar) {
        super(id, make, model, year, VehicleType.MOTORCYCLE);
        this.hasSidecar = hasSidecar;
    }

    public boolean hasSidecar() { return hasSidecar; }
}

/* ---------- Customer ---------- */
class Customer {
    private final long id;
    private final String name;
    private final String email;
    private final String phone;

    public Customer(long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    @Override
    public String toString() {
        return String.format("Customer-%d (%s)", id, name);
    }
}

/* ---------- Booking ---------- */
class Booking {
    private final long id;
    private final Vehicle vehicle;
    private final Customer customer;
    private final LocalDate startDate;
    private final LocalDate endDate; // inclusive
    private BookingStatus status;

    public Booking(long id, Vehicle vehicle, Customer customer, LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate))
            throw new IllegalArgumentException("End date cannot be before start date");
        this.id = id;
        this.vehicle = vehicle;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = BookingStatus.PENDING;
    }

    public long getId() { return id; }
    public Vehicle getVehicle() { return vehicle; }
    public Customer getCustomer() { return customer; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }

    public long getDays() {
        return ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    @Override
    public String toString() {
        return String.format("Booking-%d [%s] %s -> %s (%s)", id, vehicle, startDate, endDate, status);
    }
}

/* ---------- Availability Calendar ---------- */
class AvailabilityCalendar {
    // For each vehicle, keep a sorted set of bookings (by start date)
    private final Map<Long, NavigableSet<Booking>> vehicleBookings = new HashMap<>();

    public synchronized boolean isAvailable(Vehicle vehicle, LocalDate start, LocalDate end) {
        NavigableSet<Booking> bookings = vehicleBookings.getOrDefault(vehicle.getId(), new TreeSet<>(Comparator.comparing(Booking::getStartDate)));
        for (Booking b : bookings) {
            if (b.getStatus() == BookingStatus.CANCELLED) continue;
            // Overlap detection
            if (!b.getEndDate().isBefore(start) && !b.getStartDate().isAfter(end)) {
                return false;
            }
        }
        return true;
    }

    public synchronized void addBooking(Booking booking) {
        vehicleBookings.computeIfAbsent(booking.getVehicle().getId(), k -> new TreeSet<>(Comparator.comparing(Booking::getStartDate)))
                .add(booking);
    }

    public synchronized void removeBooking(Booking booking) {
        NavigableSet<Booking> set = vehicleBookings.get(booking.getVehicle().getId());
        if (set != null) {
            set.remove(booking);
            if (set.isEmpty()) vehicleBookings.remove(booking.getVehicle().getId());
        }
    }

    public synchronized List<Booking> getBookingsForVehicle(Vehicle vehicle) {
        return new ArrayList<>(vehicleBookings.getOrDefault(vehicle.getId(), Collections.emptyNavigableSet()));
    }
}

/* ---------- Rental Agency ---------- */
class RentalAgency {
    private final Map<Long, Vehicle> vehicles = new HashMap<>();
    private final Map<Long, Customer> customers = new HashMap<>();
    private final Map<Long, Booking> bookings = new HashMap<>();

    private final AvailabilityCalendar calendar = new AvailabilityCalendar();

    private final AtomicLong vehicleIdSeq = new AtomicLong(1);
    private final AtomicLong customerIdSeq = new AtomicLong(1);
    private final AtomicLong bookingIdSeq = new AtomicLong(1);

    /* Vehicle Management */
    public Vehicle addCar(String make, String model, int year, int seatCount) {
        Car car = new Car(vehicleIdSeq.getAndIncrement(), make, model, year, seatCount);
        vehicles.put(car.getId(), car);
        return car;
    }

    public Vehicle addTruck(String make, String model, int year, double payloadKg) {
        Truck truck = new Truck(vehicleIdSeq.getAndIncrement(), make, model, year, payloadKg);
        vehicles.put(truck.getId(), truck);
        return truck;
    }

    public Vehicle addMotorcycle(String make, String model, int year, boolean hasSidecar) {
        Motorcycle moto = new Motorcycle(vehicleIdSeq.getAndIncrement(), make, model, year, hasSidecar);
        vehicles.put(moto.getId(), moto);
        return moto;
    }

    public Optional<Vehicle> findVehicleById(long id) {
        return Optional.ofNullable(vehicles.get(id));
    }

    public List<Vehicle> listAvailableVehicles(LocalDate start, LocalDate end, VehicleType... types) {
        Set<VehicleType> typeSet = new HashSet<>(Arrays.asList(types));
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : vehicles.values()) {
            if (!typeSet.isEmpty() && !typeSet.contains(v.getType())) continue;
            if (v.getStatus() != VehicleStatus.AVAILABLE) continue;
            if (calendar.isAvailable(v, start, end)) result.add(v);
        }
        return result;