```java
import java.util.ArrayList;
import java.util.List;

public class TrafficSimulation {
    private List<Vehicle> vehicles;
    private int maxSpeed;
    private int roadLength;

    public TrafficSimulation(int maxSpeed, int roadLength) {
        this.maxSpeed = maxSpeed;
        this.roadLength = roadLength;
        this.vehicles = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void simulate() {
        for (Vehicle vehicle : vehicles) {
            vehicle.update(roadLength);
        }
    }

    public void displayStatus() {
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle.toString());
        }
    }
}
```

```java
public abstract class Vehicle {
    protected int id;
    protected int position;
    protected int speed;
    protected int acceleration;
    protected int deceleration;
    protected int followingDistance;

    public Vehicle(int id, int position, int speed, int acceleration, int deceleration, int followingDistance) {
        this.id = id;
        this.position = position;
        this.speed = speed;
        this.acceleration = acceleration;
        this.deceleration = deceleration;
        this.followingDistance = followingDistance;
    }

    public abstract void update(int roadLength);

    public int getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public int getSpeed() {
        return speed;
    }

    public String toString() {
        return "Vehicle " + id + " at position " + position + " with speed " + speed + " km/h";
    }
}
```

```java
public class Car extends Vehicle {
    public Car(int id, int position, int speed, int acceleration, int deceleration, int followingDistance) {
        super(id, position, speed, acceleration, deceleration, followingDistance);
    }

    @Override
    public void update(int roadLength) {
        position += speed;
        if (position > roadLength) {
            position = roadLength;
            speed = 0;
        }
    }
}
```

```java
public class Truck extends Vehicle {
    public Truck(int id, int position, int speed, int acceleration, int deceleration, int followingDistance) {
        super(id, position, speed, acceleration, deceleration, followingDistance);
    }

    @Override
    public void update(int roadLength) {
        position += speed;
        if (position > roadLength) {
            position = roadLength;
            speed = 0;
        }
    }
}
```

```java
public class Bike extends Vehicle {
    public Bike(int id, int position, int speed, int acceleration, int deceleration, int followingDistance) {
        super(id, position, speed, acceleration, deceleration, followingDistance);
    }

    @Override
    public void update(int roadLength) {
        position += speed;
        if (position > roadLength) {
            position = roadLength;
            speed = 0;
        }
    }
}
```