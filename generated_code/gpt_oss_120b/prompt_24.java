```java
package traffic;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

// ---------- Core Entities ----------
public abstract class Vehicle {
    private static final AtomicInteger ID_GEN = new AtomicInteger();
    private final int id;
    protected double speed; // m/s
    protected double position; // meters from start of road
    protected final Road road;

    public Vehicle(Road road, double speed) {
        this.id = ID_GEN.incrementAndGet();
        this.road = road;
        this.speed = speed;
        this.position = 0;
    }

    public int getId() { return id; }
    public double getSpeed() { return speed; }
    public double getPosition() { return position; }
    public Road getRoad() { return road; }

    public void move(double deltaTime) {
        double newPos = position + speed * deltaTime;
        if (newPos > road.getLength()) {
            newPos = road.getLength(); // stop at end
            speed = 0;
        }
        position = newPos;
    }

    public abstract String getType();
}

// Concrete vehicle types
class Car extends Vehicle {
    public Car(Road road, double speed) { super(road, speed); }
    @Override public String getType() { return "Car"; }
}

class Truck extends Vehicle {
    public Truck(Road road, double speed) { super(road, speed); }
    @Override public String getType() { return "Truck"; }
}

class Bus extends Vehicle {
    public Bus(Road road, double speed) { super(road, speed); }
    @Override public String getType() { return "Bus"; }
}

// ---------- Road & Traffic Elements ----------
class Road {
    private final double length; // meters
    private final List<TrafficLight> lights = new ArrayList<>();

    public Road(double length) { this.length = length; }

    public double getLength() { return length; }
    public List<TrafficLight> getLights() { return lights; }

    public void addTrafficLight(TrafficLight light) { lights.add(light); }
}

enum LightState { GREEN, YELLOW, RED }

class TrafficLight {
    private final double position; // meters from start
    private LightState state = LightState.RED;
    private double timer = 0; // seconds

    // durations in seconds
    private final double greenDuration;
    private final double yellowDuration;
    private final double redDuration;

    public TrafficLight(double position, double green, double yellow, double red) {
        this.position = position;
        this.greenDuration = green;
        this.yellowDuration = yellow;
        this.redDuration = red;
    }

    public double getPosition() { return position; }
    public LightState getState() { return state; }

    public void update(double deltaTime) {
        timer += deltaTime;
        switch (state) {
            case GREEN:
                if (timer >= greenDuration) { state = LightState.YELLOW; timer = 0; }
                break;
            case YELLOW:
                if (timer >= yellowDuration) { state = LightState.RED; timer = 0; }
                break;
            case RED:
                if (timer >= redDuration) { state = LightState.GREEN; timer = 0; }
                break;
        }
    }
}

// ---------- Rules ----------
interface Rule {
    void apply(Vehicle v, double deltaTime);
}

class SpeedLimitRule implements Rule {
    private final double maxSpeed; // m/s

    public SpeedLimitRule(double maxSpeed) { this.maxSpeed = maxSpeed; }

    @Override
    public void apply(Vehicle v, double deltaTime) {
        if (v.getSpeed() > maxSpeed) {
            v.speed = maxSpeed;
        }
    }
}

class StopAtRedRule implements Rule {
    private final double safeDistance = 2.0; // meters before light

    @Override
    public void apply(Vehicle v, double deltaTime) {
        Road road = v.getRoad();
        for (TrafficLight light : road.getLights()) {
            if (light.getState() == LightState.RED) {
                double distanceToLight = light.getPosition() - v.getPosition();
                if (distanceToLight > 0 && distanceToLight <= safeDistance + v.getSpeed() * deltaTime) {
                    // stop before the light
                    v.position = Math.max(v.getPosition(), light.getPosition() - safeDistance);
                    v.speed = 0;
                }
            }
        }
    }
}

// ---------- Simulation Engine ----------
class Simulation {
    private final List<Vehicle> vehicles = new ArrayList<>();
    private final List<Rule> rules = new ArrayList<>();
    private final List<TrafficLight> lights = new ArrayList<>();
    private final double timeStep; // seconds

    public Simulation(double timeStep) { this.timeStep = timeStep; }

    public void addVehicle(Vehicle v) { vehicles.add(v); }
    public void addRule(Rule r) { rules.add(r); }
    public void addTrafficLight(TrafficLight l) { lights.add(l); }

    public void run(double totalTime) {
        int steps = (int) (totalTime / timeStep);
        for (int i = 0; i < steps; i++) {
            // update lights
            for (TrafficLight l : lights) l.update(timeStep);

            // apply rules
            for (Vehicle v : vehicles) {
                for (Rule r : rules) r.apply(v, timeStep);
            }

            // move vehicles
            for (Vehicle v : vehicles) v.move(timeStep);
        }
    }

    public List<Vehicle> getVehicles() { return Collections.unmodifiableList(vehicles); }
}

// ---------- Demo ----------
public class Main {
    public static void main(String[] args) {
        Road road = new Road(1000); // 1 km
        TrafficLight light = new TrafficLight(300, 20, 3, 15);
        road.addTrafficLight(light);

        Simulation sim = new Simulation(0.5); // 0.5 sec steps
        sim.addTrafficLight(light);
        sim.addRule(new SpeedLimitRule(25)); // ~90 km/h
        sim.addRule(new StopAtRedRule());

        sim.addVehicle(new Car(road, 30));
        sim.addVehicle(new Truck(road, 22));
        sim.addVehicle(new Bus(road, 25));

        sim.run(120); // simulate 2 minutes

        for (Vehicle v : sim.getVehicles()) {
            System.out.printf("%s #%d - Position: %.1f m, Speed: %.1f m/s%n",
                    v.getType(), v.getId(), v.getPosition(), v.getSpeed());
        }
    }
}
```