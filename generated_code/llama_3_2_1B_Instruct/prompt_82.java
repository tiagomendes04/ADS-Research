```java
public class ParkingLot {
    private Slot[] slots;
    private int numSlots;

    public ParkingLot(int numSlots) {
        this.numSlots = numSlots;
        this.slots = new Slot[numSlots];
    }

    public boolean park(Car car) {
        for (int i = 0; i < numSlots; i++) {
            if (slots[i].isAvailable()) {
                slots[i].park(car);
                return true;
            }
        }
        return false;
    }

    public Car getCar(int slotNumber) {
        for (int i = 0; i < numSlots; i++) {
            if (slots[i].getCarNumber() == slotNumber) {
                return slots[i].getCar();
            }
        }
        return null;
    }

    public boolean isSlotAvailable(int slotNumber) {
        for (int i = 0; i < numSlots; i++) {
            if (slots[i].isAvailable(slotNumber)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSlotOccupied(int slotNumber) {
        for (int i = 0; i < numSlots; i++) {
            if (slots[i].isOccupied(slotNumber)) {
                return true;
            }
        }
        return false;
    }

    public void printSlots() {
        for (int i = 0; i < numSlots; i++) {
            System.out.println("Slot " + (i + 1) + ": " + slots[i].toString());
        }
    }
}
```

```java
public class Slot {
    private Car car;
    private int number;

    public Slot(int number) {
        this.number = number;
    }

    public boolean park(Car car) {
        if (this.car == null) {
            this.car = car;
            return true;
        }
        return false;
    }

    public boolean isAvailable(int slotNumber) {
        return this.car == null || this.car.getCarNumber() != slotNumber;
    }

    public boolean isOccupied(int slotNumber) {
        return this.car != null && this.car.getCarNumber() == slotNumber;
    }

    public Car getCar() {
        return this.car;
    }

    public int getNumber() {
        return this.number;
    }

    @Override
    public String toString() {
        return "Slot " + (this.number + 1) + ": " + this.car;
    }
}
```

```java
public class Car {
    private int number;

    public Car(int number) {
        this.number = number;
    }

    public Car getCarNumber() {
        return this.number;
    }
}
```