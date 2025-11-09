**TrafficSimulation.java**
```java
import java.util.ArrayList;
import java.util.List;

public class TrafficSimulation {
    private List<Vehicle> vehicles;
    private List<Intersection> intersections;

    public TrafficSimulation() {
        this.vehicles = new ArrayList<>();
        this.intersections = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void addIntersection(Intersection intersection) {
        intersections.add(intersection);
    }

    public void simulate() {
        for (Vehicle vehicle : vehicles) {
            vehicle.update();
        }

        for (Intersection intersection : intersections) {
            intersection.update();
        }
    }
}
```

**Vehicle.java**
```java
import java.util.Random;

public abstract class Vehicle {
    protected String id;
    protected double speed;
    protected double position;
    protected Random random;

    public Vehicle(String id, double speed) {
        this.id = id;
        this.speed = speed;
        this.position = 0;
        this.random = new Random();
    }

    public abstract void update();

    public void move() {
        position += speed;
    }

    public String getId() {
        return id;
    }

    public double getPosition() {
        return position;
    }
}
```

**Car.java**
```java
public class Car extends Vehicle {
    public Car(String id, double speed) {
        super(id, speed);
    }

    @Override
    public void update() {
        move();
        System.out.println("Car " + getId() + " moved to position " + getPosition());
    }
}
```

**Truck.java**
```java
public class Truck extends Vehicle {
    public Truck(String id, double speed) {
        super(id, speed);
    }

    @Override
    public void update() {
        move();
        System.out.println("Truck " + getId() + " moved to position " + getPosition());
    }
}
```

**Intersection.java**
```java
public class Intersection {
    public void update() {
        System.out.println("Intersection updated");
    }
}
```

**TrafficLight.java**
```java
public class TrafficLight {
    public enum Color { RED, GREEN, YELLOW }

    private Color color;

    public TrafficLight(Color color) {
        this.color = color;
    }

    public void update() {
        System.out.println("Traffic light color is " + color);
    }

    public void changeColor(Color color) {
        this.color = color;
    }
}
```

**Main.java**
```java
public class Main {
    public static void main(String[] args) {
        TrafficSimulation simulation = new TrafficSimulation();

        Car car1 = new Car("Car1", 10);
        Car car2 = new Car("Car2", 15);
        Truck truck = new Truck("Truck1", 20);

        simulation.addVehicle(car1);
        simulation.addVehicle(car2);
        simulation.addVehicle(truck);

        TrafficLight trafficLight = new TrafficLight(TrafficLight.Color.GREEN);
        simulation.addIntersection(new Intersection());

        simulation.simulate();
    }
}
```