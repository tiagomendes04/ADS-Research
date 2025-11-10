```java
import java.util.ArrayList;
import java.util.List;

class Slot {
    private int slotNumber;
    private boolean isOccupied;

    public Slot(int slotNumber) {
        this.slotNumber = slotNumber;
        this.isOccupied = false;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }
}

class Car {
    private String licensePlate;
    private Slot currentSlot;

    public Car(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Slot getCurrentSlot() {
        return currentSlot;
    }

    public void setCurrentSlot(Slot currentSlot) {
        this.currentSlot = currentSlot;
    }
}

class PaymentTracker {
    private List<Car> cars;
    private List<Double> payments;

    public PaymentTracker() {
        this.cars = new ArrayList<>();
        this.payments = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addPayment(double payment) {
        payments.add(payment);
    }

    public List<Car> getCars() {
        return cars;
    }

    public List<Double> getPayments() {
        return payments;
    }
}
```
```java
import java.util.Scanner;

public class ParkingLotSimulation {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numSlots = 10; // number of parking slots
        List<Slot> slots = new ArrayList<>();
        for (int i = 1; i <= numSlots; i++) {
            slots.add(new Slot(i));
        }

        PaymentTracker paymentTracker = new PaymentTracker();

        while (true) {
            System.out.println("Parking Lot Simulation Menu:");
            System.out.println("1. Add Car");
            System.out.println("2. Park Car");
            System.out.println("3. Pay Bill");
            System.out.println("4. View Cars in Lot");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter car license plate: ");
                    String licensePlate = scanner.next();
                    Car car = new Car(licensePlate);
                    paymentTracker.addCar(car);
                    System.out.println("Car added successfully.");
                    break;
                case 2:
                    System.out.print("Enter slot number: ");
                    int slotNumber = scanner.nextInt();
                    Slot slot = slots.get(slotNumber - 1);
                    if (slot.isOccupied()) {
                        System.out.println("Slot already occupied.");
                    } else {
                        System.out.println("Parking car in slot " + slotNumber);
                        slot.setOccupied(true);
                        paymentTracker.addCar(car);
                    }
                    break;
                case 3:
                    System.out.println("View payment history:");
                    for (int i = 0; i < paymentTracker.getPayments().size(); i++) {
                        System.out.println("Payment " + (i + 1) + ": " + paymentTracker.getPayments().get(i));
                    }
                    break;
                case 4:
                    System.out.println("Cars in the lot:");
                    for (Car car1 : paymentTracker.getCars()) {
                        System.out.println(car1.getLicensePlate());
                    }
                    break;
                case 5:
                    System.out.println("Exiting the simulation.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
```