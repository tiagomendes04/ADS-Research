```java
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class Driver {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private final int id;
    private final String name;
    private boolean available = true;
    private final String vehicle;

    public Driver(String name, String vehicle) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.name = name;
        this.vehicle = vehicle;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public String getVehicle() { return vehicle; }

    @Override
    public String toString() {
        return String.format("Driver{id=%d, name='%s', vehicle='%s', available=%b}", id, name, vehicle, available);
    }
}

class Rider {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private final int id;
    private final String name;

    public Rider(String name) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    @Override
    public String