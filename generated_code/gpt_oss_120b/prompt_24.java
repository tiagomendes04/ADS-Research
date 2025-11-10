```java
package traffic;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

// ------------------------------------------------------------
// Core entities
// ------------------------------------------------------------
abstract class Vehicle {
    private static final AtomicInteger ID_GEN = new AtomicInteger();
    protected final int id;
    protected double position; // meters from start of road
    protected double speed;    // m/s
    protected final double maxSpeed;
    protected final double length; // meters

    public Vehicle(double maxSpeed, double length) {
        this.id = ID_GEN.incrementAndGet();
        this.maxSpeed = maxSpeed;
        this.length = length;
        this.position = 0;
        this.speed = 0;
    }

    public int getId() { return id; }
    public double getPosition() { return position; }
    public double getSpeed() { return speed; }
    public double getLength() { return length; }

    /** Called each simulation tick */
    public void update(Road road, TrafficLight light, double deltaTime) {
        double desiredSpeed = computeDesiredSpeed(road, light);
        accelerateTowards(desiredSpeed, deltaTime);
        move(deltaTime);
    }

    protected abstract double computeDesiredSpeed(Road road, TrafficLight light);

    private void accelerateTowards(double targetSpeed, double dt) {
        double accel = getAcceleration();
        if (speed < targetSpeed) {
            speed = Math.min(speed + accel * dt, targetSpeed);
        } else {
            speed = Math.max(speed - accel * dt, targetSpeed);
        }
        speed = Math.min(speed, maxSpeed);
    }

    protected double getAcceleration() {
        return 2.5; // m/s² default
    }

    private void move(double dt) {
        position += speed * dt;
    }

    @Override
    public String toString() {
        return String.format("%s{id=%d, pos=%.1f, sp=%.1f}",
                getClass().getSimpleName(), id, position, speed);
    }
}

// ------------------------------------------------------------
class Car extends Vehicle {
    public Car() {
        super(30.0, 4.5); // max 30 m/s (~108 km/h), length 4.5 m
    }

    @Override
    protected double computeDesiredSpeed(Road road, TrafficLight light) {
        double safeDist = road.getSafeDistance(this);
        Vehicle front = road.vehicleAhead(this);
        double limit = maxSpeed;

        if (front != null) {
            double gap = front.getPosition() - front.getLength() - this.position;
            if (gap < safeDist) {
                limit = Math.min(limit, front.getSpeed() * 0.8);
            }
        }

        if (light != null && light.isRed()) {
            double distanceToLight = light.getPosition() - this.position;
            if (distanceToLight > 0 && distanceToLight < safeDist) {
                limit = 0;
            }
        }

        return limit;
    }
}

// ------------------------------------------------------------
class Truck extends Vehicle {
    public Truck() {
        super(22.0, 12.0); // max 22 m/s (~79 km/h), length 12 m
    }

    @Override
    protected double getAcceleration() {
        return 1.2; // slower acceleration
    }

    @Override
    protected double computeDesiredSpeed(Road road, TrafficLight light) {
        double safeDist = road.getSafeDistance(this) * 1.5; // trucks keep larger gap
        Vehicle front = road.vehicleAhead(this);
        double limit = maxSpeed;

        if (front != null) {
            double gap = front.getPosition() - front.getLength() - this.position;
            if (gap < safeDist) {
                limit = Math.min(limit, front.getSpeed() * 0.7);
            }
        }

        if (light != null && light.isRed()) {
            double distanceToLight = light.getPosition() - this.position;
            if (distanceToLight > 0 && distanceToLight < safeDist) {
                limit = 0;
            }
        }

        return limit;
    }
}

// ------------------------------------------------------------
class Bus extends Vehicle {
    public Bus() {
        super(25.0, 10.0); // max 25 m/s (~90 km/h), length 10 m
    }

    @Override
    protected double computeDesiredSpeed(Road road, TrafficLight light) {
        double safeDist = road.getSafeDistance(this);
        Vehicle front = road.vehicleAhead(this);
        double limit = maxSpeed;

        if (front != null) {
            double gap = front.getPosition() - front.getLength() - this.position;
            if (gap < safeDist) {
                limit = Math.min(limit, front.getSpeed() * 0.75);
            }
        }

        if (light != null && light.isRed()) {
            double distanceToLight = light.getPosition() - this.position;
            if (distanceToLight > 0 && distanceToLight < safeDist) {
                limit = 0;
            }
        }

        return limit;
    }
}

// ------------------------------------------------------------
class TrafficLight {
    public enum State { GREEN, YELLOW, RED }

    private final double position; // meters along road
    private State state;
    private double timer; // seconds remaining in current state

    public TrafficLight(double position, State initState, double initTimer) {
        this.position = position;
        this.state = initState;
        this.timer = initTimer;
    }

    public double getPosition() { return position; }

    public boolean isRed() { return state == State.RED; }

    public void update(double deltaTime) {
        timer -= deltaTime;
        if (timer <= 0) {
            switch (state) {
                case GREEN -> setState(State.YELLOW, 3);
                case YELLOW -> setState(State.RED, 5);
                case RED -> setState(State.GREEN, 8);
            }
        }
    }

    private void setState(State newState, double duration) {
        this.state = newState;
        this.timer = duration;
    }

    @Override
    public String toString() {
        return String.format("TrafficLight{pos=%.1f, state=%s, timer=%.1f}",
                position, state, timer);
    }
}

// ------------------------------------------------------------
class Road {
    private final double length; // meters
    private final List<Vehicle> vehicles = new ArrayList<>();

    public Road(double length) {
        this.length = length;
    }

    public void addVehicle(Vehicle v) {
        vehicles.add(v);
    }

    public List<Vehicle> getVehicles() {
        return Collections.unmodifiableList(vehicles);
    }

    public Vehicle vehicleAhead(Vehicle v) {
        return vehicles.stream()
                .filter(o -> o.getPosition() > v.getPosition())
                .min(Comparator.comparingDouble(Vehicle::getPosition))
                .orElse(null);
    }

    public double getSafeDistance(Vehicle v) {
        // Simple rule: speed (m/s) * 2 + 5 meters
        return v.getSpeed() * 2 + 5;
    }

    public void removeExitedVehicles() {
        vehicles.removeIf(v -> v.getPosition() > length);
    }
}

// ------------------------------------------------------------
class Simulation {
    private final Road road;
    private final List<TrafficLight> lights = new ArrayList<>();
    private final double tick; // seconds per simulation step

    public Simulation(double roadLength, double tick) {
        this.road = new Road(roadLength);
        this.tick = tick;
    }

    public void addVehicle(Vehicle v) {
        road.addVehicle(v);
    }

    public void addTrafficLight(TrafficLight light) {
        lights.add(light);
    }

    public void run(int steps) {
        for (int i = 0; i < steps; i++) {
            step();
        }
    }

    private void step() {
        // update lights first
        for (TrafficLight tl : lights) {
            tl.update(tick);
        }

        // update each vehicle
        for (Vehicle v : road.getVehicles()) {
            TrafficLight relevant = findRelevantLight(v);
            v.update(road, relevant, tick);
        }

        // clean up
        road.removeExitedVehicles();
    }

    private TrafficLight findRelevantLight(Vehicle v) {
        return lights.stream()
                .filter(tl -> tl.getPosition() > v.getPosition())
                .min(Comparator.comparingDouble(TrafficLight::getPosition))
                .orElse(null);
    }

    public void printState() {
        System.out.println("=== Simulation State ===");
        for (Vehicle v : road.getVehicles()) {
            System.out.println(v);
        }
        for (TrafficLight tl : lights) {
            System.out.println(tl);
        }
        System.out.println();
    }
}

// ------------------------------------------------------------
// Example usage (can be placed in a separate file with main)
// ------------------------------------------------------------
public class Main {
    public static void main(String[] args) {
        Simulation sim = new Simulation(1000, 0.5); // 1 km road, 0.5 s tick

        // add vehicles
        sim.addVehicle(new Car());
        sim.addVehicle(new Truck());
        sim.addVehicle(new Bus