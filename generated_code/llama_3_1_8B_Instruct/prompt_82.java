```java
// Slot.java
public class Slot {
    private int slotNumber;
    private boolean isEmpty;
    private Car parkedCar;

    public Slot(int slotNumber) {
        this.slotNumber = slotNumber;
        this.isEmpty = true;
        this.parkedCar = null;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void parkCar(Car car) {
        if (isEmpty) {
            this.parkedCar = car;
            isEmpty = false;
        }
    }

    public void removeCar() {
        if (!isEmpty) {
            this.parkedCar = null;
            isEmpty = true;
        }
    }

    public Car getParkedCar() {
        return parkedCar;
    }
}

// Car.java
public class Car {
    private String licensePlate;
    private double payment;

    public Car(String licensePlate) {
        this.licensePlate = licensePlate;
        this.payment = 0;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public double getPayment() {
        return payment;
    }

    public void pay(double amount) {
        payment += amount;
    }
}

// ParkingLot.java
import java.util.ArrayList;

public class ParkingLot {
    private ArrayList<Slot> slots;

    public ParkingLot(int numSlots) {
        this.slots = new ArrayList<>();
        for (int i = 1; i <= numSlots; i++) {
            slots.add(new Slot(i));
        }
    }

    public void parkCar(Car car, int slotNumber) {
        Slot slot = getSlot(slotNumber);
        if (slot != null) {
            slot.parkCar(car);
            System.out.println("Car " + car.getLicensePlate() + " parked at slot " + slot.getSlotNumber());
        } else {
            System.out.println("Slot " + slotNumber + " is empty");
        }
    }

    public Slot getSlot(int slotNumber) {
        for (Slot slot : slots) {
            if (slot.getSlotNumber() == slotNumber && !slot.isEmpty()) {
                return slot;
            }
        }
        return null;
    }

    public void removeCar(int slotNumber) {
        Slot slot = getSlot(slotNumber);
        if (slot != null) {
            slot.removeCar();
            System.out.println("Car removed from slot " + slotNumber);
        } else {
            System.out.println("Slot " + slotNumber + " is empty");
        }
    }

    public void printPayment(int slotNumber) {
        Slot slot = getSlot(slotNumber);
        if (slot != null) {
            Car car = slot.getParkedCar();
            if (car != null) {
                System.out.println("Car " + car.getLicensePlate() + " has paid " + car.getPayment());
            } else {
                System.out.println("Slot " + slotNumber + " is empty");
            }
        } else {
            System.out.println("Slot " + slotNumber + " does not exist");
        }
    }
}

// ParkingLotMain.java
public class ParkingLotMain {
    public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLot(10);
        Car car1 = new Car("ABC123");
        Car car2 = new Car("DEF456");

        parkingLot.parkCar(car1, 1);
        parkingLot.parkCar(car2, 2);

        parkingLot.printPayment(1);
        parkingLot.printPayment(2);

        car1.pay(10);
        car2.pay(20);

        parkingLot.printPayment(1);
        parkingLot.printPayment(2);

        parkingLot.removeCar(1);
        parkingLot.removeCar(2);
    }
}
```