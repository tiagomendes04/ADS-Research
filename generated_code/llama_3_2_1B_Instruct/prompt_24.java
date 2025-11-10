```java
import java.util.ArrayList;
import java.util.List;

class Vehicle {
    int id;
    int speed;
    int position;

    public Vehicle(int id, int speed, int position) {
        this.id = id;
        this.speed = speed;
        this.position = position;
    }

    public void move() {
        position += speed;
    }
}

class TrafficSimulator {
    List<Vehicle> vehicles;

    public TrafficSimulator() {
        vehicles = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void removeVehicle(int id) {
        vehicles.removeIf(v -> v.id == id);
    }

    public void simulate(int time) {
        for (int i = 0; i < time; i++) {
            for (Vehicle vehicle : vehicles) {
                vehicle.move();
                if (vehicle.position > 1000) {
                    System.out.println("Vehicle " + vehicle.id + " stopped at position " + vehicle.position);
                    removeVehicle(vehicle.id);
                }
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        TrafficSimulator simulator = new TrafficSimulator();
        Vehicle vehicle1 = new Vehicle(1, 10, 0);
        Vehicle vehicle2 = new Vehicle(2, 15, 500);
        Vehicle vehicle3 = new Vehicle(3, 20, 1000);

        simulator.addVehicle(vehicle1);
        simulator.addVehicle(vehicle2);
        simulator.addVehicle(vehicle3);

        simulator.simulate(100);
    }
}
```