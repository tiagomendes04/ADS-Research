```java
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RideSharingApp {

    // -------------------- Models --------------------
    static class Driver {
        private static final AtomicInteger ID_GEN = new AtomicInteger(1);
        private final int id;
        private final String name;
        private boolean available = true;
        private double rating = 0.0;
        private int ratingCount = 0;

        Driver(String name) {
            this.id = ID_GEN.getAndIncrement();
            this.name = name;
        }

        int getId() { return id; }
        String getName() { return name; }
        boolean isAvailable() { return available; }
        void setAvailable(boolean available) { this.available = available; }

        void addRating(double r) {
            rating = (rating * ratingCount + r) / (++ratingCount);
        }

        double getRating() { return rating; }

        @Override
        public String toString() {
            return String.format("Driver{id=%d, name='%s', available=%s, rating=%.2f}",
                    id, name, available, rating);
        }
    }

    static class Rider {
        private static final AtomicInteger ID_GEN = new AtomicInteger(1);
        private final int id;
        private final String name;

        Rider(String name) {
            this.id = ID_GEN.getAndIncrement();
            this.name = name;
        }

        int getId() { return id; }
        String getName() { return name; }

        @Override
        public String toString() {
            return String.format("Rider{id=%d, name='%s'}", id, name);
        }
    }

    static class Ride {
        enum Status { REQUESTED, ASSIGNED, IN_PROGRESS, COMPLETED, CANCELED }

        private static final AtomicInteger ID_GEN = new AtomicInteger(1);
        private final int id;
        private final Rider rider;
        private Driver driver;
        private final String origin;
        private final String destination;
        private Status status = Status.REQUESTED;

        Ride(Rider rider, String origin, String destination) {
            this.id = ID_GEN.getAndIncrement();
            this.rider = rider;
            this.origin = origin;
            this.destination = destination;
        }

        int getId() { return id; }
        Rider getRider() { return rider; }
        Driver getDriver() { return driver; }
        String getOrigin() { return origin; }
        String getDestination() { return destination; }
        Status getStatus() { return status; }

        void assignDriver(Driver driver) {
            this.driver = driver;
            this.status = Status.ASSIGNED;
            driver.setAvailable(false);
        }

        void start() { this.status = Status.IN_PROGRESS; }
        void complete() {
            this.status = Status.COMPLETED;
            if (driver != null) driver.setAvailable(true);
        }
        void cancel() {
            this.status = Status.CANCELED;
            if (driver != null) driver.setAvailable(true);
        }

        @Override
        public String toString() {
            return String.format(
                    "Ride{id=%d, rider=%s, driver=%s, from='%s', to='%s', status=%s}",
                    id,
                    rider.getName(),
                    driver != null ? driver.getName() : "none",
                    origin,
                    destination,
                    status);
        }
    }

    // -------------------- Service --------------------
    static class RideService {
        private final Map<Integer, Driver> drivers = new HashMap<>();
        private final Map<Integer, Rider> riders = new HashMap<>();
        private final Map<Integer, Ride> rides = new HashMap<>();

        // Driver management
        Driver registerDriver(String name) {
            Driver d = new Driver(name);
            drivers.put(d.getId(), d);
            return d;
        }

        Driver getDriver(int id) { return drivers.get(id); }

        List<Driver> listDrivers() { return new ArrayList<>(drivers.values()); }

        // Rider management
        Rider registerRider(String name) {
            Rider r = new Rider(name);
            riders.put(r.getId(), r);
            return r;
        }

        Rider getRider(int id) { return riders.get(id); }

        List<Rider> listRiders() { return new ArrayList<>(riders.values()); }

        // Ride workflow
        Ride requestRide(int riderId, String from, String to) {
            Rider rider = riders.get(riderId);
            if (rider == null) throw new IllegalArgumentException("Rider not found");
            Ride ride = new Ride(rider, from, to);
            rides.put(ride.getId(), ride);
            assignDriver(ride);
            return ride;
        }

        private void assignDriver(Ride ride) {
            Optional<Driver> opt = drivers.values().stream()
                    .filter(Driver::isAvailable)
                    .min(Comparator.comparingDouble(Driver::getRating).reversed());
            opt.ifPresent(driver -> ride.assignDriver(driver));
        }

        Ride getRide(int id) { return rides.get(id); }

        List<Ride> listRides() { return new ArrayList<>(rides.values()); }

        void startRide(int rideId) {
            Ride r = rides.get(rideId);
            if (r == null) throw new IllegalArgumentException("Ride not found");
            if (r.getStatus() != Ride.Status.ASSIGNED) throw new IllegalStateException("Ride not assigned");
            r.start();
        }

        void completeRide(int rideId, double driverRating) {
            Ride r = rides.get(rideId);
            if (r == null) throw new IllegalArgumentException("Ride not found");
            if (r.getStatus() != Ride.Status.IN_PROGRESS) throw new IllegalStateException("Ride not in progress");
            r.complete();
            if (r.getDriver() != null) r.getDriver().addRating(driverRating);
        }

        void cancelRide(int rideId) {
            Ride r = rides.get(rideId);
            if (r == null) throw new IllegalArgumentException("Ride not found");
            r.cancel();
        }
    }

    // -------------------- CLI --------------------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RideService service = new RideService();

        while (true) {
            System.out.println("\n--- Ride Sharing Menu ---");
            System.out.println("1. Register Driver");
            System.out.println("2. Register Rider");
            System.out.println("3. Request Ride");
            System.out.println("4. Start Ride");
            System.out.println("5. Complete Ride");
            System.out.println("6. Cancel Ride");
            System.out.println("7. List Drivers");
            System.out.println("8. List Riders");
            System.out.println("9. List Rides");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            int choice = Integer.parseInt(sc.nextLine().trim());
            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Driver name: ");
                        String name = sc.nextLine();
                        Driver d = service.registerDriver(name);
                        System.out.println("Registered: " + d);
                    }
                    case 2 -> {
                        System.out.print("Rider name: ");
                        String name = sc.nextLine();
                        Rider r = service.registerRider(name);
                        System.out.println("Registered: " + r);
                    }
                    case 3 -> {
                        System.out.print("Rider ID: ");
                        int riderId = Integer.parseInt(sc.nextLine());
                        System.out.print("From: ");
                        String from = sc.nextLine();
                        System.out.print("To: ");
                        String to = sc.nextLine();
                        Ride ride = service.requestRide(riderId, from, to);
                        System.out.println("Ride requested: " + ride);
                    }
                    case 4 -> {
                        System.out.print("Ride ID to start: ");
                        int rideId = Integer.parseInt(sc.nextLine());
                        service.startRide(rideId);
                        System.out.println("Ride started.");
                    }
                    case 5 -> {
                        System.out.print("Ride ID to complete: ");
                        int rideId = Integer.parseInt(sc.nextLine());
                        System.out.print("Rate driver (0-5): ");
                        double rating = Double.parseDouble(sc.nextLine());
                        service.completeRide(rideId, rating);
                        System.out.println("Ride completed.");
                    }
                    case 6 -> {
                        System.out.print("Ride ID to cancel: ");
                        int rideId = Integer.parseInt(sc.nextLine());
                        service.cancelRide(rideId);
                        System.out.println("Ride canceled.");
                    }
                    case 7 -> {
                        System.out.println("Drivers:");
                        service.listDrivers().forEach(System.out::println);
                    }
                    case 8 -> {
                        System.out.println("Riders:");
                        service.listRiders().forEach(System.out::println);
                    }
                    case 9 -> {
                        System.out.println("Rides:");
                        service.listRides().forEach(System.out::println);
                    }
                    case 0 -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
```