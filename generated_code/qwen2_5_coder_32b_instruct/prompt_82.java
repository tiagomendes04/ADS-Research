```java
class ParkingLot {
    private int totalSlots;
    private int availableSlots;
    private Map<String, Car> parkedCars;

    public ParkingLot(int totalSlots) {
        this.totalSlots = totalSlots;
        this.availableSlots = totalSlots;
        this.parkedCars = new HashMap<>();
    }

    public boolean parkCar(Car car) {
        if (availableSlots > 0) {
            parkedCars.put(car.getLicensePlate(), car);
            availableSlots--;
            return true;
        }
        return false;
    }

    public boolean unparkCar(String licensePlate) {
        if (parkedCars.containsKey(licensePlate)) {
            parkedCars.remove(licensePlate);
            availableSlots++;
            return true;
        }
        return false;
    }

    public int getAvailableSlots() {
        return availableSlots;
    }
}

class Car {
    private String licensePlate;
    private String ownerName;

    public Car(String licensePlate, String ownerName) {
        this.licensePlate = licensePlate;
        this.ownerName = ownerName;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getOwnerName() {
        return ownerName;
    }
}

class PaymentTracker {
    private Map<String, Double> payments;

    public PaymentTracker() {
        this.payments = new HashMap<>();
    }

    public void addPayment(String licensePlate, double amount) {
        payments.put(licensePlate, payments.getOrDefault(licensePlate, 0.0) + amount);
    }

    public double getTotalPaymentForCar(String licensePlate) {
        return payments.getOrDefault(licensePlate, 0.0);
    }

    public double getTotalRevenue() {
        return payments.values().stream().mapToDouble(Double::doubleValue).sum();
    }
}
```