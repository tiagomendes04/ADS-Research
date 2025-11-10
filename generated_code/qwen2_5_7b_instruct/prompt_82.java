```java
import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private List<Car> cars = new ArrayList<>();

    public void park(Car car) {
        cars.add(car);
        System.out.println(car.getName() + " parked successfully.");
    }

    public void unpark(Car car) {
        cars.remove(car);
        System.out.println(car.getName() + " unparked successfully.");
    }

    public void calculatePayment(Car car) {
        long parkingTime = car.getParkingTime();
        double payment = parkingTime * 0.5; // $0.5 per hour
        System.out.println(car.getName() + " should pay $" + payment);
    }
}

class Car {
    private String name;
    private long parkingTime;

    public Car(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setParkingTime(long parkingTime) {
        this.parkingTime = parkingTime;
    }

    public long getParkingTime() {
        return parkingTime;
    }
}

public class Main {
    public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLot();
        Car car1 = new Car("Car1");
        Car car2 = new Car("Car2");

        car1.setParkingTime(4); // 4 hours
        car2.setParkingTime(2); // 2 hours

        parkingLot.park(car1);
        parkingLot.park(car2);

        parkingLot.calculatePayment(car1);
        parkingLot.calculatePayment(car2);

        parkingLot.unpark(car1);
        parkingLot.unpark(car2);
    }
}
```