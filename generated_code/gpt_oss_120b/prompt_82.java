```java
// VehicleType.java
public enum VehicleType {
    MOTORCYCLE,
    CAR,
    TRUCK,
    VAN
}
```

```java
// Car.java
import java.time.LocalDateTime;
import java.util.UUID;

public class Car {
    private final String licensePlate;
    private final VehicleType type;
    private final String ownerName;

    public Car(String licensePlate, VehicleType type, String ownerName) {
        this.licensePlate = licensePlate;
        this.type = type;
        this.ownerName = ownerName;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getType() {
        return type;
    }

    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %s", licensePlate, type, ownerName);
    }
}
```

```java
// ParkingSlot.java
public class ParkingSlot {
    private final int id;
    private final VehicleType allowedType;
    private boolean occupied;
    private Car parkedCar;

    public ParkingSlot(int id, VehicleType allowedType) {
        this.id = id;
        this.allowedType = allowedType;
        this.occupied = false;
    }

    public int getId() {
        return id;
    }

    public VehicleType getAllowedType() {
        return allowedType;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public Car getParkedCar() {
        return parkedCar;
    }

    public boolean parkCar(Car car) {
        if (occupied) return false;
        if (car.getType() != allowedType) return false;
        this.parkedCar = car;
        this.occupied = true;
        return true;
    }

    public Car removeCar() {
        if (!occupied) return null;
        Car car = parkedCar;
        parkedCar = null;
        occupied = false;
        return car;
    }
}
```

```java
// Ticket.java
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {
    private final UUID ticketId;
    private final Car car;
    private final int slotId;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double amountDue;
    private boolean paid;

    public Ticket(Car car, int slotId) {
        this.ticketId = UUID.randomUUID();
        this.car = car;
        this.slotId = slotId;
        this.entryTime = LocalDateTime.now();
        this.paid = false;
    }

    public UUID getTicketId() {
        return ticketId;
    }

    public Car getCar() {
        return car;
    }

    public int getSlotId() {
        return slotId;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public boolean isPaid() {
        return paid;
    }

    public void closeTicket(double ratePerHour) {
        this.exitTime = LocalDateTime.now();
        Duration duration = Duration.between(entryTime, exitTime);
        long minutes = duration.toMinutes();
        double hours = Math.ceil(minutes / 60.0);
        this.amountDue = hours * ratePerHour;
    }

    public void markPaid() {
        this.paid = true;
    }

    @Override
    public String toString() {
        return String.format("Ticket[%s] Car:%s Slot:%d Entry:%s Exit:%s Due:%.2f Paid:%b",
                ticketId, car.getLicensePlate(), slotId,
                entryTime, exitTime == null ? "N/A" : exitTime,
                amountDue, paid);
    }
}
```

```java
// PaymentProcessor.java
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PaymentProcessor {
    private final Map<UUID, Double> payments = new HashMap<>();

    public boolean processPayment(Ticket ticket, double amount) {
        if (ticket.isPaid()) return false;
        if (Math.abs(amount - ticket.getAmountDue()) > 0.01) return false;
        payments.put(ticket.getTicketId(), amount);
        ticket.markPaid();
        return true;
    }

    public double getTotalCollected() {
        return payments.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    public Map<UUID, Double> getAllPayments() {
        return new HashMap<>(payments);
    }
}
```

```java
// ParkingLot.java
import java.util.*;
import java.util.stream.Collectors;

public class ParkingLot {
    private final Map<Integer, ParkingSlot> slots = new HashMap<>();
    private final Map<String, Ticket> activeTickets = new HashMap<>();
    private final PaymentProcessor paymentProcessor = new PaymentProcessor();
    private final double ratePerHour;

    public ParkingLot(int totalSlots, double ratePerHour) {
        this.ratePerHour = ratePerHour;
        int id = 1;
        for (int i = 0; i < totalSlots; i++) {
            VehicleType type = VehicleType.CAR; // default type
            if (i % 10 == 0) type = VehicleType.MOTORCYCLE;
            else if (i % 10 == 9) type = VehicleType.TRUCK;
            slots.put(id, new ParkingSlot(id, type));
            id++;
        }
    }

    public Optional<Ticket> parkCar(Car car) {
        Optional<ParkingSlot> slotOpt = slots.values().stream()
                .filter(s -> !s.isOccupied() && s.getAllowedType() == car.getType())
                .findFirst();

        if (!slotOpt.isPresent()) return Optional.empty();

        ParkingSlot slot = slotOpt.get();
        if (!slot.parkCar(car)) return Optional.empty();

        Ticket ticket = new Ticket(car, slot.getId());
        activeTickets.put(car.getLicensePlate(), ticket);
        return Optional.of(ticket);
    }

    public Optional<Ticket> exitCar(String licensePlate) {
        Ticket ticket = activeTickets.get(licensePlate);
        if (ticket == null) return Optional.empty();

        ParkingSlot slot = slots.get(ticket.getSlotId());
        if (slot == null) return Optional.empty();

        slot.removeCar();
        ticket.closeTicket(ratePerHour);
        return Optional.of(ticket);
    }

    public boolean payTicket(UUID ticketId, double amount) {
        Ticket ticket = activeTickets.values().stream()
                .filter(t -> t.getTicketId().equals(ticketId))
                .findFirst()
                .orElse(null);
        if (ticket == null) return false;
        boolean success = paymentProcessor.processPayment(ticket, amount);
        if (success) activeTickets.remove(ticket.getCar().getLicensePlate());
        return success;
    }

    public List<ParkingSlot> getAvailableSlots(VehicleType type) {
        return slots.values().stream()
                .filter(s -> !s.isOccupied() && s.getAllowedType() == type)
                .collect(Collectors.toList());
    }

    public double getTotalRevenue() {
        return paymentProcessor.getTotalCollected();
    }

    public Map<UUID, Double> getAllPayments() {
        return paymentProcessor.getAllPayments();
    }
}
```

```java
// Main.java
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ParkingLot lot = new ParkingLot(30, 5.0); // 30 slots, $5 per hour

        Car car1 = new Car("ABC-123", VehicleType.CAR, "John Doe");
        Car bike1 = new Car("XYZ-999", VehicleType.MOTORCYCLE, "Alice Smith");
        Car truck1 = new Car("TRK-777", VehicleType.TRUCK, "Bob Builder");

        // Park vehicles
        lot.parkCar(car1).ifPresent(t -> System.out.println("Parked: " + t));
        lot.parkCar(bike1).ifPresent(t -> System.out.println("Parked: " + t));
        lot.parkCar(truck1).ifPresent(t -> System.out.println("Parked: " + t));

        // Simulate time passing
        Thread.sleep(2000);

        // Exit car1
        Ticket ticketCar1 = lot.exitCar(car1.getLicensePlate()).orElseThrow();
        System.out.println("Exit ticket: " + ticketCar1);
        lot.payTicket(ticketCar1.getTicketId(), ticketCar1.getAmountDue());

        // Exit bike1
        Ticket ticketBike1 = lot.exitCar(bike1.getLicensePlate()).orElseThrow();
        System.out.println("Exit ticket: " + ticketBike1);
        lot.payTicket(ticketBike1.getTicketId(), ticketBike1.getAmountDue());

        // Revenue
        System.out.println("Total revenue: $" + lot.getTotalRevenue());
    }
}
```