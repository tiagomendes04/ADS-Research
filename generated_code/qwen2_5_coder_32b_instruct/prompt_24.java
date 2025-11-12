```java
import java.util.ArrayList;
import java.util.List;

class Road {
    private List<Vehicle> vehicles;

    public Road() {
        this.vehicles = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void simulateTraffic(int timeSteps) {
        for (int i = 0; i < timeSteps; i++) {
            for (Vehicle vehicle : vehicles) {
                vehicle.move(this);
            }
        }
    }
}

abstract class Vehicle {
    protected int position;
    protected int speed;

    public Vehicle(int position, int speed) {
        this.position = position;
        this.speed = speed;
    }

    public abstract void move(Road road);

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}

class Car extends Vehicle {
    private static final int MAX_SPEED = 10;
    private static final int MIN_DISTANCE = 2;

    public Car(int position, int speed) {
        super(position, speed);
    }

    @Override
    public void move(Road road) {
        int nextPosition = position + speed;
        for (Vehicle otherVehicle : road.getVehicles()) {
            if (otherVehicle != this && nextPosition >= otherVehicle.getPosition() - MIN_DISTANCE) {
                setSpeed(Math.max(0, speed - 1));
                nextPosition = position + speed;
            }
        }
        setPosition(nextPosition);
        if (speed < MAX_SPEED) {
            setSpeed(speed + 1);
        }
    }
}

class Truck extends Vehicle {
    private static final int MAX_SPEED = 7;
    private static final int MIN_DISTANCE = 5;

    public Truck(int position, int speed) {
        super(position, speed);
    }

    @Override
    public void move(Road road) {
        int nextPosition = position + speed;
        for (Vehicle otherVehicle : road.getVehicles()) {
            if (otherVehicle != this && nextPosition >= otherVehicle.getPosition() - MIN_DISTANCE) {
                setSpeed(Math.max(0, speed - 1));
                nextPosition = position + speed;
            }
        }
        setPosition(nextPosition);
        if (speed < MAX_SPEED) {
            setSpeed(speed + 1);
        }
    }
}
```